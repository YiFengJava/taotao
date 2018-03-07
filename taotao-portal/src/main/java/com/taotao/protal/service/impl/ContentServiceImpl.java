package com.taotao.protal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.util.HttpClientUtil;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.protal.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	@Override
	public String getContentList() {
		//调用服务
		String result=HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		//把字符转成TaoTaoResulte
		try {
			TaotaoResult taotaoresult = TaotaoResult.formatToList(result, TbContent.class);
			List<TbContent> list=(List<TbContent>) taotaoresult.getData();
		List<Map> reustList =new ArrayList<>();
			for (TbContent tbContent : list) {
			Map map=new HashMap<>();
			map.put("src", tbContent.getPic());
			map.put("height", 240);
			map.put("width", 670);
			map.put("srcB", tbContent.getPic2());
			map.put("heightB", 240);
			map.put("widthB", 550);
			map.put("href", tbContent.getUrl());
			map.put("alt", tbContent.getSubTitle());
			reustList.add(map);
		}
			return JsonUtils.objectToJson(reustList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
