package org.wcy.es.condition;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: wcy
 * @Date: 2020/11/10 17:41
 * @Version 1.0
 */
@Component
public class MatchPhraseQueryCondition implements QueryCondition {

    private static MatchPhraseQueryCondition condition = null;

    private MatchPhraseQueryCondition(){}

    public static MatchPhraseQueryCondition getInstance(){
        if(condition == null){
            synchronized (MatchPhraseQueryCondition.class) {
                condition = new MatchPhraseQueryCondition();
            }
        }
        return condition;
    }

    @Override
    public boolean condition(QueryBuilder queryBuilder) {
        MatchPhraseQueryBuilder phraseQueryBuilder = (MatchPhraseQueryBuilder)queryBuilder;
        phraseQueryBuilder.fieldName();
        Object value = phraseQueryBuilder.value();
        return Objects.nonNull(value) && StringUtils.isNotBlank(value.toString());
    }
}
