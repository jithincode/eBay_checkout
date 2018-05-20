package com.sc.glue;

import static com.sc.utils.ApplicationLogger.log;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sc.pages.LoginPage;
import com.sc.wrappers.GenericWrappers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class RegistrationPageStepDef {

	LoginPage login = new LoginPage();

	@When("^eBay App should launch successfully$")
	public void eBay_App_should_launch_successfully() {
		assertTrue("Failed!! Registration step-1 page not displayed",login.isOnLoginPage());
		log.info("The App is launched successfully");
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	@When("^I click on ebaySign-In button$")
	public void i_click_ebaySign_button() {
		login.clickOn_eBaySignInButton();
		GenericWrappers.sleepInSeconds(10);	
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	@Given("^enter valid UserName and password$")
	public void enter_valid_UserName_password() throws IOException {
		InputStream ExcelFileToRead = new FileInputStream(("testdata/IN/login.xlsx"));
        XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
        String UserId=wb.getSheetAt(0).getRow(1).getCell(2).toString();
        String Pwd=wb.getSheetAt(0).getRow(2).getCell(2).toString();
		new LoginPage().enterUserIdPwd(UserId, Pwd);
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
	}
	
	@When("^click on Submit button$")
	public void click_On_Submit_button() {
		login.clickOnSubmitSIgnInButton();
		log.info("Entered the Usercredentials and Clicked on Sign in Submit");
	}

	
}
