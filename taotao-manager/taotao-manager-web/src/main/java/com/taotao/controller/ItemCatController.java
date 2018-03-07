package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTtreeNode;
import com.taotao.service.ItemCatService;

/**
 * 商品分类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatServic;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTtreeNode> getCatList(@RequestParam(value="id",defaultValue="0")Long parentId ){
	
		List<EUTtreeNode> list=itemCatServic.getItemCatList(parentId);
		return list;
	}
	
}
