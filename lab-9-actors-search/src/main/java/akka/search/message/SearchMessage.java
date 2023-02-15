package akka.search.message;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SearchMessage {
    private final String query;
    private final CompletableFuture<Map<String, String>> resultFuture = new CompletableFuture<>();

    public SearchMessage(String query) {
        this.query = query;
    }

    public CompletableFuture<Map<String, String>> getResultFuture() {
        return resultFuture;
    }

    public String getQuery() {
        return query;
    }
}
