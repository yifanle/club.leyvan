package club.leyvan.controller;


import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import club.leyvan.pojo.User;
import club.leyvan.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="regist",method=RequestMethod.POST)
	@ResponseBody
	public User regist(@RequestBody User user,HttpSession session) {
		//创建一个信息user模型
		User msgUser = new User();
		//数据校验
		String phonenum = user.getPhonenum();
		String password = user.getPassword();
		String name = user.getName();
		
		boolean nameRegex = false;
		boolean passwordRegex = false;
		boolean phonenumRegex = false;
		
		phonenumRegex = Pattern.matches("^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$", phonenum);
		nameRegex = Pattern.matches("^[0-9a-zA-Z\u4e00-\u9fa5_]{3,16}$", name);
		passwordRegex = Pattern.matches("^[^ ]+$", password);
		
		if(phonenumRegex&&nameRegex&&passwordRegex){
			//设置前端接受到的user中的count
			user.setCount(0);
			//测试user是否接受到
//			System.out.println(user);
			//判断验证码是否正确
			if(user.getCheckcode().equals(session.getAttribute("checkCode"))){
				//调用service层的regist方法
				int result = userService.regist(user);
				//判断结果
				if(result>0){
					return user;
				}else if(result==0){
					msgUser.setMsg("手机号已注册过");
					return msgUser;
				}
			}
			msgUser.setMsg("注册失败：验证码错误");
			return msgUser;
		}
		msgUser.setMsg("非法输入");
		return msgUser;
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public User login(@RequestBody User user,HttpSession session){
		//创建消息
		User msgUser = new User();
		//数据校验
		
		String phonenum = user.getPhonenum();
		String password = user.getPassword();
		
		boolean passwordRegex = false;
		boolean phonenumRegex = false;
		
		phonenumRegex = Pattern.matches("^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$", phonenum);
		passwordRegex = Pattern.matches("^[^ ]+$", password);
		
		if(phonenumRegex&&passwordRegex){
			//登录操作
			User loginUser = userService.login(user);
			if(loginUser!=null){
				//登录成功
				session.setAttribute("loginUser", loginUser);
				return loginUser;
			}else{
				msgUser.setMsg("用户名或密码错误");
				return msgUser;
			}
		}
		msgUser.setMsg("非法输入");
		return msgUser;
	}
	
	@RequestMapping(value="checkCode",method=RequestMethod.POST)
	@ResponseBody
	public User checkCode(@RequestBody User user,HttpSession session){
		User msgUser = new User();
		int checkCode = userService.checkCode(user);
		if(checkCode>0){
			session.setAttribute("checkCode", checkCode);
			msgUser.setMsg("验证码发送成功");
		}else{
			msgUser.setMsg("验证码发送失败");
		}
		
		return msgUser;
	}
	
	@RequestMapping(value="modify",method=RequestMethod.POST)
	@ResponseBody
	public User modify(@RequestBody User user,HttpSession session){
		//表单校验
		String phonenum = user.getPhonenum();
		String password = user.getPassword();
		
		boolean passwordRegex = false;
		boolean phonenumRegex = false;
		
		phonenumRegex = Pattern.matches("^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$", phonenum);
		passwordRegex = Pattern.matches("^[^ ]+$", password);
		
		if(passwordRegex&&phonenumRegex){
			if(user.getCheckcode().equals(session.getAttribute("checkCode"))){
				//修改密码
				int result = userService.updatePassword(user);
				if(result>0){
					user.setMsg("密码修改成功");
				}else if(result==-9){
					user.setMsg("手机号未注册");
				}else{
					user.setMsg("修改密码失败，请联系站长");
				}
			}
		}
		
		return user;
	}
	
	@RequestMapping("toLogin")
	@ResponseBody
	public String toLogin(){
		return "302";
	}
	
	@RequestMapping(value="isLogin",method=RequestMethod.POST)
	@ResponseBody
	public User isLogin(HttpSession session){
		User sessionUser = (User) session.getAttribute("loginUser");
		if(sessionUser!=null){
			return sessionUser;
		}
		return null;
	}
	
}
