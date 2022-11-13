package hashtagcounter;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.*;
import static org.junit.Assert.assertEquals;

public class VkClientTest {

    private static final int PORT = 12345;

    private StubServer stubServer;
    private VkClient client;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        stubServer = new StubServer(PORT).run();
        client = new VkClient(0, "");
        VkApiClient vkApi = client.getVkApi();
        Field apiEndpointField = vkApi.getClass().getDeclaredField("apiEndpoint");
        apiEndpointField.setAccessible(true);
        apiEndpointField.set(vkApi, "http://localhost:%s/method/".formatted(PORT));
    }

    @Test
    public void test() throws ClientException, ApiException {
        whenHttp(stubServer)
            .match(Condition.method(Method.POST), Condition.startsWithUri("/method/newsfeed.search"))
            .then(stringContent("""
                    {
                    "response":{
                    "count":10,
                    "items":[],
                    "total_count":0
                    }
                    }
                    """),
                status(HttpStatus.OK_200),
                contentType("application/json")
            );

        String tag = "tag";
        int hoursAgo = 3;
        int result = client.count(tag, hoursAgo);
        assertEquals(10, result);
    }
}
