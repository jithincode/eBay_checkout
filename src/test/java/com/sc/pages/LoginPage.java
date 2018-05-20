package com.sc.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sc.wrappers.GenericWrappers;
import com.sc.wrappers.NativePage;


public class LoginPage extends NativePage {
	
	final String LOGINHEADER = "Sign in so we can personalise your eBay experience";

	@FindBy(id = "logo")
	WebElement txtPageHeader;
	
	@FindBy(id = "button_sign_in")
	WebElement btnLogin;
	
	@FindBy(id = "userid")
	WebElement ebayLoginID;
	
	@FindBy(id = "pass")
	WebElement ebayPwd;
	
	@FindBy(id = "sgnBt")
	WebElement btnLoginSumbit;


	//To verify on Login page
	public boolean isOnLoginPage() {
		return getHeaderText().contains(LOGINHEADER);
	}
	
	//To retrieve the Header text
	public String getHeaderText() {
		return this.txtPageHeader.getText();
	}
	
	//To click on Sign-in button
	public LoginPage clickOn_eBaySignInButton() {
		this.btnLogin.click();
		return this;
	}
	
	//Enter the user credentials 
	public LoginPage enterUserIdPwd(String UserId, String Pwd) {
		ebayLoginID.sendKeys(UserId);
		ebayPwd.sendKeys(Pwd);
		GenericWrappers.sleepInSeconds(5);
		GenericWrappers.waitForElementToBeClickable(btnLoginSumbit, 10);
		return this;
	}
	
	public LoginPage clickOnSubmitSIgnInButton() {
		this.btnLoginSumbit.click();
		return this;
	}
	
}
