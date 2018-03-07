package com.taotao.rest.test;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class ItemCatTest {

	@Test
	public void test(){
		//创建一个spring容器
		ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ItemCatService itemcat =ac.getBean(ItemCatService.class);
		CatResult l = itemcat.getItemCatList();
		System.out.println(l);

	}
	
	//单机redis
	@Test
	public void testJedisSingle(){
		//创建jedis对象4
		Jedis jedis=new Jedis("192.168.230.129",6379);
		//调用jedis对象的方法
		jedis.set("key1", "喻冬");
		String string=jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
	}
	
	//连接池
	@Test
	public void testJedisPool(){
		JedisPool pool =new JedisPool("192.168.230.129",6379);
		Jedis jedis=pool.getResource();
		jedis.set("key1", "喻冬1");
		String string=jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
		
		pool.close();
		
	}
	
	//集群版
	@Test
	public void testJedisCluster(){
		HashSet<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.230.129", 7001));
		nodes.add(new HostAndPort("192.168.230.129", 7002));
		nodes.add(new HostAndPort("192.168.230.129", 7003));
		nodes.add(new HostAndPort("192.168.230.129", 7004));
		nodes.add(new HostAndPort("192.168.230.129", 7005));
		nodes.add(new HostAndPort("192.168.230.129", 7006));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("key", "1000");
		String st = cluster.get("key");
		System.out.println(st);
		cluster.close();
	}
	
	//使用spring配置的集群版redis
	@Test
	public void testSpringredisCluster(){
		ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisClient = (JedisCluster) ac.getBean("jedisClient");
		String st = jedisClient.get("key");
		System.out.println(st);
		jedisClient.close();
	}
	
	//使用spring配置单机版redis
		@Test
		public void testSpringredisSingle(){
			ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
			JedisPool pool =(JedisPool) ac.getBean("jedisClient");
			Jedis jedis=pool.getResource();
//			jedis.set("key1", "喻冬1");
			String string=jedis.get("key1");
			System.out.println(string);
			//关闭jedis
			jedis.close();
			
			pool.close();
		}
		
		 public static void main(String[] args) {
		        String s1 = "Programming";
		        String s2 = new String("Programming");
		        String s3 = "Program" + "ming";
		        System.out.println(s1 == s2);
		        System.out.println(s1 == s3);
		        System.out.println(s1 == s1.intern());
		        System.out.println(s1 == s2.intern());
		    }

}
