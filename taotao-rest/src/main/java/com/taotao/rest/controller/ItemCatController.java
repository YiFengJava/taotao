package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.commom.util.ExceptionUtil;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatServe;
	
	/**
	 * 乱码问题
	 * 1，在RequestMapping中加produces设置返回类型，设置字符集
	 * 2，在spring4.1及以后有一个MappingJacksonValue例如第二种方法
	 * @param callback
	 * @return
	 */
//	@RequestMapping(value="/itemCat/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
//	@ResponseBody
//	public String getItemCatList(String callback){
//		CatResult res = itemCatServe.getItemCatList();
//		String result=JsonUtils.objectToJson(res);
//		return callback+"("+result+");";
//	}
	
	@RequestMapping("/itemCat/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		CatResult res = itemCatServe.getItemCatList();
		MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(res);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
	
	//测试
	@RequestMapping(value="/itemCat/list1",produces=MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	@ResponseBody
	public Object getItemCatList1(String callback){
		CatResult res = itemCatServe.getItemCatList();
		String result=JsonUtils.objectToJson(res);
		return res;
	}
	
	
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	@ResponseBody
//	public TaotaoResult createUser(TbUser user) {
//		try {
//			TaotaoResult result = userService.createUser(user);
//			return result;
//		} catch (Exception e) {
//			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
	
	
	@RequestMapping(value="/itemCat/pic", method = RequestMethod.POST)
	@ResponseBody
	public Object getItemCatList2(@RequestParam("base64") String base64){
		
		return null;
	}
	
}
