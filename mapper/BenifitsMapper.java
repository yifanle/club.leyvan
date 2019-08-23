package club.leyvan.mapper;

import java.util.List;

import club.leyvan.pojo.Benifits;
import club.leyvan.pojo.Page;
import club.leyvan.pojo.SearchCondition;
import club.leyvan.pojo.Type;

public interface BenifitsMapper {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<Benifits> findBenifitsByPage(Page page);
	
	/**
	 * 获取所有记录数
	 * @return
	 */
	Integer getBenifitsCount();
	
	/**
	 * 通过id获取url链接
	 * @param page
	 * @return
	 */
	Benifits getLinkById(Benifits benifits);
	
	/**
	 * 获得所有资源分类
	 * @return
	 */
	List<Type> getAllType();
	
	/**
	 * 根据条件查询总记录数
	 * @param sc
	 * @return
	 */
	Integer getBenifitsCountByConditions(SearchCondition sc);
}
