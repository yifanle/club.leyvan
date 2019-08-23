package club.leyvan.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Benifits {
	private Integer bid;
	private String url;
	@JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd")
	private Date date;
	private String title;
	private String describe;
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
