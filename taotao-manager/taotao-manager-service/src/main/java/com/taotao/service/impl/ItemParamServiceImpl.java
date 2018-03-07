package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertParam(TbItemParam itemParam) {
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());

		itemParamMapper.insert(itemParam);

		return TaotaoResult.ok();

	}

	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		TbItemParamExample example = new TbItemParamExample();

		PageHelper.startPage(page, rows);

		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);

		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		PageInfo pageInfo = new PageInfo<>(list);

		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
