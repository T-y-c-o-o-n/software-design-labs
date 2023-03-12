package akka.search.actors;

import akka.actor.UntypedActor;
import akka.search.exceptions.SearchRequestException;
import akka.search.message.ResultMessage;
import akka.search.message.SearchMessage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.HttpURLConnection;
import java.util.List;

public abstract class ChildActor extends UntypedActor {

    @Override
    public void postStop() throws Exception {
        getContext().parent().tell(new ResultMessage(List.of("error")), getSelf());
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof SearchMessage message) {
            String query = message.getQuery();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(getPath(query))
                .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    getSender().tell(
                        new ResultMessage(parseResponse(response.body().string())),
                        getSelf()
                    );
                } else {
                    throw new SearchRequestException();
                }

            }
        }
    }

    protected abstract String getPath(String query);

    protected abstract List<String> parseResponse(String response) throws Exception;
}

