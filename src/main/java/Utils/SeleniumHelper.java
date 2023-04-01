package Utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.BeforeActions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeleniumHelper {
	public static String reportNameFinal;
	public static String reportFolderNameFinal;
	public static String key = new ConfigFileReader().getEncryptionKey();
	public static List<String[]> scenarioList = new ArrayList<String[]>();

	static Logger log = LogManager.getLogger(SeleniumHelper.class);


	ConfigFileReader configFileReader;

	//private static Object driver;
	public static WebDriver driver = SeleniumDriver.getDriver();

	public static boolean isElementPresent(WebElement element) {

		try {
			Thread.sleep(1000);
			//log.info("Selenium Helper checking for WebElement>>>>"+element.getText());
			return element.isDisplayed();
		}catch(NoSuchElementException | InterruptedException e) {
			return false;
		}
	}
	
	public static boolean isElementPresentXAPTH(String xpath) {
		try {
			log.info("Selenium Helper checking for Xpath>>>>"+xpath);
			boolean isPresent = !SeleniumDriver.getDriver().findElements(By.xpath(xpath)).isEmpty();
			log.info(xpath+" Is Present ?= "+isPresent);
			return isPresent;
			
		}catch(NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementPresentCheckUsingJavaScriptExecutor(WebElement element) {

		boolean validate ;
		try {

			Object obj = ((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("return typeof(arguments[0]) != 'undefined' && arguments[0] != null;", element);

			if (obj.toString().contains("true")) {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: SUCCESS");
				validate = true;
			} else {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
				validate = false;
			}

		} catch (NoSuchElementException e) {
			System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
			validate = false;
		}
		return validate ;
	}


	public static boolean ExistElement(WebElement element) {

		try {

			WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(),5);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;

		}catch(NoSuchElementException e) {
			return false;
		}
	}


	public static void ScrolltoElement(WebElement element) {
		boolean val = true;
		try{
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("window.scrollBy(0,-130)");
		}catch(NoSuchElementException e){
			val =  false;
			e.printStackTrace();
		}

	}





	public static void HighlightElement(WebElement element) {
		try{
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",element);
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("window.scrollBy(0,-130)");
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}

	}


	public static void verifyElement(WebElement element) {
		try{

			if(element.isDisplayed()){
				((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
				((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",element);
				((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("window.scrollBy(0,-130)");
				//log.info("Element "+element.getText()+" is displayed successfully");
				//takeScreenshot(element.getText().replaceAll("[^a-zA-Z0-9_-]", "").substring(0,15));
				//Thread.sleep(700);
				((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");

			}

		}catch(NoSuchElementException | StringIndexOutOfBoundsException e){
			log.info("Expected WebElement is not displayed.");

		}

	}



	public static void ClickElement(WebElement element)  {
		try{

			//ScrolltoElement(element);
			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].click();", element);

		}catch(NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e){

			e.printStackTrace();

		}
	}

	public static void SendTextElement(WebElement Element,String Text) {
		try {

			for (int i = 1; i <= Text.length(); i++) {
				Element.sendKeys(Text.substring(i - 1 , i));
			}


			//((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].value='"+Text+"';", Element);
			//Element.sendKeys(Keys.ENTER);
			//Element.sendKeys(Keys.ENTER);


		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}



	public static void SelectElement(WebElement element,String Modulo) {
		try{

			Select Module = new Select(element);
			Module.selectByVisibleText(Modulo);

			//((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].select();", element);

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}


	public static void CleanText(WebElement element) {
		try{


			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].value = '';", element);
			//element.sendKeys(Keys.CONTROL + "a");
			//element.sendKeys(Keys.DELETE);
			//element.sendKeys(Keys.BACK_SPACE);
			//	element.sendKeys(Keys.HOME);

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}


	public static void SendText(WebElement element,String Str) {
		try{


			((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].value = '"+Str+"';", element);
			//element.sendKeys(Keys.CONTROL + "a");
			//element.sendKeys(Keys.DELETE);
			//element.sendKeys(Keys.BACK_SPACE);
			//	element.sendKeys(Keys.HOME);

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}


	public static void SendKeys(WebElement element,String Str) {
		try{

			element.sendKeys(Str);
			//((JavascriptExecutor) SeleniumDriver.getDriver()).executeScript("arguments[0].value = '"+Str+"';", element);
			//element.sendKeys(Keys.CONTROL + "a");
			//element.sendKeys(Keys.DELETE);
			//element.sendKeys(Keys.BACK_SPACE);
			//	element.sendKeys(Keys.HOME);

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}

	public static void Alert(boolean action) {

		try {
			Thread.sleep(1500);
			Alert PopUp = SeleniumDriver.getDriver().switchTo().alert();
			if (action){
				PopUp.accept();
			}else {
				PopUp.dismiss();
			}


		} catch (NoAlertPresentException | InterruptedException exception ) {
			exception.printStackTrace();
		}

	}


	public static void ChangeTabs(int NumberTab) {
		try{

			ArrayList<String> tabs = new ArrayList<>(SeleniumDriver.getDriver().getWindowHandles());
			SeleniumDriver.getDriver().switchTo().window(tabs.get(NumberTab));

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}


	public static void CloseTab() {
		try{

			SeleniumDriver.getDriver().close();

		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}


	public static String GetNetworkLogs(){
		String Network = "";
		for (LogEntry logEntry : SeleniumDriver.getDriver().manage().logs().get(LogType.PERFORMANCE).getAll())
		{
			Network = Network + logEntry;

		}

		return Network;
	}

	public static String SearchNetworkLog(String NetWorkLogs,String TexttoSearch,String IniTextDelimiter,String FinTextDelimiter,int LoopNumber){

		int Initext=0;
		int Fintext=0;
		int Cont = 1;
		for (int i = 1 ; i<NetWorkLogs.length();i++){

				int value = NetWorkLogs.indexOf(TexttoSearch,i);

				if (value!=-1){

					for ( Initext=value ;Initext>=1;Initext--){

						String Delimiter1 = NetWorkLogs.substring(Initext-1,((Initext - 1) + IniTextDelimiter.length()));

						if (IniTextDelimiter.equals(Delimiter1)){
							break;
						}
					}

					for ( Fintext=value+1 ;Fintext<=NetWorkLogs.length();Fintext++){

						String Delimiter2 = NetWorkLogs.substring(Fintext-1,((Fintext - 1) + FinTextDelimiter.length()));

						if (FinTextDelimiter.equals(Delimiter2)){
							break;
						}
					}

					i= value + 1;

					if (Cont ==LoopNumber){
						return  NetWorkLogs.substring(Initext,Fintext-1);
					}
					Cont = Cont + 1 ;


				}else {
					break;
				}

		}

		return "";
	}



	public static String SearchNetworkLogValue(String NetWorkLogs,String TexttoSearch,String IniTextDelimiter,String FinTextDelimiter){

		int Initext=0;
		int Fintext=0;

		for (int i = NetWorkLogs.length() ; i>=1;i--){

			int value = NetWorkLogs.indexOf(TexttoSearch,i);

			if (value!=-1){

				for ( Initext=value ;Initext>=1;Initext--){

					String Delimiter1 = NetWorkLogs.substring(Initext-1,Initext);

					if (IniTextDelimiter.equals(Delimiter1)){
						break;
					}
				}

				for ( Fintext=value+1 ;Fintext<=NetWorkLogs.length();Fintext++){

					int Fintext2 = Fintext-1;
					String Delimiter2 = NetWorkLogs.substring(Fintext2,Fintext);

					if (FinTextDelimiter.equals(Delimiter2)){
						break;
					}
				}

				;
				System.out.println("RESULTADO DE BUSCAR : "+ NetWorkLogs.substring(Initext,Fintext-1));
				i= value + 1;
				return  NetWorkLogs.substring(Initext,Fintext-1);
			}else {
				break;
			}

		}

		return "";
	}


	public static String takeScreenshot(String imageName) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File srcFile = ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.FILE);
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
			Date curDate = new Date();
			String imagePath;
			String strDate = sdf.format(curDate) ;

			imagePath = System.getProperty("user.dir")+"//src//test-output//Results//Screenshots"+"//"+imageName+"_"+strDate+".png";
			try {
				FileUtils.copyFile(srcFile, new File(imagePath));
				BeforeActions.threadLocalScenario.get().embed(Files.readAllBytes(Paths.get(srcFile.getAbsolutePath())),"image/png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	return imagePath;
	}
	
	public static void setUpExtentReport() {
		ConfigFileReader configFileReader = new ConfigFileReader();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
		Date curDate = new Date();
		String strDate = sdf.format(curDate) ;
		String fileName;
		if(System.getProperty("os.name").contains("Windows")) {
			fileName = System.getProperty("user.dir")+configFileReader.getReportPath()+strDate+"/"+strDate+".html";
			reportNameFinal=fileName;
			reportFolderNameFinal=System.getProperty("user.dir")+configFileReader.getReportPath()+strDate;
		}else {
			fileName = System.getProperty("user.dir")+"/Results.html";
			reportNameFinal=fileName;
			reportFolderNameFinal = System.getProperty("user.dir");
		}
		System.setProperty("extent.reporter.html.start","true");
		System.setProperty("extent.reporter.html.out",reportNameFinal);
	}

	public static void tearDownReport() {
		// TODO Auto-generated method stub
		
	}
	
}
