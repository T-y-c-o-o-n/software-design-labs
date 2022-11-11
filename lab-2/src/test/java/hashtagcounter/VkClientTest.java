package hashtagcounter;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.Before;
import org.junit.Test;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static org.junit.Assert.assertEquals;

public class VkClientTest {

    private static final int PORT = 12345;

    @Before
    public void setUp() throws InterruptedException {
        System.setProperty("api.host", "localhost:" + PORT);
        System.setProperty("oauth.host", "localhost:" + PORT);
        StubServer stubServer = new StubServer(PORT);
        whenHttp(stubServer)
            .match(Condition.method(Method.GET), Condition.startsWithUri("/method/newsfeed.search"))
            .then(Action.stringContent("""
                {
                "response":{
                "count":10
                "items":[]
                "total_count":0
                }
                }
                """));
        Thread.sleep(10000);
    }

    @Test
    public void test() throws ClientException, ApiException {
        VkClient client = new VkClient();
        String tag = "tag";
        int hoursAgo = 3;
        int result = client.count(tag, hoursAgo);
        assertEquals(10, result);
    }
}
