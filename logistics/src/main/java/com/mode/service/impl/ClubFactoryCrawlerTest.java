package com.mode.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mode.common.ClubFactoryItemDetailResponse;
import com.mode.common.ClubFactoryItemListResponse;
import com.mode.common.ClubFactoryRequestBody;
import com.mode.common.HttpUtil;

/**
 * Created by zhaoweiwei on 17/1/9.
 */
public class ClubFactoryCrawlerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static final String itemCountUrl = "http://app.fromfactory.club/bc/shop/itemsCount";
    private static final String itemListUrl = "http://app.fromfactory.club/bc/shop/items";
    private static final String itemDetail = "http://app.fromfactory.club/bc/shop/product/content/";


    private Integer getItemCount() throws Exception {
        String requestBody = genItemCountRequestBody();
        String response = HttpUtil.post(itemCountUrl, requestBody);
        return objectMapper.readTree(response).get("result").asInt();
    }

    private ClubFactoryItemListResponse getItemSummary(Integer currentPage) throws Exception {
        ClubFactoryItemListResponse clubFactoryItemListResponse = null;
        String requestBody = genItemListRequestBody(currentPage, 50);
        String response = HttpUtil.post(itemListUrl, requestBody);
        return objectMapper.readValue(response, ClubFactoryItemListResponse.class);
    }

    private ClubFactoryItemDetailResponse getItemDetail(Long id) throws Exception {
        String requestBody = "{\"jsonrpc\": \"2.0\", \"method\": \"call\", \"params\": {}, " +
                "\"id\": 0}";
        String response = HttpUtil.post(itemDetail + id, requestBody);
        return objectMapper.readValue(response, ClubFactoryItemDetailResponse.class);
    }

    private static String genItemCountRequestBody() throws Exception {
        ClubFactoryRequestBody body = new ClubFactoryRequestBody();
        ClubFactoryRequestBody.ItemFilter itemFilter = new ClubFactoryRequestBody.ItemFilter();
        itemFilter.setCurrentPage(1);
        ClubFactoryRequestBody.Params params = new ClubFactoryRequestBody.Params();
        params.setItem_filter(itemFilter);
        body.setParams(params);
        return objectMapper.writeValueAsString(body);
    }

    private static String genItemListRequestBody(Integer currentPage, Integer limit) throws
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


    public static void main(String[] args) throws Exception {
        ClubFactoryCrawlerTest c = new ClubFactoryCrawlerTest();
        Integer itemCount = c.getItemCount();
        if (itemCount != null && itemCount > 0) {
            for (int i = 1; i <= 1; i ++) {
                ClubFactoryItemListResponse clubFactoryItemListResponse = c.getItemSummary(i);
                for (ClubFactoryItemListResponse.ItemSummary itemSummary :
                     clubFactoryItemListResponse.getResult()) {
                    ClubFactoryItemDetailResponse clubFactoryItemDetailResponse = c.getItemDetail
                            (itemSummary.getId());
                    System.out.println("1688 -> " + clubFactoryItemDetailResponse.getResult().getUrls_1688());
                }
            }
        }
    }
}
