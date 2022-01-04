package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(


        //html: type of report, target: folder name, folder under target: default-cucumber-reports
        plugin = {
                "html:target/default-cucumber-reports",
                "json:target/json-report/cucumber.json",
                "junit:target/xml-report/cucumber.xml",
                "rerun:target/failedRerun.txt"
        },
        //Path of features
        features = "src/test/resources_features",
        //Path of step definitions
        glue = "stepDefinitions" ,
        //tags = "@cryspositive or @crysnegative",
        tags = "@test",
        dryRun = true


)
public class Runner {
}
