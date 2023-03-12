package akka.search.actors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class GoogleSearchActor extends ChildActor {

    @Override
    protected String getPath(String query) {
        return "https://www.googleapis.com/customsearch/v1?key=AIzaSyAlpGewYJUA7CBkSfvcf3SGISach2Uq190&cx=d6ba835d3525a48b2&num=5&q=" + query;
    }

    @Override
    protected List<String> parseResponse(String response) {
        List<String> results = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(response);
        JsonArray items = element.getAsJsonObject().get("items").getAsJsonArray();
        for (int i = 0; i < items.size(); i++) {
            String link = items.get(i).getAsJsonObject().get("link").getAsString();
            results.add(link);
        }
        return results;
    }
}
