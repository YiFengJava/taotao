package com.taotao.protal.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.taotao.commom.util.HttpClientUtil;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.pojo.Order;
import com.taotao.protal.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		//调用taotao-order的服务提交订单。
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成taotaoResult
		TaotaoResult taotaoResult = TaotaoResult.format(json);
		if (taotaoResult.getStatus() == 200) {
			Long orderId = (Long) taotaoResult.getData();
			return orderId.toString();
		}
		return "";
	}

}
