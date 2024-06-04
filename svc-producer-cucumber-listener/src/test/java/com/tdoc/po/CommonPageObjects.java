package com.tdoc.po;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

@Slf4j
public class CommonPageObjects {

    WebDriver driver;

    private final String searchBox = "//input[@id='twotabsearchtextbox']";
    private final String searchSubmitButton = "//input[@value='Go' and @type='submit']";

    private final String productTitles = "//div[@data-cy='title-recipe']";

    private final String searchCategoriesCheckbox = "//span[@class='a-size-base a-color-base' and text()='%s']/preceding-sibling::div/label/i";

    public CommonPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String product) {
        driver.findElement(By.xpath(searchBox)).sendKeys(product);
        driver.findElement(By.xpath(searchSubmitButton)).click();
        log.info("Searching for product: " + product);
    }

    public void selectCategory(String category) {
        driver.findElement(By.xpath(String.format(searchCategoriesCheckbox, category))).click();
        log.info("Selecting category: " + category);
    }

    public void checkIfSearchIsSuccessfull(String searchString) {
        List<WebElement> listOfElements = driver.findElements(By.xpath(productTitles));

        for (int i = 0; i < listOfElements.size(); i++) {
            if (!listOfElements.get(i).getText().toLowerCase().contains(searchString.toLowerCase())) {
                Assertions.fail("Search is not successful. This product is not mathing: " + listOfElements.get(i).getText() + " Looking for: " + searchString);
            }
        }
        Assertions.assertThat(true).isTrue();
        log.info("Search is successful");

        //or simply do this
//        Assertions.assertThat(listOfElements).allMatch(element -> element.getText().contains(searchString));

        //or another simple way
//        driver.findElements(By.xpath(productTitles)).forEach(title -> {
//            if (!title.getText().contains(searchString)) {
//                throw new RuntimeException("Search is not successful");
//            }
//        });
    }

}
