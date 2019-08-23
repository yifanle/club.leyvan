package club.leyvan.mapper;

import java.util.List;

import club.leyvan.pojo.FileData;

public interface FileMapper {
	int saveFileData(FileData fileData);
	
	int saveTitles(FileData fileData);
	
	List<String> getContentByFid(FileData fileData);
	
	FileData getFileDataByFid(FileData fileData);
	
	int updateCompnumByFid(FileData fileData);
	
	int insertVal(FileData fileData);
	
	List<FileData> findNotCompFilesByUid(FileData fileData);
	
	List<FileData> findCompFilesByUid(FileData fileData);
	
	List<String> findMissionRowsByNum(FileData fileData);
	
	Integer findMaxCompleteNum(FileData fileData);
	
	int deleteValByFid(FileData fileData);
	
	int deleteContentByFid(FileData fileData);
	
	int deleteFileByFid(FileData fileData);
}
