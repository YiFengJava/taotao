package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.commom.util.FtpUtil;

public class FTPTest {

	@Test
	public void testFtpClient()throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient=new FTPClient();
		
		//创建ftp连接 默认端口21
		ftpClient.connect("192.168.230.129",21);
		ftpClient.enterLocalPassiveMode();
		//登陆ftp服务器，使用用户名和密码
//		ftpClient.login("root", "1234");
		ftpClient.login("abc", "19931210yd");
		//上传文件
		
		ftpClient.enterLocalPassiveMode();
//		ftpClient.setControlEncoding("UTF-8");
		
		    
		   //读取本地文件
			FileInputStream inputStream=new FileInputStream(new File("E:\\statcziyuan\\gs01.jpg"));
			//设置上传路径
			ftpClient.changeWorkingDirectory("/home/www");
//		boolean ft = ftpClient.storeFile("gs01.jgp", inputStream);
		boolean stored = ftpClient.storeFile("gs01.jgp", inputStream);
		System.out.println(stored);
		//关闭连接
		ftpClient.logout();
	}
	
	 @Test  
	    public void testFtpClient1() throws Exception{  
	        //创建一个FTPClient对象  
	        FTPClient ftpClient = new FTPClient();  
	          
	        //创建FTP连接,端口号可以设定，我的是22,默认是21  
	        ftpClient.connect("192.168.230.129",21);  
	          
	        //登录FTP服务器，使用用户名和密码  
	        ftpClient.login("root", "1234");  
	        //ftpClient.enterRemotePassiveMode();  
	        //ftpClient.setControlEncoding("gb18030");  
	          
	        //上传文件,读取本地文件  
	        String path = "E:\\statcziyuan\\gs01.jpg";  
	        FileInputStream inputStream = new FileInputStream(new File(path)); 
	        
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//************此处是重点要不就服务器的图像就被损坏  
	        //设置上传的路径  
	        String pathname = "/usr/local/nginx/html/";//这个路径就是FTP服务端存储的路径，可以从FileZilla中获取  
	        ftpClient.changeWorkingDirectory(pathname);  
	        ftpClient.enterLocalPassiveMode();
	        
	        
	        ftpClient.setControlEncoding("UTF-8");
	        //参数一：服务器端文档名；参数二：上传文档的inputStream  
	        String remote = "11cui00.jpg";  
//	        boolean k = ftpClient.storeFile(remote, inputStream);
	        boolean k =ftpClient.storeFile(new String(remote.getBytes("UTF-8"),"iso-8859-1"),inputStream);
	        System.out.println(k);
	          
	        //关闭连接  
	        ftpClient.logout();  
	          
	    }  
	 
	 @Test
	 public void ftpt() throws FileNotFoundException{
		 boolean l = FtpUtil.uploadFile("192.168.230.129", 21, "abc", "19931210yd", 
				 "/home/www", "/images", "11.jpg", new FileInputStream(new File("E:\\statcziyuan\\gs01.jpg")));
		 System.out.println(l);
	 }
	 
	 
	 
	 
	 
}
