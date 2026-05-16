
package com.proyecto.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(

        features = "src/test/resources/features",

        glue = "com.proyecto.stepdefinitions",


        plugin = {
                "pretty",
                "json:target/cucumber-reports/Cucumber.json",
                "html:target/cucumber-reports/Cucumber.html"
        }//,


        //snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunTest {

}