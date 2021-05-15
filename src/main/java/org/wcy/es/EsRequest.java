package org.wcy.es;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wcy
 * @Date: 2020/11/3 13:22
 * @Version 1.0
 */
public class EsRequest {

    public static DeleteRequest buildDeleteRequest(EsDeleteParam deleteParam){
        DeleteRequest deleteRequest = new DeleteRequest(deleteParam.getIndex());
        deleteRequest.id(deleteParam.getId());
        deleteRequest.timeout(new TimeValue(deleteParam.getTimeout(), TimeUnit.SECONDS));
        return deleteRequest;
    }

    public static UpdateByQueryRequest buildUpdateByQueryRequest(EsUpdateQueryParam updateQueryParam,QueryBuilder queryBuilder){
        UpdateByQueryRequest request = new UpdateByQueryRequest(updateQueryParam.getIndices());
        request.setQuery(queryBuilder);
        request.setScript(updateQueryParam.getScript());
        request.setTimeout(new TimeValue(updateQueryParam.getTimeout(), TimeUnit.SECONDS));
        return request;
    }

    public static UpdateRequest buildUpdateRequest(EsUpdateParam updateParam){
        UpdateRequest request = new UpdateRequest(updateParam.getIndex(),updateParam.getId());
        request.timeout(new TimeValue(updateParam.getTimeout(), TimeUnit.SECONDS));
        return request;
    }

    /**
     * 构造 搜索请求
     * @param queryParam
     * @param queryBuilder
     * @return
     */
    public static SearchRequest buildSearchRequest(EsQueryParam queryParam, QueryBuilder queryBuilder){
        //创建搜索请求,指定要查询的 索引
        SearchRequest searchRequest = new SearchRequest(queryParam.getIndices());
        //构建搜索条件源
        SearchSourceBuilder sourceBuilder = queryParam.getSourceBuilder();
        sourceBuilder.query(queryBuilder);

        sourceBuilder = EsRequest.decideHighlight(sourceBuilder,queryParam);

        searchRequest.source(sourceBuilder);

        return searchRequest;
    }

    /**
     * 构造 统计 count 请求
     * @param queryBuilder
     * @param indices
     * @return
     */
    public static CountRequest buildCountRequest(QueryBuilder queryBuilder,String... indices){
        CountRequest countRequest = new CountRequest(indices);
        countRequest.query(queryBuilder);
        //超时时间设置
        return countRequest;
    }

    /**
     * 判断是否需要 高亮
     * @param sourceBuilder
     * @param queryParam
     * @return
     */
    private static SearchSourceBuilder decideHighlight(SearchSourceBuilder sourceBuilder,EsQueryParam queryParam){
        if (Objects.isNull(queryParam.getHighlight()) || false == queryParam.getHighlight()){
            return sourceBuilder;
        }
        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>");
        /**
         * 是否匹配查询字段 高亮：
         * 设置为true,表示 即只能查询字段才能 高亮，
         * 设置为false，表示 除了查询字段外，其他字段只要包含搜索内容的，都高亮
         */
        highlightBuilder.requireFieldMatch(queryParam.getRequireFieldMatch());
        sourceBuilder.highlighter(highlightBuilder);
        return sourceBuilder;
    }


}
