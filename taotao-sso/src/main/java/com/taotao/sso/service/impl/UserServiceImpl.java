package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.commom.util.CookieUtils;
import com.taotao.commom.util.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	@Qualifier("jedisClientSingleImpl")
	private JedisClient jedisClient;
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		//创建查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1){
			criteria.andUsernameEqualTo(content);
		}else if(type==2){
			criteria.andPhoneEqualTo(content);
		}else{
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null||list.size()==0){
			return TaotaoResult.ok(true);
		}
			return TaotaoResult.ok(false);
		
	}
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult login(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		//如果没有此用户名
		if (null == list || list.size() == 0) {
			return TaotaoResult.build(400, "用户名不存在");
		}
		TbUser user = list.get(0);
		//比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//生成token
		String token = UUID.randomUUID().toString();
		//保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		//把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置session的过期时间expire
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回token
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		if(StringUtils.isBlank(json)){
			return TaotaoResult.build(400, "此session已过期,请重新登陆！");
		}
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		return TaotaoResult.ok(json);
	}
	@Override
	public TaotaoResult logout(String token) {
		jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
		return TaotaoResult.ok();
	}

}
