/**
 * @author Kalyan,Raipati
 */
package com.sc.wrappers;

/**
 * @author Kalyan, Raipati
 * @since 20-Mar-2017
 */
public class WebPage extends Page {
	/**
	 * A constructor to initialize PageFactory for all child web pages
	 */
	public WebPage() {
		GenericWrappers.switchFromNativeToWebView();
	}
}
