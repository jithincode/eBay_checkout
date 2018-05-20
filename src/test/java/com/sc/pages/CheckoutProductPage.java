package com.sc.pages;


import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sc.wrappers.GenericWrappers;
import com.sc.wrappers.NativePage;


public class CheckoutProductPage extends NativePage {
	@FindBy(id = "button_bin")
	WebElement BuyButton;

	@FindBy(id = "take_action")
	WebElement 	ReviewButton;
	
	
	@FindBy(id = "proceedToPay")
	WebElement 	ProceedToPay;
	
	
	public CheckoutProductPage clickOn_ProceedToPay() {
		ProceedToPay.click();
		GenericWrappers.sleepInSeconds(5);
		return this;
	}
	
	public CheckoutProductPage ClickOnReviewButton() {
		ReviewButton.click();
		GenericWrappers.sleepInSeconds(5);
		return this;
	}
	
	public CheckoutProductPage ClickOnBuyButton() {
		BuyButton.click();
		GenericWrappers.sleepInSeconds(5);
		return this;
	}
	

	
	
	
	
	/*@FindBy(id = "logo")
	WebElement txtPageHeader;
	
	@FindBy(id = "search_box")
	WebElement SearchBox;
	
	@FindBy(id = "ItemsList")
	List<WebElement> ItemsLst;

	
	public boolean isOnHomePage() {
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
		return txtPageHeader.isDisplayed();
		
	}
	
	public CheckoutProductPage entertheProductinSearch(String SearchProduct) {
		SearchBox.sendKeys(SearchProduct);
		GenericWrappers.sleepInSeconds(5);
		GenericWrappers.enterUsingKeyBoard();
		return this;
	}
	
	public CheckoutProductPage clickOn_firstItem() {
		ItemsLst.get(1).click();
		GenericWrappers.sleepInSeconds(5);
		return this;
	}*/


}
