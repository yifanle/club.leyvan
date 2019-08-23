package club.leyvan.pojo;

import java.util.List;

public class FileData {
	private Integer fid;
	private String filename;
	private String path;
	private Integer num;
	private Integer fuid;
	private Integer type;
	private String date;
	private Integer compnum;
	private List<String> content;
	
	
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}
	public Integer getCompnum() {
		return compnum;
	}
	public void setCompnum(Integer compnum) {
		this.compnum = compnum;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getFuid() {
		return fuid;
	}
	public void setFuid(Integer fuid) {
		this.fuid = fuid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "FileData [fid=" + fid + ", filename=" + filename + ", path=" + path + ", num=" + num + ", fuid=" + fuid
				+ ", type=" + type + ", date=" + date + ", compnum=" + compnum + ", content=" + content + "]";
	}
	
}
