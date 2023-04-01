package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {

    public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
    public static ConfigFileReaderBD configFileReaderBD= new ConfigFileReaderBD();

    public static void Start_Connection(){

        try{

            //"jdbc:mysql://35.230.75.76:3306/salva_qa"
            String Url = "jdbc:mysql://"+configFileReaderBD.getMysqlIp()+"/"+configFileReaderBD.getMysqlBD();

            Connection connection = DriverManager.getConnection (Url,configFileReaderBD.getMysqlUser(),configFileReaderBD.getMysqlPass());
            threadConnection.set(connection);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void Close_Connection(){

        try{

            if (getConnection()!=null){
                getConnection().close();
                threadConnection.set(null);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return threadConnection.get();
    }



}
