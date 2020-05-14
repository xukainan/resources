package top.uaian.resources.tools.crawler.conf;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.host.Window;

/**
 * description:  <br>
 * date: 2020/5/14 16:24 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class AdminJavaScriptEngine extends JavaScriptEngine {

    public AdminJavaScriptEngine(WebClient webClient) {

        super(webClient);
    }

    @Override
    protected void handleJavaScriptException(final ScriptException scriptException, final boolean triggerOnError) {
        // Trigger window.onerror, if it has been set.
        final HtmlPage page = scriptException.getPage();
        if (triggerOnError && page != null) {
            final WebWindow window = page.getEnclosingWindow();
            if (window != null) {
                final Window w = (Window) window.getScriptableObject();
                if (w != null) {
                    try {
                        w.triggerOnError(scriptException);
                    }
                    catch (final Exception e) {
                        handleJavaScriptException(new ScriptException(page, e, null), false);
                    }
                }
            }
            final JavaScriptErrorListener javaScriptErrorListener = window.getWebClient().getJavaScriptErrorListener();
            if (javaScriptErrorListener != null) {
                javaScriptErrorListener.scriptException(page, scriptException);
            }
            // Throw a Java exception if the user wants us to.
            if (window.getWebClient().getOptions().isThrowExceptionOnScriptError()) {
                throw scriptException;
            }
        }
        // Log the error; ScriptException instances provide good debug info.
        // LOG.info("Caught script exception", scriptException);
    }

}
