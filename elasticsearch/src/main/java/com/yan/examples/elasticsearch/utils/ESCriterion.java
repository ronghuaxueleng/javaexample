package com.yan.examples.elasticsearch.utils;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * 条件接口
 * @author caoqiang
 * @date: 2018年6月13日 上午11:51:15
 */
public interface ESCriterion {
	public enum Operator {
		TERM, TERMS, RANGE, FUZZY, QUERY_STRING, MISSING
	}

	public enum MatchMode {
		START, END, ANYWHERE
	}

	public enum Projection {
		MAX, MIN, AVG, LENGTH, SUM, COUNT
	}

	public List<QueryBuilder> listBuilders();
}