package com.taotao.protal.pojo;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem {

	public String[] getImages() {
		String image=getImage();
		if(image!=null&&image!=""){
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
