package Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.NoSuchElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.Objects;
import java.util.stream.Collectors;

public class Curl_Helper {



    public static String SendCurl(String commandString) {
        String result ="";
        try{
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(stripAccents(commandString).trim());
            result = new BufferedReader(
                    new InputStreamReader(pr.getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));
            System.out.println(commandString.trim().replace("\\\"","\"").replace("\"{","'{").replace("}\"","}'"));
            return result;

        }catch (IOException e){
            return result = "";
        }

    }




    public static JSONObject GetJson( @NotNull String JsonStr){

        try{
            return new JSONObject(JsonStr);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }


    public static int SearchInt(@NotNull JSONObject Json, String Key){
        try {

            return Json.getInt(Key);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return -1;

    }

    public static String SearchString(@NotNull JSONObject Json, String Key){

        try {
            return Json.getString(Key);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return "";
    }

    public static JSONArray GetJsonArray(@NotNull JSONObject Json, String Key){

        try{
            return Json.getJSONArray(Key);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public static int SearchIntJsonArray(@NotNull JSONArray JsonArray, String Key, int value){

        try{
            for (int i = 0 ; i < JsonArray.length(); i++) {
                JSONObject obj = JsonArray.getJSONObject(i);
                int A = obj.getInt(Key);

                if (A==value){
                    return A;
                }

            }

        } catch (JSONException e){
            e.printStackTrace();
        }


        return 0;
    }



    public static @NotNull String SearchStringJsonArray(@NotNull JSONArray JsonArray, String Key, String value){
        try{

            for (int i = 0 ; i < JsonArray.length(); i++) {
                JSONObject obj = JsonArray.getJSONObject(i);
                String B = obj.getString(Key);

                if (B.equals(value)){
                    return B;
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }


        return "";
    }



    public static int SearchStringJsonArrayReturnInt(@NotNull JSONArray JsonArray, String Key, String value, String KeytoGet){

        try{

            for (int j = 0 ; j < 10000; j++) {

                int leng = JsonArray.length();
                int caracteres = (int)(Math.random()*leng)+1;

                System.out.println(caracteres);

            }



            for (int i = 0 ; i < JsonArray.length(); i++) {

                JSONObject obj = JsonArray.getJSONObject(i);
                String B = obj.getString(Key).toUpperCase().trim();

                if (B.equalsIgnoreCase(value)){
                    return obj.getInt(KeytoGet);
                }

            }


        }catch (JSONException e){
            e.printStackTrace();
        }

        return 0;

    }


    public static String SearchIntJsonArrayReturnString(@NotNull JSONArray JsonArray, String Key, int value, String KeytoGet){
        try{
            for (int i = 0 ; i < JsonArray.length(); i++) {
                JSONObject obj = JsonArray.getJSONObject(i);
                int A = obj.getInt(Key);

                if (A==value){
                    return obj.getString(KeytoGet);
                }

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return "";
    }



    public static boolean ValidateResponseStatusCode(String CommandCurl, String StatusCode){

        boolean flag = false;
        try{


            String Response = Curl_Helper.SendCurl(stripAccents(CommandCurl));
            PdfHelper.PDFWriter(CommandCurl,"",false);
            System.out.println(CommandCurl);

            if (isJSONValid(Response)){

                System.out.println(Curl_Helper.GetJson(Response));
                String[] parts = Response.split("STATUS CODE:");
                String Json = parts[0];
                String CodeResponse = parts[1];

                if (CodeResponse.trim().equalsIgnoreCase(StatusCode)){

                    PdfHelper.PDFWriter("STATUS CODE ESPERADO: " +StatusCode,"",false);
                    PdfHelper.PDFWriter("STATUS CODE OBTENIDO: " +CodeResponse,"G",false);
                    PdfHelper.PDFWriter(Json,"G",false);
                    flag = true;

                }else {


                    PdfHelper.PDFWriter("STATUS CODE ESPERADO: " +StatusCode,"",false);
                    PdfHelper.PDFWriter("STATUS CODE OBTENIDO: " +CodeResponse,"R",false);
                    PdfHelper.PDFWriter(Json,"R",false);


                }


            }else  {

                Response = Response.replace("STATUS CODE:","");
                if (Response.trim().equalsIgnoreCase(StatusCode)){
                    PdfHelper.PDFWriter("STATUS CODE ESPERADO: " +StatusCode,"",false);
                    PdfHelper.PDFWriter("STATUS CODE OBTENIDO: " +Response,"G",false);
                    flag = true;

                }else {
                    PdfHelper.PDFWriter("STATUS CODE ESPERADO: " +StatusCode,"",false);
                    PdfHelper.PDFWriter("STATUS CODE OBTENIDO: " +Response,"R",false);

                }

            }



        } catch (JSONException e){

            e.printStackTrace();

        }

        return flag;
    }

    public static boolean ValidateResponseByInt(String CommandCurl, String Key, int ValueResponse){

        boolean flag = false;
        try{


            String JsonResponse = Curl_Helper.SendCurl(stripAccents(CommandCurl));
            PdfHelper.PDFWriter(CommandCurl,"",false);
            System.out.println(CommandCurl);

            if (isJSONValid(JsonResponse)){
                System.out.println(Curl_Helper.GetJson(JsonResponse));
                JSONObject jsonObject = Curl_Helper.GetJson(JsonResponse);

                assert jsonObject != null;
                if (jsonObject.has(Key)){

                    if (Curl_Helper.SearchInt(Objects.requireNonNull(jsonObject), Key)==ValueResponse){
                        PdfHelper.PDFWriter(JsonResponse,"G",false);
                        flag = true;

                    }else {
                        PdfHelper.PDFWriter(JsonResponse,"R",false);
                    }

                }else {

                    PdfHelper.PDFWriter(JsonResponse,"R",false);

                }

            }else  {

                PdfHelper.PDFWriter(JsonResponse,"R",false);
            }



        } catch (JSONException e){

            e.printStackTrace();

        }

        return flag;
    }


    public static boolean ValidateResponse(String JsonResponse, String Key, String ValueResponse){

        try{

            //System.out.println(Curl_Helper.GetJson(JsonResponse));

            if (ValueResponse.indexOf('/')>=0 ){

                String[] Val = ValueResponse.split("/");
                for (String s : Val) {

                    if (String.valueOf(Curl_Helper.SearchInt(Objects.requireNonNull(Curl_Helper.GetJson(JsonResponse)), Key)).equals(s)){
                        PdfHelper.PDFWriter(JsonResponse,"G",false);
                        return true;

                    }else {

                        PdfHelper.PDFWriter(JsonResponse,"R",false);
                        return false;

                    }

                }


            }else {

                if (String.valueOf(Curl_Helper.SearchInt(Objects.requireNonNull(Curl_Helper.GetJson(JsonResponse)), Key)).equals(ValueResponse)){
                    PdfHelper.PDFWriter(JsonResponse,"G",false);
                    return true;
                }else {
                    PdfHelper.PDFWriter(JsonResponse,"R",false);
                    return false;
                }


            }



        } catch (JSONException e){

            e.printStackTrace();
            return false;

        }

        return false;

    }

    public static void ValidateResponseByString(String CommandCurl, String Key, String ValueResponse){
        try{
            String JsonResponse = Curl_Helper.SendCurl(stripAccents(CommandCurl));
            PdfHelper.PDFWriter(CommandCurl,"",false);

            if (Curl_Helper.SearchString(Objects.requireNonNull(Curl_Helper.GetJson(JsonResponse)), Key).equals(ValueResponse.trim())){
                PdfHelper.PDFWriter(JsonResponse,"G",false);
            }else {
                PdfHelper.PDFWriter(JsonResponse,"R",false);
            }

        } catch (JSONException e){

            e.printStackTrace();
        }

    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }


    public static String deleteSpecialCharacters(String palabra){

        StringBuilder resultStr= new StringBuilder();

        for (int i=0;i<palabra.length();i++)
        {

            if ((palabra.charAt(i)>64 && palabra.charAt(i)<=122) || palabra.charAt(i)== 32  || (palabra.charAt(i)>=48 && palabra.charAt(i)<=57)  )
            {

                resultStr.append(palabra.charAt(i));
            }
        }

        return resultStr.toString();
    }


    public static String Extraer_Datos_Json(String Json){

        String Node0 ="";
        String Node1 ="";

        try{

            Json = Json.trim().replace("\\\"","\"").replace("\"{","'{").replace("}\"","}'");
            System.out.println(Json);
            String[] JsonSplit = Json.split(",");

            for (int i = 0; i<JsonSplit.length; i++){

                String Element =  JsonSplit[i];

                int value = Element.indexOf("{",1);
                int longitud = Element.length();

                if (value!=-1){

                    int value2 = Element.indexOf("{",value+1);

                    if (value2!=-1){

                        String Node = Element.substring(value2 + 1,longitud);
                        String[] NodeParts = Node.split(":");
                        Node0 = Node0 + NodeParts[0].replace("\"","").replace("{","").replace("}","")+ "|";
                        Node1 =  Node1 + NodeParts[1].replace("\"","").replace("{","").replace("}","")  + "|";

                    }else {

                        String Node = Element.substring(value + 1,longitud);
                        String[] NodeParts = Node.split(":");
                        Node0 = Node0 + NodeParts[0].replace("\"","").replace("{","").replace("}","")+ "|";
                        Node1 =  Node1 + NodeParts[1].replace("\"","").replace("{","").replace("}","")  + "|";

                    }


                }else {

                    String[] NodeParts = Element.split(":");
                    Node0 = Node0 +NodeParts[0].replace("\"","").replace("{","").replace("}","")+ "|";
                    Node1 =  Node1 + NodeParts[1].replace("\"","").replace("{","").replace("}","")  + "|";

                }

            }


            return Node1 + "\n";

        }catch(NoSuchElementException e){

            e.printStackTrace();

        }

        return "";

    }


}
