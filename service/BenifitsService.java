package club.leyvan.service;

import java.util.List;

import club.leyvan.pojo.Benifits;
import club.leyvan.pojo.Page;
import club.leyvan.pojo.Type;

public interface BenifitsService {
	
	/**
	 * 分页查询资源福利
	 * @param page
	 * @return
	 */
	Page findBenifitsByPage(Page page);
	
	/**
	 * 通过id获取url
	 * @param bid
	 * @return
	 */
	Benifits getLinkById(int bid);
	
	/**
	 * 获得所有资源分类
	 * @return
	 */
	List<Type> getAllType();
}
