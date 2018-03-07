package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUTtreeNode;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemCatService;

public class TestPageHelper {
@Test
public void testPageHelper(){
	//创建一个spring容器
	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
	TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
	TbItemExample example = new TbItemExample();
	PageHelper.startPage(2, 10);
	List<TbItem> list = mapper.selectByExample(example);
	for (TbItem tbItem : list) {
		System.out.println(tbItem.getTitle());
	}
	PageInfo<TbItem> pageInfo = new PageInfo<>(list);
	long total=pageInfo.getTotal();
	System.out.println("共有商品"+total);

}

@Test
public void test(){
	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
	ItemCatService j = applicationContext.getBean(ItemCatService.class);
	List<EUTtreeNode> h = j.getItemCatList((long) 0);
	System.out.println(h.size());
}
}
