package com.dongwt.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: dongwt
 * @create: 2017-04-27 17:53
 **/
@Data
@Document(indexName = "department", type = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1769757659867770597L;

    @Id
    @Field(index = FieldIndex.not_analyzed, store = true)
    private Integer id;
    @Field(type = FieldType.String, analyzer="ik", searchAnalyzer="ik", store = true)
    private String name;
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private Integer age;
    @Field(type = FieldType.String, analyzer="ik", searchAnalyzer="ik", store = true)
    private String about;
    @Field(type = FieldType.Date,index = FieldIndex.not_analyzed, store = true, format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private Integer status;
}
