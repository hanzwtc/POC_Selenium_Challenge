package Utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;

public class MongoDriver {

    public static final ThreadLocal<MongoClient> threadConnection = new ThreadLocal<MongoClient>();
    public static final ThreadLocal<MongoDatabase> threadConnectionDataBase = new ThreadLocal<MongoDatabase>();
    public static ConfigFileReaderBD configFileReaderBD= new ConfigFileReaderBD();

    public static void Start_Connection(){
        try {

            //test//mongodb://payments-admin-test:t3stp4y.7qYJ97hYUnui8.4$@34.86.208.231:27017/?authSource=payments-db-test&readPreference=primary&appname=MongoDB%20Compass&ssl=false
           //prod//mongodb://payments-admin:p4y.s71jwoUw82hhs2$.@34.86.208.231:27017/?authSource=payments-db&readPreference=primary&appname=MongoDB%20Compass&ssl=false

            String MongoUser= configFileReaderBD.getMongoUser();
            String MongoPass= configFileReaderBD.getMongoPass();
            String MongoIp= configFileReaderBD.getMongoIp();
            String MongoBd = configFileReaderBD.getMongoBD();

            String url = "mongodb://"+MongoUser+":"+MongoPass+"@"+MongoIp+"/?authSource="+MongoBd+"&readPreference=primary&appname=MongoDB%20Compass&ssl=false";

            MongoClient mongoClient = MongoClients.create(url);

            threadConnection.set(mongoClient);

        }catch (MongoException e){
            e.printStackTrace();
        }

    }

    public static MongoClient getConnection() {
        return threadConnection.get();
    }

    public static MongoDatabase getDataBase() {
        return threadConnectionDataBase.get();
    }


    public static void Get_DataBase(){
        try {

            String MongoBd = configFileReaderBD.getMongoBD();
            threadConnectionDataBase.set(getConnection().getDatabase(MongoBd));
            System.out.println("MongoDB database connection ...");

        }catch (MongoException e){
            e.printStackTrace();
        }

    }

    public static void Close_Connection(){

        try{

            if (getConnection()!=null){
                getConnection().close();
                threadConnectionDataBase.set(null);
            }

        }catch (MongoException e){
            e.printStackTrace();
        }

    }


    public static MongoCollection<Document> Get_Collection(String NameCollection){
        try {

            return  getDataBase().getCollection(NameCollection);

        }catch (MongoException e){
            e.printStackTrace();
        }

        return null;
    }


    public static Document GetDocument(MongoCollection<Document> Collection,String Field,String Value){
        try {

            return Collection.find(new Document(Field,Value)).first();

        }catch (MongoException e){
            e.printStackTrace();
        }

        return null;
    }

    public static void UpdateField(MongoCollection<Document> Collection, String FieldSearch, String ValueSearch, String FieldUpdate, boolean ValueUpdate){

        try {

            BasicDBObject query = new BasicDBObject();
            query.put(FieldSearch,ValueSearch);

            BasicDBObject newquery = new BasicDBObject();
            newquery.put(FieldUpdate,ValueUpdate);

            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newquery);

            FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
            options.upsert(true);

            Collection.updateOne(query,updateObject);

        }catch (MongoException e){

            e.printStackTrace();

        }

    }

}

