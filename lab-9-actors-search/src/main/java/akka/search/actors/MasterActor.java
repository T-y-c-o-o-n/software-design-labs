package akka.search.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.search.exceptions.SearchRequestException;
import akka.search.message.ResultMessage;
import akka.search.message.SearchMessage;

import java.util.HashMap;
import java.util.Map;

public class MasterActor extends UntypedActor {

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
            false,
            DeciderBuilder
                .match(Throwable.class, e -> OneForOneStrategy.stop())
                .build()
        );
    }

    private SearchMessage tempQuery;
    private Map<String, String> results;

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof SearchMessage message) {
            tempQuery = message;
            results = new HashMap<>();

            ActorRef yandexActorRef = getContext().actorOf(Props.create(YandexSearchActor.class), "yandex-actor");
            yandexActorRef.tell(o, yandexActorRef);
        } else if (o instanceof ResultMessage message) {
            String result = message.result();
            String childName = getSender().path().name();
            results.put(childName, result);
            if (results.size() == 1) {
                tempQuery.getResultFuture().complete(results);
            }
        }
    }

}
