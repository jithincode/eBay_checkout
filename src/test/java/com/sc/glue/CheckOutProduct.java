package com.sc.glue;

import static com.sc.utils.ApplicationLogger.log;

import com.sc.pages.CheckoutProductPage;
import com.sc.wrappers.GenericWrappers;

import cucumber.api.java.en.When;

public class CheckOutProduct {

	CheckoutProductPage CheckOutProduct = new CheckoutProductPage();
	
	@When("^Checkout the Product to Cart$")
	public void checkout_the_Product_to_cart() throws InterruptedException {
		CheckOutProduct.ClickOnBuyButton();
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
		CheckOutProduct.ClickOnReviewButton();
		log.info("Checked out the product");
		GenericWrappers.swipingVerticalTopToBottum();
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	@When("^I proceed to Pay$")
	public void select_the_product_from_the_list() {
		CheckOutProduct.clickOn_ProceedToPay();
		log.info("I Proceeded to pay");
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
		GenericWrappers.sleepInSeconds(10);	
	}
	
	
}
