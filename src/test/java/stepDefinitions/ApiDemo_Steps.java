package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageActions.ApiDemo_Action;


public class ApiDemo_Steps {


    static Logger log = LogManager.getLogger(ApiDemo_Steps.class);
    ApiDemo_Action Actions = new ApiDemo_Action();


    @Given("^The Method configuration \"(.*)\"")
    public void Method_configuration(String Method){
        Actions.Method_configuration(Method,"Method/Data/StatusCode");
    }

    @And("^The Endpoint configuration \"(.*)\"")
    public void Endpoint_configuration(String Endpoint) {
        Actions.Endpoint_configuration(Endpoint);
    }

    @And("^Endpoint is parameterized \"(.*)\"")
    public void Endpoint_parameter(String Data) {
        Actions.Endpoint_parameter(Data);
    }

    @When("^Api services execute")
    public void Api_executes(){
        Actions.Api_executes();
    }

    @Then("^Verify responde code \"(.*)\"")
    public void Verify_response(String StatusCode){
        Actions.Verify_response(StatusCode);
    }







    @And("^User evidence of the petition")
    public void Generar_Evidencia() {

        Actions.Generar_Evidencia();

    }


}
