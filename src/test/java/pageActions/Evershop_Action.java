package pageActions;

import Utils.PdfHelper;
import Utils.ReportHTML;
import Utils.SeleniumDriver;
import Utils.SeleniumHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pageLocators.Evershop_Locators;

public class Evershop_Action {

    Evershop_Locators Page = new Evershop_Locators();
    WebDriver driver = SeleniumDriver.getDriver();
    Actions action = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public static int IdCaso = 1 , ContPass = 0 , ContFail = 0;
    public static boolean Validation = false ;
    public static String IniTime ="" , IniTimeCase ="" , Category ="";

    public Evershop_Action() {}


    public void launchURL(String url,String columns )  {

        driver.get(url);
        PdfHelper.CreatePDF("EVIDENCE OF AUTOMATED TEST - EVERSHOP");
        if(IdCaso==1){
            ReportHTML.IniReport("TEST EVERSHOP",columns);

            IniTime = ReportHTML.GetFechaActual();

        }

        Validation = true;
        IniTimeCase = ReportHTML.GetFechaActual();
        Category ="";

    }

    public void loginPage_verifyTitle(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void ClickLogin(){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.btn_Login)){

                    SeleniumHelper.ClickElement(Page.btn_Login);

                   PdfHelper.PDFWriterChrome("CLICK ON LOGIN BUTTON","B",true);

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            }

        }

    }

    public void ClickCreateAccount(){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.btn_Create_Account)){

                    SeleniumHelper.ClickElement(Page.btn_Create_Account);
                        PdfHelper.PDFWriterChrome("CLICK ON CREATE ACCOUNT","B",true);

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }


            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            }

        }


    }


    public void Register_User(String username, String email, String password){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.lbl_UserName)){
                    SeleniumHelper.verifyElement(Page.lbl_UserName);

                    SeleniumHelper.SendKeys(Page.lbl_UserName,username);
                    SeleniumHelper.SendKeys(Page.lbl_Email,email);
                    SeleniumHelper.SendKeys(Page.lbl_Password,password);

                    PdfHelper.PDFWriterChrome("COMPLETE FULL USER INFO FOR REGISTRATION","B",true);

                    SeleniumHelper.ClickElement(Page.btn_SignUP);

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }


            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            }

        }

    }


    public void Validate_User_Registration(String email){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.btn_Profile_Account)){
                    SeleniumHelper.verifyElement(Page.btn_Profile_Account);

                    SeleniumHelper.ClickElement(Page.btn_Profile_Account);

                    String emailWeb = Page.txt_Email_Verification.getText().trim();


                    if (emailWeb.equalsIgnoreCase(email)){
                        PdfHelper.PDFWriterChrome("SUCCESFULLY REGSITRATED","G",true);
                    }
                    else{
                        Validation=false;
                        PdfHelper.PDFWriterChrome("FAILED REGISTRATION","R",true);
                    }

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("FAILED REGISTRATION","R",true);

                }


            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("FAILED REGISTRATION","R",true);
            }

        }

    }

    public void Validate_User_Login(String email){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.btn_Profile_Account)){
                    SeleniumHelper.verifyElement(Page.btn_Profile_Account);

                    SeleniumHelper.ClickElement(Page.btn_Profile_Account);

                    String emailWeb = Page.txt_Email_Verification.getText().trim();


                    if (emailWeb.equalsIgnoreCase(email)){
                        PdfHelper.PDFWriterChrome("SUCCESFULLY LOGIN","G",true);
                    }
                    else{
                        Validation=false;
                        PdfHelper.PDFWriterChrome("FAILED LOGIN","R",true);
                    }

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("FAILED LOGIN","R",true);

                }


            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("FAILED LOGIN","R",true);
            }

        }

    }


    public void Log_User(String email, String password){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.lbl_Email)){
                    SeleniumHelper.verifyElement(Page.lbl_Email);


                    SeleniumHelper.SendKeys(Page.lbl_Email,email);
                    SeleniumHelper.SendKeys(Page.lbl_Password,password);

                    PdfHelper.PDFWriterChrome("Email and Password used","B",true);

                    SeleniumHelper.ClickElement(Page.btn_SignIN);



                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }


            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            }

        }

    }




    public void Select_Product_Category(String category){

        if (Validation){

            try {
                if(category.equalsIgnoreCase("Men")){
                    SeleniumHelper.ClickElement(Page.btn_MenCategory);
                    Category = "Men";
                } else if (category.equalsIgnoreCase("Kids")) {
                    SeleniumHelper.ClickElement(Page.btn_KidsCategory);
                    Category = "Kids";
                }else if (category.equalsIgnoreCase("Women")) {
                    SeleniumHelper.ClickElement(Page.btn_WomenCategory);
                    Category = "Women";
                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            }

        }

    }





    public void Select_Product(String ProductsDetail){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.txt_Category_Validation)){
                    SeleniumHelper.verifyElement(Page.txt_Category_Validation);

                   // if (ProductsDetail.contains("/")){

                        String[] Shoes = ProductsDetail.split("/");

                        int NroShoes = Shoes.length;

                        for (int i=0; i < NroShoes; i++) {

                            String[] Shoe = Shoes[i].split("-");

                            String xpath = "//span[text()='"+Shoe[0]+"']";
                            WebElement element = driver.findElement(By.xpath(xpath));
                            SeleniumHelper.verifyElement(element);
                            SeleniumHelper.ClickElement(element);

                            if(SeleniumHelper.isElementPresent(Page.txt_Product_Validation)){

                                SeleniumHelper.CleanText(Page.lbl_Quantity);
                                SeleniumHelper.SendKeys(Page.lbl_Quantity,Shoe[1]);

                                xpath = "//a[text()='"+Shoe[2]+"']";
                                WebElement shoeSize = driver.findElement(By.xpath(xpath));
                                SeleniumHelper.ClickElement(shoeSize);

                                xpath = "//a[text()='"+Shoe[3]+"']";
                                WebElement shoeColor = driver.findElement(By.xpath(xpath));
                                SeleniumHelper.ClickElement(shoeColor);

                                Thread.sleep(2000);

                                SeleniumHelper.ClickElement(Page.btn_AddToCart);

                                SeleniumHelper.verifyElement(Page.txt_AddToCart_Validation);

                                PdfHelper.PDFWriterChrome("PRODUCT DETAILS ADDED TO THE CART","B",true);

                                if(Category.equalsIgnoreCase("Men")){
                                    SeleniumHelper.ClickElement(Page.btn_MenCategory);
                                } else if (Category.equalsIgnoreCase("Kids")) {
                                    SeleniumHelper.ClickElement(Page.btn_KidsCategory);
                                }else if (Category.equalsIgnoreCase("Women")) {
                                    SeleniumHelper.ClickElement(Page.btn_WomenCategory);
                                }

                            }

                        }


                   // }

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("NO SE CARGO LA PAGINA DE BELMOND ANDEAN EXPLORER","R",true);

                }


            } catch (NoSuchElementException e) {

                e.printStackTrace();
                Validation=false;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }



    public void Checkout_Product(){

        if (Validation){

            try {
                if (SeleniumHelper.isElementPresent(Page.btn_miniCart)){

                    SeleniumHelper.ClickElement(Page.btn_miniCart);
                    Thread.sleep(500);
                    PdfHelper.PDFWriterChrome("CLICK ON CHECKOUT","B",true);
                    SeleniumHelper.ClickElement(Page.btn_CheckOut);

                }else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }


    public void Checkout_Information(){

        if (Validation){

            try {

              //  String check = Page.chk_addressShipping.getAttribute("class");

                if (SeleniumHelper.isElementPresent(Page.lbl_fullnameCheckout)){

                    SeleniumHelper.SendKeys(Page.lbl_fullnameCheckout, "HANS KEVIN BERMUDEZ BARRERA");
                    SeleniumHelper.SendKeys(Page.lbl_telephoneCheckout, "(555)555-1234");
                    SeleniumHelper.SendKeys(Page.lbl_addressCheckout, "823 West Pine Street");
                    SeleniumHelper.SendKeys(Page.lbl_cityCheckout, "California");
                    SeleniumHelper.SelectElement(Page.cmb_countryCheckout,"United States");
                    SeleniumHelper.SelectElement(Page.cmb_provinceCheckout,"California");
                    SeleniumHelper.SendKeys(Page.lbl_postcodeCheckout, "90210");
                    Thread.sleep(500);
                    SeleniumHelper.ClickElement(Page.chk_freeshipCheckout);

                    PdfHelper.PDFWriterChrome("FULL CHECKOUT INFORMATION COMPLETED","B",true);

                    SeleniumHelper.ClickElement(Page.btn_payment);
                    Thread.sleep(500);

                }

                else if(Page.chk_addressShipping.getAttribute("class").equalsIgnoreCase("checkbox-checked")){

                    //WON'T FILL INFORMATION
                    PdfHelper.PDFWriterChrome("CLIENT ALREADY HAVE INFORMATION","B",true);

                }

                else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void PlaceOrder(){

        if (Validation){

            try {

                if (SeleniumHelper.isElementPresent(Page.btn_visaOption)){

                    SeleniumHelper.ClickElement(Page.btn_visaOption);
                    Thread.sleep(500);

                    driver.switchTo().frame(0);

                    SeleniumHelper.ClickElement(Page.lbl_cardNumber);
                    SeleniumHelper.SendTextElement(Page.lbl_cardNumber,"4242424242424242");
                    SeleniumHelper.SendTextElement(Page.lbl_cardExpDate,"0424");
                    SeleniumHelper.SendTextElement(Page.lbl_cardCVC,"242");

                    PdfHelper.PDFWriterChrome("PAYMENT INFORMATION COMPLETED","B",true);

                    driver.switchTo().defaultContent();

                    SeleniumHelper.ClickElement(Page.btn_placeOrder);

                }

                else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAGE OR ELEMENT DIDN'T FOUND","R",true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }


    public void Order_Validation(){

        if (Validation){

            try {

                if (SeleniumHelper.isElementPresent(Page.txt_PlaceOrderVerificationMsg)){

                    PdfHelper.PDFWriterChrome("PAYMENT COMPLETED THANK YOU","B",true);

                }

                else {

                    Validation=false;
                    PdfHelper.PDFWriterChrome("PAYMENT INCOMPLETE","R",true);

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
                Validation=false;
                PdfHelper.PDFWriterChrome("PAYMENT INCOMPLETE","R",true);
            }

        }

    }



    public void Generar_Evidencia_Registration(String UserName , String Email) {

        try{

            String Horacase = ReportHTML.TiempoDuracion(IniTimeCase,ReportHTML.GetFechaActual());
            PdfHelper.PDFWriterChrome("TIEMPO DE DURACION : " + Horacase ,"B",false);

            String PathPDF = PdfHelper.ClosePDF();
            String Valores = UserName+"/"+Email;
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







    public void Generar_Evidencia_Compra(String UserName , String Email ,String ProdMen, String ProdKids, String ProdWomen) {

        try{

            String Horacase = ReportHTML.TiempoDuracion(IniTimeCase,ReportHTML.GetFechaActual());
            PdfHelper.PDFWriterChrome("TIEMPO DE DURACION : " + Horacase ,"B",false);

            String PathPDF = PdfHelper.ClosePDF();
            String Valores = UserName+"/"+Email+"/"+ProdMen+"/"+ProdKids+"/"+ProdWomen;
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

