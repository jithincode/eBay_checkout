package com.sc.glue;

import static com.sc.utils.ApplicationLogger.log;
import static org.junit.Assert.assertTrue;

import com.sc.pages.HomePage;
import com.sc.pages.LoginPage;
import com.sc.wrappers.GenericWrappers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class HomePageStepDef {

	HomePage Homepage = new HomePage();

	@When("^System should navigate ebay Home page$")
	public void eBay_System_should_navigate_ebay_Home_page() {
		assertTrue("Failed!! Home Page is not displayed",Homepage.isOnHomePage());
		log.info("Landed to Home Page");
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	@Given("^I enter the ProductName \"([^\"]*)\"$")
	public void enter_valid_UserName_password(String SearchProduct) {
		Homepage.entertheProductinSearch(SearchProduct);
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	
	@When("^select the product from the list$")
	public void select_the_product_from_the_list() {
		Homepage.clickOn_firstItem();
		log.info("I selected the first product");
		GenericWrappers.sleepInSeconds(10);	
	}
	
}
