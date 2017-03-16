package com.mode.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Li on 2016/12/21.
 */
public class HttpUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

    private static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";

    private static final int TIMEOUT_SECONDS = 120;

    private static final int POOL_SIZE = 120;


    private static final String url = "http://track.yw56.com.cn/zh-CN";

    public static String post(String trackNums) throws Exception {
        String httpResult = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("InputTrackNumbers", trackNums));
        post.setEntity(new UrlEncodedFormEntity(params));
        try(CloseableHttpResponse response = httpClient.execute(post);) {
            try(BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));) {
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                httpResult = result.toString();

            }
        }
//        System.out.println(httpResult);
        return httpResult;
    }

    public static String post(String url, String requestBody) throws Exception {
        String httpResult = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(requestBody,"utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        try(CloseableHttpResponse response = httpClient.execute(post);) {
            httpResult = EntityUtils.toString(response.getEntity());
        }
        //System.out.println("httpResult -> " + httpResult);
        return httpResult;
    }



    public static void main(String[] args) throws Exception {
//        String result = post("LT321470161CN");
//        System.out.println("result -> " + result);
//        Document doc = Jsoup.parse(result);
//        Elements elements = doc.getElementsByAttributeValue("class", "collapsed");
//        System.out.println("element -> " + elements);
//        for (Element element : elements) {
//            System.out.println(element.attr("aria-controls"));
////            System.out.println(element.getElementsByTag("code").text());
//            System.out.println(element.text());
//        }

        HttpHost proxy = new HttpHost("210.101.131.231", 8080);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
//        CloseableHttpClient httpClient = HttpClients.custom().create().build();
//        HttpGet get = new HttpGet("http://www.whatsmode.com/all-clothing/tops/pug-dog-print-sweatshirt.html");
        HttpGet get = new HttpGet("http://www.whatsmode.com");
        String httpResult = null;
        try(CloseableHttpResponse response = httpClient.execute(get);) {
            httpResult = EntityUtils.toString(response.getEntity());
        }
        System.out.println(httpResult);

//        HttpHost proxy = new HttpHost("97.73.176.22", 87, "http");
//
//        CloseableHttpClient httpClient = HttpClients.custom().create().build();
//        try {
//            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//
//            HttpHost target = new HttpHost("issues.apache.org", 443, "https");
//            HttpGet req = new HttpGet("/");
//
//            System.out.println("executing request to " + target + " via " + proxy);
//            HttpResponse rsp = httpclient.execute(target, req);
//        } finally {
//            // When HttpClient instance is no longer needed,
//            // shut down the connection manager to ensure
//            // immediate deallocation of all system resources
//            httpclient.getConnectionManager().shutdown();
//        }
    }



}
