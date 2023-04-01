package pageActions;

import Utils.Curl_Helper;
import Utils.PdfHelper;
import Utils.ReportHTML;
import org.openqa.selenium.NoSuchElementException;

public class ApiDemo_Action {

    public static int IdCaso = 1 , ContPass = 0 , ContFail = 0;
    public static boolean Validation = true ;
    public static String IniTime ="" , IniTimeCase ="", ApiMethod="" ,  ApiURL ="" , ApiData="", ApiStatusCode="", ApiCurl="";

    public ApiDemo_Action() {}


    public void Method_configuration(String Method, String columns){

        if (Validation){

            try {

                PdfHelper.CreatePDF("EVIDENCIA DE PRUEBA AUTOMATIZADA - API DEMO");
                if(IdCaso==1){
                    ReportHTML.IniReport("DEMO",columns);
                    IniTime = ReportHTML.GetFechaActual();
                    ApiMethod = Method;
                }

                Validation = true;
                ApiData = "";
                ApiCurl = "";
                IniTimeCase = ReportHTML.GetFechaActual();

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;

            }
        }


    }


    public void Endpoint_configuration(String Endpoint){

        if (Validation){

            try {

                if(IdCaso==1){
                    ApiURL = Endpoint;
                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;

            }
        }


    }


    public void Endpoint_parameter(String Data){

        if (Validation){

            try {

                ApiData=Data;

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;

            }
        }


    }



    public void Api_executes(){

        if (Validation){

            try {
                    String endpointParameter = ApiURL + ApiData ;

                    ApiCurl = "curl "+ endpointParameter + " -X " + ApiMethod + " -w \"STATUS CODE:%{response_code}\"";



            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;

            }
        }


    }



    public void Verify_response(String StatusCode){

        if (Validation){

            try {

                   ApiStatusCode = StatusCode;

                   Validation = Curl_Helper.ValidateResponseStatusCode(ApiCurl,StatusCode);
                   Thread.sleep(1000);

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }







    public void Generar_Evidencia() {

        try{

            //String Horacase = ReportHTML.TiempoDuracion(IniTimeCase,ReportHTML.GetFechaActual());
            //PdfHelper.PDFWriterChrome("TIEMPO DE DURACION : " + Horacase ,"B",false);

            String PathPDF = PdfHelper.ClosePDF();

            String Values = ApiMethod + "/" + ApiData + "/" + ApiStatusCode;

            ReportHTML.AddNewCase(PathPDF,Validation,IdCaso,Values);

            IdCaso = IdCaso + 1 ;
            if(Validation){
                ContPass = ContPass + 1 ;
            }else{
                ContFail = ContFail+ 1 ;
            }

            ReportHTML.Values = IniTime + "_" +ContPass + "_" + ContFail;

        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

    }




}

