package club.leyvan.service;

import java.util.List;

import club.leyvan.pojo.FileData;
import club.leyvan.pojo.User;

public interface FileService {
	
	/**
	 * 保存文件信息
	 * @param fileData
	 * @return
	 */
	int saveFileData(FileData fileData);
	
	/**
	 * 保存标题
	 * @param fileData
	 * @return
	 */
	int saveTitles(FileData fileData);
	
	/**
	 * 根据fid获得表单项
	 * @param fileData
	 * @return
	 */
	FileData getContentByFid(FileData fileData);
	
	/**
	 * 将从表单中的值写入数据库中
	 * @param fileData
	 * @return
	 */
	boolean insertValIntoDB(FileData fileData);
	
	/**
	 * 校验id和pwd是否正确
	 * @param user
	 */
	User checkUser(User user);

	/**
	 * 获得用户已收集完成的任务
	 * @param checkedUser
	 * @return
	 */
	List<FileData> findCompleteMissions(User checkedUser);
	
	/**
	 * 查找未完成的任务
	 * @param checkedUser
	 * @return
	 */
	List<FileData> findNotCompleteMissions(User checkedUser);
	
	/**
	 * 根据fid将数据保存到excel
	 * @param fid
	 * @return
	 */
	FileData insertValIntoExcel(Integer fid);
	
	/**
	 * 根据fid删除任务数据
	 * @param fid
	 * @return
	 */
	boolean delete(Integer fid);
}
