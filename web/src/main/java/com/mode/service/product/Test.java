package com.mode.service.product;

import java.util.HashMap;
import java.util.Map;

import com.mode.base.AppConfig;

/**
 * Created by zhaoweiwei on 17/1/5.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String endpoint = "ecs.amazonaws.com";
        String awsAccessKeyId = "AKIAICSPAKS22NGF4URA";
        String awsSecretKey = "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";
        String associateTag = "20023e-20";
        String childASIN = "B00FS2N32M";

        SignedRequestsHelper helper = new SignedRequestsHelper(endpoint, awsAccessKeyId, awsSecretKey);;
        Map<String, String> params = new HashMap<String, String>();
//        params.put(AppConfig.AMAZON_SERVICE_NAME, AppConfig.AMAZON_SERVICE_NAME_VALUE);
//        params.put(AppConfig.AMAZON_SERVICE_ACCOCIATE_TAG, associateTag);
//        //params.put(AppConfig.AMAZON_SERVICE_OPERATION, AppConfig.AMAZON_SERVICE_OPERATION_VALUE);
//        params.put(AppConfig.AMAZON_SERVICE_OPERATION, "ItemSearch");
//        params.put("SearchIndex", "Fashion");
//        params.put("BrowseNode", "7141124011");
//        params.put("Sort", "salerank");
//        params.put("ItemPage", "1");

        //params.put(AppConfig.AMAZON_SERVICE_ITEM_ID, childASIN);
//        params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, AppConfig.AMAZON_SERVICE_RESPONSE_GROUP_LARGE);
        //params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, "TopSellers");


        // BrowseNode
//        params.put(AppConfig.AMAZON_SERVICE_NAME, AppConfig.AMAZON_SERVICE_NAME_VALUE);
//        params.put(AppConfig.AMAZON_SERVICE_ACCOCIATE_TAG, associateTag);
//        params.put(AppConfig.AMAZON_SERVICE_OPERATION, "BrowseNodeLookup");
//        params.put("BrowseNodeId", "1040660");
//        params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, "BrowseNodeInfo");

        // Top Seller
//        params.put(AppConfig.AMAZON_SERVICE_NAME, AppConfig.AMAZON_SERVICE_NAME_VALUE);
//        params.put(AppConfig.AMAZON_SERVICE_ACCOCIATE_TAG, associateTag);
//        params.put(AppConfig.AMAZON_SERVICE_OPERATION, "BrowseNodeLookup");
//        params.put("BrowseNodeId", "1040660");
//        params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, "TopSellers");
//        params.put("ItemPage", "10");

        // Reviewrank
        params.put(AppConfig.AMAZON_SERVICE_NAME, AppConfig.AMAZON_SERVICE_NAME_VALUE);
        params.put(AppConfig.AMAZON_SERVICE_ACCOCIATE_TAG, associateTag);
        params.put(AppConfig.AMAZON_SERVICE_OPERATION, "ItemSearch");
        params.put("SearchIndex", "Apparel");
        params.put("BrowseNode", "1045268");
        params.put("Sort", "salesrank");
        params.put("ItemPage", "1");



        String requestUrl = helper.sign(params);
        System.out.println(requestUrl);


    }
}
