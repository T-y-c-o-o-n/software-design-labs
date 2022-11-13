package hashtagcounter;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class VkClient {
    private final VkApiClient vkApi;
    private final int appId;
    private final String accessToken;

    public VkClient(int appId, String accessToken) {
        TransportClient transportClient = new HttpTransportClient();
        vkApi = new VkApiClient(transportClient);
        this.appId = appId;
        this.accessToken = accessToken;
    }

    public VkApiClient getVkApi() {
        return vkApi;
    }

    public int count(String hashTag, int hourAgo) throws ClientException, ApiException {
        long startTime = Instant.now().minus(hourAgo, ChronoUnit.HOURS).getEpochSecond();
        long endTime = Instant.now().minus(hourAgo - 1, ChronoUnit.HOURS).getEpochSecond();

        NewsfeedSearchQuery search = vkApi.newsfeed()
            .search(new ServiceActor(appId, accessToken))
            .q("#" + hashTag)
            .startTime((int) startTime)
            .endTime((int) endTime);
        SearchResponse searchResponse = search.execute();
        return searchResponse.getCount();
    }
}
