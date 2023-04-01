package baseRunner;

import Utils.*;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import stepDefinitions.AfterActions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseRunnerBrowser extends AbstractTestNGCucumberTests{
    //private TestNGCucumberRunner testNGCucumberRunner;
	public static String filenameFinal = "Default";
	public static String featurePath="";
	ConfigFileReader configFileReader;


	public BaseRunnerBrowser(String featurePath) {
		this.featurePath = featurePath;
	}


	@Override
	@DataProvider(parallel=false)
	public Object[][] scenarios(){
		return super.scenarios();
	}
    
	@BeforeSuite
	public void beforeSuite() throws InvalidFormatException, IOException {
		FeatureOverride.overrideFeatureFiles(featurePath);
	}
	
	@BeforeClass
	public static void setup() {
		SeleniumHelper.setUpExtentReport();

//		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_hhmmss");
//		Date curDate = new Date();
//		String strDate = sdf.format(curDate) ;
////		String xlfileName = System.getProperty("user.dir")+"/"+strDate+".xls";
////		filenameFinal=xlfileName;
////		System.out.println("excel path = "+xlfileName);
////		File newFile1 = new File(xlfileName);
////		System.setProperty("xlFile", xlfileName);
////		System.setProperty("lgcag", "");
	}
 
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			String exception = result.getThrowable().toString();
			SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			Date date = new Date();
			//String strDate = sdf.format(curDate) ;
			try {
				date=objSDF.parse(objSDF.format(date));
			}catch(ParseException e) {
				e.printStackTrace();	
			}
			String required_error = "\\(Session)".split(result.getThrowable().toString())[0];
			//String required_error = result.getThrowable().toString().split("\\(Session)")[0];
			String required_exception = result.getThrowable().getClass().toGenericString().split("class")[1];
			String pageSource = AfterActions.getThreadLocalPageSource();
			new ExcelUtils().updateLogStatus("","","FAIL", date ,required_error,required_exception,pageSource);
		}
	}
 
	@AfterClass
	public void afterClass() {

		SeleniumHelper.tearDownReport();
		SeleniumDriver.tearDown();
		MySql.Close_Connection();
		ReportHTML.CloseReport();
		ReportHTML.CrearTXT();

	}

}
