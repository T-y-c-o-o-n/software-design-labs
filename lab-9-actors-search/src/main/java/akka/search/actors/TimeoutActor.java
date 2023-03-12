package akka.search.actors;

import akka.actor.UntypedActor;
import akka.search.message.NotifyTimeoutMessage;
import akka.search.message.SetTimeoutMessage;

public class TimeoutActor extends UntypedActor {
    @Override
    public void onReceive(Object o) {
        if (o instanceof SetTimeoutMessage m) {
            try {
                Thread.sleep(m.timeoutMillis());
            } catch (InterruptedException ignored) {
            } finally {
                getSender().tell(new NotifyTimeoutMessage(), getSelf());
            }
        }
    }
}
