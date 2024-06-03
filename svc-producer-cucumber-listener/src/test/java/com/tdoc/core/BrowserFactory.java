package com.tdoc.core;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

@Slf4j
public class BrowserFactory {
    public static WebDriver createInstance(String browserName, String execType) {
        WebDriver driver=null;

        switch (browserName.trim().toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                Assert.fail("No Such Browser Avaialble.");
        }
        log.info("Browser created successfully. browserName: " + browserName + " execType: " + execType);
        return driver;
    }
}
