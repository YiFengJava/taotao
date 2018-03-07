package com.taotao.protal.service;

import com.taotao.protal.pojo.ItemInfo;

public interface ItemService {

	ItemInfo getItemById(long itemId);
	
	String getItemDescById(long itemId);
	
	String getItemParamById(long itemId);
}
