package org.wcy.es.query;

import org.elasticsearch.index.query.QueryBuilders;

/**
 * @Author: wcy
 * @Date: 2020/11/11 10:29
 * @Version 1.0
 */
public class EsQueryBuilders {

    private EsQueryBuilders() {
    }

    private QueryBuilders queryBuilders;

    public static EsBoolQueryBuilder boolQuery() {
        return new EsBoolQueryBuilder();
    }
}
