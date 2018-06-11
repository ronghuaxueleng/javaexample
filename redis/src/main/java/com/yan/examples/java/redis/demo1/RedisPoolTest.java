package com.yan.examples.java.redis.demo1;

public class RedisPoolTest {
	public static void main(String[] args) {
        RedisPool.getJedis().set("name","caoqiang");
        System.out.println(RedisPool.getJedis().get("name"));
    }
}
