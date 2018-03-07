package com.taotao.service.impl;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.util.IDUtils;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
/**
 * 商品管理service
 * @author Administrator
 *
 */
@Service
public class ItemServicImpl implements ItemService {
		
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		itemMapper.selectByPrimaryKey(itemId);
		
		
		TbItemExample example =new TbItemExample();
		//添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		
		TbItemExample example=new TbItemExample();
		
		PageHelper.startPage(page, rows);
		 
		List<TbItem> list =itemMapper.selectByExample(example);
		
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
		//item补全
//		生成商品id
		long itemId=IDUtils.genItemId();
		item.setId(itemId);
		//商品状态
		item.setStatus((byte) 1);
		//商品创建时间和更新时间
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		//添加商品规格
		TaotaoResult res=insertItemParam(itemId, itemParam);
		if(res.getStatus()!=200){
			throw new Exception();
		}
		//添加商品描述
		TaotaoResult taotaoResult=insertItemDesc(itemId, desc);
		if(taotaoResult.getStatus()!=200){
			throw new Exception();
		}
		return TaotaoResult.ok();
	}

	/**
	 * 插入商品描述
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult insertItemDesc(Long itemId,String desc){
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	@Autowired
	TbItemParamItemMapper itemParamItemMapper ;
	/**
	 * 插入商品规格
	 */
	private TaotaoResult insertItemParam(Long itemId,String itemParam){
		TbItemParamItem itemParamItem=new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
