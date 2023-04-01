package stepDefinitions;

import Utils.SeleniumDriver;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import pageActions.Evershop_Action;


public class Evershop_Steps {


    public Evershop_Steps() {
        PageFactory.initElements(SeleniumDriver.getDriver(), this);
    }

    static Logger log = LogManager.getLogger(Evershop_Steps.class);
    Evershop_Action Actions = new Evershop_Action();


    @Given("^User open the Evershop Web \"(.*)\"")
    public void User_open_Evershop_Web(String columCreation) {

        if(columCreation.equalsIgnoreCase("for registration")){
            Actions.launchURL("https://demo.evershop.io/", "Name/Email");
        }

        else
        {
            Actions.launchURL("https://demo.evershop.io/", "Name/Email/ProductsDetail_Men/ProductsDetail_Kids/ProductsDetail_Women");
        }

    }

    @And("^User clicks on Login icon")
    public void Click_Login() {
        Actions.ClickLogin();
    }

    @And("^User clicks on Account Creation")
    public void Click_Account_Creation() {
        Actions.ClickCreateAccount();
    }

    @And("^User completes information for registration \"(.*)\",\"(.*)\",\"(.*)\"")
    public void User_registration(String username, String email, String password) {
        Actions.Register_User(username, email, password);
    }

    @Then("^User verify the correct creation of the account with \"(.*)\" validation")
    public void Validate_User_Registration(String email) {
        Actions.Validate_User_Registration(email);
    }

    @And("^User log with credentials using \"(.*)\",\"(.*)\"")
    public void Log_User(String email, String password) {
        Actions.Log_User(email, password);
    }

    @Then("^User verify the correct login of the account with \"(.*)\" validation")
    public void Validate_User_Login(String email) {
        Actions.Validate_User_Login(email);
    }

    @And("^User select Category Product \"(.*)\"")
    public void Product_Category(String category) {
        Actions.Select_Product_Category(category);
    }

    @When("^User select Shoes to buy by \"(.*)\"")
    public void Select_Product(String ProductsDetail) {
        Actions.Select_Product(ProductsDetail);
    }

    @And("^User clicks on miniCart and Checkout")
    public void Product_Checkout() {
        Actions.Checkout_Product();
    }

    @And("^User complete the information for Payment")
    public void Checkout_Information() {
        Actions.Checkout_Information();
    }

    @And("^User choose the payment method and place order")
    public void PlaceOrder(){
        Actions.PlaceOrder();
    }

    @Then("^User verify the Buy Order")
    public void Order_Validation(){
        Actions.Order_Validation();
    }



    @And("^User Evidence Create Account \"(.*)\",\"(.*)\"")
    public void User_Evidence_Create_Account(String UserName , String Email) {

        Actions.Generar_Evidencia_Registration(UserName,Email);

    }


    @And("^User Create the buy report \"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\"")
    public void User_Evidence_Compra(String UserName , String Email ,String ProdMen, String ProdKids, String ProdWomen) {

        Actions.Generar_Evidencia_Compra(UserName,Email,ProdMen,ProdKids,ProdWomen);

    }


}
