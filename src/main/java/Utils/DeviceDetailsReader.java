package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DeviceDetailsReader {

String dir = System.getProperty("user.dir");

    private final Properties properties;
    private final String propertyFilePath = dir+"//configs//Configuration.properties";
    public DeviceDetailsReader () {
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
            throw new RuntimeException("DeviceDetails.properties not found at "+ propertyFilePath);
        }
    }

    public String getBrowserName() {
        String getBrowserName = properties.getProperty("browserName" );
        if (getBrowserName != null) return getBrowserName;
        else throw new RuntimeException ("browserName is not specified in the DeviceDetails.properties file.");
    }

    public String getDevice() {
        String getDevice = properties.getProperty("device" );
        if (getDevice != null) return getDevice;
        else throw new RuntimeException ("getDevice is not specified in the DeviceDetails.properties file.");
    }



    public String getOs_version() {
        String getOs_version = properties.getProperty("os_version" );
        if (getOs_version != null) return getOs_version;
        else throw new RuntimeException ("getOs_version is not specified in the DeviceDetails.properties file.");
    }


    public String getApp() {
        String getName = properties.getProperty("app" );
        if (getName != null) return getName;
        else throw new RuntimeException ("getName is not specified in the DeviceDetails.properties file.");
    }

    public String getUSERNAME() {
        String getUSERNAME = properties.getProperty("USERNAME" );
        if (getUSERNAME != null) return getUSERNAME;
        else throw new RuntimeException ("getUSERNAME is not specified in the DeviceDetails.properties file.");
    }

    public String getAUTOMATE_KEY() {
        String getAUTOMATE_KEY = properties.getProperty("AUTOMATE_KEY" );
        if (getAUTOMATE_KEY != null) return getAUTOMATE_KEY;
        else throw new RuntimeException ("getAUTOMATE_KEY is not specified in the DeviceDetails.properties file.");
    }



    public String getUdid() {
        String getCloudAddress = properties.getProperty("Udid" );
        if (getCloudAddress != null) return getCloudAddress;
        else throw new RuntimeException ("Udid is not specified in the DeviceDetails.properties file.");
    }

    public String getAppPackage() {
        String getCloudAddress = properties.getProperty("AppPackage" );
        if (getCloudAddress != null) return getCloudAddress;
        else throw new RuntimeException ("Udid is not specified in the DeviceDetails.properties file.");
    }


    public String getAppActivity() {
        String getCloudAddress = properties.getProperty("AppActivity" );
        if (getCloudAddress != null) return getCloudAddress;
        else throw new RuntimeException ("Udid is not specified in the DeviceDetails.properties file.");
    }


}
