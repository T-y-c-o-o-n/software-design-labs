package akka.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.search.actors.MasterActor;
import akka.search.message.SearchMessage;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.*;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final ActorSystem ACTOR_SYSTEM = ActorSystem.create("SearchActorSystem");
    public static final int PORT = 12345;
    public static final String YANDEX_PATH = "http://127.0.0.1:12345/yandex/search/xml";
    public static final String BING_PATH = "http://localhost:12345/api.bing.microsoft.com/v7.0/search";
    private static final StubServer STUB_SERVER = new StubServer(PORT).run();
    ;

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
        whenHttp(STUB_SERVER)
            .match(Condition.method(Method.GET), Condition.startsWithUri("/api.bing.microsoft.com/v7.0/search"))
            .then(Action.composite(
                    delay(30000),
                    stringContent("""
                        {
                          "_type": "SearchResponse",
                          "queryContext": {
                            "originalQuery": "Microsoft Cognitive Services"
                          },
                          "webPages": {
                            "webSearchUrl": "https://www.bing.com/search?q=Microsoft+cognitive+services",
                            "totalEstimatedMatches": 22300000,
                            "value": [
                              {
                                "id": "https://api.cognitive.microsoft.com/api/v7/#WebPages.0",
                                "name": "Microsoft Cognitive Services",
                                "url": "https://www.microsoft.com/cognitive-services",
                                "displayUrl": "https://www.microsoft.com/cognitive-services",
                                "snippet": "Knock down barriers between you and your ideas. Enable natural and contextual interaction with tools that augment users' experiences via the power of machine-based AI. Plug them in and bring your ideas to life.",
                                "deepLinks": [
                                  {
                                    "name": "Face",
                                    "url": "https://azure.microsoft.com/services/cognitive-services/face/",
                                    "snippet": "Add facial recognition to your applications to detect, identify, and verify faces using a Face service from Microsoft Azure. ... Cognitive Services; Face service;"
                                  },
                                  {
                                    "name": "Text Analytics",
                                    "url": "https://azure.microsoft.com/services/cognitive-services/text-analytics/",
                                    "snippet": "Cognitive Services; Text Analytics API; Text Analytics API . Detect sentiment, ... you agree that Microsoft may store it and use it to improve Microsoft services, ..."
                                  },
                                  {
                                    "name": "Computer Vision API",
                                    "url": "https://azure.microsoft.com/services/cognitive-services/computer-vision/",
                                    "snippet": "Extract the data you need from images using optical character recognition and image analytics with Computer Vision APIs from Microsoft Azure."
                                  },
                                  {
                                    "name": "Emotion",
                                    "url": "https://www.microsoft.com/cognitive-services/en-us/emotion-api",
                                    "snippet": "Cognitive Services Emotion API - microsoft.com"
                                  },
                                  {
                                    "name": "Bing Speech API",
                                    "url": "https://azure.microsoft.com/services/cognitive-services/speech/",
                                    "snippet": "Add speech recognition to your applications, including text to speech, with a speech API from Microsoft Azure. ... Cognitive Services; Bing Speech API;"
                                  },
                                  {
                                    "name": "Get Started for Free",
                                    "url": "https://azure.microsoft.com/services/cognitive-services/",
                                    "snippet": "Add vision, speech, language, and knowledge capabilities to your applications using intelligence APIs and SDKs from Cognitive Services."
                                  }
                                ]
                              }
                            ]
                          },
                          "relatedSearches": {
                            "id": "https://api.cognitive.microsoft.com/api/v7/#RelatedSearches",
                            "value": [
                              {
                                "text": "microsoft bot framework",
                                "displayText": "microsoft bot framework",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+bot+framework"
                              },
                              {
                                "text": "microsoft cognitive services youtube",
                                "displayText": "microsoft cognitive services youtube",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+youtube"
                              },
                              {
                                "text": "microsoft cognitive services search api",
                                "displayText": "microsoft cognitive services search api",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+search+api"
                              },
                              {
                                "text": "microsoft cognitive services news",
                                "displayText": "microsoft cognitive services news",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+news"
                              },
                              {
                                "text": "ms cognitive service",
                                "displayText": "ms cognitive service",
                                "webSearchUrl": "https://www.bing.com/search?q=ms+cognitive+service"
                              },
                              {
                                "text": "microsoft cognitive services text analytics",
                                "displayText": "microsoft cognitive services text analytics",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+text+analytics"
                              },
                              {
                                "text": "microsoft cognitive services toolkit",
                                "displayText": "microsoft cognitive services toolkit",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+toolkit"
                              },
                              {
                                "text": "microsoft cognitive services api",
                                "displayText": "microsoft cognitive services api",
                                "webSearchUrl": "https://www.bing.com/search?q=microsoft+cognitive+services+api"
                              }
                            ]
                          },
                          "rankingResponse": {
                            "mainline": {
                              "items": [
                                {
                                  "answerType": "WebPages",
                                  "resultIndex": 0,
                                  "value": {
                                    "id": "https://api.cognitive.microsoft.com/api/v7/#WebPages.0"
                                  }
                                }
                              ]
                            },
                            "sidebar": {
                              "items": [
                                {
                                  "answerType": "RelatedSearches",
                                  "value": {
                                    "id": "https://api.cognitive.microsoft.com/api/v7/#RelatedSearches"
                                  }
                                }
                              ]
                            }
                          }
                        }
                        """)),
                status(HttpStatus.OK_200),
                contentType("application/json")
            );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String query = scanner.next();
            System.out.println("Searching for the query '" + query + "' ..." + System.lineSeparator());
            search(query);
        }
    }

    private static void search(String query) {
        ActorRef masterActorRef = ACTOR_SYSTEM.actorOf(Props.create(MasterActor.class), "master-actor");
        SearchMessage queryMessage = new SearchMessage(query);
        masterActorRef.tell(queryMessage, ActorRef.noSender());
        queryMessage.getResultFuture().handleAsync((result, e) -> {
            if (e == null) {
                System.out.println("Results for the query:");
                for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                    System.out.println();
                    System.out.println(entry.getKey() + ":");
                    for (String link : entry.getValue()) {
                        System.out.println(link);
                    }
                }
            } else {
                System.out.println("An error occurred while searching fo the query: " + e.getMessage());
            }
            ACTOR_SYSTEM.stop(masterActorRef);
            return null;
        });
    }
}
