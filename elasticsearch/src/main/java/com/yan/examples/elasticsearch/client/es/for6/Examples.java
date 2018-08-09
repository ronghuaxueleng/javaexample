package com.yan.examples.elasticsearch.client.es.for6;

public class Examples {
	private static ApisFor6 apis = null;

	public Examples() throws Exception {
		apis = ApisFor6.getInstance();
	}
	
	public static void main(String[] args) throws Exception {
		new Examples();
		//创建索引
		apis.createIndex("index-test");
	}
}
