package com.taotao.rest.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {

	@Test
	public void addDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://192.168.230.129:8090/solr");
		//创建一个文档对象
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "tes001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 1234523);
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	
	@Test
	public void deleteDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://192.168.230.129:8090/solr");
//		//创建一个文档对象
//		SolrInputDocument document=new SolrInputDocument();
//		document.addField("id", "tes001");
//		document.addField("item_title", "测试商品1");
//		document.addField("item_price", 1234523);
//		//把文档写入索引库
//		solrServer.add(document);
		solrServer.deleteById("tes001");
		
		//提交
		solrServer.commit();
	}
}
