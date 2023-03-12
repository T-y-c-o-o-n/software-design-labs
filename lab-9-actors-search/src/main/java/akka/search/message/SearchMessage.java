package akka.search.message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SearchMessage {
    private final String query;
    private final CompletableFuture<Map<String, List<String>>> resultFuture = new CompletableFuture<>();

    public SearchMessage(String query) {
        this.query = query;
    }

    public CompletableFuture<Map<String, List<String>>> getResultFuture() {
        return resultFuture;
    }

    public String getQuery() {
        return query;
    }
}
