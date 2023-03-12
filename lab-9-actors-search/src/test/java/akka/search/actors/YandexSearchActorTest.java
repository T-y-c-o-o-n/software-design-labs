package akka.search.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.search.message.ResultMessage;
import akka.search.message.SearchMessage;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.*;
import static org.junit.Assert.assertEquals;

public class YandexSearchActorTest {
    public static final int PORT = 12345;
    private static final StubServer STUB_SERVER = new StubServer(PORT).run();;

    static {
        whenHttp(STUB_SERVER)
            .match(Condition.method(Method.GET), Condition.startsWithUri("/yandex/search/xml"))
            .then(stringContent("""
                    <?xml version="1.0" encoding="utf-8"?>
                    <yandexsearch version="1.0">
                      <request>
                        <groupings>
                          <groupby attr="" mode="flat" groups-on-page="10" docs-in-group="1" curcateg="-1"/>
                        </groupings>
                      </request>
                      <response date="20230215T143152">
                        <results>
                          <grouping attr="" mode="flat" groups-on-page="10" docs-in-group="1" curcateg="-1">
                            <page first="1" last="10">
                              0
                            </page>
                            <group>
                              <doccount>
                                1
                              </doccount>
                              <relevance priority="all"/>
                              <doc id="ZD2C9DE0E67925FF3">
                                <relevance priority="all"/>
                                <url>
                                  https://tr-page.yandex.ru/translate?lang=en-ru&amp;url=https%3A%2F%2Fh30434.www3.hp.com%2Ft5%2FGaming-Desktops%2FDo-I-still-need-Microsoft-10-11-with-Omen-Windows-11%2Ftd-p%2F8618963
                                </url>
                                <domain>
                                  tr-page.yandex.ru
                                </domain>
                                <charset>
                                  utf-8
                                </charset>
                              </doc>
                            </group>
                            <group>
                              <doccount>
                                1
                              </doccount>
                              <relevance priority="all"/>
                              <doc id="Z6819E56602D3994C">
                                <relevance priority="all"/>
                                <url>
                                  https://music.yandex.ru/users/uid-gzulzwuc/playlists/1001
                                </url>
                                <domain>
                                  music.yandex.ru
                                </domain>
                                <charset>
                                  utf-8
                                </charset>
                              </doc>
                            </group>
                            <group>
                              <doccount>
                                1
                              </doccount>
                              <relevance priority="all"/>
                              <doc id="ZD20E1FF74735A174">
                                <relevance priority="all"/>
                                <url>
                                  https://tr-page.yandex.ru/translate?lang=en-ru&amp;url=https%3A%2F%2Fchowder.fandom.com%2Fwiki%2FI_Don%2527t_Wanna_Grow_Up
                                </url>
                                <domain>
                                  tr-page.yandex.ru
                                </domain>
                                <charset>
                                  utf-8
                                </charset>
                              </doc>
                            </group>
                            <group>
                              <doccount>
                                1
                              </doccount>
                              <relevance priority="all"/>
                              <doc id="Z5E8DD644CB893A7A">
                                <relevance priority="all"/>
                                <url>
                                  https://tr-page.yandex.ru/translate?lang=en-ru&amp;url=https%3A%2F%2Fquizlet.com%2F763312520%2Fneuroscience-lab-exam-flash-cards%2F
                                </url>
                                <domain>
                                  tr-page.yandex.ru
                                </domain>
                                <charset>
                                  utf-8
                                </charset>
                              </doc>
                            </group>
                            <group>
                              <doccount>
                                1
                              </doccount>
                              <relevance priority="all"/>
                              <doc id="ZB49B7E589533D301">
                                <relevance priority="all"/>
                                <url>
                                  https://www.DomostroyDon.ru/novosti/rynok-nedvizhimosti/v-centre-rostova-na-donu-postroyat-eshhe-odin-biznes-centr
                                </url>
                                <domain>
                                  www.DomostroyDon.ru
                                </domain>
                                <charset>
                                  utf-8
                                </charset>
                              </doc>
                            </group>
                          </grouping>
                        </results>
                      </response>
                    </yandexsearch>

                    """),
                status(HttpStatus.OK_200),
                contentType("application/xml")
            );
    }

    private static ActorSystem actorSystem;
    private static int masterOnReceiveResultMessageCalls;
    private static ResultMessage receivedResultMessage;
    private static ActorRef childActorRef;

    @Before
    public void before() {
        actorSystem = ActorSystem.create("TestSearchActorSystem");
        masterOnReceiveResultMessageCalls = 0;
    }

    @Test
    public void searchMessageTest() throws Throwable {
        ActorRef testMasterActorRef = actorSystem.actorOf(Props.create(TestMaster.class), "test-master");
        testMasterActorRef.tell(new SearchMessage("kek"), ActorRef.noSender());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1, masterOnReceiveResultMessageCalls);
        assertEquals(5, receivedResultMessage.results().size());
    }

    public static class TestMaster extends UntypedActor {

        @Override
        public SupervisorStrategy supervisorStrategy() {
            return new OneForOneStrategy(
                false,
                DeciderBuilder
                    .match(Throwable.class, e -> {
                        System.err.println(e);
                        return OneForOneStrategy.stop();
                    })
                    .build()
            );
        }

        public TestMaster() {
        }

        @Override
        public void onReceive(Object o) throws Throwable {
            System.out.println(o);
            if (o instanceof SearchMessage query) {
                childActorRef = getContext().actorOf(Props.create(YandexSearchActor.class), "test-yandex");
                childActorRef.tell(o, getSelf());
            } else if (o instanceof ResultMessage m) {
                masterOnReceiveResultMessageCalls++;
                receivedResultMessage = m;
            }
        }
    }
}
