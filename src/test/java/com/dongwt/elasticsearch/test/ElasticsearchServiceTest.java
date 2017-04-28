package com.dongwt.elasticsearch.test;

import com.alibaba.fastjson.JSONObject;
import com.dongwt.elasticsearch.model.Employee;
import com.dongwt.elasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.internal.InternalSearchHit;
import org.elasticsearch.search.internal.InternalSearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: dongwt
 * @create: 2017-04-27 18:29
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:spring/spring-mvc.xml"})
public class ElasticsearchServiceTest {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private Client client;

    private String index = "department";

    private String type = "employee";


    @Test
    public void ping() {
        elasticsearchService.ping();
    }

    @Test
    public void queryById() {
        elasticsearchService.queryById("1");
    }

    @Test
    public void prepareHealth() {
        elasticsearchService.prepareHealth();
    }

    @Test
    public void test(){
        SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.matchQuery(
                "name.pinyin",
                "刘"
        ))
                .execute().actionGet();
        InternalSearchHits searchHits = (InternalSearchHits) response.getHits();
        List<Employee> list = new ArrayList<>();
        for(SearchHit searchHit : searchHits){
            InternalSearchHit internalSearchHit = (InternalSearchHit)searchHit;
            Employee employee = JSONObject.parseObject(internalSearchHit.getSourceAsString(),Employee.class);
            list.add(employee);
        }

        System.out.println(JSONObject.toJSONString(list));
    }

}
