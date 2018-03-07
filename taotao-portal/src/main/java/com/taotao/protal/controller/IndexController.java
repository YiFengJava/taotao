package com.taotao.protal.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.service.ContentService;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		String adJson=contentService.getContentList();
		model.addAttribute("ad1",adJson);
		return "index";
	}
	
	@RequestMapping(value="/httpClient/post",method=RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	@ResponseBody
	public String testPost(String username,String password){
		String result= "username"+username+"\tpassword"+password;
		System.out.println(result);
		return result;
	}

	
}
