package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTtreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTtreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") long parentId){
		return contentCategoryService.getCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContenCategory(long parentId,String name){
		return contentCategoryService.insertContenCategory(parentId, name);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContenCategory(long id,String name){
		return contentCategoryService.updateContenCategory(id, name);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContenCategory(long parentId,long id){
		return contentCategoryService.deleteContenCategory(parentId, id);
	}
}
