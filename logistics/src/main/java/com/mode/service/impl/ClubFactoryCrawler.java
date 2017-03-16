package com.mode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mode.common.ClubFactoryData;
import com.mode.common.ClubFactoryItemDetailResponse;
import com.mode.common.ClubFactoryItemListResponse;
import com.mode.common.ClubFactoryRequestBody;
import com.mode.common.HttpUtil;
import com.mode.dao.ClubFactoryDataDao;

/**
 * Created by zhaoweiwei on 17/1/9.
 */
@Service
public class ClubFactoryCrawler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClubFactoryDataDao clubFactoryDataDao;

    private static final String itemCountUrl = "http://app.fromfactory.club/bc/shop/itemsCount";
    private static final String itemListUrl = "http://app.fromfactory.club/bc/shop/items";
    private static final String itemDetail = "http://app.fromfactory.club/bc/shop/product/content/";

    @Value("${club.factory.limit:'50'}")
    private Integer limit;


    private Integer getItemCount() throws Exception {
        String requestBody = genItemCountRequestBody();
        String response = HttpUtil.post(itemCountUrl, requestBody);
        return objectMapper.readTree(response).get("result").asInt();
    }

    private ClubFactoryItemListResponse getItemSummary(Integer currentPage) throws Exception {
        String requestBody = genItemListRequestBody(currentPage, limit);
        String response = HttpUtil.post(itemListUrl, requestBody);
        return objectMapper.readValue(response, ClubFactoryItemListResponse.class);
    }

    private String getItemDetail(Long id) throws Exception {
        String requestBody = "{\"jsonrpc\": \"2.0\", \"method\": \"call\", \"params\": {}, " +
                "\"id\": 0}";
        String response = HttpUtil.post(itemDetail + id, requestBody);
        return response;
    }

    private String genItemCountRequestBody() throws Exception {
        ClubFactoryRequestBody body = new ClubFactoryRequestBody();
        ClubFactoryRequestBody.ItemFilter itemFilter = new ClubFactoryRequestBody.ItemFilter();
        itemFilter.setCurrentPage(1);
        ClubFactoryRequestBody.Params params = new ClubFactoryRequestBody.Params();
        params.setItem_filter(itemFilter);
        body.setParams(params);
        return objectMapper.writeValueAsString(body);
    }

    private String genItemListRequestBody(Integer currentPage, Integer limit) throws
            Exception {
        ClubFactoryRequestBody body = new ClubFactoryRequestBody();
        ClubFactoryRequestBody.ItemFilter itemFilter = new ClubFactoryRequestBody.ItemFilter();
        itemFilter.setCurrentPage(currentPage);
        itemFilter.setLimit(limit);
        ClubFactoryRequestBody.Params params = new ClubFactoryRequestBody.Params();
        params.setItem_filter(itemFilter);
        body.setParams(params);
        return objectMapper.writeValueAsString(body);
    }

    public void process() throws Exception {
        Integer itemCount = getItemCount();
        System.out.println("Total itemCount -> " + itemCount);
        if (itemCount != null && itemCount > 0) {
            Integer pagenum = (int) Math.ceil((double) itemCount / limit);
            for (int i = 1; i <= pagenum; i ++) {
                System.out.println("Start process pagenum -> " + i);
                try {
                    ClubFactoryItemListResponse clubFactoryItemListResponse = getItemSummary(i);
                    for (ClubFactoryItemListResponse.ItemSummary itemSummary :
                            clubFactoryItemListResponse.getResult()) {
                        Long itemId = null;
                        try {
                            itemId = itemSummary.getId();
                            String imageUrl = itemSummary.getImage_url();
                            if (itemId != null && !StringUtils.isEmpty(imageUrl)) {
                                String response = getItemDetail(itemSummary.getId());
                                if (!StringUtils.isEmpty(response)) {
                                    ClubFactoryItemDetailResponse clubFactoryItemDetailResponse =
                                            objectMapper.readValue(response, ClubFactoryItemDetailResponse.class);
                                    String urls1688 = String.valueOf(clubFactoryItemDetailResponse.getResult()
                                            .getUrls_1688());
                                    if (!StringUtils.isEmpty(urls1688)
                                            && !"false".equalsIgnoreCase(urls1688)) {
                                        ClubFactoryData clubFactoryData = clubFactoryDataDao
                                                .getClubFactoryData(itemId);
                                        if (clubFactoryData != null) {
                                            clubFactoryData.setItemId(itemId);
                                            clubFactoryData.setImageUrl(imageUrl);
                                            clubFactoryData.setItemDetail(response);
                                            clubFactoryData.setUrls1688(urls1688);
                                            clubFactoryData.setUpdatedAt(System.currentTimeMillis());
                                            clubFactoryDataDao.updateClubFactoryData(clubFactoryData);
                                        } else {
                                            clubFactoryData = new ClubFactoryData();
                                            clubFactoryData.setItemId(itemId);
                                            clubFactoryData.setImageUrl(imageUrl);
                                            clubFactoryData.setItemDetail(response);
                                            clubFactoryData.setUrls1688(urls1688);
                                            clubFactoryData.setCreatedAt(System.currentTimeMillis());
                                            clubFactoryData.setUpdatedAt(System.currentTimeMillis());
                                            clubFactoryDataDao.createClubFactoryData(clubFactoryData);
                                        }
                                    }
                                }

                                //Thread.sleep(1000l);
                            }
                        } catch (Exception e) {
                            System.out.println("Error process itemId -> " + itemId);
                            e.printStackTrace();
                        }
                    }
                    System.out.println("End process pagenum -> " + i);
                } catch (Exception e) {
                    System.out.println("Error process pagenum -> " + i);
                    e.printStackTrace();
                }
            }
        }

    }

    public List<ClubFactoryData> listClubFactoryData(Integer limit, Integer offset) {
        return clubFactoryDataDao.listClubFactoryData(offset, limit);
    }

    public Integer countClubFactoryData() {
        return clubFactoryDataDao.countClubFactoryData();
    }


    public static void main(String[] args) throws Exception {
//        ClubFactoryCrawler c = new ClubFactoryCrawler();
//        Integer itemCount = c.getItemCount();
//        if (itemCount != null && itemCount > 0) {
//            System.out.println((int)Math.ceil((double)100/3));
//            for (int i = 1000; i <= 1000; i ++) {
//                ClubFactoryItemListResponse clubFactoryItemListResponse = c.getItemSummary(i);
//                for (ClubFactoryItemListResponse.ItemSummary itemSummary :
//                        clubFactoryItemListResponse.getResult()) {
//                    ClubFactoryItemDetailResponse clubFactoryItemDetailResponse = c.getItemDetail
//                            (itemSummary.getId());
//                    System.out.println("1688 -> " + clubFactoryItemDetailResponse.getResult().getUrls_1688());
//                }
//            }
//        }
    }
}
