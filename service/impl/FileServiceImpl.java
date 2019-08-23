package club.leyvan.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.leyvan.mapper.FileMapper;
import club.leyvan.mapper.UserMapper;
import club.leyvan.pojo.FileData;
import club.leyvan.pojo.User;
import club.leyvan.service.FileService;
import club.leyvan.utils.POIUtils;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileMapper fileMapper;
	@Autowired
	UserMapper userMapper;

	@Override
	public int saveFileData(FileData fileData) {
		//封装fileData
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String finalDate = sdf.format(date);
		fileData.setDate(finalDate);
		//设置type为上传状态1
		fileData.setType(1);
		//设置完成数量
		fileData.setCompnum(0);
		
		return fileMapper.saveFileData(fileData);
//		return 0;
	}

	@Override
	public int saveTitles(FileData fileData) {
		return fileMapper.saveTitles(fileData);
	}

	@Override
	public FileData getContentByFid(FileData fileData) {
		List<String> contents = fileMapper.getContentByFid(fileData);
		if(contents.size()>0){
			fileData.setContent(contents);
			return fileData;
		}
		return null;
	}

	@Override
	public boolean insertValIntoDB(FileData fileData) {
		//先根据fid查询到用户和path修改compnum
		FileData queryFile = fileMapper.getFileDataByFid(fileData);
		int result = -1;
		queryFile.setContent(fileData.getContent());
		if(queryFile.getCompnum()==queryFile.getNum()){
			return false;
		}
		queryFile.setCompnum(queryFile.getCompnum()+1);
		int insertResult = fileMapper.insertVal(queryFile);
		result = fileMapper.updateCompnumByFid(queryFile);
		if(result>0&&insertResult>0){
			return true;
		}
		return false;
	}
	
	@Override
	public User checkUser(User user) {
		User checkUser = userMapper.checkUserByUidAndPwd(user);
		
		return checkUser;
	}

	@Override
	public List<FileData> findCompleteMissions(User checkedUser) {
		FileData queryFile = new FileData();
		queryFile.setFuid(checkedUser.getUid());
		List<FileData> complete = fileMapper.findCompFilesByUid(queryFile);
		return complete;
	}

	@Override
	public List<FileData> findNotCompleteMissions(User checkedUser) {
		FileData queryFile = new FileData();
		queryFile.setFuid(checkedUser.getUid());
		List<FileData> notComplete = fileMapper.findNotCompFilesByUid(queryFile);
		return notComplete;
	}

	@Override
	public FileData insertValIntoExcel(Integer fid) {
		Workbook workbook = null;
		String downloadPath = "";
		List<List<String>> list = new ArrayList<List<String>>();
		//封装查询对象
		FileData queryFile = new FileData();
		queryFile.setFid(fid);
		//根据fid查找fileData的数据 except:content and val
		FileData fileData = fileMapper.getFileDataByFid(queryFile);
		if(fileData==null){
			return fileData;
		}
		//根据fileData查找val中的完成数
		if(fileMapper.findMaxCompleteNum(fileData)!=null){
			int maxNum = fileMapper.findMaxCompleteNum(fileData);
			if(maxNum>0){
				//循环查询row
				for(int i = 1;i<=maxNum;i++){
					fileData.setCompnum(i);
					List<String> rows = fileMapper.findMissionRowsByNum(fileData);
					list.add(rows);
				}
			}
			workbook = POIUtils.fillValueToExcel(fileData, list);
			if(workbook!=null){
				downloadPath = POIUtils.writeExcel(workbook, fileData);
				fileData.setPath(downloadPath);
			}
		}
		
		return fileData;
	}

	@Override
	public boolean delete(Integer fid) {
		//查找到file，先删除文件
		FileData queryFile = new FileData();
		queryFile.setFid(fid);
		FileData fileData = fileMapper.getFileDataByFid(queryFile);
		if(fileData==null){
			return false;
		}
		//根据path推出downloadpath
		Pattern p = Pattern.compile("upload");
        Matcher m = p.matcher(fileData.getPath());
        String downloadPath = m.replaceAll("download");
        //分别删除上传的文件和下载的文件
        File uploadFile = new File(fileData.getPath());
        File downloadFile = new File(downloadPath);
        
        uploadFile.delete();
        downloadFile.delete();
        //删除数据库数据
        //1.先删除从表
        int vresult = fileMapper.deleteValByFid(fileData);
        int cresult = fileMapper.deleteContentByFid(fileData);
        int fresult = fileMapper.deleteFileByFid(fileData);
        
        if(vresult>=0&&cresult>=0&&fresult>=0){
        	return true;
        }
        
		return false;
	}
	
}
