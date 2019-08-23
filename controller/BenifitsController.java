package club.leyvan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import club.leyvan.pojo.Benifits;
import club.leyvan.pojo.Page;
import club.leyvan.pojo.Type;
import club.leyvan.service.BenifitsService;

@Controller
public class BenifitsController {
	
	@Autowired
	BenifitsService benifitsService;
	
	@RequestMapping(value="/user/getBenifits",method=RequestMethod.POST)
	@ResponseBody
	public Page findBenifitsByPage(@RequestBody Page page){
		page = benifitsService.findBenifitsByPage(page);
		return page;
	}
	
	@RequestMapping(value="getLink",method=RequestMethod.POST)
	@ResponseBody
	public Benifits getLink(@RequestBody int bid){
		return benifitsService.getLinkById(bid);
	}
	
	@RequestMapping(value="/user/getAllType",method=RequestMethod.POST)
	@ResponseBody
	public List<Type> getAllType(){
		return benifitsService.getAllType();
	}
}
