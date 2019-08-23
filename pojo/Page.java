package club.leyvan.pojo;

import java.util.List;

public class Page {
	private Integer totalPage;
	private Integer currPage;
	private Integer pageSize; 
	private Integer totalCount;
	private Integer firstIndex;
	private SearchCondition sc;
	private List<Benifits> list;
	
	
	public SearchCondition getSc() {
		return sc;
	}
	public void setSc(SearchCondition sc) {
		this.sc = sc;
	}
	public Integer getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(Integer firstIndex) {
		this.firstIndex = firstIndex;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<Benifits> getList() {
		return list;
	}
	public void setList(List<Benifits> list) {
		this.list = list;
	}
	
	
}
