package top.uaian.resources.service.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.uaian.model.inner.crawler.CrawlerWebsite;
import top.uaian.model.inner.crawler.Resource;
import top.uaian.model.inner.crawler.ResourceExtend;
import top.uaian.resources.tools.crawler.JsoupUtils;
import top.uaian.resources.tools.crawler.WebClientUtils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Autowired
    CrawlerWebsiteService crawlerWebsiteService;


    @Override
    public String CrawlerStart() {
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        List<CrawlerWebsite> CrawlerWebsites = crawlerWebsiteService.list();
        WebClient webClient = WebClientUtils.getWebClient();
        CrawlerWebsites.stream().forEach(crawlerWebsite -> {
            Resource resource = new Resource();
            resource.setCode(UUID.randomUUID().toString());
            ResourceExtend resourceExtend = new ResourceExtend();
            resourceExtend.setResOrigin(crawlerWebsite.getId());
            resourceExtend.setResCode(UUID.randomUUID().toString());
            JSONObject webConf = JSONObject.parseObject(crawlerWebsite.getWebConf());
            JSONObject indexJson = JSONObject.parseObject(webConf.getString("Index"));
            Map<String,String> pages = new HashMap<>();
            pages.put("Index", indexJson.getString("url"));
            Crawler(webClient, webConf, indexJson, pages, resource, resourceExtend);
            System.out.println(JSONObject.toJSONString(resource));
        });

        return null;
    }


    private static void Crawler(WebClient webClient, JSONObject webConf, JSONObject startJson,
                                Map<String, String> pages, Resource resource, ResourceExtend resourceExtend){
        try {
            String url = startJson.getString("url");
            HtmlPage startPage = webClient.getPage(url);
            String temChild = startJson.getString ("child");
            if(StringUtils.isEmpty(temChild)) {
                return;
            }
            JSONArray startChilds = JSONArray.parseArray(temChild);
            for (int i = 0; i < startChilds.size(); i++) {
                String childName = JSONObject.parseObject(startChilds.getString(i)).getString("childName");
                JSONObject childJson = JSONObject.parseObject(webConf.getString(childName));
                Elements childEles = JsoupUtils.getElements(startPage.asXml(), childJson.getString("Label"));
                String tempChildJson = childJson.getString("Ext");
                Elements childElements = new Elements();
                if(!StringUtils.isEmpty(tempChildJson)) {
                    String[] exts = tempChildJson.split(",");
                    if (exts.length > 0) {
                        for (int j = 0; j < exts.length; j++) {
                            childElements.addAll(childEles.select("a:contains("+exts[j]+")"));
                        }
                    }else {
                        childElements.addAll(childEles);
                    }
                }else {
                    childElements.addAll(childEles);
                }


                String[] childpages = JSONObject.parseObject(startChilds.getString(i)).getString("childpage").split(
                        ",");

                childElements.stream().forEach(childEle -> {
                    String tempText = childJson.getString("text");
                    if("true".equals(tempText)) {
                        String elementText = JsoupUtils.getElementText(childEle);
                        System.out.println(elementText);
                        setText(childName, elementText, resource);
                        return;
                    }
                    String isProperty = childJson.getString("isProperty");
                    String tempAttribute = childJson.getString("Arrtibute");
                    if (StringUtils.isEmpty(tempAttribute)) {
                        return;
                    }
                    String attribute = JsoupUtils.getElementAttributes(childEle, tempAttribute);
                    if("Image".equals(childName)) {
                        resource.setScreenshots(new String[]{attribute});
                        System.out.println(attribute);
                        return;
                    }
                    pages.put(childName, attribute);
                    StringBuffer childPage = new StringBuffer("");
                    for (int p = 0; p < childpages.length; p++) {
                        String tempUrl = cancelSlash(pages.get(childpages[p]));
                        childPage.append("/" + tempUrl);
                    }
                    if("List".equals(childName)) {
                        resourceExtend.setResOriginUrl(childPage.substring(1, childPage.length()));
                        System.out.println(childPage.substring(1, childPage.length()));
                    }
                    childJson.put("url",childPage.substring(1, childPage.length()));
                    Crawler(webClient,webConf, childJson, pages, resource, resourceExtend);
                });
            }
        }catch (IOException io) {
//            //todo 增加批次号
//            logger.info("爬取Java1234主页失败: " + io.getMessage());
        }
    }

    private static void setText(String childName, String elementText, Resource resource) {
        switch (childName) {
            case "Title":
                resource.setName(elementText);
                break;
            case "Link":
                if (elementText.startsWith("http")) {
                    resource.setResUrl(elementText);
                }
                break;
            case "LinkPw":
                if (elementText.length() == 4) {
                    resource.setResPwd(elementText);
                }
                break;
        }
    }

    private static String cancelSlash(String url) {
        if(url.startsWith("/")) {
            url = url.substring(1, url.length());
        }
        if(url.endsWith("/")) {
            url = url.substring(0, url.length() -1);
        }
        return url;
    }


    public static void main(String[] args) throws IOException {
        String indexUrl = "http://www.java1234.com/a/kaiyuan/java/2020/0303/15933.html";
        WebClient webClient = WebClientUtils.getWebClient();
        HtmlPage page = webClient.getPage(indexUrl);
        String xml = page.asXml();
        Document doc = Jsoup.parse(xml);
        Elements selects = doc.select("div.title h2");
        Element first = selects.first();
        String text = first.text();
        System.out.println(text);
    }

}
