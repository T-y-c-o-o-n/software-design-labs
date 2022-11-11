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
    private final VkApiClient vk;

    public VkClient() {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
    }

    public int count(String hashTag, int hourAgo) throws ClientException, ApiException {
        long startTime = Instant.now().minus(hourAgo, ChronoUnit.HOURS).getEpochSecond();
        long endTime = Instant.now().minus(hourAgo - 1, ChronoUnit.HOURS).getEpochSecond();
        System.out.println(startTime + " --- " + endTime);
        NewsfeedSearchQuery search = vk.newsfeed()
            .search(
                new ServiceActor(
                    51473962,
                    "vk1.a.M0Q6-B0l1kRsENNcij1ZvugFOi_Qrhx2nB51LiS_4MfdFy2NZyaAO1TXDrNPDrn2XiIrcT0JMfyV6S3kMQiu6ic5jaVwGJJID-Tb7X8mTSqtUL8tLYl8hOf8F4a29zOpgcJP-mRtUL_p4Ox79ptFZbPsjBJLbGCwTWxgicz1I73mYu1tsqCmC4hCgBQ67203"
                )
            ).q("#" + hashTag)
            .startTime((int) startTime)
            .endTime((int) endTime);
        SearchResponse searchResponse = search.execute();
        return searchResponse.getCount();
    }
}
