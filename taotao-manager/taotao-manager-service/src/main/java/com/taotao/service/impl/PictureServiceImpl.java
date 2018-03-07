package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.commom.util.FtpUtil;
import com.taotao.commom.util.IDUtils;
import com.taotao.common.pojo.PictureResult;
import com.taotao.service.PictureService;
/**
 * 图片上传
 * @author Administrator
 *
 */
@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public PictureResult uploadFile(MultipartFile uploadFile){
//		PictureResult resultMap=new PictureResult();
		try {
			//生成一个新的文件名
			//去原始文件名
			String oldName =uploadFile.getOriginalFilename();
			//UUID.randomUUID();
			String newName=IDUtils.genImageName();
			newName=newName+oldName.substring(oldName.lastIndexOf("."));
			String imagePath="images"+new DateTime().toString("/yyyy/MM/dd");
			boolean res=FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, 
					imagePath, newName, uploadFile.getInputStream());
			
			if(!res){
//				resultMap.put("error", 1);
//				resultMap.put("message", "文件上传失败");
				return new PictureResult(1, "", "文件上传失败");
			}
			return new PictureResult(0, IMAGE_BASE_URL+imagePath+"/"+newName, "文件上传成功");
//			resultMap.put("error", 0);
//			resultMap.put("url", IMAGE_BASE_URL+"/"+newName);
//			return resultMap;
		} catch (Exception e) {
			return new PictureResult(1, "", "文件上传失败,发生了异常");
//			resultMap.put("error", 1);
//			resultMap.put("message", "文件上传失败,发生了异常");
//			return resultMap;
		}
		
	}

}
