package org.wcy.es.condition;

import org.elasticsearch.index.query.QueryBuilder;
import org.wcy.es.QueryType;

/**
 * @Author: wcy
 * @Date: 2020/11/10 17:45
 * @Version 1.0
 */
public class QueryConditionFactory {

    /**
     * 根据查询构造器，构建一个 查询的值状态对象
     * @param queryBuilder 查询构造器
     * @return
     */
    public static QueryCondition build(QueryBuilder queryBuilder){
        String name = queryBuilder.getName();
        try {
            QueryType queryType = QueryType.valueOf(name.toUpperCase());
            QueryCondition condition = queryType.getCondition();
            return condition;
        }catch (Exception e){
            return null;
        }
    }
}
