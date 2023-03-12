package akka.search.actors;

import java.util.List;

import static akka.search.Main.BING_PATH;

public class BingSearchActor extends ChildActor {
    @Override
    protected String getPath(String query) {
        return BING_PATH;
    }

    @Override
    protected List<String> parseResponse(String response) {
        return List.of(response);
    }
}
