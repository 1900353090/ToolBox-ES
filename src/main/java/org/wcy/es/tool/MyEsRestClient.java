package org.wcy.es.tool;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;
import org.wcy.es.*;
import org.wcy.es.annotation.ESColumn;
import org.wcy.es.module.base.BaseDoc;
import org.springframework.lang.NonNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>Title : MyEsRestClient.java</p>
 * <p>Description : </p>
 * <p>DevelopTools : IntelliJ IDEA 2018.2.3 x64</p>
 * <p>DevelopSystem : Windows 10</p>
 * <p>Company : org.wcy</p>
 * @author : WangChenYang
 * @date : 2020/11/6 11:19
 * @version : 0.0.1
 */
@Component("myEsRestClient")
@Primary
public class MyEsRestClient<T> {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Data
    @Accessors(chain = true)
    public static class SEResult {
        private SearchHit[] hits;
        private long count;
    }

    /**
     * @Description: 分页查询es
     * @Param: queryParam查询参数，queryBuilder构造器，highlight是否高亮
     * @return:
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/6-11:37
    */
    public IEsPage<T> searchPage(EsQueryParam queryParam, QueryBuilder queryBuilder, boolean highlight){
        queryParam.setHighlight(highlight);
        SearchRequest searchRequest = EsRequest.buildSearchRequest(queryParam, queryBuilder);
        return this.searchPage(searchRequest, highlight);
    }

    /**
     * @Description: 查询es
     * @Param: highlight是否高亮
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/6-11:35
    */
    private IEsPage<T> searchPage(SearchRequest searchRequest, boolean highlight){
        SEResult seResult = this.getHits(searchRequest);
        SearchHit[] hits = seResult.getHits();
        if (Objects.isNull(hits)){
            return new EsPage<>();
        }
        List<T> records = new ArrayList<>();
        for (SearchHit hit:hits){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Object cla;
            if (Objects.nonNull(cla=sourceAsMap.get("_class"))){
                try {
                    Class<?> clazz = Class.forName(cla.toString());
                    T data = (T) JSON.parseObject(JSON.toJSONString(sourceAsMap), clazz);
                    if(highlight && String.class.equals(clazz)) {
                        //获取高亮字段
                        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                        data = (T) ElasticSearchUtil.setObject(highlightFields, clazz, data);
                    }
                    records.add(data);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return new EsPage<T>().setRecords(records).setTotal(seResult.getCount());
    }

    /**
     * @Description: 获取es结果集
     * @Author: 王晨阳
     * @LastUpdater: 王晨阳
     * @Date: 2020/11/6-11:36
    */
    private SEResult getHits(SearchRequest searchRequest){
        String[] indices = searchRequest.indices();
        try {
            this.existIndex(indices);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHit[] hits = search.getHits().getHits();
        if (ArrayUtils.isEmpty(hits)){
            return new SEResult();
        }
        long count = 0;
        try {
            count = search.getHits().getTotalHits().value;
        }catch (Exception e) {
            e.getMessage();
        }
        return new SEResult().setHits(hits).setCount(count);
    }

    /**
     * 获取总数
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:56
     * @version 0.0.1
    */
    public Long getCount(QueryBuilder queryBuilder,String... indices){
        CountRequest countRequest = EsRequest.buildCountRequest(queryBuilder,indices);
        CountResponse count = null;
        try {
            count = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count.getCount();
    }

    /**
     * 批量修改【根据查询条件】
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:56
     * @version 0.0.1
    */
    public BulkByScrollResponse updateByQuery(EsUpdateQueryParam updateQueryParam, QueryBuilder queryBuilder){
        UpdateByQueryRequest updateByQueryRequest = EsRequest.buildUpdateByQueryRequest(updateQueryParam, queryBuilder);
        BulkByScrollResponse bulkByScrollResponse =null;
        try {
            bulkByScrollResponse = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bulkByScrollResponse;
    }

    /**
     * 单条修改
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:56
     * @version 0.0.1
     */
    public BulkByScrollResponse updateById(Map<String,Object> paras, @NonNull String indexName, @NonNull String id){
        if(StringUtils.isBlank(indexName)) {
            throw new NullPointerException("indexName is null");
        }
        if(StringUtils.isBlank(id)) {
            throw new NullPointerException("id is null");
        }
        if(paras.isEmpty()) {
            throw new NullPointerException("paras is null");
        }
        //构建搜索条件源
        QueryBuilder queryBuilders = QueryBuilders.boolQuery()
                .must(
                        QueryBuilders.termQuery("id", id)
                );
        StringBuilder idOrCode = new StringBuilder();
        for (String key:paras.keySet()) {
            idOrCode.append("ctx._source.").append(key).append("=params.").append(key).append(";");
        }
        Script painless = new Script(ScriptType.INLINE, "painless", idOrCode.toString(), paras);
        EsUpdateQueryParam updateQueryParam = new EsUpdateQueryParam(painless, indexName);
        return this.updateByQuery(updateQueryParam, queryBuilders);
    }

    /**
     * 单条修改
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:56
     * @version 0.0.1
    */
    public UpdateResponse update(EsUpdateParam updateParam){
        UpdateRequest updateRequest = EsRequest.buildUpdateRequest(updateParam);
        updateRequest.doc(updateParam.getJsonStr(), XContentType.JSON);
        UpdateResponse update =null;
        try {
            update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return update;
    }

    /**
     * 根据Id删除
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:56
     * @version 0.0.1
    */
    public DeleteResponse deleteById(EsDeleteParam deleteParam){
        DeleteRequest deleteRequest = EsRequest.buildDeleteRequest(deleteParam);
        DeleteResponse delete = null;
        try {
            delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return delete;
    }

    /**
     * 判断索引是否存在
     * @author 王晨阳
     * @lastUpdaterAuthor 王晨阳
     * @date 2021/4/6-18:57
     * @version 0.0.1
    */
    public boolean existIndex(String... indices) throws IOException {
        GetIndexRequest indexRequest = new GetIndexRequest(indices);
        boolean exists = restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
        return exists;
    }
}
