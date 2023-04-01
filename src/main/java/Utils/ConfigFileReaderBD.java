package Utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReaderBD {
	private Properties properties;
	private final String propertyFilePath = System.getProperty("user.dir")+"//configs//ConfigurationBD.properties";
	public ConfigFileReaderBD() {
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

		

	public String getMysqlIp(){
		String getReportPath = properties.getProperty("MysqlIp");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMysqlBD(){
		String getReportPath = properties.getProperty("MysqlBD");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMysqlUser(){
		String getReportPath = properties.getProperty("MysqlUser");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMysqlPass(){
		String getReportPath = properties.getProperty("MysqlPass");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMongoIp(){
		String getReportPath = properties.getProperty("MongoIp");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMongoBD(){
		String getReportPath = properties.getProperty("MongoBD");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMongoUser(){
		String getReportPath = properties.getProperty("MongoUser");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getMongoPass(){
		String getReportPath = properties.getProperty("MongoPass");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getGoogleCloudUser(){
		String getReportPath = properties.getProperty("GoogleCloudUser");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}

	public String getGoogleCloudPass(){
		String getReportPath = properties.getProperty("GoogleCloudPass");
		if(getReportPath != null) return getReportPath;
		else throw new RuntimeException ("Report Path is not specified in the Configuration.properties file.");
	}
}
