package com.bjsxt.config;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class DemoConfig implements ServerApplicationConfig{
//只要实现这个接口，会在项目启动时自动执行
	
	//注解的方式启动
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scaned) {
		System.out.println("+++++++++++++++++++++执行了getAnnotatedEndpointClasses"+
	"++++++websoket服务数量为："+scaned.size()+"++++++++++++++++++");
System.out.println("endPoint扫描到的数量："+scaned.size());
		
	
		
		//返回   可以进行过滤
		return scaned;
	}

	
	//接口方式的启动
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> arg0) {
		// TODO Auto-generated method stub
		System.out.println("执行了getEndpointConfigs");
		System.out.println("实现EndPoint接口的类数量："+arg0.size());
		return null;
	}

}
