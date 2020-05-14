package top.uaian.resources.tools.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import top.uaian.resources.tools.crawler.conf.AdminJavaScriptEngine;

public class WebClientUtils {

    private static WebClient webClient;

    public static WebClient getWebClient(){
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);
        webClient.setJavaScriptEngine(new AdminJavaScriptEngine(webClient));
        return webClient;
    }




}
