package com.sc.pages;


import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sc.wrappers.GenericWrappers;
import com.sc.wrappers.NativePage;


public class HomePage extends NativePage {
	
	@FindBy(id = "logo")
	WebElement txtPageHeader;
	
	@FindBy(id = "search_box")
	WebElement SearchBox;
	
	@FindBy(id = "ItemsList")
	List<WebElement> ItemsLst;

	
	public boolean isOnHomePage() {
		GenericWrappers.takeScreenShotAndAddtoExtentReport();
		return txtPageHeader.isDisplayed();
		
	}
	
	public HomePage entertheProductinSearch(String SearchProduct) {
		SearchBox.sendKeys(SearchProduct);
		GenericWrappers.sleepInSeconds(5);
		GenericWrappers.enterUsingKeyBoard();
		return this;
	}
	
	public HomePage clickOn_firstItem() {
		ItemsLst.get(1).click();
		GenericWrappers.sleepInSeconds(5);
		return this;
	}


}
