package com.yan.examples.elasticsearch.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.ClearCache;
import io.searchbox.indices.CloseIndex;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.Flush;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.Optimize;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;

public class JestService {
	private final String connUrl = "http://127.0.0.1:9200";// es连接地址
	private final String clusterName = "yan";// 集群名称

	private JestClient jestClient;

	public JestService() {
		jestClient = this.getJestClient();
	}

	/**
	 * 获取JestClient对象
	 *
	 * @return
	 */
	private JestClient getJestClient() {
		HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(connUrl).discoveryEnabled(true)
				.discoveryFrequency(10l, TimeUnit.SECONDS)
				.gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()).multiThreaded(true)
				.readTimeout(10000).build();
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(httpClientConfig);
		return factory.getObject();
	}

	/**
	 * 创建索引
	 *
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean createIndex(String indexName) throws Exception {
		// 设置集群名称
		Settings.Builder settingsBuilder = Settings.builder();
		// settingsBuilder.put("number_of_shards",5);
		// settingsBuilder.put("number_of_replicas",1);
		settingsBuilder.put("cluster.name", clusterName).build();// 集群名

		CreateIndex createIndex = new CreateIndex.Builder(indexName).settings(settingsBuilder.build().getAsMap())
				.build();

		JestResult jr = jestClient.execute(createIndex);
		return jr.isSucceeded();

	}

	/**
	 * Put映射
	 *
	 * @param indexName
	 * @param typeName
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {
		PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
		JestResult jr = jestClient.execute(putMapping);
		return jr.isSucceeded();
	}

	/**
	 * Get映射
	 *
	 * @param indexName
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public String getIndexMapping(String indexName, String typeName) throws Exception {
		GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
		JestResult jr = jestClient.execute(getMapping);
		return jr.getJsonString();
	}

	/**
	 * 索引文档
	 *
	 * @param indexName
	 * @param typeName
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean index(String indexName, String typeName, List<Object> objs) throws Exception {
		Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
		for (Object obj : objs) {
			Index index = new Index.Builder(obj).build();
			bulk.addAction(index);
		}
		BulkResult br = jestClient.execute(bulk.build());
		return br.isSucceeded();
	}

	/**
	 * 更新文档
	 * 
	 * @param script
	 * @param indexName
	 * @param type
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public JsonObject updateDocument(String script, String indexName, String type, String id) throws IOException {
		Update update = new Update.Builder(script).index(indexName).type(type).id(id).build();
		JestResult result = jestClient.execute(update);
		return result.getJsonObject();
	}

	/**
	 * 搜索文档
	 *
	 * @param indexName
	 * @param query
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public SearchResult search(String indexName, String query, String typeName) throws Exception {
		Search.Builder builder = new Search.Builder(query).addIndex(indexName);
		if (typeName != null && !typeName.isEmpty()) {
			builder.addType(typeName);
		}
		Search search = builder.build();
		return jestClient.execute(search);
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 * @throws Exception
	 */
	public SearchResult searchAll(String indexName) throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName).build();
		return jestClient.execute(search);

	}

	/**
	 * Count文档
	 *
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Double count(String indexName, String typeName, String query) throws Exception {

		Count count = new Count.Builder().addIndex(indexName).addType(typeName).query(query).build();
		CountResult results = jestClient.execute(count);
		return results.getCount();
	}

	/**
	 * 获取Document
	 *
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JestResult getDocument(String indexName, String typeName, String id) throws Exception {
		Get get = new Get.Builder(indexName, id).type(typeName).build();
		return jestClient.execute(get);
	}

	/**
	 * Delete索引
	 *
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String indexName) throws Exception {
		JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
		return jr.isSucceeded();
	}

	/**
	 * 删除Document
	 *
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteDocument(String indexName, String typeName, String id) throws Exception {
		DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
		return dr.isSucceeded();
	}

	/**
	 * 关闭JestClient客户端
	 *
	 * @throws Exception
	 */
	public void closeJestClient() throws Exception {
		if (jestClient != null) {
			jestClient.shutdownClient();
		}
	}

	/**
	 * 清缓存
	 *
	 * @throws Exception
	 */
	public boolean clearCache() throws Exception {
		ClearCache clearCache = new ClearCache.Builder().build();
		JestResult result = jestClient.execute(clearCache);
		return result.isSucceeded();
	}

	/**
	 * 关闭索引
	 *
	 * @param index
	 * @throws Exception
	 */
	public boolean closeIndex(String index) throws Exception {
		CloseIndex closeIndex = new CloseIndex.Builder(index).build();
		JestResult result = jestClient.execute(closeIndex);
		return result.isSucceeded();
	}

	/**
	 * 优化索引
	 *
	 * @throws Exception
	 */
	public boolean optimize() throws Exception {
		Optimize optimize = new Optimize.Builder().build();
		JestResult result = jestClient.execute(optimize);
		return result.isSucceeded();
	}

	/**
	 * 刷新索引
	 *
	 * @throws Exception
	 */
	public boolean flush() throws Exception {
		Flush flush = new Flush.Builder().build();
		JestResult result = jestClient.execute(flush);
		return result.isSucceeded();
	}

	/**
	 * 判断索引目录是否存在
	 *
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean indicesExists(String indexName) throws Exception {
		IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
		JestResult result = jestClient.execute(indicesExists);
		return result.isSucceeded();
	}

	/**
	 * 查看节点信息
	 *
	 * @throws Exception
	 */
	public JsonObject nodesInfo() throws Exception {
		NodesInfo nodesInfo = new NodesInfo.Builder().build();
		JestResult result = jestClient.execute(nodesInfo);
		return result.getJsonObject();
	}

	/**
	 * 查看集群健康信息
	 *
	 * @return
	 * @throws Exception
	 */
	public JsonObject health() throws Exception {
		Health health = new Health.Builder().build();
		JestResult result = jestClient.execute(health);
		return result.getJsonObject();
	}

	/**
	 * 节点状态
	 *
	 * @throws Exception
	 */
	public JsonObject nodesStats() throws Exception {
		NodesStats nodesStats = new NodesStats.Builder().build();
		JestResult result = jestClient.execute(nodesStats);
		return result.getJsonObject();
	}
}