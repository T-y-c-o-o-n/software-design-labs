package akka.search.actors;

import akka.actor.UntypedActor;
import akka.search.exceptions.SearchRequestException;
import akka.search.message.ResultMessage;
import akka.search.message.SearchMessage;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class YandexSearchActor extends UntypedActor {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public void postStop() throws Exception {
        getContext().parent().tell(new ResultMessage("error"), getSelf());
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof SearchMessage message) {
            String query = message.getQuery();
            HttpResponse<String> response = httpClient.send(
                HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://yandex.ru/search/?text=" + query))
                    .build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );
            System.out.println(response.statusCode());
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                getSender().tell(
                    new ResultMessage(response.body()),
                    getSelf()
                );
            } else {
                throw new SearchRequestException();
            }
        }
    }
}
