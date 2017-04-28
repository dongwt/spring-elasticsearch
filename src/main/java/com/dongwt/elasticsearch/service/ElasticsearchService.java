package com.dongwt.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: dongwt
 * @create: 2017-04-27 18:17
 **/
@Service
public class ElasticsearchService{

    @Autowired
    private Client client;

    public boolean ping() {
        try {
            ActionFuture<ClusterHealthResponse> health = client.admin().cluster().health(new ClusterHealthRequest());
            ClusterHealthStatus status = health.actionGet().getStatus();
            if (status.value() == ClusterHealthStatus.RED.value()) {
                throw new RuntimeException("elasticsearch cluster health status is red.");
            }

            System.out.println(JSONObject.toJSONString(health.actionGet()));

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void prepareHealth(){
        ClusterHealthResponse response = client.admin().cluster().prepareHealth("department")
                .setWaitForYellowStatus()
                .setTimeout(TimeValue.timeValueSeconds(5))
                .get();
        System.out.println(JSONObject.toJSONString(response));
    }


    public GetResponse queryById(String id){
        GetResponse response = client.prepareGet("department","employee",id).get();
        System.out.println(JSONObject.toJSONString(response));
        return response;
    }


}
