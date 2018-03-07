package com.taotao.sso.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.sso.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {

	@Autowired
	private JedisPool JedisPool;
	@Override
	public String set(String key, String value) {
		Jedis jedis=JedisPool.getResource();
		String result=jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis=JedisPool.getResource();
		String value=jedis.get(key);
		jedis.close();
		return value;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis=JedisPool.getResource();
		String value=jedis.hget(hkey, key);
		jedis.close();
		return value;
	}

	@Override
	public long incr(String key) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis=JedisPool.getResource();
		Long result = jedis.hdel(hkey,key);
		jedis.close();
		return result;
	}

}
