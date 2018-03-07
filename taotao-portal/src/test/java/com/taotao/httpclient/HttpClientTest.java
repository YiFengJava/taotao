package com.taotao.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {

	@Test
	public void doGet() throws Exception{
		//创建一个httpclient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//创建一个get对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//取相应的结果
		int k = response.getStatusLine().getStatusCode();
		System.out.println(k);
		HttpEntity en = response.getEntity();
		String kk = EntityUtils.toString(en,"utf-8");
		System.out.println(kk);
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doGetWithParam() throws Exception{
		//创建一个httpclient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//创建一个get对象
		URIBuilder uriBuilder=new URIBuilder("https://www.baidu.com/s");
		uriBuilder.addParameter("wd", "花千骨");
		HttpGet get = new HttpGet(uriBuilder.build());
		CloseableHttpResponse response = httpClient.execute(get);
		//取相应的结果
		int k = response.getStatusLine().getStatusCode();
		System.out.println(k);
		HttpEntity en = response.getEntity();
		String kk = EntityUtils.toString(en,"utf-8");
		System.out.println(kk);
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doPost()throws Exception{   
		//创建一个httpclient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost post=new HttpPost("http://localhost:8083/httpClient/post.action");
		//执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		String kk = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(kk);
		//关闭httpclient
				response.close();
				httpClient.close();
	}
	
	@Test
	public void doPostWithParam() throws Exception{    
		//创建一个httpclient对象
				CloseableHttpClient httpClient=HttpClients.createDefault();
				
				HttpPost post=new HttpPost("http://localhost:8083/httpClient/post.action");
				//创建一个Entity，模拟一个表单
				List<NameValuePair> kvList=new ArrayList<>();
				kvList.add(new BasicNameValuePair("username", "张三"));
				kvList.add(new BasicNameValuePair("password", "password1234"));
				//包装成一个Entity对象
				StringEntity entity=new UrlEncodedFormEntity(kvList,"UTF-8");
				//设置请求内容
				post.setEntity(entity);
				//执行post请求
				CloseableHttpResponse response = httpClient.execute(post);
				String kk = EntityUtils.toString(response.getEntity(),"UTF-8");
				System.out.println(kk);
				//关闭httpclient
						response.close();
						httpClient.close();
	}
}
