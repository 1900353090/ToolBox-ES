package org.wcy.es.condition;

import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Objects;

/**
 * @Author: wcy
 * @Date: 2020/11/11 13:29
 * @Version 1.0
 */
public class NestedQueryCondition implements QueryCondition {

    private static NestedQueryCondition condition = null;

    private NestedQueryCondition(){}

    public static NestedQueryCondition getInstance(){
        if(condition == null){
            synchronized (NestedQueryCondition.class) {
                condition = new NestedQueryCondition();
            }
        }
        return condition;
    }

    @Override
    public boolean condition(QueryBuilder queryBuilder) {
        NestedQueryBuilder nestedQueryBuilder = (NestedQueryBuilder)queryBuilder;
        /**
         * 获取查询对象build
         */
        QueryBuilder query = nestedQueryBuilder.query();
        /**
         * 获取 上面查询对象build 的 值状态方法
         */
        QueryCondition build = QueryConditionFactory.build(query);
        if (Objects.isNull(build)) {
            return true;
        }
        /**
         * 验证
         */
        return build.condition(query);
    }
}
