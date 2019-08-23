package club.leyvan.pojo;

public class User {
	//	uidint(4) NOT NULL用户id
	//	phonenumvarchar(20) NOT NULL用户手机号
	//	passwordvarchar(32) NOT NULL用户密码
	//	namevarchar(32) NOT NULL昵称
	private Integer uid;
	private String phonenum;
	private String password;
	private String name;
	private Integer count;
	private Integer checkcode;
	private String msg;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(Integer checkcode) {
		this.checkcode = checkcode;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", phonenum=" + phonenum + ", password=" + password + ", name=" + name + ", count="
				+ count + ", checkcode=" + checkcode + "]";
	}
	
	
	
}
