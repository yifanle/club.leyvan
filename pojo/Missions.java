package club.leyvan.pojo;

import java.util.List;

public class Missions {
	private List<FileData> complete;
	private List<FileData> notComplete;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FileData> getComplete() {
		return complete;
	}
	public void setComplete(List<FileData> complete) {
		this.complete = complete;
	}
	public List<FileData> getNotComplete() {
		return notComplete;
	}
	public void setNotComplete(List<FileData> notComplete) {
		this.notComplete = notComplete;
	}
	
	
}
