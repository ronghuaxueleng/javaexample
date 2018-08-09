package com.yan.examples.elasticsearch.client.es.for6;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.yan.examples.elasticsearch.client.es.ESClient;

public class ApisFor6 {
	private Client client = null;
	private static ApisFor6 apis = null;
	
	private ApisFor6() throws Exception {
		client = ESClient.getClient();
	} 
	
	public static ApisFor6 getInstance() throws Exception {
		if (apis == null) {
			apis = new ApisFor6();
		}
		return apis;
	}
	
	/**
	 * 创建一个索引
	 * 
	 * @param indexName
	 *            索引名
	 */
	public void createIndex(String indexName) {
		try {
			CreateIndexResponse indexResponse = this.client.admin().indices().prepareCreate(indexName).get();

			System.out.println(indexResponse.isAcknowledged()); // true表示创建成功
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给索引增加mapping。
	 * 
	 * @param index
	 *            索引名
	 * @param type
	 *            mapping所对应的type
	 */
	public void addMapping(String index, String type) {
		try {
			// 使用XContentBuilder创建Mapping
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("properties").startObject()
					.field("name").startObject().field("index", "not_analyzed").field("type", "string").endObject()
					.field("age").startObject().field("index", "not_analyzed").field("type", "integer").endObject()
					.endObject().endObject();
			System.out.println(builder.toString());
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(type);
			this.client.admin().indices().putMapping(mappingRequest).actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param index
	 *            要删除的索引名
	 */
	public void deleteIndex(String index) {
		DeleteIndexResponse deleteIndexResponse = this.client.admin().indices().prepareDelete(index).get();
		System.out.println(deleteIndexResponse.isAcknowledged()); // true表示成功
	}

	/**
	 * 创建一个文档
	 * 
	 * @param index
	 *            index
	 * @param type
	 *            type
	 */
	public void createDoc(String index, String type) {

		try {
			// 使用XContentBuilder创建一个doc source
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "zhangsan")
					.field("age", "lisi").endObject();

			IndexResponse indexResponse = this.client.prepareIndex().setIndex(index).setType(type)
					// .setId(id) // 如果没有设置id，则ES会自动生成一个id
					.setSource(builder.toString()).get();
			System.out.println(indexResponse.isFragment());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新文档
	 * 
	 * @param index
	 * @param type
	 * @param id
	 */
	public void updateDoc(String index, String type, String id) {
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "lisi").field("age", 12)
					.endObject();

			UpdateResponse updateResponse = this.client.prepareUpdate().setIndex(index).setType(type).setId(id)
					.setDoc(builder.toString()).get();
			System.out.println(updateResponse.isFragment()); // true表示成功
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
