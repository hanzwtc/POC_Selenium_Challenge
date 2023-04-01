package Utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	private Properties properties;
	private final String propertyFilePath = System.getProperty("user.dir")+"//configs//Configuration.properties";
	public ConfigFileReader () {
		BufferedReader reader;
		try {
			reader = new BufferedReader (new FileReader (propertyFilePath) );
			properties = new Properties ();
			try {
				properties.load (reader);
				reader.close ();
			}catch (IOException e) {
				e.printStackTrace();
			}}
		catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Configuration.properties not found at "+ propertyFilePath);
		}
	}

		

	public String getReportPath(){
		String getReportPath = properties.getProperty("reportFilePath");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	
	public String getXlSheetName (String Xltype){
		String getXlSheetName =	properties.getProperty ("DataSheetBlue") ;
		if (Xltype.equalsIgnoreCase("gold") ){
			getXlSheetName = properties.getProperty("DataSheetGold");
		}
		if(getXlSheetName!= null) return getXlSheetName;
		else throw new RuntimeException ("Excel Sheet Name is not specified in the Configuration. properties file.");
	}


	public String getSeleniumServerPort() {
		String portFromPropertyFile = properties.getProperty ( "seleniumServerPort");
		String portFromMvn = System.getProperty("seleniumServerPort" );
		if (portFromMvn==null|portFromMvn.equalsIgnoreCase("default")||portFromMvn.isEmpty ()) {
		return portFromPropertyFile;
		}else {
		return portFromMvn;
	}}

	public String getBrowserName() {
		String browserNameFromPropertyFile = properties.getProperty("browserName");
		String browserNameFromMvn = System.getProperty ("browserName "); //from myn command line
		if (browserNameFromMvn== null || browserNameFromMvn.equalsIgnoreCase("default")||browserNameFromMvn.isEmpty()) {
			return browserNameFromPropertyFile;
		}else {
			return browserNameFromMvn;
		}
	}


	public String getAutomationHost() {
		String browserNameFromPropertyFile = properties.getProperty("AutomationHost");
		String browserNameFromMvn = System.getProperty ("AutomationHost "); //from myn command line
		if (browserNameFromMvn== null || browserNameFromMvn.equalsIgnoreCase("default")||browserNameFromMvn.isEmpty()) {
			return browserNameFromPropertyFile;
		}else {
			return browserNameFromMvn;
		}
	}

	
	public String getHeadlesMode() {
		String headlessFromPropertyFile = properties.getProperty("headless");
		String headlessFromMvn= System.getProperty("headless"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
			}else {
				return headlessFromMvn;
			}
	}

	public String getMobileEmulation() {
		String headlessFromPropertyFile = properties.getProperty("MobileEmulation");
		String headlessFromMvn= System.getProperty("MobileEmulation"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
		}else {
			return headlessFromMvn;
		}
	}

	public String getWidth() {
		String headlessFromPropertyFile = properties.getProperty("Width");
		String headlessFromMvn= System.getProperty("Width"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
		}else {
			return headlessFromMvn;
		}
	}

	public String getHeight() {
		String headlessFromPropertyFile = properties.getProperty("Height");
		String headlessFromMvn= System.getProperty("Height"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
		}else {
			return headlessFromMvn;
		}
	}

	public String getPixelRatio() {
		String headlessFromPropertyFile = properties.getProperty("PixelRatio");
		String headlessFromMvn= System.getProperty("PixelRatio"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
		}else {
			return headlessFromMvn;
		}
	}

	public String getUserAgent() {
		String headlessFromPropertyFile = properties.getProperty("UserAgent");
		String headlessFromMvn= System.getProperty("UserAgent"); //from mvn command line
		if(headlessFromMvn==null ||headlessFromMvn.equalsIgnoreCase("default" )||headlessFromMvn.isEmpty ()) {
			return headlessFromPropertyFile;
		}else {
			return headlessFromMvn;
		}
	}



	public String getContentFlag() {
		String contentFlagFromPropertyFile = properties.getProperty("contentFlag");
		String contentFlagFromMvn= System.getProperty("contentFlag"); //from mvn command line
		if(contentFlagFromMvn==null ||contentFlagFromMvn.equalsIgnoreCase("default" )||contentFlagFromMvn.isEmpty ()) {
			return contentFlagFromPropertyFile;
			}else {
				return contentFlagFromMvn;
			}
	}
	
	public String getMongoDBServiceUpdateFlag() {
		String mongoDBServiceUpdateFlagFromPropertyFile = properties.getProperty("mongoDBServiceUpdateFlag");
		String mongoDBServiceUpdateFlagFromMvn= System.getProperty("mongoDBServiceUpdateFlag"); //from mvn command line
		if(mongoDBServiceUpdateFlagFromMvn==null ||mongoDBServiceUpdateFlagFromMvn.equalsIgnoreCase("default" )||mongoDBServiceUpdateFlagFromMvn.isEmpty ()) {
			return mongoDBServiceUpdateFlagFromPropertyFile;
			}else {
				return mongoDBServiceUpdateFlagFromMvn;
			}
	}
	
//	public String getEnvironment() {
//		String environmentFromPropertyFile = properties.getProperty("environment");
//		String environmentFromMvn= System.getProperty("environment");
//		if(environmentFromPropertyFile==null ||environmentFromPropertyFile.equalsIgnoreCase("default" )||environmentFromPropertyFile.isEmpty ()) {
//			System.setProperty("environment",environmentFromPropertyFile);
//			return environmentFromPropertyFile;
//			}else {
//				System.setProperty("environment",environmentFromMvn);
//				return environmentFromMvn;
//			}
//		
//		
//	}


	public String getEncryptionKey() {
		String encryptionKeyFromPropertyFile = properties.getProperty("encryptionKey");
		String defaultEncryptionKey = "defaultKey";
		if(encryptionKeyFromPropertyFile== null || encryptionKeyFromPropertyFile.equalsIgnoreCase ("default")||encryptionKeyFromPropertyFile.isEmpty()) {
			return defaultEncryptionKey;
		}else {
			return encryptionKeyFromPropertyFile;
		}
	}


	


}
