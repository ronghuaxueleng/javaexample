package com.yan.examples.java.redis.demo1;

public class RedisPoolTest {
	public static void main(String[] args) {
        RedisPool.getJedis().set("name","陈浩翔");
        System.out.println(RedisPool.getJedis().get("name"));
    }
}
