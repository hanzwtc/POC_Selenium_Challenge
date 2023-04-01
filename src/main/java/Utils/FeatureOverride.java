package Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class FeatureOverride {
	
	public static String currentFeatureFile;
	public static boolean searchByScenarioName;
	
	private static List<String> setExcelDataToFeature(File featureFile) throws InvalidFormatException, IOException{
		currentFeatureFile = featureFile.getPath();
		List<String> fileData = new ArrayList<String>();
		try(BufferedReader buffReader = new BufferedReader(new InputStreamReader (new BufferedInputStream(new FileInputStream(featureFile)),"UTF-8"))){
			String data;
			List<Map<String,String>> excelData= null;
			String scenarioName = null;
			searchByScenarioName = false;
			List<String> parameterList = new ArrayList<String>();
			boolean foundHashTag = false;
			boolean featureData = false;
			boolean exampleData = false;
			while((data=buffReader.readLine()) != null) {
				
				String sheetName= null;
				String excelFilePath=null;
				if(data.trim().startsWith("Scenario")) {
					parameterList = new ArrayList<String>();
					scenarioName = data.substring(data.indexOf(":")+1).trim();
//					System.out.println(scenarioName);
				}
				
				if(exampleData && data.trim().startsWith("|")) {
					String[] tmpParamArray = data.trim().split("\\|");
					for(int ItemIndex=1; ItemIndex<tmpParamArray.length; ItemIndex++) {
						parameterList.add(tmpParamArray[ItemIndex].trim());
					}
					exampleData=false;
//					System.out.println(parameterList.toString());
				}
				
				if(data.trim().startsWith("Examples")) {
					exampleData=true;
				}
				
				if(data.trim().startsWith("##@data")) {
					searchByScenarioName=true;
					excelFilePath = data.substring(StringUtils.ordinalIndexOf(data,"@",2)+1, data.lastIndexOf("@"));
//					System.out.println(excelFilePath);
					sheetName = data.substring(data.lastIndexOf("@")+1, data.length());
//					System.out.println(sheetName);
					foundHashTag = true;
					fileData.add(data);
//					System.out.println(searchByScenarioName);
				}
				
				if(data.trim().startsWith("##@externaldata")) {
					searchByScenarioName=false;
					excelFilePath = data.substring(StringUtils.ordinalIndexOf(data,"@",2)+1, data.lastIndexOf("@"));
					sheetName = data.substring(data.lastIndexOf("@")+1, data.length());
					foundHashTag = true;
					fileData.add(data);
				}

				if(foundHashTag) {
					excelData = new ExcelReader().getData(scenarioName, excelFilePath, sheetName, parameterList);
					for(int rowNumber = 0; rowNumber<excelData.size(); rowNumber++) {
						String cellData="";
						for(Entry<String, String> mapData : excelData.get(rowNumber).entrySet()) {
							cellData=cellData+"|"+mapData.getValue();
						}
						fileData.add(cellData+"|");
					}
					foundHashTag = false;
					featureData = true;
					continue;
				}

				if(data.startsWith("|")||data.endsWith("|")) {
					if(featureData) {
						continue;
					}else {
						fileData.add(data);
						continue;
					}
				}else {
					featureData=false;
				}
				fileData.add(data);
			}
		}
		//System.out.println(fileData.toString());
		return fileData;

	}
	
	public static List<File> listOfFeatureFiles(File folder){
		List<File> featureFiles = new ArrayList<File>();
		for(File filelEntry:folder.listFiles()) {
			if(filelEntry.isDirectory()) {
				featureFiles.addAll(listOfFeatureFiles(filelEntry));
			}else {
				if(filelEntry.isFile() && filelEntry.getName().endsWith(".feature")) {
					featureFiles.add(filelEntry);
				}
			}
		}
		return featureFiles;
	}
	
	public static void overrideFeatureFiles(String featuresDirectoryPath) throws InvalidFormatException, IOException {
		List<File> listOfFeatureFiles = listOfFeatureFiles(new File(featuresDirectoryPath));
		for(File featureFile:listOfFeatureFiles) {

			List<String> featureWithExcelData = setExcelDataToFeature(featureFile);

			try(BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(featureFile),"UTF-8"));){

				for (String string:featureWithExcelData) {
					writer.write(string);
					writer.write("\n");
				}

			}

		}
	}
}
		