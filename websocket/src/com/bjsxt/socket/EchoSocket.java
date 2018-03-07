package com.bjsxt.socket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/echo")
public class EchoSocket {
	
	public EchoSocket() {
		System.out.println("执行了构造方法");
	}

	//websocket中的session与servelt中session不一样
	//打开一个管道时执行
	@OnOpen
	public void open(Session session){
		//一个服务session代表一个会话
		System.out.println("打开了sessionid:"+session.getId());
	}
	
	@OnMessage
	public void Message(Session session,String msg){
		try {
			if (session.isOpen()) {
	             session.getBasicRemote().sendText(msg);
	             System.out.println(msg);
	         }
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	//关闭管道时关闭选项卡时执行
	@OnClose
	public void close(Session session){
		System.out.println("关闭了sessionid:"+session.getId());
	}
}
