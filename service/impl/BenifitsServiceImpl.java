package club.leyvan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.leyvan.mapper.BenifitsMapper;
import club.leyvan.pojo.Benifits;
import club.leyvan.pojo.Page;
import club.leyvan.pojo.SearchCondition;
import club.leyvan.pojo.Type;
import club.leyvan.service.BenifitsService;

@Service
public class BenifitsServiceImpl implements BenifitsService {
	
	@Autowired
	BenifitsMapper benifitsMapper;

	@Override
	public Page findBenifitsByPage(Page page) {
		//封装pageSize
		page.setPageSize(6);
		//封装TotalCount
		Integer totalCount = 0;
		if(page.getSc()!=null){
			SearchCondition sc = page.getSc();
			sc.setKeyword("%"+sc.getKeyword()+"%");
			totalCount = benifitsMapper.getBenifitsCountByConditions(sc);
			page.setTotalCount(totalCount);
		}else{
			totalCount = benifitsMapper.getBenifitsCount();
			page.setTotalCount(totalCount);
		}
		
		//封装totalPage
		Double tc = totalCount.doubleValue();
		Double totalPage = Math.ceil(tc/6);
		page.setTotalPage(totalPage.intValue());
		//封装fistIndex
		page.setFirstIndex((page.getCurrPage()-1)*page.getPageSize());
		//封装list
		List<Benifits> list = benifitsMapper.findBenifitsByPage(page);
		page.setList(list);
		
		return page;
	}

	@Override
	public Benifits getLinkById(int bid) {
		Benifits benifits = new Benifits();
		benifits.setBid(bid);
		return benifitsMapper.getLinkById(benifits);
	}

	@Override
	public List<Type> getAllType() {
		return benifitsMapper.getAllType();
	}

}
