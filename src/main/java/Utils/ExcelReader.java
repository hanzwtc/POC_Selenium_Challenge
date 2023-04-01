package Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;



public class ExcelReader {

	public List<Map<String, String>> getData(String scenarioName, String excelFilePath, String sheetName, List<String> parameterList) 
			throws InvalidFormatException , IOException {
		// TODO Auto-generated method stub
		
		Sheet sheet = getSheetByName(excelFilePath, sheetName);
		//System.out.println(sheet.toString());
		if(sheet==null) {
			throw new IOException (excelFilePath+"@"+sheetName+"  is not valid for Feature file  : "+FeatureOverride.currentFeatureFile+"\n Scenario Name  :  ");
		}
		return readSheet(sheet, parameterList, scenarioName);
	}
	
	public List<Map<String, String>> getData(String scenarioName, String excelFilePath, int sheetNumber, List<String> parameterList) 
			throws InvalidFormatException , IOException {
		// TODO Auto-generated method stub
		
		Sheet sheet = getSheetByIndex(excelFilePath, sheetNumber);
		return readSheet(sheet, parameterList, scenarioName);
	}

	private Sheet getSheetByIndex(String excelFilePath, int sheetNumber) throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		Sheet sheet = ((Workbook) getWorkBook(excelFilePath)).getSheetAt(sheetNumber);
		return sheet;
	}

	private Sheet getSheetByName(String excelFilePath, String sheetName) throws InvalidFormatException , IOException {
		// TODO Auto-generated method stub
		Sheet sheet = ((Workbook) getWorkBook(excelFilePath)).getSheet(sheetName);
		return sheet;
	}

	private Object getWorkBook(String excelFilePath) throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		return WorkbookFactory.create(new File(excelFilePath));
	}
	
	private List<Map<String, String>> readSheet(Sheet sheet, List<String> parameterList, String scenarioName) throws InvalidFormatException {
//		System.out.println(sheet.toString()+"<<>>"+parameterList.toString()+"<<>>"+scenarioName);
		
		// TODO Auto-generated method stub
		Row row = null;
		int totalRow = sheet.getPhysicalNumberOfRows();
		int setCurrentRow= 0;
		int setEndRow = totalRow;
		boolean scenarioNameFoundFlag = false;
		if(FeatureOverride.searchByScenarioName) {
			for(int rowNumber = 0; rowNumber<setEndRow; rowNumber++) {
				String cellValue= null;
				if(sheet.getRow(rowNumber).getCell(0)!=null) {
					cellValue=sheet.getRow(rowNumber).getCell(0).getStringCellValue().trim();
				}
				if(scenarioNameFoundFlag) {
					if(cellValue!=null&&cellValue!="") {
						setEndRow = rowNumber;
						break;
					}
				}
				else if (cellValue!=null&&cellValue.equalsIgnoreCase(scenarioName)) {
					setCurrentRow=rowNumber;
					scenarioNameFoundFlag=true;
				}
			}
			if(!scenarioNameFoundFlag) {
				throw new InvalidFormatException("Scenario Name \""+scenarioName+"\" not found in Excel..  Feature File : "+FeatureOverride.currentFeatureFile);
			}
		}
		
		
		List <Map<String, String>> excelRows = new ArrayList <Map<String, String>>();
		int headerRowNumber= 0;
		if(headerRowNumber!=-1) {
			int totalColumn = sheet.getRow(setCurrentRow).getLastCellNum();
			ArrayList<Integer> colInScope = new ArrayList<Integer>();
			ArrayList<String> excelCols = new ArrayList<String>();
			
			for(int currentRow=setCurrentRow; currentRow<setEndRow; currentRow++) {
				if (currentRow==setCurrentRow) {
					for(int colNum = 0; colNum<totalColumn; colNum++) {
						try {
							excelCols.add(sheet.getRow(currentRow).getCell(colNum).getStringCellValue().toLowerCase());
						}catch(NullPointerException e) {
							throw new NullPointerException("Column name should not be Blank in Data Sheet.  Row ::"+(int)(currentRow+1)
							+", Column :: "+(int)(colNum+1) + ". \n Feature File :: "+ FeatureOverride.currentFeatureFile + "\n Scenario Name :: "+ scenarioName);
						}
					}
					for (int colNum = 0; colNum <parameterList.size(); colNum++) {
						int colIndex = excelCols.indexOf(parameterList.get(colNum).toLowerCase());
						if (colIndex<0) {
							throw new InvalidFormatException("Parameter Name\n" + parameterList.get(colNum) + "\n Not found in Excel Sheet. \n Feature File "+ FeatureOverride.currentFeatureFile );
						}
						colInScope.add(colIndex);
					}
				}else {
					if (System.getProperty("environment")!=null && FeatureOverride.searchByScenarioName) {
						if (sheet.getRow(currentRow)!=null && sheet.getRow(currentRow).getCell(1)!=null) {
							if (!sheet.getRow(currentRow).getCell(1).getStringCellValue().equalsIgnoreCase(System.getProperty("environment"))) {
								//System.out.println("Env from Prop file "+System.getProperty("environment"));
								//System.out.println("Env from Excel file "+System.getProperty("environment"));
								continue;
							}
						}else {
							//System.out.println("Env from Prop file "+System.getProperty("environment"));
							//System.out.println("Env from Excel file "+System.getProperty("environment"));
							continue;
						}
					}
					row = getRow(sheet, sheet.getFirstRowNum() + currentRow);
					LinkedHashMap<String, String> columnMapData = new LinkedHashMap<String, String>();
					for (int itemNum = 0; itemNum < colInScope.size();itemNum++) {
					columnMapData.putAll(getCellValue(sheet, row, colInScope.get(itemNum), setCurrentRow));	
					}
					excelRows.add(columnMapData);
				}
			}
		}
		//System.out.println(excelRows.toString());
		return excelRows;
	}

	private LinkedHashMap<String, String> getCellValue(Sheet sheet, Row row, Integer currentColumn,	int headerRow) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> columnMapData = new LinkedHashMap<String, String>();
		Cell cell;
		if(row==null) {
			if(sheet.getRow(headerRow).getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
				String columnHeaderName = sheet.getRow(headerRow).getCell(currentColumn).getStringCellValue();
				columnMapData.put(columnHeaderName, "");
			}
		} else {
			cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if(cell.getCellType()==CellType.STRING) {
				if(sheet.getRow(headerRow).getCell(cell.getColumnIndex(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()!=CellType.BLANK) {
					String columnHeaderName = sheet.getRow(headerRow).getCell(cell.getColumnIndex()).getStringCellValue();
					//System.out.println(columnHeaderName.toString()+"<<>>"+cell.getStringCellValue());
					columnMapData.put(columnHeaderName, cell.getStringCellValue());
				}
			}else if (cell.getCellType()==CellType.NUMERIC) {
				if(sheet.getRow(headerRow).getCell(cell.getColumnIndex(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()!=CellType.BLANK) {
						String columnHeaderName = sheet.getRow(headerRow).getCell(cell.getColumnIndex()).getStringCellValue();
						columnMapData.put(columnHeaderName, NumberToTextConverter.toText(cell.getNumericCellValue()));
				}
			}else if (cell.getCellType()==CellType.BLANK) {
				if(sheet.getRow(headerRow).getCell(cell.getColumnIndex(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()!=CellType.BLANK) {
						String columnHeaderName = sheet.getRow(headerRow).getCell(cell.getColumnIndex()).getStringCellValue();
						columnMapData.put(columnHeaderName, cell.getStringCellValue());
				}
			}else if (cell.getCellType()==CellType.BOOLEAN) {
				if(sheet.getRow(headerRow).getCell(cell.getColumnIndex(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()!=CellType.BLANK) {
					String columnHeaderName = sheet.getRow(headerRow).getCell(cell.getColumnIndex()).getStringCellValue();
					columnMapData.put(columnHeaderName, Boolean.toString(cell.getBooleanCellValue()));
				}
			}else if (cell.getCellType()==CellType.ERROR) {
				if(sheet.getRow(headerRow).getCell(cell.getColumnIndex(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()!=CellType.BLANK) {
					String columnHeaderName = sheet.getRow(headerRow).getCell(cell.getColumnIndex()).getStringCellValue();
					columnMapData.put(columnHeaderName, Byte.toString(cell.getErrorCellValue()));
				}
			}
		}
		return columnMapData;
	}

	private Row getRow(Sheet sheet, int rowNumber) {
		// TODO Auto-generated method stub
		return sheet.getRow(rowNumber);
	}
}
