Feature: As a user I want to search the products and filter them by category, price, and name.

  @smoke
  Scenario: Search for products
    Given I am on the home page
    When I search for "Iphone"
    Then I should see a list of products that contain the word "Iphone"

  @test
  Scenario: Search for products and filter by category
    Given I am on the home page
    And I search for "Mobile Phones"
    When I select the search category as "Samsung"
    Then I should see a list of products that contain the word "Samsung" and are from the category "Samsung"

  @test
  Scenario: Scenario Number 1
    Given I am on the home page

  @test
  Scenario: Scenario Number 2
    Given I am on the home page

  @test
  Scenario: Scenario Number 3
    Given I am on the home page

  @test
  Scenario: Scenario Number 4
    Given I am on the home page

  @test
  Scenario: Scenario Number 5
    Given I am on the home page