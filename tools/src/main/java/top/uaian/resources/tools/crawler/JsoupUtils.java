package top.uaian.resources.tools.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {

    public static Elements getElements(String pageAsXml, String cssQuery){
        Document document = Jsoup.parse(pageAsXml);
        return document.select(cssQuery);
    }

    public static Element getFirstElement(String pageAsXml, String cssQuery){
        Document document = Jsoup.parse(pageAsXml);
        return document.select(cssQuery).first();
    }

    public static String getElementAttributes(Element element, String attributeName){
        Attributes attributes = element.attributes();
        return attributes.get(attributeName);
    }
}
