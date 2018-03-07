package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTtreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

		List<EUTtreeNode> getCategoryList(long parentId);
		
		TaotaoResult insertContenCategory(long parentId,String name);
		
		TaotaoResult deleteContenCategory(long parentId ,long id);
		
		TaotaoResult updateContenCategory(long id,String name);
		
}
