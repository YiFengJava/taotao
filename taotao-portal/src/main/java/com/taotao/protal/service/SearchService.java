package com.taotao.protal.service;

import com.taotao.protal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,int page );
}
