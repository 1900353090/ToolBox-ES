package org.wcy.es;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.wcy.es.module.QueryParam;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @Author: wcy
 * @Date: 2020/11/3 14:42
 * @Version 1.0
 */
@Accessors(chain = true)
public class EsQueryParam implements Serializable {

    /**
     * 默认页码，第一页
     */
    private static final int DEFAULT_PAGE_NO = 1;
    /**
     * 默认分页大小，默认10条记录
     */
    private static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认1秒超时
     */
    private static final int DEFAULT_TIME_OUT = 1;

    /**
     * 页码
     */
    @Getter
    @Setter
    private Integer pageNo = DEFAULT_PAGE_NO;
    /**
     * 分页大小
     */
    @Getter
    @Setter
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 超时时间，单位 秒
     */
    @Getter
    @Setter
    private Integer timeout = DEFAULT_TIME_OUT;
    /**
     * 是否匹配查询字段 高亮：
     * 设置为true,表示 即只能查询字段才能 高亮，
     * 设置为false，表示 除了查询字段外，其他字段只要包含搜索内容的，都高亮
     */
    @Getter
    @Setter
    private Boolean requireFieldMatch = true;
    /**
     * 是否高亮，默认为false
     */
    @Getter
    @Setter
    private Boolean highlight = false;
    /**
     * 要查询的 索引列表
     */
    @Getter
    @Setter
    private String[] indices;

    /**
     * 搜索源构造器，用于 排序
     */
    @Setter
    private SearchSourceBuilder sourceBuilder;


    public EsQueryParam(boolean one,String... indices){
        this.indices = indices;
        if (one){
            this.pageNo = 1;
            this.pageSize = 1;
        }
    }
    public EsQueryParam(QueryParam queryParam, String... indices){
        this.indices = indices;
        this.pageNo = queryParam.getPageNo();
        this.pageSize = queryParam.getPageSize();
    }
    public EsQueryParam(Integer pageNo,Integer pageSize,String... indices){
        this.indices = indices;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public SearchSourceBuilder getSourceBuilder(){
        if (Objects.isNull(sourceBuilder)){
            sourceBuilder = new SearchSourceBuilder();
        }
        Integer sum = 0;
        Integer pageSize = sourceBuilder.size()==-1?this.getPageSize():sourceBuilder.size();
        Integer form = sourceBuilder.from()==-1?(sum=(this.getPageNo()-1)*pageSize)>0?sum-1:0:sourceBuilder.from();
        sourceBuilder.from(form).size(pageSize)
                .timeout(new TimeValue(this.getTimeout(), TimeUnit.SECONDS));
        return sourceBuilder;
    }
}
