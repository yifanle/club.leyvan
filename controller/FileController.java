package club.leyvan.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import club.leyvan.pojo.FileData;
import club.leyvan.pojo.Missions;
import club.leyvan.pojo.User;
import club.leyvan.service.FileService;
import club.leyvan.utils.POIUtils;

@Controller
public class FileController {
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="upload",method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value = "myfile", required = true)MultipartFile file,HttpSession session,HttpServletRequest  request){
		 String path = "c:/leyvan/upload";
	     String fileName = file.getOriginalFilename();
	     User user = (User)session.getAttribute("loginUser");
	     String dir = path;
	     if(user!=null){
	    	 dir = POIUtils.makePath(path); 
	     }
	     File targetFile = new File(dir, fileName);
	     // 保存
	     try{
	    	boolean isSimpleExcel = POIUtils.isSimpleExcel(file);
	    	if(isSimpleExcel){
	    		if (!targetFile.exists()){
	    			targetFile.mkdirs();
	   	     	}
	    		file.transferTo(targetFile);
	    		session.setAttribute("path", dir+"\\"+fileName);
		        return "200";
	    	}else{
	    		return "404";
	    	}
	     }
	     catch (Exception e){
	        e.printStackTrace();
	        return "500";
	     }
	}
	
	@RequestMapping(value="ensure",method=RequestMethod.POST)
	@ResponseBody
	public String ensure(@RequestBody FileData fileData,HttpSession session){
		String path = (String)session.getAttribute("path");
		String fileName = path.substring(path.lastIndexOf("\\")+1, path.length());
		//将所有path中的\\替换为/
		Pattern p = Pattern.compile("\\\\");
        Matcher m = p.matcher(path);
        String finnalPath = m.replaceAll("/");
		fileData.setPath(finnalPath);
		fileData.setFilename(fileName);
		int result = fileService.saveFileData(fileData);
		if(result>0){
			return parseUrl(fileData);
		}
		return "500";
	}
	
	@RequestMapping(value="user/loadQuestions",method=RequestMethod.POST)
	@ResponseBody
	public FileData loadQuestions(@RequestBody FileData fileData){
		FileData result = fileService.getContentByFid(fileData);
		if(result!=null){
			return result;
		}
		return null;
	}
	
	@RequestMapping(value="user/collection",method=RequestMethod.POST)
	@ResponseBody
	public String collection(@RequestBody String[] arr){
		//将arr封装入fileData中
		FileData fileData = new FileData();
		//获得最后一个携带的fid
		Integer fid = Integer.parseInt(arr[arr.length-1]);
		//将除了最后一个fid的表单内容装入list中
		List<String> content = new ArrayList<String>();
		//循环装载表单值
		for(int i = 0;i<arr.length-1;i++){
			content.add(arr[i]);
		}
		//将准备好的数据存入fileData
		fileData.setFid(fid);
		fileData.setContent(content);
		//传入service层进行文件处理
		boolean result = fileService.insertValIntoDB(fileData);
		if(result){
			return "200";
		}
		return "500";
	}
	
	@RequestMapping(value="loadMissions",method=RequestMethod.POST)
	@ResponseBody
	public Missions loadMissions(@RequestBody User user){
		Missions missions = new Missions();
		//checkUser
		User checkedUser = fileService.checkUser(user);
		if(checkedUser==null){
			return null;
		}
		//查找complete
		List<FileData> complete = fileService.findCompleteMissions(checkedUser);
		List<FileData> notComplete = fileService.findNotCompleteMissions(checkedUser);
		missions.setName(checkedUser.getName());
		missions.setComplete(complete);
		missions.setNotComplete(notComplete);
		return missions;
	}
	
	@RequestMapping(value="download",method=RequestMethod.POST)
	public ResponseEntity<byte[]> download(Integer fid) {
		FileData fileData = fileService.insertValIntoExcel(fid);
		if(fileData==null){
			return null;
		}
		HttpHeaders headers = new HttpHeaders();
		File file = new File(fileData.getPath());
		String name = fileData.getFilename();
		String suffix = name.substring(name.indexOf("."), name.length());
		
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "download"+suffix);
		ResponseEntity<byte[]> entity = null;
		try {
			entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody Integer fid){
		boolean isOk = fileService.delete(fid);
		if(isOk)
			return "200";
		return "500";
	}
	
	private String parseUrl(FileData fileData){
		List<String> list = POIUtils.parseTitle(fileData);
		if(list==null){
			return "500";
		}
		fileData.setContent(list);
		int result = fileService.saveTitles(fileData);
		if(result>0){
			return fileData.getFid().toString();
		}
		return "500";
	}
}
