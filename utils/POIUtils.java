package club.leyvan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import club.leyvan.pojo.FileData;

public class POIUtils {
	private static Logger logger  = Logger.getLogger(POIUtils.class);  
	private final static String xls = "xls";  
    private final static String xlsx = "xlsx"; 
	
	public static boolean isSimpleExcel(MultipartFile file){
		Workbook workbook = getWorkBook(file);
		if(workbook!=null){
			Sheet firstSheet = workbook.getSheetAt(0);
			if(firstSheet==null){
				//如果第一页为空则不符合
				return false;
			}
			//获得sheet的开始行
			int firstRowNum = firstSheet.getFirstRowNum();
			//获得sheet的结束行
			int lastRowNum = firstSheet.getLastRowNum();
			//如果文件为空则不符合
			if(firstRowNum<0){
				return false;
			}
			//如果文件不为一行则不符合
			if(firstRowNum!=lastRowNum){
				//循环除了第一行的所有行
				for(int rowNum=firstRowNum+1;rowNum<=lastRowNum;rowNum++){
					//获得当前行
					Row row = firstSheet.getRow(rowNum);
					if(row!=null){
						//获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						//获得当前行最后列
						int lastCellNum = row.getLastCellNum();
						//循环判断所有列内是否为空，不为空则不符合
						for(int cellNum = firstCellNum;cellNum<lastCellNum;cellNum++){
							Cell cell = row.getCell(cellNum);
							String cellValue = getCellValue(cell);
							if(cellValue!=""||cellValue.equals("none")){
								return false;
							}
						}
					}
				}
			}
			
		}
		return true;
	}
	
	/**
	 * 解析文件头部
	 * @param fileData
	 * @return
	 */
	public static List<String> parseTitle(FileData fileData){
		List<String> titles = new ArrayList<String>();
		Workbook workbook = getWorkBook(fileData);
		//解析第一行
		if(workbook!=null){
			Sheet sheet = workbook.getSheetAt(0);
			int firstRowNum = sheet.getFirstRowNum();
			if(firstRowNum==0){
				Row row = sheet.getRow(firstRowNum);
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				//循环第一行所有的单元格
				for(int cellNum = firstCellNum;cellNum<lastCellNum;cellNum++){
					Cell cell = row.getCell(cellNum);
					String cellValue = getCellValue(cell);
					//解析错误情况
					if(cellValue==""||cellValue.equals("none")){
						return null;
					}
					titles.add(cellValue);
				}
			}
		}
		return titles;
	}
	
	/**
	 * 读取数据库中的数据写入文件中
	 * @param fileData
	 * @return
	 */
	public static Workbook fillValueToExcel(FileData fileData,List<List<String>> list){
		//获得workbook
		Workbook workbook = getWorkBook(fileData);
		//填写内容
		if(workbook!=null){
			Sheet sheet = workbook.getSheetAt(0);
			Row firstRow = sheet.getRow(0);
			//获得每行cell数量
			int lastCellNum = firstRow.getLastCellNum();
			if(list.size()>0){
				for(int i = 1;i<=list.size();i++){
					Row row = sheet.getRow(i);
					if(row==null){
						row = sheet.createRow(i);
					}
					for(int j=0;j<lastCellNum;j++){
						Cell cell = row.getCell(j);
						if(cell==null){
							cell = row.createCell(j);
						}
						cell.setCellValue(list.get(i-1).get(j));
					}
				}
			}
			return workbook;
		}
		return null;
	}
	
	/**
	 * 根据文件后缀返回对应的workbook
	 * @param file
	 * @return
	 */
	private static Workbook getWorkBook(MultipartFile file) {  
	        //获得文件名  
	        String fileName = file.getOriginalFilename();  
	        //创建Workbook工作薄对象，表示整个excel  
	        Workbook workbook = null;  
	        try {  
	            //获取excel文件的io流  
	            InputStream is = file.getInputStream();  
	            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
	            if(fileName.endsWith(xls)){  
	                //2003  
	                workbook = new HSSFWorkbook(is);  
	            }else if(fileName.endsWith(xlsx)){  
	                //2007  
	                workbook = new XSSFWorkbook(is);  
	            }  
	        } catch (IOException e) { 
	        	logger.info(e.getMessage());  
	        }  
	        return workbook;  
	}
	
	public static String writeExcel(Workbook workbook,FileData fileData){
		//将文件写入
		Pattern p = Pattern.compile("upload");
        Matcher m = p.matcher(fileData.getPath());
        String downloadPath = m.replaceAll("download");
        String path = downloadPath.substring(0, downloadPath.indexOf("."));
		File mkdir = new File(path);
		if(!mkdir.exists()){
			mkdir.mkdirs();
		}
		File file = new File(downloadPath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return downloadPath;
	}
	
	
	private static Workbook getWorkBook(FileData file) {  
		//创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
		if(file!=null){
			//获得文件名  
	        String fileName = file.getFilename();  
	       
	        try {  
	            //获取excel文件的io流  
	            InputStream is = new FileInputStream(new File(file.getPath()));  
	            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
	            if(fileName.endsWith(xls)){  
	                //2003  
	                workbook = new HSSFWorkbook(is);  
	            }else if(fileName.endsWith(xlsx)){  
	                //2007  
	                workbook = new XSSFWorkbook(is);  
	            }  
	        } catch (IOException e) { 
	        	logger.info(e.getMessage());  
	        }  
		}
        return workbook;  
	}
	
	/**
	 * 判断并读取每个单元格中的 值
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		//判断数据的类型
        switch (cell.getCellType()){
	        case Cell.CELL_TYPE_NUMERIC: //数字
	            cellValue = String.valueOf(cell.getNumericCellValue());
	            break;
	        case Cell.CELL_TYPE_STRING: //字符串
	            cellValue = String.valueOf(cell.getStringCellValue());
	            break;
	        case Cell.CELL_TYPE_BOOLEAN: //Boolean
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_FORMULA: //公式
	            cellValue = String.valueOf(cell.getCellFormula());
	            break;
	        case Cell.CELL_TYPE_BLANK: //空值 
	            cellValue = "";
	            break;
	        case Cell.CELL_TYPE_ERROR: //故障
	            cellValue = "none";
	            break;
	        default:
	            cellValue = "none";
	            break;
        }
		return cellValue;
	}
	
	/**
	 * 文件目录分离算法
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public static String makePath(String savePath){
		int hashcode = UUID.randomUUID().hashCode();
		//返回的路径
		String dir = "";
		//第一层文件夹
		int dir1 = hashcode & 0xf;
		//第二层文件夹
		int dir2 = (hashcode & 0xf0)>>4;
		//构造文件路径理论上有16*16个目录
		dir = savePath+"\\"+dir1+"\\"+dir2;
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
}
