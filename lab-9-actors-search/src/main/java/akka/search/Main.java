package akka.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.search.actors.MasterActor;
import akka.search.message.SearchMessage;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final ActorSystem ACTOR_SYSTEM = ActorSystem.create("SearchActorSystem");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String query = scanner.next();
            System.out.println("Searching for the query '" + query + "' ..." + System.lineSeparator());
            search(query);
        }
    }

    private static void search(String query) {
        ActorRef masterActorRef = ACTOR_SYSTEM.actorOf(Props.create(MasterActor.class), "master-actor");
        SearchMessage queryMessage = new SearchMessage(query);
        masterActorRef.tell(queryMessage, ActorRef.noSender());
        queryMessage.getResultFuture().handleAsync((result, e) -> {
            if (e == null) {
                System.out.println("Results for the query:");
                for (Map.Entry<String, String> entry : result.entrySet()) {
                    System.out.println(entry.getKey() + " --- " + entry.getValue());
                }
            } else {
                System.out.println("An error occurred while searching fo the query: " + e.getMessage());
            }
            return null;
        });
    }
}
