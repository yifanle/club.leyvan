package club.leyvan.service;

import club.leyvan.pojo.User;

public interface UserService {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	int regist(User user);
	
	/**
	 * 发送验证码
	 * @param user
	 * @return
	 */
	int checkCode(User user);
	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	int updatePassword(User user);
}
