package com.mode.service.product;

import java.util.Map;

import com.mode.entity.Product;

/**
 * Created by zhaoweiwei on 16/7/20.
 */
public interface AmazonProductImportService {

    public String productImport(String url) throws Exception;

    public Map<String, String> productImport(Product product);
}
