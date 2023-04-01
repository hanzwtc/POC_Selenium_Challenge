package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;



public class SeleniumDriver {

	public static DeviceDetailsReader device= new DeviceDetailsReader();

	private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	private static final ThreadLocal<SoftAssert> threadLocalSoftAssert= new ThreadLocal<>();

	static Logger log = LogManager.getLogger(SeleniumDriver.class);

	//initialize timeouts
	public final static int TIMEOUT= 45;
	public final static int PAGE_LOAD_TIMEOUT = 360;
	public static EventFiringWebDriver driver=null;

	ConfigFileReader configFileReader;

	public SeleniumDriver () throws UnknownHostException, MalformedURLException{
		WebDriver driver1;

		EventHandler handler;

		configFileReader = new ConfigFileReader();
		
		String browserName = configFileReader.getBrowserName().toUpperCase();
		String AutomationHost = configFileReader.getAutomationHost().toUpperCase();


		if (browserName.contains("chrome".toUpperCase()) && AutomationHost.contains("SeleniumLocalHost".toUpperCase())) {
			driver1=createChromeDriver();
			driver = new EventFiringWebDriver(driver1);
			driver.manage().window().maximize();
			handler = new EventHandler();
			driver.register(handler);
		}else if(browserName.equalsIgnoreCase("firefox")&& AutomationHost.equalsIgnoreCase("SeleniumLocalHost")) {
			driver1=createFirefoxDriver();
			driver = new EventFiringWebDriver(driver1);
			driver.manage().window().maximize();
			handler = new EventHandler();
			driver.register(handler);
		}else if(browserName.equalsIgnoreCase("ie")&& AutomationHost.equalsIgnoreCase("SeleniumLocalHost")) {
			driver1=createIEDriver();
			driver = new EventFiringWebDriver(driver1);
			driver.manage().window().maximize();
			handler = new EventHandler();
			driver.register(handler);
		}else if(browserName.equalsIgnoreCase("safari")&& AutomationHost.equalsIgnoreCase("SeleniumLocalHost")) {
			driver1=createSafariDriver();
			driver = new EventFiringWebDriver(driver1);
			driver.manage().window().maximize();
			handler = new EventHandler();
			driver.register(handler);

		}else if( browserName.contains("ios".toUpperCase())|| browserName.contains("android".toUpperCase()) && AutomationHost.contains("SeleniumBrowserstack".toUpperCase())) {
			driver1=createWebDriverBrowserStackMobile();
			driver = new EventFiringWebDriver(driver1);
			handler = new EventHandler();
			driver.register(handler);

		}else if( browserName.contains("chrome".toUpperCase())|| browserName.contains("firefox".toUpperCase()) || browserName.contains("edge".toUpperCase()) || browserName.contains("safari".toUpperCase()) && AutomationHost.contains("SeleniumBrowserstack".toUpperCase())) {
			driver1 = createWebDriverBrowserStack();
			driver = new EventFiringWebDriver(driver1);
			driver.manage().window().maximize();
			handler = new EventHandler();
			driver.register(handler);

		}else {
			throw new WebDriverException("Browser name>>>>"+browserName+"<<<<is not recognized");
		}

			driver.manage().timeouts().implicitlyWait(TIMEOUT,TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			String window=driver.getWindowHandle();
			log.info("window id: "+window);
			threadLocalDriver.set(driver);

	}


	public static WebDriver getDriver() {
		return threadLocalDriver.get();

	}



	
	public static void setUpDriver() throws UnknownHostException, MalformedURLException {
		if(getDriver()==null) new SeleniumDriver();
		
	}
	
	public static void tearDown() {
		if(getDriver()!=null) {
			try {
				getDriver().quit();
				threadLocalDriver.set(null);
			}catch(Exception e) {
			threadLocalDriver.set(null);
			}
		}
	}


	
	public WebDriver createChromeDriver() {
		WebDriver driver = null;
		configFileReader = new ConfigFileReader();
		ChromeOptions options= new ChromeOptions();

		if(configFileReader.getHeadlesMode().equalsIgnoreCase("true")) {
			options.addArguments("--headless");
			System.out.println("True for chrome Headlesss");
		}
//		if(!seleniumServerHostName.isEmpty() && !seleniumServerHostName.equalsIgnoreCase("localhost") && proxyPort>6) {
//		options.addArguments("--proxy-server=http://" + localHachineName + ":" + proxyPort);
//		options.addArguments("--proxy-bypass-list=\"127.6.6.1;localhost;*.nam.nsroot.net;*.cfapps-gtl-dev.nam.nsroot.net;*.citigroup.net;*.ibankatciti.r")
//		}

		if (configFileReader.getMobileEmulation().equalsIgnoreCase("true")){

			Map<String, Object> deviceMetrics = new HashMap<>();
			deviceMetrics.put("width", Integer.parseInt(configFileReader.getWidth()));
			deviceMetrics.put("height",Integer.parseInt(configFileReader.getHeight()));
			deviceMetrics.put("pixelRatio", Double.parseDouble(configFileReader.getPixelRatio()));
			Map<String, Object> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceMetrics", deviceMetrics);
			mobileEmulation.put("userAgent", configFileReader.getUserAgent());
			options.setExperimentalOption("mobileEmulation", mobileEmulation);

		}

		Map<String, Integer> prefs=new HashMap<String, Integer>();
		prefs.put("profile.default_content_settings.popups", 1);
		prefs.put("profile.default_content_setting_values.notifications", 1);
		options.setExperimentalOption("prefs",prefs);

		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disab1e-gpu", "--window-size=1926,1269","--ignore-certificate-errors");
		options.addArguments("chrome.switches", "--disable-extensions");

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		options.setCapability("goog:loggingPrefs", logPrefs );

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");


		int countRetry = 0;
		int maxRetryCount=3;
		Exception webDriverException = null;
		
		while(driver==null && countRetry<maxRetryCount){

			try{
					String chromedriverFromSystemPath = getChromedriverFromSystemPath();
					if(chromedriverFromSystemPath.length()>0) {
						System.setProperty("webdriver.chrome.driver", chromedriverFromSystemPath);
					}
					else if(new File("C:/Program Files (x86)/Selenium Client/Selenium drivers/chromedriver.exe").exists() && isChromedriverCompatibleNithChromeBrowser()) {
						System.setProperty("webdr1ver.chrome.dr1ver", "C:/Program Files (x86)/Selen1um Client/Selenium dr1vers/chromedr1ver.exe" );
						}
					else {
						String proxyDetails = "pftwbappp04-wcg.credito.bcp.com.pe:8080";

						try{
							System.out.println(">> Selenium Chrom Driver - Initiation - Try Part");
							WebDriverManager.chromedriver().setup();
						}catch (Exception e) {
							System.out.println(">> Selenium Chrom Driver - Initiation - Catch Part");
							WebDriverManager.chromedriver().forceDownload().proxy(proxyDetails).setup();
						}
					}
					driver=new ChromeDriver(capabilities);


				}
				catch(WebDriverException e) {
					webDriverException=e;
					log.info("Trying Again "+(countRetry+1) +" more times to create driver instances..");	
				}
			countRetry++;
			}
		if(driver==null) throw new WebDriverException("Cannot create Chrome Driver Instance after "+maxRetryCount+"Trials" + webDriverException.getMessage());
		return driver;	
		}

	//**********************************************************************************************************************************************************

	public WebDriver createWebDriverBrowserStackMobile() {

		WebDriver driver = null;

		//int proxyPort=LocalProxyServer.getPortNum();
		ChromeOptions options= new ChromeOptions();
		//if(configFileReader.getHeadlesMode().equalsIgnoreCase("true")) {
		//	options.addArguments("--headless");
	//		System.out.println("True for chrome Headlesss");
	//	}

		//Map<String, Integer> prefs=new HashMap<String, Integer>();
		//prefs.put("profile.default_content_settings.popups", 1);
		//prefs.put("profile.default_content_setting_values.notifications", 1);
		//options.setExperimentalOption("prefs",prefs);

		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disab1e-gpu", "--window-size=1926,1269","--ignore-certificate-errors");
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		options.setCapability("goog:loggingPrefs", logPrefs );



		DesiredCapabilities capabilities = new DesiredCapabilities();

		if(configFileReader.getBrowserName().equals("ios")|| configFileReader.getBrowserName().equals("android")){
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability("browserName", device.getBrowserName());
			capabilities.setCapability("device", device.getDevice());
			capabilities.setCapability("realMobile", "true");
			capabilities.setCapability("os_version", device.getOs_version());
			capabilities.setCapability("name", "TEST-INTERSEGURO-VEHICULAR");
			capabilities.setCapability("browserstack.networkLogs", "true");
		}

		int countRetry = 0;
		int maxRetryCount=3;
		Exception webDriverException = null;

		while(driver==null && countRetry<maxRetryCount){
			try{

					String URL = "https://" + device.getUSERNAME() + ":" + device.getAUTOMATE_KEY() + "@hub-cloud.browserstack.com/wd/hub";
				driver = new RemoteWebDriver(new URL(URL), capabilities);

			}
			catch(WebDriverException | MalformedURLException e) {
				webDriverException=e;
				log.info("Trying Again "+(countRetry+1) +" more times to create driver instances..");
			}
			countRetry++;
		}
		if(driver==null) throw new WebDriverException("Cannot create Chrome Driver Instance after "+maxRetryCount+"Trials" + webDriverException.getMessage());
		return driver;
	}


	public WebDriver createWebDriverBrowserStack() {

		WebDriver driver = null;

		//int proxyPort=LocalProxyServer.getPortNum();
		ChromeOptions options= new ChromeOptions();
		//if(configFileReader.getHeadlesMode().equalsIgnoreCase("true")) {
		//	options.addArguments("--headless");
		//		System.out.println("True for chrome Headlesss");
		//	}

		//Map<String, Integer> prefs=new HashMap<String, Integer>();
		//prefs.put("profile.default_content_settings.popups", 1);
		//prefs.put("profile.default_content_setting_values.notifications", 1);
		//options.setExperimentalOption("prefs",prefs);

		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disab1e-gpu", "--window-size=1926,1269","--ignore-certificate-errors");
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		options.setCapability("goog:loggingPrefs", logPrefs );




		DesiredCapabilities capabilities = new DesiredCapabilities();
		String browsername = configFileReader.getBrowserName();

		if(browsername.equalsIgnoreCase("chrome")|| browsername.equalsIgnoreCase("firefox")|| browsername.equalsIgnoreCase("edge")|| browsername.equalsIgnoreCase("safari")){

			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability("browser", device.getBrowserName());

			if (browsername.equalsIgnoreCase("safari")){
				capabilities.setCapability("os", "OS X");
			}else {
				capabilities.setCapability("os", "Windows");
			}

			//capabilities.setCapability("os_version", device.getOs_version());
			capabilities.setCapability("name", "TEST-INTERSEGURO-VEHICULAR");
			capabilities.setCapability("browserstack.networkLogs", "true");

		}

		int countRetry = 0;
		int maxRetryCount=3;
		Exception webDriverException = null;

		while(driver==null && countRetry<maxRetryCount){
			try{

				String URL = "https://" + device.getUSERNAME() + ":" + device.getAUTOMATE_KEY() + "@hub-cloud.browserstack.com/wd/hub";
				driver = new RemoteWebDriver(new URL(URL), capabilities);

			}
			catch(WebDriverException | MalformedURLException e) {
				webDriverException=e;
				log.info("Trying Again "+(countRetry+1) +" more times to create driver instances..");
			}
			countRetry++;
		}
		if(driver==null) throw new WebDriverException("Cannot create Chrome Driver Instance after "+maxRetryCount+"Trials" + webDriverException.getMessage());
		return driver;
	}


	//**********************************************************************************************************************************************************

	public static String getChromedriverFromSystemPath() {
		String cmd;
		if(System.getProperty("os.name").contains("Windows")) {
			//cmd = "where /r c:\\Users\\" +System.getProperty("user.name")+"\\ chromedriver.exe";
			cmd = "where /r c:\\ chromedriver.exe";
		}else {
			cmd = "which chromedriver";	
			}
		java.util.Scanner s = null;
		try {
			s= new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");	
		}catch(IOException e) {e.printStackTrace();}
		assert s != null;
		String res = s.hasNext() ? s.next() : "";
		return res.split("\\r\\n")[0];
			
	}
	
	public boolean isChromedriverCompatibleNithChromeBrowser() {
		double chromeDriverVer= getChromeDriverVersion();
		int chromeBrowserVer= getChromeBrowserVersion();
		if(chromeDriverVer<74 && chromeBrowserVer >2.38) return true;
		else if (chromeDriverVer==74 && chromeBrowserVer >=2.44) return true;
		else if (chromeDriverVer==75 && chromeBrowserVer >=75) return true;
		else if (chromeDriverVer==76 && chromeBrowserVer >=76) return true;
		else return chromeDriverVer == 77 && chromeBrowserVer >= 77;
	}

	private int getChromeBrowserVersion() {
		// TODO Auto-generated method stub
		String cmd = "wmic datafile where name = \"C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe\" get version /value";
		java.util.Scanner s = null;
		try {
			s= new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");	
		}catch(IOException e) {e.printStackTrace();}
		assert s != null;
		String res = s.hasNext() ? s.next() : "";
		return Integer.parseInt(res.replaceAll("\n","").replaceAll("\r", "").replaceAll("Version=","").trim().substring(0, 2));
	}

	private double getChromeDriverVersion() {
		// TODO Auto-generated method stub
		String cmd = "\"C:\\Program Files (x86)\\Selenium Client\\Selenium drivers\\chromedriver.exe\" -v";
		java.util.Scanner s = null;
		try {
			s= new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");	
		}catch(IOException e) {e.printStackTrace();}
		assert s != null;
		String res = s.hasNext() ? s.next() : "";
		return Double.parseDouble(res.substring(13,17));
	}

	public WebDriver createFirefoxDriver() {
		return null;
			
		}
	
	public WebDriver createIEDriver() {
		
		WebDriver driver = null;
		String SeleniumServerHostName = configFileReader.getAutomationHost();
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
		
		if(SeleniumServerHostName.isEmpty() || SeleniumServerHostName.equalsIgnoreCase("SeleniumLocalHost")) {
			WebDriverManager.iedriver().arch32().setup();
		}
		
		int countRetry=0;
		int maxRetryCount=3;
		
		while(driver==null && countRetry<maxRetryCount){
			try{
				if(SeleniumServerHostName.isEmpty() || SeleniumServerHostName.equalsIgnoreCase("SeleniumLocalHost")) {
					String seleniumServerPort = configFileReader.getSeleniumServerPort();
					String remoteHost = "http://"+SeleniumServerHostName+":"+seleniumServerPort+"/wd/hub";
					driver = new RemoteWebDriver(new URL(remoteHost),capabilities);
				}else {
					driver = new InternetExplorerDriver(capabilities);
				}
			} catch (Exception e) {
				log.info("retrying "+(countRetry+1)+ " more time(s) to create driver instances..");
			}
			countRetry++;
		}
		if(driver==null) throw new WebDriverException("Unable to create IE Driver instance after +"+maxRetryCount+"retries..");
		return driver;
	}
	
	public WebDriver createSafariDriver() {
		return null;
			
		}




}
