package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import com.taotao.common.pojo.EUTtreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUTtreeNode> getItemCatList(Long parentId) {
		
		TbItemCatExample tbItemCatExample=new TbItemCatExample();
		Criteria criteria=tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);
		List<EUTtreeNode> resultList=new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EUTtreeNode node =new EUTtreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	

}
