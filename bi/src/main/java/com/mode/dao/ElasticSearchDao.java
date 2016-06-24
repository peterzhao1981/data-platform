package com.mode.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaoweiwei on 16/6/23.
 */
@Repository
public class ElasticSearchDao {

    @Autowired
    private Client client;

    public List<Long> getRequsetCountByHour(Long startTs, Long endTs) throws Exception {
        List<Long> list = new ArrayList<Long>();
        try {
            SearchResponse response = client.prepareSearch("logstash-*").setSearchType(SearchType
                    .COUNT).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),
                    FilterBuilders.andFilter(FilterBuilders.termFilter("response", "200")
                            , FilterBuilders.rangeFilter("timestamp").gte(startTs).lt
                                    (endTs)
                    ))).addAggregation(AggregationBuilders.dateHistogram("over_time").field
                    ("timestamp").interval(DateHistogram.Interval.HOUR).timeZone("+08:00")).get();

            DateHistogram histogram = response.getAggregations().get("over_time");
            List<DateHistogram.Bucket> buckets = ( List<DateHistogram.Bucket>)histogram
                    .getBuckets();

            for (DateHistogram.Bucket bucket : buckets)
                list.add(bucket.getDocCount());

            return list;
        } catch (Exception e) {
            throw e;
        }
    }
}
