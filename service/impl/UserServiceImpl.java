package club.leyvan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.leyvan.mapper.UserMapper;
import club.leyvan.pojo.User;
import club.leyvan.service.UserService;
import club.leyvan.utils.MD5Utils;
import club.leyvan.utils.SendSms;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int regist(User user) {
		User exsUser = userMapper.findUserByPhonnum(user);
		//判断手机号是否注册过
		if(exsUser!=null){
			return 0;
		}
		//密码加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
		return userMapper.regist(user);
	}

	@Override
	public int checkCode(User user) {
		return SendSms.sendCheckCode(user.getPhonenum());
	}

	@Override
	public User login(User user) {
		User loginUser = userMapper.login(user);
		if(loginUser!=null){
			if(loginUser.getPassword().equals(MD5Utils.md5(user.getPassword()))){
				//登录成功
				return loginUser;
			}
		}
		return null;
	}

	@Override
	public int updatePassword(User user) {
		User exsUser = userMapper.findUserByPhonnum(user);
		if(exsUser==null){
			return -9;
		}
		user.setPassword(MD5Utils.md5(user.getPassword()));
		return userMapper.updatePassword(user);
	}

}
