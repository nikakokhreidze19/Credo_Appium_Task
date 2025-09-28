package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.example.util.BaseConfig;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@CucumberOptions(
        features = "src/test/java/feature",
        glue = "steps",
        plugin = {
                "pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "html:target/cucumber-reports.html"
        },
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void beforeSuite() {
        BaseConfig.startApp();
    }


    @AfterSuite
    public void afterSuite() {
        BaseConfig.stopApp();
    }
}
