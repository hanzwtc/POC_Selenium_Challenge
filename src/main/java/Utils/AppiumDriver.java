package Utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class AppiumDriver {

    private static final ThreadLocal<AndroidDriver<AndroidElement>> threadLocalAndroidDriver= new ThreadLocal<>();
    private static final ThreadLocal<IOSDriver<IOSElement>> threadLocalIosDriver= new ThreadLocal<>();

    static Logger log = LogManager.getLogger(AppiumDriver.class);
    public static DeviceDetailsReader device= new DeviceDetailsReader();
    public static ConfigFileReader Host= new ConfigFileReader();

    public final static int TIMEOUT= 60;
   // public final static int PAGE_LOAD_TIMEOUT = 60;

    public AppiumDriver() throws UnknownHostException {

        String browserName = device.getBrowserName().toUpperCase();
        String AutomationHost = Host.getAutomationHost().toUpperCase();

        if(browserName.contains("android".toUpperCase())){

            AndroidDriver<AndroidElement>  driverAndroid = null;
            if (AutomationHost.contains("AppiumLocalHost".toUpperCase())){

                  driverAndroid  = createAndroidDriverLocal();

            }else if (AutomationHost.contains("AppiumBrowserstack".toUpperCase())){

                driverAndroid  = createAndroidDriverBrowserStack();

            }

            assert driverAndroid != null;
            driverAndroid.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
            threadLocalAndroidDriver.set(driverAndroid);

        }else if(browserName.contains("ios".toUpperCase())){


            IOSDriver<IOSElement>  driverIos = null;
            if (AutomationHost.contains("AppiumLocalHost".toUpperCase())){

                driverIos  = createIosDriverBrowserStack();

            }else if (AutomationHost.contains("AppiumBrowserstack".toUpperCase())){

                driverIos  = createIosDriverBrowserStack();

            }


            assert driverIos != null;
            driverIos.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
            threadLocalIosDriver.set(driverIos);

        }

    }


    public AndroidDriver<AndroidElement> createAndroidDriverBrowserStack() {


        AndroidDriver<AndroidElement>  driver = null;
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("device", device.getDevice());
        caps.setCapability("os_version", device.getOs_version());
        caps.setCapability("realMobile", "false");
        caps.setCapability("project", "INTERSEGURO");
        caps.setCapability("build", "TEST");
        caps.setCapability("name", "TEST-INTERSEGURO-VEHICULAR");
        caps.setCapability("app", device.getApp());
        caps.setCapability("browserstack.user", device.getUSERNAME());
        caps.setCapability("browserstack.key", device.getAUTOMATE_KEY());
        caps.setCapability("browserstack.networkLogs", "true");
        caps.setCapability("unicodeKeyboard", "true");
        caps.setCapability("resetKeyboard", "true");
        caps.setCapability("autoGrantPermissions", "true");
        caps.setCapability("browserstack.gpsLocation", "-12.1714046501,-76.9448038567");


        int countRetry = 0;
        int maxRetryCount=3;
        Exception webDriverException = null;

        while(driver==null && countRetry<maxRetryCount){

            try{

                String URL = "http://hub-cloud.browserstack.com/wd/hub";
                driver = new AndroidDriver<>(new URL(URL), caps);

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


    public AndroidDriver<AndroidElement> createAndroidDriverLocal() {


        AndroidDriver<AndroidElement>  driver = null;
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("device", device.getDevice());
        caps.setCapability("udid", device.getUdid());
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", device.getOs_version());
        caps.setCapability("appPackage", device.getAppPackage());
        caps.setCapability("appActivity", device.getAppActivity());
        caps.setCapability("unicodeKeyboard", "true");
        caps.setCapability("resetKeyboard", "true");
        caps.setCapability("autoGrantPermissions", "true");
        caps.setCapability("locationServicesEnabled", "true");
        caps.setCapability("locationServicesAuthorized", "true");
        caps.setCapability("gpsEnabled", "true");
        caps.setCapability("enablePerformanceLogging","true");
        caps.setCapability("networkSpeed", "gsm");
        //caps.setCapability("optionalIntentArguments", "-d geo:46.457398,-119.407305");
        caps.setCapability("intentAction", "android.intent.action.VIEW");
        caps.setCapability("newCommandTimeout", 300);
        int countRetry = 0;
        int maxRetryCount=3;
        Exception webDriverException = null;

        while(driver==null && countRetry<maxRetryCount){
            try{

                String URL = "http://127.0.0.1:4723/wd/hub";
                driver = new AndroidDriver<>(new URL(URL), caps);

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

    public static void setUpAndroidDriver() {

        try {
            if(getAndroidDriver()==null) new AppiumDriver();

        }catch (UnknownHostException e){
            e.printStackTrace();
        }

    }

    public static AndroidDriver<AndroidElement> getAndroidDriver() {
        return threadLocalAndroidDriver.get();
    }




    public IOSDriver<IOSElement> createIosDriverBrowserStack() {


        IOSDriver<IOSElement>  driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability("device", device.getDevice());
            caps.setCapability("os_version", device.getOs_version());
            caps.setCapability("project", "INTERSEGURO");
            caps.setCapability("build", "TEST");
            caps.setCapability("name", "TEST-INTERSEGURO-VEHICULAR");
            caps.setCapability("app", device.getApp());
            caps.setCapability("autoGrantPermissions", "true");
            caps.setCapability("browserstack.user", device.getUSERNAME());
            caps.setCapability("browserstack.key", device.getAUTOMATE_KEY());
            caps.setCapability("browserstack.networkLogs", "true");

        int countRetry = 0;
        int maxRetryCount=3;
        Exception webDriverException = null;

        while(driver==null && countRetry<maxRetryCount){

            try{

                   String URL = "http://hub-cloud.browserstack.com/wd/hub";
                   driver = new IOSDriver<>(new URL(URL), caps);

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

    public static IOSDriver<IOSElement> getIosDriver() {
        return threadLocalIosDriver.get();

    }

    public static void setUpIosDriver() {
        try{
             if(getIosDriver()==null) new AppiumDriver();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }

    }


    public static void tearDown() {

            if(getAndroidDriver()!=null) {
                try {
                    getAndroidDriver().quit();
                    threadLocalAndroidDriver.set(null);
                }catch(Exception e) {
                    threadLocalAndroidDriver.set(null);
                    e.printStackTrace();
                }
            }

            if(getIosDriver()!=null) {
                try {
                    getIosDriver().quit();
                    threadLocalIosDriver.set(null);
                }catch(Exception e) {
                    threadLocalIosDriver.set(null);
                    e.printStackTrace();
                }
            }


    }


}
