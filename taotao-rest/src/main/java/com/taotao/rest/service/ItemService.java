package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {

	TaotaoResult getItemInfo(long itemId);
	
	TaotaoResult getItemDesc(long itemId);
	
	TaotaoResult getItemParam(long itemId);
}
