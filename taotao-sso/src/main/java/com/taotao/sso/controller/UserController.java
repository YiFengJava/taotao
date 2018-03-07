package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.commom.util.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		TaotaoResult result = null;
		if (StringUtils.isBlank(param)) {
			result = TaotaoResult.build(400, "参数不能为空");
		}
		if (type == null) {
			result = TaotaoResult.build(400, "类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "类型错误");
		}
		if (result != null) {
			if (callback != null) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}
		try {
			result = userService.checkData(param, type);
		} catch (Exception e) {
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createUser(TbUser user) {
		try {
			TaotaoResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,
			HttpServletRequest request,HttpServletResponse response) {
		try {

			TaotaoResult result = userService.login(username, password,request,response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		TaotaoResult result=null;
		try {

			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result=TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}
	
	
	
	@RequestMapping("/logout/{token}")
//	@ResponseBody
	public String logout(@PathVariable String token,String callback,Model model) {
		
		try {
			TaotaoResult result = userService.logout(token);
			if(result.getStatus()==200){
				return "index";
			}
			return "index";
		} catch (Exception e) {
			// TODO: handle exception
			return "index";
		}
		
//		TaotaoResult result=null;
//		try {
//			userService.logout(token); = userService.logout(token);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result=TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//		
//		if (callback != null) {
//			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
//			mappingJacksonValue.setJsonpFunction(callback);
//			return mappingJacksonValue;
//		} else {
//			return result;
//		}
	}
	
}
