package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTtreeNode;


public interface ItemCatService {
	
	List<EUTtreeNode> getItemCatList(Long parentId);

}
