package com.taotao.protal.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.protal.pojo.ItemInfo;
import com.taotao.protal.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable long itemId,Model model){
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		return "item";
	}
	
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showDesc(@PathVariable long itemId){
		String str = itemService.getItemDescById(itemId);
		return str;
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showParam(@PathVariable long itemId){
		String str = itemService.getItemParamById(itemId);
		return str;
	}
	
}
