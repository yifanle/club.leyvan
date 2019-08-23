package club.leyvan.mapper;

import club.leyvan.pojo.User;

public interface UserMapper {
	
	int regist(User user);
	
	User findUserByPhonnum(User user);
	
	User login(User user);
	
	User checkUserByUidAndPwd(User user);
	
	int updatePassword(User user);
}
