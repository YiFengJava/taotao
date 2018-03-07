package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbUser;
import com.taotao.protal.pojo.CartItem;
import com.taotao.protal.pojo.Order;
import com.taotao.protal.service.CartService;
import com.taotao.protal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> cartList = cartService.getCartItemLists(request, response);
		model.addAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order, Model model,HttpServletRequest request) {
		try {
			TbUser user = (TbUser) request.getAttribute("user");
			
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			String orderId = orderService.createOrder(order);
			
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错，请稍后重试！");
			return "error/exception";
		}
		
	}
	
}
