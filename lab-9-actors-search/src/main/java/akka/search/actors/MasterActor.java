package akka.search.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.search.exceptions.SearchRequestException;
import akka.search.message.NotifyTimeoutMessage;
import akka.search.message.ResultMessage;
import akka.search.message.SearchMessage;
import akka.search.message.SetTimeoutMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterActor extends UntypedActor {

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
            false,
            DeciderBuilder
                .match(Throwable.class, e -> {
                    System.err.println(e);
                    return OneForOneStrategy.stop();
                })
                .build()
        );
    }

    public static final int TIMEOUT = 1000;

    private SearchMessage tempQuery;
    private Map<String, List<String>> results;

    @Override
    public void onReceive(Object o) {
        if (o instanceof SearchMessage message) {
            tempQuery = message;
            results = new HashMap<>();

            ActorRef yandexActorRef = getContext().actorOf(Props.create(YandexSearchActor.class), "yandex-actor");
            yandexActorRef.tell(o, getSelf());

            ActorRef googleActorRef = getContext().actorOf(Props.create(GoogleSearchActor.class), "google-actor");
            googleActorRef.tell(o, getSelf());

            ActorRef bingActorRef = getContext().actorOf(Props.create(BingSearchActor.class), "bing-actor");
            bingActorRef.tell(o, getSelf());

            ActorRef timeoutActorRef = getContext().actorOf(Props.create(TimeoutActor.class), "timeout-actor");
            timeoutActorRef.tell(new SetTimeoutMessage(TIMEOUT), getSelf());
        } else if (o instanceof ResultMessage message) {
            List<String> childResults = message.results();
            String childName = getSender().path().name();
            results.put(childName, childResults);
            if (results.size() == 3) {
                tempQuery.getResultFuture().complete(results);
            }
        } else if (o instanceof NotifyTimeoutMessage) {
            tempQuery.getResultFuture().complete(results);
        }
    }

}
