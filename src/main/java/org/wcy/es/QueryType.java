package org.wcy.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wcy.es.condition.*;

/**
 * @Author: wcy
 * @Date: 2020/11/10 13:57
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum QueryType {

    MATCH("match","必须匹配[分词匹配]", MatchQueryCondition.getInstance()),
    MATCH_PHRASE("match_phrase","短语匹配[不分词匹配]", MatchPhraseQueryCondition.getInstance()),
    MATCH_ALL("match_all","全查", MatchAllQueryCondition.getInstance()),
    NESTED("nested","内嵌对象查询", NestedQueryCondition.getInstance()),
    TERM("term","精准查询[不分词匹配]", TermQueryCondition.getInstance());


    private String name;
    private String desc;
    private QueryCondition condition;
}
