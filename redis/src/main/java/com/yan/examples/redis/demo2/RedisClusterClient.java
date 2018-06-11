package com.yan.examples.redis.demo2;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 集群模式
 * @author caoqiang
 * @date: 2018年6月11日 上午10:19:02
 */
public class RedisClusterClient {

	private String serverInfo = "127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003,127.0.0.1:7004,127.0.0.1:7005";

	private Set<HostAndPort> getClusterInfo(String serverInfo) {
		Set<HostAndPort> set = new HashSet<HostAndPort>();
		if (serverInfo == null || "".equals(serverInfo.length())) {
			throw new RuntimeException("The serverInfo can not be empty");
		}
		String ipPort[] = serverInfo.split(",");
		int len = ipPort.length;
		for (int i = 0; i < len; i++) {
			String server[] = ipPort[i].split(":");
			set.add(new HostAndPort(server[0], Integer.parseInt(server[1])));
		}
		return set;
	}

	@SuppressWarnings("resource")
	public void test() {

		Set<HostAndPort> jedisClusterNodes = getClusterInfo(serverInfo);
		// Jedis Cluster will attempt to discover cluster nodes automatically
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		jc.set("foo", "bar");
		String value = jc.get("foo");
		System.out.println(value);
	}
	
	public static void main(String[] args) {
		new RedisClusterClient().test();
	}

}