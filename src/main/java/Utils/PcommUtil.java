package Utils;

import com.ibm.eNetwork.ECL.ECLErr;
import com.ibm.eNetwork.ECL.ECLPS;
import com.ibm.eNetwork.ECL.ECLScreenDesc;
import com.ibm.eNetwork.ECL.ECLSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PcommUtil {
	
	public static ThreadLocal<ECLSession> threadLocalSession= new ThreadLocal<ECLSession>();
	public static ThreadLocal<ECLPS> threadLocalPs= new ThreadLocal<ECLPS>();
	static Logger log = LogManager.getLogger(PcommUtil.class);
	public static void CaptureScreen(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public static void launchPcomm(String pcommPath) throws ECLErr, InterruptedException {
		pcommPath = pcommPath.replaceAll("\\\\", "/");
		//pcommPath = System.getProperty("user.dir").replaceAll("\\\\", "/")+"/"+pcommPath;
		File f = new File(pcommPath);
		if(!f.exists()) {
			//System.out.println("Pcomm session file not found in path" +pcommPath );
			Assert.fail("Pcomm session file not found in path" +pcommPath );
		}
		//System.out.println(pcommPath);
		System.loadLibrary("pcseclj");
		Properties pcommProperty = new Properties();
		pcommProperty.put("SESSION_HOST", pcommPath);
		pcommProperty.put("SESSION_WIN_STATE", "NORMAL");
		pcommProperty.put("SESSION_VT_KEYPAD ", "SESSION_VT_KEYPAD_APPL");
		pcommProperty.put("SESSION_VT_LOCAL_ECHO", "SESSION_VT_LOCAL_ECHO_ON");
		
		ECLSession session = new ECLSession(pcommProperty);
		System.out.println("INICIANDO HOST");
		Thread.sleep(1500);
		session.StartCommunication();


		do {
			System.out.println("VALOR HOST = "+session.IsCommStarted());
		}
		while (!session.IsCommStarted());

		Thread.sleep(3000);
		session.connect();
		threadLocalSession.set(session);
		threadLocalPs.set(session.GetPS());

	}
	

    public void loginToPcomm(String environment, String userID, String password) throws ECLErr, InterruptedException {
  	  
  	  ECLPS ps=getPS();
  	  ECLScreenDesc eclScreen = new ECLScreenDesc();
  	  eclScreen.SetActive(true);
 // 	  ps.SendKeys(environment+"[enter]");
  	  ps.WaitForCursor(19, 041, 5, true);
  	  ps.SendKeys(userID+"[tab]");
  	  ps.WaitForCursor(20, 041, 5, true);
  	  ps.SendKeys(password+"[enter]");
  	  ps.WaitForCursor(23, 18, 5, true);
  	  Thread.sleep(1000);
  	//  ps.SendKeys("[pf4]");
  	  
    }
    
    public static void logout() {
  	  getSession().disconnect();
  	  getSession().dispose();
  	  threadLocalSession.set(null);
  	  threadLocalPs.set(null);
    }

    public static String getText(int startRow, int startColumn,int endRow, int endColumn) throws ECLErr {
    	int columnLength = endColumn-startColumn+1;
    	char[] text= new char[columnLength+1];
		String outputString = "";
		for(int i = startRow; i<=endRow; i++) {
			getPS().GetString(text,columnLength,i,startColumn,endColumn-startColumn+1);
			outputString=outputString+String.valueOf(text);
			outputString=outputString.substring(0, outputString.length()-1);
		}
    	return outputString;
    }

    public static void setText(String value, int row, int col) throws ECLErr {
    	getPS().SendKeys(value, row, col);
    }
    
    public static void setText(String value) throws ECLErr {
    	getPS().SendKeys(value);
    }
    
	public static ECLSession getSession() {
		// TODO Auto-generated method stub
		return threadLocalSession.get();
	}

	public static ECLPS getPS() {
		// TODO Auto-generated method stub  
		return threadLocalPs.get();
	}

	public static void capturePCOMMScreenshot(String imageName) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmssSSS");
		Date curDate = new Date();
		String strDate = sdf.format(curDate) ;
		String fullFileName= imageName+strDate;
		String location = System.getProperty("user.dir")+"//src//test-output//Results//Screenshots"+"//";
		try {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = new Robot().createScreenCapture(screenRect);
			ImageIO.write(image, "jpg", new FileOutputStream(location+fullFileName+".jpg"));
		} catch (AWTException e) {System.out.println(e.getMessage());}
		catch (IOException e) {System.out.println(e.getMessage());}
	}

	public static void capturePCOMMScreenshot() {
		capturePCOMMScreenshot("");
	}

}
