package MyRunner;


import baseRunner.BaseRunnerBrowser;
import cucumber.api.CucumberOptions;



@CucumberOptions(

		plugin = {"json:target/cucumber.json", "html:target/cucumber-reports","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		features = "src/test/java/Features",
		glue = "stepDefinitions",
		tags = {"@SendRequestApiTest"}

		)

public class TestRunner extends BaseRunnerBrowser{
	public static String featurePath = "src/test/java/Features";
	public TestRunner() {
		super(featurePath);
	}

}





