package Utils;

import com.google.common.collect.ImmutableList;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import stepDefinitions.BeforeActions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AppiumHelper {

    public static List<String[]> scenarioList = new ArrayList<>();

    static Logger log = LogManager.getLogger(AppiumHelper.class);

    public static boolean isElementPresent(MobileElement element) {

        try {
            Thread.sleep(1000);
            //log.info("Selenium Helper checking for WebElement>>>>"+element.getText());
            return element.isDisplayed();
        }catch(NoSuchElementException | InterruptedException e) {
            return false;
        }
    }

    public static boolean isElementPresentIos(IOSElement element) {

        try {
            Thread.sleep(1000);
            //log.info("Selenium Helper checking for WebElement>>>>"+element.getText());
            return element.isDisplayed();
        }catch(NoSuchElementException | InterruptedException e) {
            return false;
        }
    }

    public static void WifiOn() {

        if (AppiumDriver.getAndroidDriver()!=null){

            ConnectionState state = AppiumDriver.getAndroidDriver().setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
            Assert.assertTrue(state.isWiFiEnabled(), "Wifi is not switched on");

        }

    }
    public static void WifiOff() {
        if (AppiumDriver.getAndroidDriver()!=null){

            ConnectionState state = AppiumDriver.getAndroidDriver().setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
            Assert.assertFalse(state.isWiFiEnabled(), "Wifi is not switched off");
        }

    }


    public static String takeScreenshotAndroid(String imageName)  {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File srcFile = ((TakesScreenshot) AppiumDriver.getAndroidDriver()).getScreenshotAs(OutputType.FILE);

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

    public static String takeScreenshotIos(String imageName) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File srcFile = ((TakesScreenshot) AppiumDriver.getIosDriver()).getScreenshotAs(OutputType.FILE);

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

    public static void adbExec(String deviceId,String command) throws IOException {
        String commandString;
        if(!deviceId.equals("")) {
            commandString = String.format("%s", "adb -s " + deviceId + " shell " + command);
        }else
            commandString = String.format("%s", "adb shell " + command);
        System.out.print("Command is "+commandString+"\n");

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(commandString);
        String result = new BufferedReader(
                new InputStreamReader(pr.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));

        System.out.println(result);

    }


    public static void CleanText(MobileElement element){

        Rectangle rectangle = element.getRect();
        int YAxis = element.getCenter().getY();
        int XAxis = rectangle.getWidth();
        TouchAction action = new TouchAction(AppiumDriver.getAndroidDriver());
        action.tap(TapOptions.tapOptions().withPosition(PointOption.point(XAxis,
                YAxis))).perform();
        element.clear();

    }

    public static void SwipeUp(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        new TouchAction(driver).longPress(PointOption.point(width / 2, 100))
                .moveTo(PointOption.point(width / 2, height - 100)).release()
                .perform();
    }

    // decline
    public static void SwipeDown(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        new TouchAction(driver)
                .longPress(PointOption.point(width / 2, height - 100))
                .moveTo(PointOption.point(width / 2, 100)).release().perform();
    }

    // Swipe left
    public  static void SwipeLeft(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        new TouchAction(driver)
                .longPress(PointOption.point(width - 100, height / 2))
                .moveTo(PointOption.point(100, height / 2)).release().perform();
    }

    // Swipe right
    public static void SwipeRight(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        new TouchAction(driver).longPress(PointOption.point(100, height / 2))
                .moveTo(PointOption.point(width - 100, height / 2)).release()
                .perform();
    }


    public static void Tap_To_Element_Android(MobileElement element) {
        AndroidTouchAction touch = new AndroidTouchAction (AppiumDriver.getAndroidDriver());
        touch.tap (TapOptions.tapOptions ()
                .withElement (ElementOption.element (element)))
                .perform ();


    }


    public static void Tap_To_Element_Coordinates_Android(MobileElement element) {


        TouchAction action= new TouchAction(AppiumDriver.getAndroidDriver());
        int X = element.getCenter().x ;
        int Y = element.getCenter().y ;
        action.tap(PointOption.point(X, Y)).perform();

    }


    public static void tapElement(MobileElement el) {
        Rectangle elRect = el.getRect();
        Point point = new Point(
                elRect.x + ((elRect.getWidth()*95)/100),
                elRect.y + elRect.getHeight() - ((elRect.getWidth()*5)/100)
        );

        System.out.println(point);
        tapAtPoint(point);
    }

    public static void tapAtPoint(Point point) {

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tap = new Sequence(input, 0);
        tap.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.x, point.y));
        tap.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(input, Duration.ofMillis(200)));
        tap.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AppiumDriver.getAndroidDriver().perform(ImmutableList.of(tap));

    }



    public static Point getCenter(Rectangle rect) {
        return new Point(rect.x + rect.getWidth() / 2, rect.y + rect.getHeight() / 2);
    }



    public static void ZoomIn(Point locus,int distance){
        zoom(locus,200,200+distance);

    }

    public static void ZoomOut(Point locus,int distance){
        zoom(locus,200+distance,200);

    }


    public static void zoom(Point locus, int startRadius, int endRadius) {


        double angle = Math.PI / 2 - (2 * Math.PI / 360 * 45);

        // find coordinates for starting point of action (converting from polar coordinates to cartesian)
        int fingerStartx = (int)Math.floor(locus.x + startRadius * Math.cos(angle));
        int fingerStarty = (int)Math.floor(locus.y - startRadius * Math.sin(angle));


        // find coordinates for ending point of action (converting from polar coordinates to cartesian)
        int fingerEndx = (int)Math.floor(locus.x + endRadius * Math.cos(angle));
        int fingerEndy = (int)Math.floor(locus.y - endRadius * Math.sin(angle));


        angle = angle + Math.PI;
        // find coordinates for starting point of action (converting from polar coordinates to cartesian)
        int fingerStartx2 = (int)Math.floor(locus.x + startRadius * Math.cos(angle));
        int fingerStarty2 = (int)Math.floor(locus.y - startRadius * Math.sin(angle));


        // find coordinates for ending point of action (converting from polar coordinates to cartesian)
        int fingerEndx2 = (int)Math.floor(locus.x + endRadius * Math.cos(angle));
        int fingerEndy2 = (int)Math.floor(locus.y - endRadius * Math.sin(angle));

        System.out.println(fingerStartx + ","+fingerStarty+"   "+fingerEndx + ","+fingerEndy+"   "+fingerStartx2 + ","+fingerStarty2+"   "+fingerEndx2 + ","+fingerEndy2+"   ");

        MultiTouchAction multiTouch = new MultiTouchAction(AppiumDriver.getAndroidDriver());

        TouchAction action0 = new TouchAction(AppiumDriver.getAndroidDriver()).press(PointOption.point(fingerStartx,fingerStarty)).moveTo(PointOption.point(fingerEndx,fingerEndy)).release();

        TouchAction action1 = new TouchAction(AppiumDriver.getAndroidDriver()).press(PointOption.point(fingerStartx2, fingerStarty2)).moveTo(PointOption.point(fingerEndx2, fingerEndy2)).release();

        multiTouch.add(action0).add(action1).perform();


    }




}
