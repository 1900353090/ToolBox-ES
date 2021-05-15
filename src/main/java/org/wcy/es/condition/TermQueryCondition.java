package org.wcy.es.condition;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: wcy
 * @Date: 2020/11/10 17:41
 * @Version 1.0
 */
@Component
public class TermQueryCondition implements QueryCondition {

    private static TermQueryCondition condition = null;

    private TermQueryCondition(){}

    public static TermQueryCondition getInstance(){
        if(condition == null){
            synchronized (TermQueryCondition.class) {
                condition = new TermQueryCondition();
            }
        }
        return condition;
    }

    @Override
    public boolean condition(QueryBuilder queryBuilder) {
        TermQueryBuilder termQueryBuilder = (TermQueryBuilder)queryBuilder;
        termQueryBuilder.fieldName();
        Object value = termQueryBuilder.value();
        return Objects.nonNull(value) && StringUtils.isNotBlank(value.toString());
    }
}
