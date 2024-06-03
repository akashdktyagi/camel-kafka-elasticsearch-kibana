package com.tdoc.stepdefs;

import com.tdoc.contexts.ScnContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class CommonStepDefs {

    Scenario scenario;
    ScnContext scnContext;
    Properties properties;

    public CommonStepDefs(ScnContext scnContext){
        this.scnContext = scnContext;
        this.properties = scnContext.getProperties();
    }

    @Before
    public void setUp(Scenario s){
        this.scenario=s;
        this.scnContext.setScenario(s);
    }

    @After
    public void tearDownAndTakeScreenShotIfFailed(Scenario s){
        if (s.isFailed() && properties.getProperty("screen_shot_strategy").equalsIgnoreCase("on_failure")){
            scnContext.takeScreenshotAndAttachWithReport();
            log.debug("Screenshot taken for the failed scenario: "+s.getName());
        }
        scnContext.quitDriver();
        log.info("Scenario: "+s.getName()+" is completed. Browser Closed");
    }

    @AfterStep
    public void shouldTakeScreenShotAfterEachStep(){
        if (properties.getProperty("screen_shot_strategy").equalsIgnoreCase("after_each_step")){
            scnContext.takeScreenshotAndAttachWithReport();
            log.debug("Screenshot taken after each step.");
        }
    }


    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        scnContext.invokeDriver();
        scnContext.navigateBrowser(scnContext.getProperties().getProperty("app_url"));
        log.info("Browser opened and navigated to the url: "+scnContext.getProperties().getProperty("app_url"));
    }


}
