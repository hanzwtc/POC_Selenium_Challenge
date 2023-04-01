package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public static final String currentDir = System.getProperty("user.dir");
	static Logger log = LogManager.getLogger(ExcelUtils.class);
	
	public static ConfigFileReader configFileReader;
	
	public static String testDataExcelPath = null;
	
	private static XSSFWorkbook excelWorkbook;
	
	private static XSSFSheet excelSheet;
	
	private static XSSFCell excelCell;
	
	private static XSSFRow excelRow;
	
	public static int rowNumber;
	
	public static int columnNumber;
	
	public static String testDataExcelFileName = "";
	
	public static String testDataExcelSheetName = "";
	
	synchronized public void updateLogStatus(String method, String element, String status, Date date, String Error,
			String exception, String pageSource) {
		
		String fileName = System.getProperty("user.dir")+"/target/LogFile.xlsx";
		// TODO Auto-generated method stub
		
		try {
			InputStream inputStream = null;
			FileOutputStream fos = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			Row row = null;
			Cell cell = null;
			CellStyle cellStyle = null;
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet("Log");
			int totalUsedRows = sheet.getLastRowNum();
			row= sheet.createRow(totalUsedRows+1);
			for(int i=0;i<totalUsedRows+1;i++) {
				int totalUsedColumns = sheet.getRow(i).getLastCellNum();
				row=sheet.getRow(totalUsedRows+1);
				
				cell=row.createCell(2);
				cell.setCellValue(method);
				
				cell=row.createCell(3);
				cell.setCellValue(element);
				
				cell=row.createCell(4);
				cell.setCellValue(status);
				
				cell=row.createCell(5);
				cell.setCellValue(date);
				cellStyle=workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd hh:m:ss a"));
				cell.setCellStyle(cellStyle);
				
				cell=row.createCell(6);
				cell.setCellValue(Error);
				
				cell=row.createCell(7);
				if(exception.length()>32000) exception = exception.substring(0,32000);
				cell.setCellValue(exception);
				
				cell=row.createCell(8);
				if(pageSource.length()>32000) pageSource = pageSource.substring(0,32000);
				cell.setCellValue(pageSource);
				
				break;
			}
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			workbook.close();
			
			fos.flush();
			fos.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	synchronized public void createExcel(String scenarioName) throws IOException {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:m:ss");
		String filePath = System.getProperty("user.dir")+"/target/LogFile.xlsx";
		File file = new File(filePath);
		
		if(file.exists()) {
			updateExcel(scenarioName);
		} else {
			File fileName = new File(filePath);
			List<String> list = new ArrayList();
			Workbook wb = new XSSFWorkbook();
			OutputStream fileOut = new FileOutputStream(fileName);
			Sheet sheet = wb.createSheet("Log");
			Map<String,Object[]> data = new TreeMap<String,Object[]>();
			data.put("1", new Object[] {"Scenarios", "Steps", "Methods Called", "Element", "Status", "Time Stamp", "Error Message", "Exception", "Page Source"});
			Set<String> keyset = data.keySet();
			int rowNum = 0;
			for(String key : keyset) {
				Row row = sheet.createRow(rowNum++);
				Object[] objArr = data.get(key);
				int cellNum = 0;
				for(Object obj : objArr) {
					Cell cell = row.createCell(cellNum++);
					if(obj instanceof String) cell.setCellValue((String)obj);
					else if(obj instanceof Integer) cell.setCellValue((Integer)obj);
				}
			}
			wb.write(fileOut);
			wb.close();
			fileOut.close();
			updateExcel(scenarioName);
		}
		
	}

	synchronized private void updateExcel(String scenarioName) {

		// TODO Auto-generated method stub
		String fileName = System.getProperty("user.dir")+"/target/LogFile.xlsx";
		try{
			InputStream inputStream = null;
			FileOutputStream fos = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			Row row = null;
			Cell cell = null;
			CellStyle cellStyle = null;
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet("Log");
			int totalUsedRows = sheet.getLastRowNum();
			row= sheet.createRow(totalUsedRows+1);
			for(int i=0;i<totalUsedRows+1;i++) {
				int totalUsedColumns = sheet.getRow(i).getLastCellNum();
				row=sheet.getRow(totalUsedRows+1);
				cell=row.createCell(0);
				cell.setCellValue(scenarioName);	
				break;
			}
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			workbook.close();
			
			fos.flush();
			fos.close();
		}catch(Exception e) {
			System.out.println(e);
		}		
	}

	public void updateStepExcel(String stepText) {
		// TODO Auto-generated method stub
		String fileName = System.getProperty("user.dir")+"/target/LogFile.xlsx";
		try{
			InputStream inputStream = null;
			FileOutputStream fos = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			Row row = null;
			Cell cell = null;
			CellStyle cellStyle = null;
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet("Log");
			int totalUsedRows = sheet.getLastRowNum();
			row= sheet.createRow(totalUsedRows+1);
			for(int i=0;i<totalUsedRows+1;i++) {
				int totalUsedColumns = sheet.getRow(i).getLastCellNum();
				row=sheet.getRow(totalUsedRows+1);
				cell=row.createCell(1);
				cell.setCellValue(stepText);
				break;
			}
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			workbook.close();
			
			fos.flush();
			fos.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public synchronized void createExlFile(String filePath) {
		// TODO Auto-generated method stub
		synchronized (ExcelUtils.class) {
			try {
				String fileName = filePath;
				File file = new File(filePath);
				if(!file.exists()) {
					HSSFWorkbook workbook = new HSSFWorkbook();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
					Date curDate = new Date();
					String strDate = sdf.format(curDate);
					HSSFSheet sheet = workbook.createSheet(strDate);
					HSSFRow rowhead = sheet.createRow((short)0);
					rowhead.createCell(0).setCellValue("No.");
					rowhead.createCell(1).setCellValue("US#");
					rowhead.createCell(2).setCellValue("Scenario");
					rowhead.createCell(3).setCellValue("Feasible");
					rowhead.createCell(4).setCellValue("Status");
					rowhead.createCell(5).setCellValue("Comments");
					sheet.autoSizeColumn(5);
					FileOutputStream fileout = new FileOutputStream(fileName);
					workbook.write(fileout);
					
					sheet = workbook.createSheet("Tracker");
					rowhead = sheet.createRow((short)0);
					rowhead.createCell(0).setCellValue("No.");
					rowhead.createCell(1).setCellValue("From URL");
					rowhead.createCell(2).setCellValue("To URL");
					rowhead.createCell(3).setCellValue("Started At");
					rowhead.createCell(4).setCellValue("Ended At");
					rowhead.createCell(5).setCellValue("Total M Seconds");
					rowhead.createCell(6).setCellValue("Total Seconds");
					fileout = new FileOutputStream(fileName);
					workbook.write(fileout);
					
					fileout.close();
					System.out.println("Your Output File has been Generated");
					log.info("Your Output File has been Generated");
				}else {
					System.out.println("Your Output File has already Generated");
					log.info("Your Output File has already Generated");
				}
			} catch (Exception e) {
				System.out.println(e);
				log.info("Error in Excel Creation "+e.getMessage());
			}
		}
		
	}

	public void writeExlFile(String scegetName, String scegetStatus, boolean sceisFailed, String filePath) {
		// TODO Auto-generated method stub
		synchronized (ExcelUtils.class) {
			try {
				FileInputStream myXls = new FileInputStream(filePath);
				HSSFWorkbook bddTestResultSheet = new HSSFWorkbook(myXls);
				HSSFSheet workSheet = bddTestResultSheet.getSheetAt(0);
				int a = workSheet.getLastRowNum();
				System.out.println(a);
				Row row = workSheet.createRow(++a);
				row.createCell(0).setCellValue(a);
				workSheet.autoSizeColumn(0);
				String string = scegetName;
				String[] parts =string.split(":");
				row.createCell(1).setCellValue(parts[1].trim());
				workSheet.autoSizeColumn(1);
				row.createCell(2).setCellValue(parts[0].trim());
				workSheet.autoSizeColumn(2);
				
				if(parts[0].trim().contains("Non Feasible")) {
					row.createCell(3).setCellValue("Not Feasible to Automate");
					row.createCell(4).setCellValue("-");
					row.createCell(3).setCellValue("Manualy test this as it is not feasile to Automate");
				}else if (parts[0].trim().contains("SpillOver")) {
					row.createCell(3).setCellValue("-");
					row.createCell(4).setCellValue("-");
					row.createCell(3).setCellValue("Spilled Over to next Sprint");
				}else {
					row.createCell(3).setCellValue("-");
					row.createCell(4).setCellValue(scegetStatus.trim());
					if(parts[0].trim().contains("Partially Automated")) {
						row.createCell(5).setCellValue("Only part of the Scenario is Automated, remaining validation will be done Manually to Acheive 100% ..");
					}else {
						row.createCell(5).setCellValue(System.getProperty("Data"));
					}
				}
				workSheet.autoSizeColumn(3);
				workSheet.autoSizeColumn(4);
				workSheet.autoSizeColumn(5);
				myXls.close();
				FileOutputStream outputFile = new FileOutputStream(new File (filePath));
				bddTestResultSheet.write(outputFile);
				outputFile.close();
				System.out.println("Excel File is Successfully written");
				log.info("Excel File is Successfully written");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
