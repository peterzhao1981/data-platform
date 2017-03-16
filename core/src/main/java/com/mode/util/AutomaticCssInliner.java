package com.mode.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Strings;

/**
 * Css inliner for email,from
 * http://stackoverflow.com/questions/4521557/automatically-convert-style-sheets-to-inline-style
 *
 * @author dennis<xzhuang@avos.com>
 * @date 2013-1-9
 */
public class AutomaticCssInliner {
    /**
     * Hecho por Grekz, http://grekz.wordpress.com
     */
    public static Document inlineStyles(String html) throws IOException {
        // Document doc = Jsoup.connect("http://mypage.com/inlineme.php").get();
        Document doc = Jsoup.parse(html);
        String style = "style";
        Elements els = doc.select(style);// to get all the style elements
        for (Element e : els) {
            String styleRules = e.getAllElements().get(0).data().replaceAll("\n", "").trim(), delims =
                    "{}";
            StringTokenizer st = new StringTokenizer(styleRules, delims);
            while (st.countTokens() > 1) {
                String selector = st.nextToken(), properties = st.nextToken();
                // Process selectors such as "a:hover"
                if (selector.indexOf(":") > 0) {
                    selector = selector.substring(0, selector.indexOf(":"));
                }
                if (Strings.isNullOrEmpty(selector)) {
                    continue;
                }
                Elements selectedElements = doc.select(selector);
                for (Element selElem : selectedElements) {
                    String oldProperties = selElem.attr(style);
                    selElem.attr(
                            style,
                            oldProperties.length() > 0 ? concatenateProperties(oldProperties,
                                    properties) : properties);
                }
            }
            e.remove();
        }
        return doc;
    }

    private static String concatenateProperties(String oldProp, String newProp) {
        oldProp = oldProp.trim();
        if (!newProp.endsWith(";")) {
            newProp += ";";
        }
        return newProp + oldProp; // The existing (old) properties should take precedence.
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("MMM d, yyyy");
        System.out.println(sdf.format(time));


    }
}
