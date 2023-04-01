package baseRunner;

import com.ibm.eNetwork.ECL.*;

import java.util.Properties;
public class PCOMMRunnerSample {


      public static void main(String[] args) throws ECLErr {

            try{

            System.loadLibrary("pcseclj");

            Properties prop = new Properties();
           // prop.put("SESSION_VT_LOCAL_ECHO ", "true");

            prop.put("SESSION_HOST", "E:\\HOST\\host.ws");  // works OK
            prop.put("SESSION_WIN_STATE", "MAX");
            prop.put("SESSION_VT_KEYPAD ", "SESSION_VT_KEYPAD_APPL");
           prop.put("SESSION_VT_LOCAL_ECHO", "SESSION_VT_LOCAL_ECHO_ON");

            ECLSession session = new ECLSession(prop);

           session.StartCommunication(); //works OK
           Thread.sleep(2500);
           session.connect(); //works OK
           ECLFieldList fieldList = session.GetPS().GetFieldList();

            session.GetPS   ().SetCursorPos(14, 67); //works OK
            session.GetPS().SetString("S88647"); // does not work
            session.GetPS().SetCursorPos(15, 67); //works OK
            session.GetPS().SetString("Carlos12"); // does not work
            session.GetPS().SendKeys("[enter]");


                if (session.GetPS().WaitForStringInRect("Menu  Principal",3,17,3,31,3000,true,true)){

                    session.GetPS().SendKeys("A02CICSP",23,18);
                    session.GetPS().SendKeys("[enter]");



                }


            }
            catch(Exception e)

            {

                System.out.println(e);

            }

      }
      
      public void loginToPcomm(String environment, String userID, String password) throws ECLErr, InterruptedException {
    	  
    	  ECLPS ps=getPS();
    	  ECLScreenDesc eclScreen = new ECLScreenDesc();
    	  eclScreen.SetActive(true);
    	  ps.SendKeys(environment+"[enter]");
    	  ps.WaitForCursor(1, 1, 5, true);
    	  ps.SendKeys(userID+"[tab]");
    	  ps.WaitForCursor(2, 2, 5, true);
    	  ps.SendKeys(password+"[enter]");
    	  ps.WaitForCursor(3, 3, 5, true);
    	  Thread.sleep(1000);
    	//  ps.SendKeys("[pf4]");
    	  
      }
      
      public static void logout() {
    	  getSession().disconnect();
    	  getSession().dispose();
      }

	private static ECLSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	private ECLPS getPS() {
		// TODO Auto-generated method stub
		return null;
	}

}
