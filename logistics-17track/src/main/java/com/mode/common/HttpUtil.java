package com.mode.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Li on 2016/12/21.
 */
public class HttpUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

    private static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";

    private static final int TIMEOUT_SECONDS = 120;

    private static final int POOL_SIZE = 120;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String url = "http://www.17track.net/restapi/handlertrack.ashx";

    public static String post(List<String> trackNums) throws Exception {
        String httpResult = null;
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(url);

        HttpHost proxy = new HttpHost("61.191.41.130", 80);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
        HttpPost post = new HttpPost(url);

        post.addHeader("Content-Type", "application/json");
        post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        post.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//        post.setHeader("Referer", "http://www.google.com");
        Raw raw = new Raw();
        List<Num> nums = new ArrayList<>();
        for (String trackNum : trackNums) {
            nums.add(new Num(trackNum));
        }
        raw.setData(nums);
        System.out.println(objectMapper.writeValueAsString(raw));
        HttpEntity entity = new ByteArrayEntity(objectMapper.writeValueAsString(raw).getBytes());
        post.setEntity(entity);
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
        System.out.println(httpResult);
        return httpResult;
    }


    public static String post1(List<String> trackNums) throws Exception {
        String httpResult = null;
        // Create global request configuration
//        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
//                .setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
        HttpHost proxy = new HttpHost("27.24.158.131", 81);
        RequestConfig defaultRequestConfig = RequestConfig.custom().setLocalAddress(InetAddress
                .getByName("192.168.2.10")).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//        CloseableHttpClient httpClient = HttpClients.custom().setProxy(proxy).build();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig
//                (defaultRequestConfig).build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.setHeader("Referer", "http://www.google.com");
        String raw = "{\"data\":[{\"num\":\"LT307328054CN\"}]}";
        HttpEntity entity = new ByteArrayEntity(raw.getBytes());
        post.setEntity(entity);
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
        return httpResult;
    }


    public static String post(String trackNum) throws Exception {
        String httpResult = null;
        String url = "http://track.yw56.com.cn/zh-CN";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("InputTrackNumbers", "LX203277898CN,LM128697434CN"));
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
        return httpResult;
    }


    public static void main(String[] args) throws Exception {
//        String result = post("abc");
//        System.out.println("result -> " + result);
//        Document doc = Jsoup.parse(result);
//        Elements elements = doc.getElementsByAttributeValue("class", "collapsed");
//        System.out.println("element -> " + elements);
//        for (Element element : elements) {
//            System.out.println(element.attr("aria-controls"));
////            System.out.println(element.getElementsByTag("code").text());
//            System.out.println(element.text());
//        }

        Raw raw = new Raw();
        List<Num> nums = new ArrayList<>();
        List<String> trackNums = new ArrayList<>();
        trackNums.add("LT234893515CN");
        trackNums.add("LT235454118CN");

        for (String trackNum : trackNums) {
            nums.add(new Num(trackNum));
        }
        raw.setData(nums);
        System.out.println(objectMapper.writeValueAsString(raw));









//        List<String> trackNums = new ArrayList<>();
//        trackNums.add("LX203277898CN");
////        trackNums.add("LM122361953CN");
//        String result = post1(trackNums);
//        System.out.println("result -> " + result);
//        System.out.println(objectMapper.readTree(result).get("dat").size());





//        System.out.println("result1 -> " + objectMapper.readTree(result).get("dat").get(0).get
//                ("track"));
//        String track = objectMapper.readTree(result).get("dat").get(0).get("track").asText();
//        System.out.println(track);
//        if (track.equals("null")) {
//            System.out.println("is null");
//        }
//        System.out.println("a".equals(null));
//        StringBuilder sb = new StringBuilder();
//        System.out.println("sb" + sb.toString());

//        List<String> trackNums = new ArrayList<>();
//        trackNums.add("2");
//        trackNums.add("2");
//        trackNums.add("3");
//        trackNums.add("4");
//        trackNums.add("5");
//        trackNums.add("6");
//        trackNums.add("7");
//        trackNums.add("8");

//        int logisticsBatchSize = 2;
//
//        int batch = trackNums.size() / logisticsBatchSize;
//
//        for (int i = 0; i <= batch; i ++) {
//            if (i < batch) {
//                List<String> currentBatch = new ArrayList<>();
//                for (int j = 0; j < logisticsBatchSize; j ++) {
//                    currentBatch.add(trackNums.get(i * logisticsBatchSize + j));
//                }
//                System.out.println("currentBatch -> " + currentBatch);
//            } else {
//                List<String> currentBatch = new ArrayList<>();
//                for (int j = batch * logisticsBatchSize; j <= trackNums
//                        .size() - 1; j ++) {
//                    currentBatch.add(trackNums.get(j));
//                }
//                if (currentBatch.size() != 0) {
//                    System.out.println("currentBatch -> " + currentBatch);
//                }
//            }
//        }


    }
}
