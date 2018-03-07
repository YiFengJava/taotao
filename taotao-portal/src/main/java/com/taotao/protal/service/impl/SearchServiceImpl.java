package com.taotao.protal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.util.HttpClientUtil;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.pojo.SearchResult;
import com.taotao.protal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Override
	public SearchResult search(String queryString,int page) {
		//条用涛涛search
		Map<String, String> param=new HashMap<>();
		param.put("q",queryString);
		param.put("page", page+"");
		try {
			String json=HttpClientUtil.doGet(SEARCH_BASE_URL,param );
//			TaotaoResult result = TaotaoResult.formatToPojo(json, TaotaoResult.class);
			TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
//			TaotaoResult result = JsonUtils.jsonToPojo(json, TaotaoResult.class);
			
			if(result!=null&&result.getStatus()==200){
				SearchResult res = (SearchResult) result.getData();
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
