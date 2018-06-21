package com.yan.examples.elasticsearch.utils;

import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
        try {
            ElasticSearchService service = new ElasticSearchService("es", "192.168.1.1", 9300);
            ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
            constructor.must(new ESQueryBuilders().term("gender", "f").range("age", 20, 50));
            constructor.should(new ESQueryBuilders().term("gender", "f").range("age", 20, 50).fuzzy("age", 20));
            constructor.mustNot(new ESQueryBuilders().term("gender", "m"));
            constructor.setSize(15);  //查询返回条数，最大 10000
            constructor.setFrom(11);  //分页查询条目起始位置， 默认0
            constructor.setAsc("age"); //排序

            List<Map<String, Object>> list = service.search("bank", "account", constructor);
            Map<Object, Object> map = service.statSearch("bank", "account", constructor, "state");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
