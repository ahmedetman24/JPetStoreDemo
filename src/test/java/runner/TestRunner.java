package runner;

import io.cucumber.testng.CucumberOptions;
import testcases.TestBase;

@CucumberOptions(features = "src/test/java/featureFiles", glue = {"stepDefinitions"}, plugin = {"pretty", "html:target/cucumber-html-report.html"})
public class TestRunner extends TestBase
{

}
