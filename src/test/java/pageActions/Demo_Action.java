package pageActions;

import Utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pageLocators.Demo_Locators;

public class Demo_Action {

    Demo_Locators Page = new Demo_Locators();
    WebDriver driver = SeleniumDriver.getDriver();
    Actions action = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public static int IdCaso = 1 , ContPass = 0 , ContFail = 0;
    public static boolean Validation = false ;
    public static String IniTime ="" , IniTimeCase ="" , ProcessId="";

    public Demo_Action() {}


    public void launchURL(String url)  {

        driver.get(url);
        PdfHelper.CreatePDF("EVIDENCIA DE PRUEBA AUTOMATIZADA - DEMO");
        if(IdCaso==1){
            ReportHTML.IniReport("DEMO","ProcessId Generados/Request Enviados");

            IniTime = ReportHTML.GetFechaActual();

        }

        ProcessId="";
        Validation = true;
        IniTimeCase = ReportHTML.GetFechaActual();


    }



    public void GenerarProcessId(){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.Btn_Start)){

                    SeleniumHelper.ClickElement(Page.Btn_Start);
                    Thread.sleep(1000);
                    ProcessId = js.executeScript("return document.getElementById('processId').value").toString();

                    PdfHelper.PDFWriterChrome("SE OBTIENE EL PROCESSID "+ProcessId,"G",true);


                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("NO SE CARGO LOCALHOST 8085","R",true);

                }


            } catch (NoSuchElementException | InterruptedException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("NO SE CARGO LA PAGINA PERU RAIL","R",true);
            }
        }

    }


    public void Enviar_Peticiones(int CantGenerarCodigo,int CantPeticiones){

        if (Validation){

            try {
                int cont = 0 ;

                for (int i = 1 ; i<=CantGenerarCodigo;i++){
                    GenerarProcessId();

                    for (int j = 1 ; j<=CantPeticiones;j++){

                        cont = cont + 1;
                        String msg = GenerarPalabra();

                        String Curl = "curl http://localhost:8085/message/receive/"+ProcessId+" --data-raw \""+msg+"\" ";
                        System.out.println(Curl_Helper.SendCurl(Curl));
                        PdfHelper.PDFWriterChrome(Curl,"B",false);

                        //String xpath = "//td[contains(text(),'respuesta al process: "+ProcessId+", message: "+msg+"='')])["+cont+"]";
                        //WebElement element = driver.findElement(By.xpath(xpath));

                        String trama = SeleniumHelper.SearchNetworkLog(SeleniumHelper.GetNetworkLogs(),"respuesta al process: "+ProcessId,"\"{\\\"statusMessage","\",\"eventId\"",1);
                        System.out.println(trama);

                        PdfHelper.PDFWriterChrome(trama,"",false);

                    }



                }
                Generar_Evidencia(CantGenerarCodigo,CantPeticiones);

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("NO SE CARGO LA PAGINA PERU RAIL","R",true);

            }
        }

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

    public void Generar_Evidencia(int CantGenerarCodigo,int CantPeticiones) {

        try{

            //String Horacase = ReportHTML.TiempoDuracion(IniTimeCase,ReportHTML.GetFechaActual());
            //PdfHelper.PDFWriterChrome("TIEMPO DE DURACION : " + Horacase ,"B",false);

            String PathPDF = PdfHelper.ClosePDF();
            String Valores = CantGenerarCodigo+"/"+CantPeticiones;
            ReportHTML.AddNewCase(PathPDF,Validation,IdCaso,Valores);

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

