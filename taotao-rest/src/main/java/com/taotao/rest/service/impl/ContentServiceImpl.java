package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	//jedisClientClusterImpl   jedisClientSingleImpl
	@Autowired
	@Qualifier("jedisClientSingleImpl")
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public List<TbContent> getContentList(long ContentCId) {
		//从缓存中查询
		try {
			String result=jedisClient.hget(INDEX_CONTENT_REDIS_KEY, ContentCId+"");
			if(!StringUtils.isBlank(result)){
				//把字符转成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(ContentCId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//向缓存中添加内容
		try {
			String cacheString =JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, ContentCId+"", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return list;
	}

}
