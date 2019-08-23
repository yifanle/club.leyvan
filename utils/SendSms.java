package club.leyvan.utils;

import java.util.Random;

public class SendSms {
	
	//用户名
	private static String Uid = "muzile";
	//接口安全秘钥
	private static String Key = "d41d8cd98f00b204e980";
	
	public static int sendCheckCode(String phonenum){
		HttpClientUtil client = HttpClientUtil.getInstance();
		
		Random random = new Random();
		int checkCode = random.nextInt(999999);
		if(checkCode<100000){
			checkCode+=100000;
		}
		String checkStr = String.valueOf(checkCode);
		String text = "您的验证码："+checkStr+"，为了您的账户安全，请不要将验证码告知他人。";
		//UTF发送
		int result = client.sendMsgUtf8(Uid, Key, text, phonenum);
		if(result>0){
			System.out.println("UTF8成功发送条数=="+result);
		}else{
			System.out.println(client.getErrorMsg(result));
			return -1;
		}
		
		return checkCode;
	}
	
}
