package hashtagcounter;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.ArrayList;
import java.util.List;

public class HashTagCounter {
    private final VkClient vkClient;

    public HashTagCounter(VkClient vkClient) {
        this.vkClient = vkClient;
    }

    public List<Integer> hashTagCountPerLastHours(String hashTag, int lastHours) throws ClientException, ApiException {
        if (lastHours < 1 || lastHours > 24) {
            throw new IllegalArgumentException("lastHours must be >= 1 and <= 24");
        }
        List<Integer> counts = new ArrayList<>();
        for (int hoursAgo = lastHours; hoursAgo >= 1; --hoursAgo) {
            int count = vkClient.count(hashTag, hoursAgo);
            counts.add(count);
        }
        return counts;
    }

    public static void main(String[] args) throws ClientException, ApiException {
        List<Integer> counts = new HashTagCounter(
            new VkClient(
                Integer.parseInt(System.getenv("APP_ID")),
                System.getenv("ACCESS_TOKEN")
            )
        ).hashTagCountPerLastHours("Россия", 24);
        for (int count : counts) {
            System.out.println(count);
        }
    }
}
