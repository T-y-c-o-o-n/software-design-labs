package akka.search.actors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import static akka.search.Main.YANDEX_PATH;

public class YandexSearchActor extends ChildActor {

    @Override
    protected String getPath(String query) {
        return YANDEX_PATH;
    }

    @Override
    protected List<String> parseResponse(String response) throws Exception {
        List<String> results = new ArrayList<>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new StringBufferInputStream(response));
        doc.normalizeDocument();
        NodeList urlNodes = doc.getElementsByTagName("url");
        for (int i = 0; i < urlNodes.getLength(); i++) {
            Node item = urlNodes.item(i);
            results.add(item.getTextContent().trim());
        }
        return results;
    }
}
