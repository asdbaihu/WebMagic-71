package com.fzd.util;

/**
 * Created by SRKJ on 2017/7/19.
 */

import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

public interface ESCriterion {
    public enum Operator {
        TERM, TERMS, RANGE, FUZZY, QUERY_STRING, MISSING, MATCH, EXISTS, WILDCARD
    }

    public enum MatchMode {
        START, END, ANYWHERE
    }

    public enum Projection {
        MAX, MIN, AVG, LENGTH, SUM, COUNT
    }

    public List<QueryBuilder> listBuilders();
}