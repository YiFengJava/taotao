package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable long itemCatId){
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult  insertItemParam(@PathVariable long cid,String paramData){
		//创建
		TbItemParam itemParam =new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaotaoResult result =itemParamService.insertParam(itemParam);
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page,Integer rows){
		EUDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
}
