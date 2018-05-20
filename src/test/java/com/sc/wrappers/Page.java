/**
 * @author Kalyan,Raipati
 */
package com.sc.wrappers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.support.PageFactory;

/**
 * @author Kalyan, Raipati
 * @since 10-Mar-2017
 */
public class Page {
	/**
	 * A constructor to initialize PageFactory for all child pages
	 */
	public Page() {
		if (GenericWrappers.isAppiumDriver()) {
			PageFactory.initElements(new AppiumFieldDecorator(
					GenericWrappers.webDriver), this);

		} else {
			PageFactory.initElements(GenericWrappers.webDriver, this);
		}
	}

}
