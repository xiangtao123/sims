package com.jsrush.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * 操作Excel文件
 * 
 * @author sunburst
 *
 */
public class ExcelUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 创建一个文件
	 * 
	 * @param filePathName
	 * @return
	 */
	public static File createNewFile(String filePathName) {
		File file = new File(filePathName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建文件薄对象
	 * 
	 * @param workbookType
	 *            xlsx xls
	 * @return
	 * @throws Exception
	 */
	public static Workbook createWorkbook(String workbookType) throws Exception {
		Workbook workbook = null;
		if (workbookType == null) {
			workbook = new HSSFWorkbook();
		} else if (workbookType.equals("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (workbookType.equals("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			log.error("invalid file name, should be xls or xlsx");
			throw new Exception("invalid file name, should be xls or xlsx");
		}
		return workbook;
	}

	/**
	 * 
	 * 根据页签名称查询对象或创建
	 * 
	 * @param workbook
	 * @param sheetTitle
	 * @return
	 */
	public static Sheet getOrAddSheet(Workbook workbook, String sheetTitle) {
		Sheet sheet = workbook.getSheet(sheetTitle);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetTitle);
		}
		return sheet;
	}

	/**
	 * 获取表头行数据
	 * @param headerTitle
	 * @param dataMapKey
	 * @return
	 */
	public static Map<String, Object> createHeadRowData(String[] headerTitle, String[] dataMapKey) {
		if (headerTitle.length != dataMapKey.length) {
			log.warn(" headerTitle's length not equals dataMapKey's length! ");
		}

		Map<String, Object> headRowDataMap = new HashMap<String, Object>();
		for (int i = 0, len = headerTitle.length; i < len; i++) {
			headRowDataMap.put(dataMapKey[i], headerTitle[i]);
		}
		return headRowDataMap;
	}
	
	/**
	 * 获取数据与列头对应关系
	 * @param dataMapKey
	 * @return
	 */
	public static Map<String, Integer> createDataKeyMap(String[] dataMapKey){
		Map<String, Integer> dataKeyMap = new HashMap<String, Integer>();
		for (int i = 0, len = dataMapKey.length; i< len; i++) {
			dataKeyMap.put(dataMapKey[i], i);
		}
		return dataKeyMap;
	}
	
	
	/**
	 * 填充表格数据
	 * 
	 * @param sheet
	 * @param dataList
	 */
	public static void fillCellData(Sheet sheet, Map<String, Integer> dataKeyMap,	List<Map<String, Object>> dataList) {
		Iterator<Map<String, Object>> iterator = dataList.iterator();

		int rowIndex = 0;
		while (iterator.hasNext()) {
			Map<String, Object> rowData = iterator.next();
			Row row = sheet.createRow(rowIndex++);
			Set<String> keySet = rowData.keySet();
			Iterator<String> keySetIt = keySet.iterator();

			while (keySetIt.hasNext()) {
				String key = keySetIt.next();
				Cell cell = row.createCell(dataKeyMap.get(key));
				Object valueObj = rowData.get(key);
				if (valueObj == null) {
					cell.setCellValue("");
				} else {
					try{
						cell.setCellValue(Integer.parseInt(valueObj.toString()));
					} catch (Exception e) {
						cell.setCellValue(valueObj.toString());
					}
				}
			}

		}

	}

	/**
	 * 将WorkBook内容写入到文件中
	 * 
	 * @param workbook
	 * @param filePathName
	 * @throws Exception
	 */
	public static void writeWorkbookToFile(Workbook workbook,
			String filePathName) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePathName);
			workbook.write(fos);
			log.info(" workbook write to file["+filePathName+"] success!");
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				log.error(LogUtil.stackTraceToString(e));
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}

	/**
	 * 获取文件中的工作薄Workbook对象
	 * 
	 * @param filePathName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException 
	 */
	public static Workbook readWorkbookFromFile(String filePathName) throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		File file = new File(filePathName);
		Workbook workbook = WorkbookFactory.create(file);
		return workbook;
	}
	
	public static void readData(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator(); 
		
		for (int i = 0 ; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			int lastRowNum = sheet.getLastRowNum()+1;
			String sheetName = sheet.getSheetName();
			System.err.println("\n\t read sheet["+i+":"+sheetName+"]: lastRowNum= "+lastRowNum + "\n\t");
			
			for (int j = 0; j < lastRowNum; j++) {
				Row row = sheet.getRow(j);
				System.err.print("\n read row["+j+"]  ");
				if (row == null) {
					System.err.println(" 无数据. ");
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int cellType = cell.getCellType();
					if (cellType == Cell.CELL_TYPE_FORMULA) {
						
					}
					switch(cellType){
		                case Cell.CELL_TYPE_FORMULA:
			                 System.err.print(evaluator.evaluate(cell).formatAsString() +  "      "); // cell.getCellFormula()+"="+
			                 break;
		                case Cell.CELL_TYPE_STRING :
		                   System.err.print(cell.getStringCellValue() +  "      ");
		                   break;
		                case Cell.CELL_TYPE_NUMERIC :
			                   System.err.print(cell.getNumericCellValue() +  "      ");
			                   break;
	                }
					
				}
			}
			
		}
	}
	
}
