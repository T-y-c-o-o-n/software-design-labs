package hashtagcounter;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HashTagCounterTest {
    private HashTagCounter hashTagCounter;

    @Mock
    private VkClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hashTagCounter = new HashTagCounter(client);
    }

    @Test
    public void test() throws ClientException, ApiException {
        String tag = "tag";
        int hoursAgo = 3;
        List<Integer> expectedCounts = new ArrayList<>();
        for (int i = hoursAgo; i >= 1; --i) {
            int count = i * i;
            when(client.count(tag, i))
                .thenReturn(count);
            expectedCounts.add(count);
        }
        List<Integer> counts = hashTagCounter.hashTagCountPerLastHours(tag, hoursAgo);
        assertEquals(expectedCounts, counts);
    }
}
