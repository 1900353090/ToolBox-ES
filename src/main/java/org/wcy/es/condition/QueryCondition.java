package org.wcy.es.condition;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @Author: wcy
 * @Date: 2020/11/10 16:49
 * @Version 1.0
 */
public interface QueryCondition<T extends QueryBuilder> {

    /**
     * 算出查询的值，是否满足查询的状态
     * @param t
     * @return
     */
    boolean condition(T t);
}
