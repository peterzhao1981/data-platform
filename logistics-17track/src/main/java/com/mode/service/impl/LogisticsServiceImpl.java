package com.mode.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mode.common.HttpUtil;
import com.mode.common.Result;
import com.mode.dao.Logistic17TrackDao;
import com.mode.entity.Logistic17Track;
import com.mode.service.LogisticsService;
import com.sun.source.tree.Tree;

/**
 * Created by zhaoweiwei on 16/12/27.
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Logistic17TrackDao logistic17TrackDao;

    @Value("${logistic.batch.size:'1'}")
    private int logisticsBatchSize;


    @Override
    public void updateLogistics() {
        while (true) {
            List<Logistic17Track> list = logistic17TrackDao.getLogistic17Track(logisticsBatchSize);
            System.out.println(list.size());
            if (list.size() == 0) {
                break;
            }
            List<String> currentBatch = new ArrayList<>();
            for (Logistic17Track logistic17Track : list) {
                System.out.println(logistic17Track.getTrackNum());
                currentBatch.add(logistic17Track.getTrackNum());
            }
            try {
                String response = HttpUtil.post(currentBatch);
                System.out.println(response);
                if (!StringUtils.isEmpty(response)) {
                    Result result = objectMapper.readValue(response, Result.class);
                    if (result != null) {
                        List<Result.Dat> datList = result.getDat();
                        if (datList != null) {
                            for (Result.Dat dat : datList) {
                                String no = dat.getNo();
                                String state = null;
                                String city = null;
                                String zip = null;
                                String userName = null;
                                String startDate = null;
                                if (!StringUtils.isEmpty(no)) {
                                    Result.Track track = dat.getTrack();
                                    if (track != null) {
                                        Result.Z z0 = track.getZ0();
                                        if (z0 != null) {
                                            String d = z0.getD();
                                            if (!StringUtils.isEmpty(d)) {
                                                try {
                                                    String[] s = d.split(",");
                                                    if (s.length > 1) {
                                                        city = s[0].trim();
                                                        if (s[1].trim().length() > 0) {
                                                            s = s[1].trim().split(" ");
                                                            if (s.length > 1) {
                                                                state = s[0];
                                                                zip = s[1];
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        List<Result.Z> z1 = track.getZ1();
                                        if (z1 != null) {
                                            for (Result.Z z : z1) {
                                                if (!StringUtils.isEmpty(z.getZ())) {
//                                                System.out.println(z.getZ());
                                                    if (z.getZ().startsWith("妥投")) {
                                                        try {
                                                            String[] s = z.getZ().split("，");
//                                                        System.out.println(s[0] + s[1]);
                                                            if (s.length > 1) {
                                                                userName = s[1].trim();
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else if (z.getZ().startsWith("美国, 妥投，")) {
                                                        try {
                                                            String[] s = z.getZ().split("，");
//                                                        System.out.println(s[0] + s[1]);
                                                            if (s.length > 1) {
                                                                userName = s[1].trim();
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }

                                            if (z1 != null && z1.size() > 0) {
                                                Result.Z z = z1.get(z1.size() - 1);
                                                if (z != null) {
                                                    startDate = z.getA();
                                                }
                                            }

                                        }
                                        List<Result.Z> z2 = track.getZ2();
                                        // ++++
                                        for (Result.Z z : z2) {
                                            if ("Out for Delivery".equalsIgnoreCase(z.getZ())) {
                                                System.out.println("Out for Delivery");
                                                String d = z.getD();
                                                if (!StringUtils.isEmpty(d)) {
                                                    try {
                                                        String[] s = d.split(",");
                                                        if (s.length > 1) {
                                                            city = s[0].trim();
                                                            if (s[1].trim().length() > 0) {
                                                                s = s[1].trim().split(" ");
                                                                if (s.length > 1) {
                                                                    state = s[0];
                                                                    zip = s[1];
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                        // ----
//                                        if (z2 != null && z2.size() > 0) {
//                                            Result.Z z = z2.get(z2.size() - 1);
//                                            if (z != null) {
//                                                startDate = z.getA();
//                                            }
//                                        }
                                    }
                                }



                                Logistic17Track logistic17Track = new Logistic17Track();
                                logistic17Track.setTrackNum(no);
//                                if (StringUtils.isEmpty(state)) {
//                                    logistic17TrackDao.updateLogistic17TrackCount(no);
//                                } else {
//                                    logistic17Track.setState(state);
//                                    logistic17Track.setCity(city);
//                                    logistic17Track.setZip(zip);
//                                    logistic17Track.setUserName(userName);
//                                    logistic17Track.setStartDate(startDate);
//                                    logistic17Track.setStatus("done");
//                                    logistic17TrackDao.updateLogistic17Track(logistic17Track);
//                                }

                                if (StringUtils.isEmpty(startDate)) {
                                    logistic17TrackDao.updateLogistic17TrackCount(no);
                                } else {
                                    logistic17Track.setState(state);
                                    logistic17Track.setCity(city);
                                    logistic17Track.setZip(zip);
                                    logistic17Track.setUserName(userName);
                                    logistic17Track.setStartDate(startDate);
                                    logistic17Track.setStatus("done");
                                    logistic17TrackDao.updateLogistic17Track(logistic17Track);
                                }

                                System.out.println(no);
                                System.out.println(state);
                                System.out.println(city);
                                System.out.println(zip);
                                System.out.println(userName);
                                System.out.println(startDate);
                                System.out.println("");
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                Thread.sleep(20 * 1000l);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

//    @Override
//    public Map<String, String> getLogistics(List<String> trackNums) {
//        TreeMap<String, String> map = new TreeMap<>();
//
//        int batch = trackNums.size() / logisticsBatchSize;
//
//        for (int i = 0; i <= batch; i ++) {
//            if (i < batch) {
//                List<String> currentBatch = new ArrayList<>();
//                for (int j = 0; j < logisticsBatchSize; j ++) {
//                    currentBatch.add(trackNums.get(i * logisticsBatchSize + j));
//                }
//                if (currentBatch.size() > 0) {
//                    Map<String, String> status = processBatch(currentBatch);
//                    for (Map.Entry<String, String> entry : status.entrySet()) {
//                        map.put(entry.getKey(), entry.getValue());
//                    }
//                }
//                try {
//                    Thread.sleep(1000l);
//                } catch (Exception e) {
//
//                }
//            } else {
//                List<String> currentBatch = new ArrayList<>();
//                for (int j = batch * logisticsBatchSize; j <= trackNums
//                        .size() - 1; j ++) {
//                    currentBatch.add(trackNums.get(j));
//                }
//                if (currentBatch.size() > 0) {
//                    Map<String, String> status = processBatch(currentBatch);
//                    for (Map.Entry<String, String> entry : status.entrySet()) {
//                        map.put(entry.getKey(), entry.getValue());
//                    }
//                    try {
//                        Thread.sleep(1000l);
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//        }
//        return map.descendingMap();
//    }


//    private Map<String, String> processBatch(List<String> batch) {
//        try {
//            String result = HttpUtil.post(batch);
//            System.out.println(result);
//            if (!StringUtils.isEmpty(result)) {
//                return getLastestStatus(result);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }


//    private Map<String, String> getLastestStatus(String result) throws Exception {
//        Map<String, String> map = new HashMap<>();
//        JsonNode dat = objectMapper.readTree(result).get("dat");
//        if (dat != null) {
//            for (int i = 0; i < dat.size(); i ++) {
//                StringBuilder status = new StringBuilder();
//                JsonNode no = dat.get(i).get("no");
//                JsonNode z0 = dat.get(i).get("track").get("z0");
//                if (no != null) {
//                    String noStr = no.asText();
//                    if (!equalsNullOrEmpty(noStr)) {
//                        if (z0 != null) {
//                            // a is timestamp
//                            JsonNode a = z0.get("a");
//                            if (a != null) {
//                                String aStr = a.asText();
//                                if (!equalsNullOrEmpty(aStr)) {
//                                    status.append(aStr);
//                                    status.append(" ");
//                                    // b is unknown
//                                    JsonNode b = z0.get("b");
//                                    if (b != null) {
//                                        String bStr = b.asText();
//                                        if (!equalsNullOrEmpty(bStr)) {
//                                            status.append(bStr);
//                                            status.append(",");
//                                        }
//                                    }
//                                    // c is arrival
//                                    JsonNode c = z0.get("c");
//                                    if (c != null) {
//                                        String cStr = c.asText();
//                                        if (!equalsNullOrEmpty(cStr)) {
//                                            status.append(cStr);
//                                            status.append(",");
//                                        }
//                                    }
//                                    // d is channel
//                                    JsonNode d = z0.get("d");
//                                    if (d != null) {
//                                        String dStr = d.asText();
//                                        if (!equalsNullOrEmpty(dStr)) {
//                                            status.append(dStr);
//                                            status.append(",");
//                                        }
//                                    }
//                                    // z is status
//                                    JsonNode z = z0.get("z");
//                                    if (z != null) {
//                                        String zStr = z.asText();
//                                        if (!equalsNullOrEmpty(zStr)) {
//                                            status.append(zStr);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (status.toString().length() > 0) {
//                            map.put(status.toString(), noStr);
//                        }
//                    }
//                }
//
//
//            }
//        }
//        return map;
//    }

}
