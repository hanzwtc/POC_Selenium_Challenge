package stepDefinitions;

import Utils.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AfterActions {
	
	static Logger log = LogManager.getLogger(AfterActions.class);
	private static final ThreadLocal<String> pageSource = new ThreadLocal<>();

	@After
	public static void tearDown(Scenario scenario) {

		System.out.println("TEST END >>>>");
		
		String browserName = new ConfigFileReader().getBrowserName();
		if(browserName!=null && !browserName.isEmpty()) {


			if (SeleniumDriver.getDriver()!=null){
				getPageText(SeleniumDriver.getDriver());
			}
			log.info("CLOSING BROWSER >>>>");

			SeleniumDriver.tearDown();// CERRAR SELENIUM DIRVER POR CADA EJECUCION
			AppiumDriver.tearDown(); // CERRAR ANDROID O IOS DRIVER POR CADA EJECUCION

		}



		//if(PcommUtil.getSession()!=null) { //PARA QUE CIERRE HOST
		//	PcommUtil.capturePCOMMScreenshot("Final-Step");
		//	PcommUtil.logout();
		//}

		//log.info("Run Result Failed >> "+scenario.isFailed());

		ExcelUtils objExcelFile = new ExcelUtils();
		String filePath = System.getProperty("xlFile");
		System.out.println("Data >> "+System.getProperty("Data"));
		objExcelFile.createExlFile(filePath);
	//	objExcelFile.writeExlFile(scenario.getName(),scenario.getStatus().toString(),scenario.isFailed(), filePath);

		String scenarioStatus;

		if(scenario.getStatus().equals("passed")) {
			scenarioStatus = "Passed";
		}else {
			scenarioStatus = "Failed";
		}
		SeleniumHelper.scenarioList.add(new String[] {scenario.getName(),scenarioStatus});
		AppiumHelper.scenarioList.add(new String[] {scenario.getName(),scenarioStatus});
	}

	private static void getPageText(WebDriver driver) {

		// TODO Auto-generated method stub
		String pageText = driver.findElement(By.tagName("html")).getText();
		if(pageText.length()>32767) {
			pageText = pageText.substring(0,32766);
		}
		pageSource.set(pageText);
	
	}

	public static String getThreadLocalPageSource() {
		
		// TODO Auto-generated method stub
		return pageSource.get();
		
	}


}
