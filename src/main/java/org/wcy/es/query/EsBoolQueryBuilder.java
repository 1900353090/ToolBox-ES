package org.wcy.es.query;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.wcy.es.condition.QueryCondition;
import org.wcy.es.condition.QueryConditionFactory;
import java.util.List;
import java.util.Objects;

/**
 * @Author: wcy
 * @Date: 2020/11/11 10:31
 * @Version 1.0
 */
public class EsBoolQueryBuilder extends BoolQueryBuilder {


    /**
     * Adds a query that <b>must</b> appear in the matching documents and will
     * contribute to scoring. No {@code null} value allowed.
     */
    @Override
    public BoolQueryBuilder must(QueryBuilder queryBuilder) {
        QueryCondition build = QueryConditionFactory.build(queryBuilder);
        if (Objects.isNull(build) || build.condition(queryBuilder)) {
            return super.must(queryBuilder);
        }
        return this;
    }

    /**
     * Gets the queries that <b>must</b> appear in the matching documents.
     */
    @Override
    public List<QueryBuilder> must() {
        return super.must();
    }

    /**
     * Adds a query that <b>must</b> appear in the matching documents but will
     * not contribute to scoring. No {@code null} value allowed.
     */
    @Override
    public BoolQueryBuilder filter(QueryBuilder queryBuilder) {
        QueryCondition build = QueryConditionFactory.build(queryBuilder);
        if (Objects.isNull(build) || build.condition(queryBuilder)) {
            return super.filter(queryBuilder);
        }
        return this;
    }

    /**
     * Gets the queries that <b>must</b> appear in the matching documents but don't contribute to scoring
     */
    @Override
    public List<QueryBuilder> filter() {
        return super.filter();
    }

    /**
     * Adds a query that <b>must not</b> appear in the matching documents.
     * No {@code null} value allowed.
     */
    @Override
    public BoolQueryBuilder mustNot(QueryBuilder queryBuilder) {
        QueryCondition build = QueryConditionFactory.build(queryBuilder);
        if (Objects.isNull(build) || build.condition(queryBuilder)) {
            return super.mustNot(queryBuilder);
        }
        return this;
    }

    /**
     * Gets the queries that <b>must not</b> appear in the matching documents.
     */
    @Override
    public List<QueryBuilder> mustNot() {
        return super.mustNot();
    }

    /**
     * Adds a clause that <i>should</i> be matched by the returned documents. For a boolean query with no
     * {@code MUST} clauses one or more <code>SHOULD</code> clauses must match a document
     * for the BooleanQuery to match. No {@code null} value allowed.
     *
     * @see #minimumShouldMatch(int)
     */
    @Override
    public BoolQueryBuilder should(QueryBuilder queryBuilder) {
        QueryCondition build = QueryConditionFactory.build(queryBuilder);
        if (Objects.isNull(build) || build.condition(queryBuilder)) {
            return super.should(queryBuilder);
        }
        return this;
    }

}
