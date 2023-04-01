package Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
public class EventHandler implements WebDriverEventListener{
	public StackTraceElement[] stackTrace;
	ExcelUtils utils = new ExcelUtils();
	Date date;
	String strDateFormat = "yyyy-MM-dd hh:mm:ss a"; // Date format is Specified
	SimpleDateFormat objsDF;
	
	public void afterChangeValueof(WebElement arg0, WebDriver arg1) {
		date = new Date (); // Current System Date and time is assigned to objDate
		objsDF = new SimpleDateFormat(strDateFormat);
		try {
			date=objsDF.parse(objsDF.format(date));
		}catch (ParseException e) {e.printStackTrace();}
		utils.updateLogStatus("after sending keys", arg0.toString(), "PASS", date,"","","");
	}

	
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		//System.out.println("inside method afterClickOn on");
		date = new Date();
		objsDF = new SimpleDateFormat(strDateFormat);
		try {
			date=objsDF.parse(objsDF.format(date));
		}catch (ParseException e) {e.printStackTrace();}
		utils.updateLogStatus("After click on", arg0.toString(), "PASS", date,"","","");
	}
	
	public void afterNavigateBack(WebDriver arg0) {
		System.out.println("Inside the after navigateback to"+arg0.getCurrentUrl());
	}

	public void afterNavigateTo(String arg0, WebDriver arg1) {
		//System.out.println("inside method afterClickOn on");
		date = new Date();
		objsDF = new SimpleDateFormat(strDateFormat);
		try {
			date=objsDF.parse(objsDF.format(date));
		}catch (ParseException e) {e.printStackTrace();}
		utils.updateLogStatus("After Navigate To", arg0.toString(), "PASS", date,"","","");
	}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		//System.out.println("inside method afterClickOn on");
		date = new Date();
		objsDF = new SimpleDateFormat(strDateFormat);
		try {
			date=objsDF.parse(objsDF.format(date));
		}catch (ParseException e) {e.printStackTrace();}
		utils.updateLogStatus("After Finding Element", arg0.toString(), "PASS", date,"","","");
		
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}

}
