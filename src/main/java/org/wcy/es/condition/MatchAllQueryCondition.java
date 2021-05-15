package org.wcy.es.condition;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author: wcy
 * @Date: 2020/11/10 17:41
 * @Version 1.0
 */
@Component
public class MatchAllQueryCondition implements QueryCondition {

    private static MatchAllQueryCondition condition = null;

    private MatchAllQueryCondition(){}

    public static MatchAllQueryCondition getInstance(){
        if(condition == null){
            synchronized (MatchAllQueryCondition.class) {
                condition = new MatchAllQueryCondition();
            }
        }
        return condition;
    }

    @Override
    public boolean condition(QueryBuilder queryBuilder) {
        return true;
    }
}
