package com.mode.config;

import javax.annotation.PreDestroy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhaoweiwei on 16/6/23.
 */
@Configuration
public class ElasticSearchConfiguration {

    @Value("${datasource.elasticSearch.ip}")
    private String ip;

    @Value("${datasource.elasticSearch.port}")
    private String port;

    @Bean(destroyMethod = "close")
    public TransportClient client() throws Exception {
        return new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(ip, Integer.parseInt(port)));
    }

}
