package top.uaian.resources.service.admin;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.uaian.resources.tools.crawler.JsoupUtils;
import top.uaian.resources.tools.crawler.WebClientUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    private final String Java1234_Url = "http://www.java1234.com/";


    @Override
    public String CrawlerStart() {
        CrawlerJava1234Start();
        return null;
    }

    private void CrawlerJava1234Start() {
        WebClient webClient = WebClientUtils.getWebClient();
        ArrayList topHrefs = CrawlerIndex(webClient);
        if(!Optional.ofNullable(topHrefs).isPresent() || topHrefs.size() <=0) {
            return;
        }

    }

    private ArrayList CrawlerIndex(WebClient webClient){
        try {
            ArrayList topUrls = new ArrayList();
            HtmlPage indexPage = webClient.getPage(Java1234_Url);
            //主页导航栏<a>标签
            Elements topEles = JsoupUtils.getElements(indexPage.asXml(), "div.top li > a[rel]");
            Elements kaiyuans = topEles.select(":contains(Java开源项目分享)");
            Elements bysjs = topEles.select(":contains(Java毕业设计)");
            if(kaiyuans.addAll(bysjs)) {
                kaiyuans.stream().forEach(kaiyuan -> {
                    topUrls.add(JsoupUtils.getElementAttributes(kaiyuan, "href"));
                });
            }
            return topUrls;
        }catch (IOException io) {
            //todo 增加批次号
            logger.info("爬取Java1234主页失败: " + io.getMessage());
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        String indexUrl = "http://www.java1234.com/";
        WebClient webClient = WebClientUtils.getWebClient();
        HtmlPage page = webClient.getPage(indexUrl);
        String xml = page.asXml();
        Document doc = Jsoup.parse(xml);
        Elements selects = doc.select("div.top li > a[rel]");
        Element first = selects.first();
        Attributes attributes = first.attributes();
        String href = attributes.get("href");
        System.out.println(href);
        try {
            String topUrl = indexUrl.substring(0, indexUrl.length() -1) + href;
            HtmlPage topPage = webClient.getPage(topUrl);
            Document topDoc = Jsoup.parse(topPage.asXml());
            Elements topEles = topDoc.select("span.more a");
            topEles.stream().forEach(topEle -> {
                Attributes topAttributes = topEle.attributes();
                String topHref = topAttributes.get("href");
                System.out.println(topHref);
                String moreUrl = topUrl.substring(0, indexUrl.length() -1) + topHref;
                try {
                    HtmlPage morePage = webClient.getPage(moreUrl);
                    Document moreDoc = Jsoup.parse(morePage.asXml());
                    //爬取当前页的
                    Elements listEles = moreDoc.select("div.listbox a.title");
                    //获取下一页
                    boolean isNextExist = true;
                    while (isNextExist) {
                        Elements byPageEles = moreDoc.select("div.dede_pages a");
                        Elements nextpageEle = byPageEles.select(":contains(下一页)");
                        if(nextpageEle != null && nextpageEle.size() == 1) {
                            String nextPageHref = nextpageEle.first().attributes().get("href");
                            String nextUrl = moreUrl + nextPageHref;
                            System.out.println(nextUrl);
                            //todo 爬取百度链接
                        }else {
                            isNextExist = false;
                        }
                    }
                    listEles.stream().forEach(listEle -> {
                        Attributes listAttributes = listEle.attributes();
                        String listHref = listAttributes.get("href");
                        String detailUrl = indexUrl.substring(0, indexUrl.length() -1) + listHref;
                        HtmlPage detailPage = null;
                        try {
                            detailPage = webClient.getPage(detailUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Document detailDoc = Jsoup.parse(detailPage.asXml());
                        Elements detailEles = detailDoc.select("strong a");
                        detailEles.stream().forEach(detailEle -> {
                            Attributes detailAttributes = detailEle.attributes();
                            //百度网盘链接
                            String detail_href = detailAttributes.get("href");
                            System.out.println(detail_href);
                        });
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
