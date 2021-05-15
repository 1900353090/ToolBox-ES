package org.wcy.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.wcy.es.module.base.BaseDoc;

import java.io.IOException;
import java.io.Serializable;

/**
 * @Author: wcy
 * @Date: 2020/11/3 15:26
 * @Version 1.0
 */
public interface IEsRestClient<T extends BaseDoc> extends Serializable {


    /**
     * 判断索引是否存在
     * @param indices
     * @return
     * @throws IOException
     */
    boolean existIndex(String... indices) throws IOException;

    /**
     * 搜索【非高亮】
     * @param queryParam   查询需要的参数
     * @param queryBuilder 查询构造器
     * @return
     */
    IEsPage<T> search(EsQueryParam queryParam, QueryBuilder queryBuilder);

    /**
     * 高亮查询
     * @param queryParam   查询需要的参数
     * @param queryBuilder 查询构造器
     * @return
     */
    IEsPage<T> highlightSearch(EsQueryParam queryParam, QueryBuilder queryBuilder);

    /**
     * 批量修改【根据查询条件】
     * @param updateQueryParam  批量修改的条件参数
     * @param queryBuilder 构造器
     * @return
     */
    BulkByScrollResponse updateByQuery(EsUpdateQueryParam updateQueryParam, QueryBuilder queryBuilder);

    /**
     * 单条修改
     * @param updateParam 修改的条件参数
     * @return
     */
    UpdateResponse update(EsUpdateParam updateParam);

    /**
     * 根据Id删除
     * @param deleteParam 删除的条件参数
     * @return
     */
    DeleteResponse deleteById(EsDeleteParam deleteParam);

    /**
     * 获取总数
     * @param queryBuilder
     * @param indices
     * @return
     */
    Long getCount(QueryBuilder queryBuilder, String... indices);
}
