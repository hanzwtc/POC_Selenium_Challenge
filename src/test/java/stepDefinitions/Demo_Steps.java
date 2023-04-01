package stepDefinitions;

import Utils.SeleniumDriver;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import pageActions.Demo_Action;


public class Demo_Steps {


    public Demo_Steps() {
        PageFactory.initElements(SeleniumDriver.getDriver(), this);
    }

    static Logger log = LogManager.getLogger(Demo_Steps.class);
    Demo_Action Actions = new Demo_Action();


    @Given("^El Usuario Ingresa a LocalHost8085$")
    public void Usuario_Ingresa_PeruRail() {

        Actions.launchURL("http://localhost:8085/");

    }

    @When("^El Usuario indica el Nro de Actualizaciones ProcessId y Nro de Request\"(.*)\",\"(.*)\"")
    public void Enviar_Peticiones(int CantProcessId, int CantRequest) {

        Actions.Enviar_Peticiones(CantProcessId,CantRequest);
    }


    @And("^El Usuario procede a generar la EvidenciaV3\"(.*)\",\"(.*)\"")
    public void Generar_EvidenciaDemo(int CantProcessId, int CantRequest) {

        Actions.Generar_Evidencia(CantProcessId,CantRequest);

    }


}
