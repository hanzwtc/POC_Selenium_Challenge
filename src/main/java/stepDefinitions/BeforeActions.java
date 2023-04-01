package stepDefinitions;

import Utils.AppiumDriver;
import Utils.ConfigFileReader;
import Utils.ExcelUtils;
import Utils.SeleniumDriver;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Scenario;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class BeforeActions {


	public static ThreadLocal<Scenario> threadLocalScenario = new ThreadLocal<>();

	@Before
	public void setUp(Scenario scenario) throws IOException{
		
		Logger log = LogManager.getLogger(BeforeActions.class);
		System.out.println("SETTING UP DRIVER>>>>");
		log.info("SETTING UP DRIVER>>>>");
		
		ExcelUtils utils = new ExcelUtils();
		threadLocalScenario.set(scenario);
		utils.createExcel(scenario.getName());
		String browserName = new ConfigFileReader().getBrowserName();
		String AutomationHost = new ConfigFileReader().getAutomationHost();


		if(browserName!=null && !browserName.isEmpty()) {

			if (browserName.contains("/") && AutomationHost.contains("/")){

					String[] browser = browserName.split("/");
					String[] host = AutomationHost.split("/");

					for (int i = 0; i < host.length; i++) {


						if(browser[i].equalsIgnoreCase("curl")){

							System.out.println("INITIALIZING DRIVER CURL >>>>");

						}else {

							if(host[i].equalsIgnoreCase("SeleniumLocalHost") || host[i].equalsIgnoreCase("SeleniumBrowserstack")){
								SeleniumDriver.setUpDriver();

							}else{

								if (browser[i].equalsIgnoreCase("android")){
									AppiumDriver.setUpAndroidDriver();

								}else if (browser[i].equalsIgnoreCase("ios")){
									AppiumDriver.setUpIosDriver();
								}

							}

						}

					}


			}else {

					if (browserName.equalsIgnoreCase("curl")){

						System.out.println("INITIALIZING DRIVER CURL >>>>");

					}else {

						if(AutomationHost.equalsIgnoreCase("SeleniumLocalHost") || AutomationHost.equalsIgnoreCase("SeleniumBrowserstack")){
							SeleniumDriver.setUpDriver();

						}else{

							if (browserName.equalsIgnoreCase("android")){
								AppiumDriver.setUpAndroidDriver();

							}else if (browserName.equalsIgnoreCase("ios")){
								AppiumDriver.setUpIosDriver();
							}

						}

					}


			}

		}else throw new WebDriverException("Browser name is Invalid");

		System.out.println("INITIALIZING DRIVER >>>>");
		log.info("INITIALIZING DRIVER >>>>");
		BeforeActions.threadLocalScenario.set(scenario);
		
	}








	@BeforeStep
	public void beforeStep(Scenario scn) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		int currentStepIndex = 0;
		
		currentStepIndex++;
		currentStepIndex++;
		
		Field testCaseField = scn.getClass().getDeclaredField("testCase");
		testCaseField.setAccessible(true);
		
		TestCase tc = (TestCase) testCaseField.get(scn);
		Field testSteps = tc.getClass().getDeclaredField("testSteps");
		testSteps.setAccessible(true);
		
		List<TestStep> teststeps = tc.getTestSteps();
		try {
			PickleStepTestStep pts = (PickleStepTestStep) teststeps.get(currentStepIndex);
			new ExcelUtils().updateStepExcel(pts.getStepText());
			currentStepIndex++;
		}catch(Exception ignore) {}
	}


}
