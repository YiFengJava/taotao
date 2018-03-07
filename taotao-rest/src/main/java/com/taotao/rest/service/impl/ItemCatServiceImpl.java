package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}

	
	/**
	 * 用递归查分类列表
	 * @param prarentId
	 * @return
	 */
	private List<?> getCatList(long prarentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criterria = example.createCriteria();
		criterria.andParentIdEqualTo(prarentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		int count=0;
		for (TbItemCat itemcat : list) {
			if (itemcat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (prarentId == 0) {
					catNode.setName("<a href='/products/" + itemcat.getId() + ".html'>" + itemcat.getName() + "</a>");
				} else {
					catNode.setName(itemcat.getName());
				}

				catNode.setUrl("/products/" + itemcat.getId() + ".html");
				catNode.setItem(getCatList(itemcat.getId()));
				resultList.add(catNode);
				count++;
				//第一级自取四十个
				if(count>=14 && prarentId == 0){
					break;
				}
			} else {
				resultList.add("/products/" + itemcat.getId() + ".html|" + itemcat.getName());

			}
		}
		return resultList;
	}
	
}
