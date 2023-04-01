package MyRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roxstudio.utils.CUrl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.ParseException;

public class SampleMain {

	public static void main(String[] args) throws JsonProcessingException, ParseException, InterruptedException, MalformedURLException, UnknownHostException {


		//db.getCollection('customers').find({"subscriptions.clientSourceId":"690095876"})

		//74826855 - DNI PRUEBA
		//dmmp77@hotmail.com - CORREO PARA GENERAR POLIZAS

 				//user: 45656190
				//pass: 1234567U@

		//String NroPoliza = "690095896";


/*
		MongoClient mongoClient = MongoClients.create("mongodb://payments-admin-test:t3stp4y.7qYJ97hYUnui8.4$@34.86.208.231:27017/?authSource=payments-db-test&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
		MongoDatabase database = mongoClient.getDatabase("payments-db-test");
		System.out.println("MongoDB database connection ");


		MongoCollection<Document> collection = database.getCollection("customers");

		Document result = collection.find(new Document("subscriptions.clientSourceId",NroPoliza)).first();
		//System.out.println(result.toJson());

		JsonWriterSettings settings = JsonWriterSettings.builder()
				.int64Converter((value, writer) -> writer.writeNumber(value.toString()))
				//.dateTimeConverter((value, writer) -> writer.writeString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(value))))
				.build();

		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String json = ow.writeValueAsString(result);

		JSONArray JsonArray = 	Curl_Helper.GetJsonArray((Objects.requireNonNull(Curl_Helper.GetJson(result.toJson(settings)))),"subscriptions");
		//System.out.println(JsonArray);


		for (int i = 0 ; i < JsonArray.length(); i++) {

			JSONObject obj = JsonArray.getJSONObject(i);

			String NroPolizaJson = obj.getString("clientSourceId");
			boolean active = obj.getBoolean("active");

			if (NroPolizaJson.equalsIgnoreCase(NroPoliza) && active){
				System.out.println(obj);
				String NuevoNroPoliza = "690095897";
				String NuevoIdPoliza = "101559";

				String _id = "R"+obj.getString("_id");
				String creationToken = obj.getJSONObject("card").getString("creationToken")+"R";


				obj.put("_id",_id);
				obj.put("clientSourceId",NuevoNroPoliza);
				obj.getJSONObject("card").put("creationToken",creationToken);
				obj.getJSONObject("metadata").put("idPoliza",NuevoIdPoliza);
				obj.getJSONObject("metadata").put("numeroPoliza",NuevoNroPoliza);
				//obj.put("amount", "710.00");
				obj.put("userCreated","RETENCION_VEHICULAR");


				//obj.put("dateStart",fechaActual);
				//obj.put("dateCreated",fechaActual);

				//fecha.add(Calendar.YEAR, 1);


				//String ProxAnio = (fecha.get(Calendar.YEAR)+1)+"-"+fecha.get(Calendar.MONTH)+"-"+fecha.get(Calendar.DAY_OF_MONTH) +"T05:00:00.000Z";
				//obj.put("dateExpiration",ProxAnio);


				BasicDBObject query2 = new BasicDBObject();
				query2.put("subscriptions.clientSourceId", NroPoliza);
				BasicDBObject dbObject = BasicDBObject.parse(String.valueOf(obj));
				BasicDBObject push_data = new BasicDBObject("$push", new BasicDBObject("subscriptions", dbObject));

				collection.findOneAndUpdate(query2, push_data);


				BasicDBObject query = new BasicDBObject();
				query.put("subscriptions.clientSourceId",NuevoNroPoliza);

				BasicDBObject newquery = new BasicDBObject();
				BigDecimal decimal = new BigDecimal("750.00");

				newquery.put("subscriptions.$.amount",decimal);

				BasicDBObject updateObject = new BasicDBObject();
				updateObject.put("$set", newquery);

				FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
				options.upsert(true);

				collection.updateOne(query,updateObject);





				break;

			}

		}


		//Calendar fecha = Calendar.getInstance();;
		//int mes = fecha.get(Calendar.MONTH);

		//String fechaActual = fecha.get(Calendar.YEAR)+"-"+fecha.get(Calendar.MONTH) +"-"+fecha.get(Calendar.DAY_OF_MONTH) +"T05:00:00.000Z";
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		//simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String[] part = ReportHTML.GetFechaAnioMesDia().split("-");

		String FechaActual = Integer.parseInt(part[0])+1+"-"+part[1]+"-"+part[2]+"T05:00:00.000Z";


		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));


		BasicDBObject query = new BasicDBObject();
		query.put("subscriptions.clientSourceId","690095897");

		BasicDBObject newquery = new BasicDBObject();

		newquery.put("subscriptions.$.dateCreated",simpleDateFormat.parse(FechaActual));

		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newquery);

		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(true);

		collection.updateOne(query,updateObject);
/*
		/*
		///ACTUALIZAR CAMPO ACTIVE A FALSE

		BasicDBObject query = new BasicDBObject();
		query.put("subscriptions.clientSourceId","690095895");

		BasicDBObject newquery = new BasicDBObject();
		newquery.put("subscriptions.$.active",false);

		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newquery);

		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(true);

		collection.updateOne(query,updateObject);

		/// PRUEBA PARA AGREGAR EL NUEVO REGISTRO DE RETENCION


		BasicDBObject query2 = new BasicDBObject();
		query2.put("subscriptions.clientSourceId", "690095895");

		String json = "{\"_id\": \"R61815e08b1582767d964795a\", \"clientSourceId\": \"690095895\", \"card\": {\"_id\": \"61815e05b1582767d9647958\", \"creationToken\": \"tkn_test_sUppFTyH51FQyQHe\", \"provider\": {\"clientId\": \"606f5cc232946a7a080172e5\", \"providerId\": \"CULQI\", \"sourceId\": \"crd_test_L3cnVfUfzsaOhHg0\"}, \"number\": \"511111******1118\", \"brand\": \"MasterCard\", \"type\": \"debito\", \"category\": \"Clásica\"}, \"metadata\": {\"idPoliza\": \"101445\", \"numeroPoliza\": \"690095895\"}, \"years\": 1, \"frecuency\": \"T\", \"amount\": {\"numberDecimal\": \"87.00\"}, \"dateStart\": {\"date\": 1635868168282}, \"dateExpiration\": {\"date\": 1667347200000}, \"currency\": \"USD\", \"active\": false, \"userCreated\": \"admin\", \"dateCreated\": {\"date\": 1635868168282}}";
		 BasicDBObject dbObject = com.mongodb.BasicDBObject.parse(json);
		BasicDBObject push_data = new BasicDBObject("$push", new BasicDBObject("subscriptions", dbObject));



		System.out.println(collection.findOneAndUpdate(query2, push_data).toJson());

*/
		//db.getCollection('customers').find({"subscriptions.clientSourceId":"690095876"})
		//db.getCollection('payments').find({'source.sourceId' : '16432'})

/*
		String IdRegistro = "61aa60504a99ebc1f9eba9c7";
		String NroPoliza = "147558271";

		MongoClient mongoClient = MongoClients.create("mongodb://payments-admin-test:t3stp4y.7qYJ97hYUnui8.4$@34.86.208.231:27017/?authSource=payments-db-test&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
		MongoDatabase database = mongoClient.getDatabase("payments-db-test");
		System.out.println("MongoDB database connection ");


		MongoCollection<Document> collection = database.getCollection("payments");

		FindIterable<Document> documents = collection.find(new Document("source.sourceId",IdRegistro));


		System.out.println("SE PROCEDE ACTUALIZAR LOS CRONOGRAMAS DE LA TABLA PAYMENTS CON EL ID - "+ IdRegistro);

		for (Document document : documents) {

			//doc.get("purchaseNumber")== NroPoliza  &&

			if (Objects.equals(document.getString("purchaseNumber"), NroPoliza) && (Objects.equals(document.getString("status"), "PENDING") || Objects.equals(document.getString("status"), "NOT_PAID"))){


				String idPayments = document.getString("_id");

				BasicDBObject query = new BasicDBObject();
				query.put("_id",idPayments);

				BasicDBObject newquery = new BasicDBObject();
				newquery.put("status","EXONERATED");

				BasicDBObject updateObject = new BasicDBObject();
				updateObject.put("$set", newquery);

				FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
				options.upsert(true);

				collection.updateOne(query,updateObject);

				//Thread.sleep(1000);

				Document result = collection.find(new Document("_id",idPayments)).first();

				assert result != null;
				System.out.println(result.toJson());
				System.out.println("\n" + "\n" );

			}




		}

*/






		//String codGenerate = Curl_Helper.SendCurl("curl http://localhost:8085/start -X \"POST\"");
		//System.out.println(codGenerate);

		//String server =  Curl_Helper.SendCurl("curl http://localhost:8085/notify/userUnico/"+codGenerate);
		//System.out.println(server);


		//Curl_Helper.SendCurl("curl http://localhost:8085/message/receive/"+codGenerate+" --data-raw \"hola1\" ");

		CUrl curl = new CUrl("http://httpbin.org/post")
				.data("hello=world&foo=bar")
				.data("foo=overwrite");
		curl.exec();
		System.out.println(curl.getHttpCode());




		System.out.println("where /r c:\\Users\\" +System.getProperty("user.name")+"\\ chromedriver.exe");
	}

	public static String GenerarPalabra(){

		StringBuilder palabra = new StringBuilder();

		int caracteres = (int)(Math.random()*15)+2;

		for (int i=0; i<caracteres; i++){
			int codigoAscii = (int)Math.floor(Math.random()*(122 -
					97)+97);
			palabra.append((char) codigoAscii);
		}
		return palabra.toString();
	}


}


















