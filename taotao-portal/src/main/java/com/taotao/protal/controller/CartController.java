package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.pojo.CartItem;
import com.taotao.protal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable long itemId,@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request, HttpServletResponse response){
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String getcCartList(HttpServletRequest request, HttpServletResponse response,Model model){
		List<CartItem> list = cartService.getCartItemLists(request, response);
		model.addAttribute("cartList", list);
		return "cart";
		
	} 
	
	@RequestMapping("/update/num/{itemId}")
//	@ResponseBody
	public String updateCartNum(@PathVariable long itemId,Integer num,HttpServletRequest request, HttpServletResponse response){
		try {
			cartService.updateCartNum(itemId, num, request, response);
			
		} catch (Exception e) {
			System.out.println("购物车数量更新失败！");
		}
		
		return "cart";
	}
	
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartNum(@PathVariable long itemId,HttpServletRequest request, HttpServletResponse response){
		try {
			cartService.deleteCartItem(itemId, request, response);
			
		} catch (Exception e) {
			System.out.println("删除成功！");
		}
		
		return "redirect:/cart/cart.html";
	}
}
