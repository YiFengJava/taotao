package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	EUDataGridResult getContentListByCategoryId(Integer page,Integer rows,Long categoryId); 

	TaotaoResult insertContent(TbContent content);
	
	
	
}
