package com.taotao.protal.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.commom.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.protal.service.impl.UserServiceImpl;

public class LoginIntercepter implements HandlerInterceptor  {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在Handler执行之前处理
				//判断用户是否登录
				//从cookie中取token
				String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
				//根据token换取用户信息，调用sso系统的接口。
				TbUser user = userServiceImpl.getUserByToken(token);
				//取不到用户信息
				if (null == user) {
					//跳转到登录页面，把用户请求的url作为参数传递给登录页面。
					response.sendRedirect(userServiceImpl.SSO_BASE_URL + userServiceImpl.SSO_PAGE_LOGIN 
							+ "?redirect=" + request.getRequestURL());
					//返回false
					return false;
				}
				//取到用户信息，放行
				
				request.setAttribute("user",user);
				//返回值决定handler是否执行。true：执行，false：不执行。
				return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}