package com.mode.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mode.common.HttpUtil;
import com.mode.service.LogisticsService;

/**
 * Created by zhaoweiwei on 16/12/27.
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${logistic.batch.size:'1'}")
    private int logisticsBatchSize;

    @Override
    public Map<String, String> getLogistics(List<String> trackNums) {
        Map<String, String> map = new HashMap<>();

        int batch = trackNums.size() / logisticsBatchSize;

        for (int i = 0; i <= batch; i ++) {
            if (i < batch) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < logisticsBatchSize; j ++) {
                    sb.append(trackNums.get(i * logisticsBatchSize + j));
                    sb.append(",");
                }
                if (sb.length() > 1) {
                    if (",".equals(sb.substring(sb.length() - 1))) {
                        String currentBatch = sb.substring(0, sb.length() - 1);
                        System.out.println("currentBatch -> " + currentBatch);
                        Map<String, String> status = processBatch(currentBatch);
                        for (Map.Entry<String, String> entry : status.entrySet()) {
                            map.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                try {
                    Thread.sleep(500l);
                } catch (Exception e) {
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int j = batch * logisticsBatchSize; j <= trackNums
                        .size() - 1; j ++) {
                    sb.append(trackNums.get(j));
                    sb.append(",");
                }
                if (sb.length() > 1) {
                    if (",".equals(sb.substring(sb.length() - 1))) {
                        String currentBatch = sb.substring(0, sb.length() - 1);
                        System.out.println("currentBatch -> " + currentBatch);
                        Map<String, String> status = processBatch(currentBatch);
                        for (Map.Entry<String, String> entry : status.entrySet()) {
                            map.put(entry.getKey(), entry.getValue());
                        }

                    }
                }
                try {
                    Thread.sleep(500l);
                } catch (Exception e) {
                }
            }
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String,String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue()) * -1;
            }
        });
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> e: list) {
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }

    private Map<String, String> processBatch(String batch) {
        try {
            String result = HttpUtil.post(batch);
            if (!StringUtils.isEmpty(result)) {
                return getLastestStatus(result);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    private Map<String, String> getLastestStatus(String result) throws Exception {
        System.out.println("");
        System.out.println("");
        Map<String, String> map = new HashMap<>();

        Document doc = Jsoup.parse(result);
        Elements elements = doc.getElementsByAttributeValue("class", "collapsed");
        for (Element element : elements) {
            String trackNum = element.attr("aria-controls");
            if (!StringUtils.isEmpty(trackNum)) {
                String status = element.text();
                System.out.println(trackNum);
                System.out.println(status);
                map.put(trackNum, status);
            }
        }
        return map;
    }

}
