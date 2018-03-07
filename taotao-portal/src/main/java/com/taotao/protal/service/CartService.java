package com.taotao.protal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.pojo.CartItem;

public interface CartService {

	TaotaoResult addCartItem(long itemId, int num,HttpServletRequest request, HttpServletResponse response);
	
	List<CartItem> getCartItemLists(HttpServletRequest request, HttpServletResponse response);

	TaotaoResult updateCartNum(long itemId,Integer num,HttpServletRequest request, HttpServletResponse response);

	TaotaoResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response);
}
