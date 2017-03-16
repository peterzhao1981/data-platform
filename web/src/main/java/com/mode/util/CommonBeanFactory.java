package com.mode.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mode.service.product.SignedRequestsHelper;

/**
 * Created by zhaoweiwei on 16/7/20.
 */
@Configuration
public class CommonBeanFactory {

    @Value("${mode.amazon.endPoint}")
    private String endpoint;
    @Value("${mode.amazon.awsAccessKeyId}")
    private String awsAccessKeyId;
    @Value("${mode.amazon.awsSecretKey}")
    private String awsSecretKey;

    @Value("${mode.amazon.associateTag}")
    private String associateTag;

    @Bean
    public SignedRequestsHelper signedRequestsHelper() throws Exception {
        return new SignedRequestsHelper(endpoint, awsAccessKeyId, awsSecretKey);
    }
}
