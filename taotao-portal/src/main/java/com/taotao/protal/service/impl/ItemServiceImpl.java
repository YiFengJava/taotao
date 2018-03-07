package com.taotao.protal.service.impl;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.util.HttpClientUtil;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.protal.pojo.Item;
import com.taotao.protal.pojo.ItemInfo;
import com.taotao.protal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

//	@Autowired
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	@Override
	public ItemInfo getItemById(long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)){
				 TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
				 if(result.getStatus()==200){
					 ItemInfo item = (ItemInfo) result.getData();
					 return item;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getItemDescById(long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
			TaotaoResult reulst = TaotaoResult.formatToPojo(json, TbItemDesc.class);
			if(reulst.getStatus()==200){
				TbItemDesc itemDesc = (TbItemDesc) reulst.getData();
				String resultString = itemDesc.getItemDesc();
				return resultString;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getItemParamById(long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
			TaotaoResult reulst = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
			if(reulst.getStatus()==200){
				TbItemParamItem itemParam = (TbItemParamItem) reulst.getData();
				String paramData = itemParam.getParamData();
				//生成html
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
