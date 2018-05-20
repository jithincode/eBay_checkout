#Author: Jithin N
#User Should be already registered
Feature: eBay Login 

Background: 
	Given eBay App should launch successfully 
	
@EbayLogin 
Scenario: 16 Verify t hat successful login through desktop 
	And I click on ebaySign-In button
	When enter valid UserName and password
	And click on Submit button
	Then System should navigate ebay Home page

@EbayLoginAndSearchProduct
Scenario Outline: 16 Verify t hat successful login through desktop 
	And I click on ebaySign-In button
	When enter valid UserName and password
	And click on Submit button
	Then System should navigate ebay Home page
	And I enter the ProductName "<ProductName>"
	And select the product from the list

Examples: 
		|ProductName|
		|Redmi Note 3|
		
@EbayLoginAndCheckOutProduct
Scenario Outline: 16 Verify t hat successful login through desktop 
	And I click on ebaySign-In button
	When enter valid UserName and password
	And click on Submit button
	Then System should navigate ebay Home page
	And I enter the ProductName "<ProductName>"
	And select the product from the list
	And Checkout the Product to Cart
	Then I proceed to Pay
Examples: 
		|ProductName|
		|Redmi Note 3|

