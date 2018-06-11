package com.yan.examples.java.redis.demo2;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

/**
 * 非集群环境
 * @author caoqiang
 * @date: 2018年6月11日 上午10:13:29
 * 此例中使用分片的概念，分片Shard的目的就是将数据均匀分布到多台redis实例中
 */
public class RedisSingleClient {

	// 未分片客户端连接
	private Jedis jedis;
	// 未分片客户端连接池
	private JedisPool jedisPool;
	// 分片客户端连接
	private ShardedJedis shardedJedis;
	// 分片客户端连接池
	private ShardedJedisPool shardedJedisPool;
	// jedispool配置
	private JedisPoolConfig config;

	public void initJedisPoolConfig() {
		config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(10);
		config.setMaxWaitMillis(1000L);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
	}
	
	//初始化未分片客户端连接池
	public void initJedisPool() {
		initJedisPoolConfig();
		jedisPool = new JedisPool(config, "127.0.0.1", 6379);
	}
	
	/**
	 * 初始化分片客户端连接池
	 * 这里配置多台机器的地址
	 * 将数据均匀分布到多台redis实例中
	 */
	public void initShardedJedisPool() {
		initJedisPoolConfig();
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1", 6379));
//		shards.add(new JedisShardInfo("192.168.11.177", 7379));
//		shards.add(new JedisShardInfo("192.168.11.178", 7379));
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public Jedis getJedis() {
		jedis = this.jedisPool.getResource();
		return jedis;
	}

	public ShardedJedis getShardedJedis() {
		shardedJedis = this.shardedJedisPool.getResource();
		return shardedJedis;
	}

	@SuppressWarnings("rawtypes")
	public void returnResource(Pool pool, Object redis) {
		if (redis != null) {
			pool.close();
		}
	}

	public void set(String key, String value) {
		try {
			jedis = getJedis();
			shardedJedis = getShardedJedis();
			jedis.set(key, value);
			shardedJedis.getShard("yan").set("yan", "demo");
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.close();
			shardedJedisPool.close();
		} finally {
			returnResource(jedisPool, jedis);
			returnResource(shardedJedisPool, shardedJedis);
		}
	}

	public String get(String key) {
		String value = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			// 释放资源
			jedisPool.close();
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public String getShardValue(String key) {
		String value = null;
		try {
			shardedJedis = getShardedJedis();
			value = shardedJedis.getShard(key).get(key);
		} catch (Exception e) {
			e.printStackTrace();
			// 释放资源
			shardedJedisPool.close();
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	public void test() {
		initJedisPool();
		initShardedJedisPool();
		jedis = getJedis();
		shardedJedis = getShardedJedis();
		jedis.set("momo", "nono");
		shardedJedis.set("yan", "demo");
		System.out.println(jedis.get("momo"));
		System.out.println(shardedJedis.get("yan"));
		shardedJedis.getShard("yan").set("yan", "demo");
		System.out.println(shardedJedis.getShard("yan").get("yan"));
	}
	
	public static void main(String[] args) {
		new RedisSingleClient().test();
	}

}