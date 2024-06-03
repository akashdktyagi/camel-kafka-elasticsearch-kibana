package com.tdoc.stepdefs;

import com.tdoc.contexts.ScnContext;
import com.tdoc.po.CommonPageObjects;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchStepDefs {

    ScnContext scnContext;
    CommonPageObjects commonPageObjects;

    public SearchStepDefs(ScnContext scnContext){
        this.scnContext = scnContext;
        commonPageObjects = new CommonPageObjects(scnContext.getDriver());
    }

    @When("I search for {string}")
    public void i_search_for(String product) {
        commonPageObjects.searchProduct(product);
        log.info("Searching for the product: "+product);
    }

    @Then("I should see a list of products that contain the word {string}")
    public void i_should_see_a_list_of_products_that_contain_the_word(String productName) {
        commonPageObjects.checkIfSearchIsSuccessfull(productName);
    }

    @When("I select the search category as {string}")
    public void i_select_the_search_category_as(String string) {
        commonPageObjects.selectCategory(string);
    }
    @Then("I should see a list of products that contain the word {string} and are from the category {string}")
    public void i_should_see_a_list_of_products_that_contain_the_word_and_are_from_the_category(String brandName, String brandName2) {
        commonPageObjects.checkIfSearchIsSuccessfull(brandName);
    }

}
