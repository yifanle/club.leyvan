package club.leyvan.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {
	
	@RequestMapping("easyExcel")
	public String toEasyExcel() {
		return "main";
	}
	
	@RequestMapping("questionnaire")
	public String toCollection(){
		return "collection";
	}
	
	@RequestMapping(value="toMission")
	public String toMission(){
		return "missionList";
	}
	
}
