package org.wcy.es.condition;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: wcy
 * @Date: 2020/11/10 17:41
 * @Version 1.0
 */
@Component
public class MatchQueryCondition implements QueryCondition {

    private static MatchQueryCondition condition = null;

    private MatchQueryCondition(){}

    public static MatchQueryCondition getInstance(){
        if(condition == null){
            synchronized (MatchQueryCondition.class) {
                condition = new MatchQueryCondition();
            }
        }
        return condition;
    }

    @Override
    public boolean condition(QueryBuilder queryBuilder) {
        MatchQueryBuilder matchQueryBuilder = (MatchQueryBuilder)queryBuilder;
        matchQueryBuilder.fieldName();
        Object value = matchQueryBuilder.value();
        return Objects.nonNull(value) && StringUtils.isNotBlank(value.toString());
    }
}
