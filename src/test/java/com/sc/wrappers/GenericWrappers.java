package com.sc.wrappers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import static com.sc.utils.ApplicationLogger.log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import com.cucumber.listener.Reporter;
import com.sc.utils.ScrollPageUitlity;
import cucumber.api.Scenario;

public class GenericWrappers {
	public static AndroidDriver<WebElement> webDriver;
	public static WebDriverWait wait;
	public static String screenShotPath;
	public static String rcwlogpath;
	public static boolean isAppClosed;

	/**
	 * This method will start the appium server
	 * 
	 * @throws MalformedURLException
	 */
	
	static Dimension size;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void startAndroidServer() throws MalformedURLException {

		log.debug("Init of server");
		if ((webDriver == null) || (webDriver != null & !(isAppiumDriver()))) {
			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				 File appDir = new File(System.getProperty("user.dir"));
				  File app = new File(appDir,properties("AppPath"));  
				  
				if (properties("Isrealdevice").equalsIgnoreCase("Yes")) {
					cap.setCapability("device","Android");
					//mandatory capabilities
					cap.setCapability("deviceName","Redmi");
					cap.setCapability("platformName",properties("Platform"));
					cap.setCapability("platformVersion",properties("Version"));
					cap.setCapability("automationName","Ebay Product Checkout");
					cap.setCapability("appPackage", properties("Bundleid"));
					cap.setCapability("appActivity", properties("AppActivity"));
					cap.setCapability("app", app.getAbsolutePath());
				} 
				cap.setCapability("noReset", true);
				log.debug(cap);

				webDriver = new AndroidDriver(new URL(
						properties("AppiumURL") + "/wd/hub"), cap);
				webDriver.manage().timeouts()
						.implicitlyWait(5, TimeUnit.SECONDS);
				log.debug("Application launched successfully");
				isAppClosed = false;

			} catch (Exception e) {
				e.printStackTrace();
				log.debug("issue in driver creation");
			}
		} else if (webDriver != null && isAppiumDriver()) {
			if (isAppClosed)
				launchApp();
		}

	}
	
	public static void enterUsingKeyBoard() {
		webDriver.pressKeyCode(66);
	}
	
	public static void swipingVerticalTopToBottum() throws InterruptedException {
		  //Get the size of screen.
		  size = webDriver.manage().window().getSize();
		  System.out.println(size);
		
		  int starty = (int) (size.height * 0.80);
		  //Find endy point which is at top side of screen.
		  int endy = (int) (size.height * 0.20);
		  //Find horizontal point where you wants to swipe. It is in middle of screen width.
		  int startx = size.width / 2;
		  System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);

		  //Swipe from Bottom to Top.
		  webDriver.swipe(startx, starty, startx, endy, 3000);
		  Thread.sleep(2000);
		 }

	
	

	

	public static String properties(String key) {
		Properties prob = null;
		File file = new File("./BuildDetails.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prob = new Properties();
		try {
			prob.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prob.getProperty(key);
	}

	

	public static void scrolltoViewElement(WebElement element) {
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoViewIfNeeded(true);", element);
				//"arguments[0].scrollIntoView(true);", element);
	}

	public static WebElement getElementByTextWithScroll(
			List<WebElement> elements, String value) {
		WebElement returnElement = null;
		for (WebElement element : elements) {
			scrolltoViewElement(element);
			if (element.getText().contains(value)) {
				log.debug(element.getText());
				returnElement = element;
				break;
			}
		}
		return returnElement;
	}
	
	public static void switchWindow()
			{
			Set<String> Window=webDriver.getWindowHandles();
				 webDriver.switchTo().window((String) Window.toArray()[1]);
			}
	
	
	public static void jsClickOnElement(WebElement element) {
		// waitForElementToBeClickable(element, 2);
		try {
			((JavascriptExecutor) webDriver).executeScript(
					"arguments[0].click();", element);
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			jsClickOnElement(element);
		} catch (Exception e) {
			e.printStackTrace();
			element.click();
		}
	}

	public static boolean jsGetChekedValueOnElement(WebElement element) {
		boolean checked = false;
		try {
			checked = (boolean) ((JavascriptExecutor) webDriver).executeScript(
					"arguments[0].checked;", element);
			log.debug("the checked value is " + checked);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("error on chekbox");
		}
		return checked;
	}

	public static String jsGetTextoFElement(WebElement element) {
		return (String) ((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].innerHTML;", element);
	}

	public static String getTextOnElement(WebElement element) {
		return  element.getText();
	}
	
	public void captureScreenShotAndAddToGeneiReport(Scenario scenario) {

		String scenarioName = scenario.getName();
		if (scenario.isFailed()) {

			try {

				File scrFile = ((TakesScreenshot) webDriver)
						.getScreenshotAs(OutputType.FILE);
				String Filename = (screenShotPath + scenarioName + ".png")
						.replaceAll(" ", "_");
				System.out.println("Screen shot saved in location " + Filename);
				try {
					FileUtils.copyFile(scrFile, new File(Filename));
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Screeshot asserted in HTML " + Filename);
				sleepInMilliSeconds(2000);
				// addImageFilePath(Filename);

			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots
						.getMessage());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void closeAppiumAndBrowser() {
		try {

			if (webDriver instanceof AppiumDriver<?>) {
				((AppiumDriver<WebElement>) webDriver).closeApp();
			} else
				webDriver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public WebElement getWebelElement(String locators) {
		WebElement ele = null;
		String[] loc = locators.split("`");
		switch (loc[0]) {
		case "ByName":
			ele = webDriver.findElement(By.name(loc[1]));
			break;
		case "FByName":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByName(loc[1]);
			break;
		case "FByClassName":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByClassName(loc[1]);
			break;
		case "FByXpath":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByXPath(loc[1]);
			break;

		default:
			break;
		}
		return ele;
	}

	
	public void enterUsingKeyBoard(Keyboard key, String value) {
		key.pressKey(value);

	}
	
	
	/**
	 * This method will wait for the presence of the element to be located
	 * 
	 * @param locator
	 */
	public void waitForpresenceOfElementLocated(By locator) {
		wait = new WebDriverWait(webDriver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This method will wait for the presence of the element to be located
	 * 
	 * @param element
	 */
	public void waitForpresenceOfElement(WebElement element) {
		wait = new WebDriverWait(webDriver, 100);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method will wait for the presence of the element to be located
	 * 
	 * @param element
	 */
	public static void waitForVisibilityOfElement(WebElement element) {
		wait = new WebDriverWait(webDriver, 100);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method will wait for the presence of the element to be located
	 * returns boolean
	 * 
	 * @param element
	 * @param waitTimeInSec
	 * @return boolean
	 */
	public static boolean waitForVisibilityOfElement(WebElement element,
			int waitTimeInSec) {
		WebElement visibleElement;
		wait = new WebDriverWait(webDriver, waitTimeInSec);
		try {
			visibleElement = wait.until(ExpectedConditions
					.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return visibleElement != null ? true : false;
	}

	public static boolean waitForPageWithTitle(String title, int waitTimeInSec) {
		Boolean isOnPage;
		try {
			wait = new WebDriverWait(webDriver, waitTimeInSec);
			return wait.until(ExpectedConditions.titleContains(title));
			
		} catch (Exception e) {
			e.printStackTrace();
			isOnPage = false;
			// return false;
		}
		return isOnPage;
	}

	public static boolean waitForPageWithUrl(String title, int waitTimeInSec) {

		Boolean isOnPage;

		try {
			wait = new WebDriverWait(webDriver, waitTimeInSec);
			return wait.until(ExpectedConditions.urlContains(title));
			
		} catch (Exception e) {
			e.printStackTrace();
			isOnPage = false;
			// return false;
		}
		return isOnPage;
	}
	/**
	 * This method will wait for the element to be clickable returns boolean
	 * 
	 * @param element
	 * @param waitTimeInSec
	 * @return boolean
	 */
	public static boolean waitForElementToBeClickable(WebElement element,
			int waitTimeInSec) {
		WebElement visibleElement;
		wait = new WebDriverWait(webDriver, waitTimeInSec);
		try {
			visibleElement = wait.until(ExpectedConditions
					.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return visibleElement != null ? true : false;
	}

	
	public static boolean waitForInVisibilityOfElement(WebElement element,
			int waitTimeInSec) {
		boolean isVisible = false;
		List<WebElement> elements = new ArrayList<>();
		elements.add(element);
		wait = new WebDriverWait(webDriver, waitTimeInSec);
		try {
			isVisible = wait.until(ExpectedConditions
					.invisibilityOfAllElements(elements));
		} catch (Exception e) {
			//e.printStackTrace();
			return isVisible;
		}

		// WebDriverWait wait = new WebDriverWait(webDriver, 30);
		// Function<WebDriver, Boolean> myFunction = new Function<WebDriver,
		// Boolean>()
		// { public Boolean apply(WebDriver arg0) {
		// System.out.println("Checking for the object!!");
		// WebElement element = arg0.findElement(By.id("dynamicText"));
		// if (element != null) {
		// System.out.println("A new dynamic object is found."); } return true;
		// }};
		// wait.until(myFunction);

		return isVisible;

	}

	/**
	 * This method will wait for the invisibility of the Element located
	 * 
	 * @param locator
	 */
	public void waitForinvisiblityOfElementLocated(By locator) {
		wait = new WebDriverWait(webDriver, 100);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

	}

	/**
	 * This method will wait for the visibility of the given cssSelector web
	 * Element
	 * 
	 * @param locator
	 */
	public void waitForvisiblityOfGivenCssselector(String locator) {
		wait = new WebDriverWait(webDriver, 100);
		wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By
				.cssSelector("." + locator))));

	}

	/**
	 * This method will wait for the visibility of the given WebElement
	 * 
	 * @param ele
	 */
	public void waitForvisiblityOfGivenElement(WebElement ele) {
		wait = new WebDriverWait(webDriver, 180);
		wait.until(ExpectedConditions.visibilityOf(ele));

	}

	/**
	 * This method will switch From Native to WebView
	 */
	@SuppressWarnings("unchecked")
	public static void switchFromNativeToWebView() {
		if (isAppiumDriver()) {
			if (!((AppiumDriver<WebElement>) webDriver).getContext().contains(
					"NAT")) {
				return;
			}
			Set<String> AvailableContexts = ((AppiumDriver<WebElement>) webDriver)
					.getContextHandles();
			for (String context : AvailableContexts) {
				if (context.contains("WEBVIEW"))
					((AppiumDriver<WebElement>) webDriver).context(context);
			}
		}
	}

	/**
	 * This method will switch the control to the Native App
	 */
	@SuppressWarnings("unchecked")
	public static void switchToNativeApp() {

		if (isAppiumDriver()) {
			if (((AppiumDriver<WebElement>) webDriver).getContext().contains(
					"NATIVE_APP")) {
				return;
			}
			Set<String> AvailableContexts = ((AppiumDriver<?>) webDriver)
					.getContextHandles();
			for (String context : AvailableContexts) {
				// log.debug("Available context" + context);
				if (context.contains("NATIVE_APP"))
					((AppiumDriver<WebElement>) webDriver).context(context);
			}
		}
	}

	/**
	 * This method will return the webelemnt with locator ById
	 * 
	 * @param id
	 * @return
	 */
	public WebElement getWebElementById(String id) {
		return webDriver.findElement(By.id(id));
	}

	/**
	 * This method will return the webelement with locator ByXpath
	 * 
	 * @param id
	 * @return
	 */
	public static WebElement getWebElementByXpath(String xpath) {
		return webDriver.findElement(By.xpath(xpath));
	}

	public void switchElementToClick(WebElement ele, String data) {
		if (data.contains("Yes")) {
			ele.click();
		}

	}

	public void selectElementFromDropdown(WebElement ele, String data) {
		ele.clear();
		ele.sendKeys(data);

	}

	public void enterText(WebElement ele, String data) {
		// ele.clear();
		ele.sendKeys(data);
	}

	
	public static void hideKeyboardOnWebView() {
		switchToNativeApp();
		try {
			WebElement keyboard = webDriver.findElement(By
					.name("Hide keyboard"));
			keyboard.click();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		switchFromNativeToWebView();
	}

	public static void waitBeforeExecute() {

		int seconds = 3;
		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < seconds * 1000);
	}

	public static void sleepInSeconds(int seconds) {

		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < seconds * 1000);
	}

	public static void sleepInMilliSeconds(long milliseconds) {

		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < milliseconds);
	}

	public static void closeApp() {
		if (webDriver instanceof AppiumDriver)
			((AppiumDriver) GenericWrappers.webDriver).closeApp();
	}

	
	public static void launchApp() {
		if (webDriver instanceof AppiumDriver) {
			if (isAppClosed)
				((AppiumDriver) GenericWrappers.webDriver).launchApp();
		}
	}

	
	public static boolean isAppiumDriver() {
		return (webDriver instanceof AppiumDriver);
	}

	
	public static void quitBrowser() {
		webDriver.quit();
	}

	public static void quitApp() {
		((AppiumDriver) webDriver).quit();
	}

	
	private static void swipeInIpadSettingPanel() {

		int height = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getHeight();
		// log.debug(height);
		int width = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getWidth();
		// log.debug(height);
		((AppiumDriver) webDriver).swipe(width - 100, height, width - 100,
				height - 200, 500);
		sleepInMilliSeconds(100);
	}

	
	private static void swipeOffIpadSettingPanel() {
		int height = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getHeight();
		int width = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getWidth();
		((AppiumDriver) webDriver).tap(1, height / 2, width / 2, 1);
		sleepInMilliSeconds(100);
	}

	
	private static boolean isWifiEnabled() {
		int value = Integer.parseInt(((AppiumDriver<?>) webDriver)
				.findElementByAccessibilityId("Wi-Fi").getAttribute("value"));
		return value == 1 ? true : false;
	}

	
	private boolean isIpadWifiEnabledWithSwipe() {
		swipeInIpadSettingPanel();
		int value = Integer.parseInt(((AppiumDriver<?>) webDriver)
				.findElementByAccessibilityId("Wi-Fi").getAttribute("value"));
		swipeOffIpadSettingPanel();
		return value == 1 ? true : false;
	}

	
	public static void turnOnIpadWifi() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (!isWifiEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId("Wi-Fi")
					.click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}

	
	public static void turnOffIpadWifi() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (isWifiEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId("Wi-Fi")
					.click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}

	
	private static boolean isBluetoothEnabled() {
		int value = Integer.parseInt(((AppiumDriver<?>) webDriver)
				.findElementByAccessibilityId("Bluetooth")
				.getAttribute("value"));
		return value == 1 ? true : false;
	}

	
	private static boolean isIpadBluetoothEnabledWithSwipe() {
		swipeInIpadSettingPanel();
		int value = Integer.parseInt(((AppiumDriver<?>) webDriver)
				.findElementByAccessibilityId("Bluetooth")
				.getAttribute("value"));
		swipeOffIpadSettingPanel();
		return value == 1 ? true : false;
	}

	
	public static void turnOnIpadBluetooth() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (!isBluetoothEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId(
					"Bluetooth").click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}

	
	public static void turnOffIpadBluetooth() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (isBluetoothEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId(
					"Bluetooth").click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}

	
	public static WebElement getElementByText(List<WebElement> elements,
			String value) {
//		GenericWrappers.sleepInSeconds(2);
		GenericWrappers.sleepInMilliSeconds(100);
		WebElement returnElement = null;
		for (WebElement element : elements) {
			//log.info(element.getText().toLowerCase());
			if (element.getText().toLowerCase().contains(value.toLowerCase())) {  // commented as LOV selection happening as "British Indian Ocean Territory" in iPad
				returnElement = element;
				break;
			}
		}
		return returnElement;
	}

	public static WebElement getLOVElementByText(String value, WebElement ele) {
		WebElement returnElement = null;
		for (WebElement element : ele
				.findElements(By
				// .cssSelector(".ui-select-choices-row-inner"))) {
				// .xpath(".//div[@class='option']"))){
						.xpath("./../following-sibling::*//div[contains(@class,'option')]"))) {
			if (element.getText().toLowerCase().contains(value.toLowerCase())) {
				returnElement = element;
				break;
			}
		}
		return returnElement;
	}

	public static WebElement getChildByLocator(WebElement element, By locator) {
		return element.findElement(locator);
	}

	
	public static List<String> getTextOfAllElements(List<WebElement> elements) {
		List<String> textList = new ArrayList<>();
		for (WebElement element : elements) {
			textList.add(element.getText().trim());
		}
		return textList;
	}

	public static void setDriver(String driver) throws MalformedURLException {

		switch (driver) {
		
		//We can add multiple drivers if we are delaing with in Switch case
		case "Android":
			startAndroidServer();
			break;


		default:
			break;
		}

	}

	public static boolean isNotEmpty(String s) {
		return Objects.nonNull(s) && !s.isEmpty();
	}

	public static Dimension getWindowDimention() {
		return webDriver.manage().window().getSize();
	}

	public static Dimension getElementDimention(WebElement element) {
		return element.getSize();
	}

	public static String getCheckboxValueOnElement(WebElement element) {
		return element.isSelected() ? "Yes" : "No";
	}

	public static String getFilePathOfScreenShot() {

		// if (Objects.isNull(extentReportFolder))
		// setScreenshotPath();

		String fileName = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSSa")
				.format(new Date());
		File scrFile = ((TakesScreenshot) webDriver)
				.getScreenshotAs(OutputType.FILE);

		String filename = screenShotPath;
		filename = filename + "/" + fileName.replaceAll(" ", "_") + ".png";
		String fileNameReport = fileName.replaceAll(" ", "_") + ".png";

		//log.debug(fileNameReport);
		log.debug(filename);
		try {
			FileUtils.copyFile(scrFile, new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileNameReport = Boolean.parseBoolean(GenericWrappers
				.properties("EnableExtentx")) ? filename : fileNameReport;
		return fileNameReport;
	}

	public static void takeScreenShotAndAddtoExtentReport(boolean isFullPage) {

		try {
			if ("Yes".equalsIgnoreCase(properties("EnableScreenShot"))) {
				if (isFullPage) {
					ScrollPageUitlity.scrollByPageAndTakeScreenShot();
				} else {
					// Reporter.addScreenCaptureFromPath(getFilePathOfScreenShot());
					Reporter.addScreenCaptureFromPath(getFilePathOfScreenShot());
				}
			}
		} catch (IOException e) {
			log.error("Issue in adding screenshot");
			e.printStackTrace();
		}

	}

	public static void takeScreenShotAndAddtoExtentReport() {

		try {
			if ("Yes".equalsIgnoreCase(properties("EnableScreenShot"))) {
				Reporter.addScreenCaptureFromPath(getFilePathOfScreenShot());
			}

		} catch (IOException e) {
			log.error("Issue in adding screenshot");
			e.printStackTrace();
		}

	}
	public static void takeScreenShotAndAddtoExtentReport(WebElement element) {

		if ("Yes".equalsIgnoreCase(properties("EnableScreenShot"))) {
			ScrollPageUitlity.scrollByPageAndTakeScreenShot(element);
		}
	}

	public static void takeScreenShotAndAddtoExtentReportByPage(List<WebElement> element) {
		if ("Yes".equalsIgnoreCase(properties("EnableScreenShot"))) {
			ScrollPageUitlity.scrollByElementAndTakeScreenShot(element);
		}
	}
	
	public static String getReportPath() {

		String reportDate = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSS-a")
				.format(new Date());
		
		screenShotPath = "Reports/report" ;
		File oldReport=new File(screenShotPath);
		File newReport=new File(screenShotPath+reportDate);
		oldReport.renameTo(newReport);
		String reportPath = screenShotPath + "/RTOBAutomationReport.html";
		return reportPath; 

	/*	screenShotPath = "reports/" + reportDate;
		String reportPath = "reports/" + reportDate
				+ "/RTOBAutomationReport.html";

		return reportPath; */

	}

	public static String getFullPageScreenshotPath() {

		Screenshot scrFile = new AShot().shootingStrategy(
				ShootingStrategies.viewportRetina(500, 0, 0, 2))
				.takeScreenshot(webDriver);
		String fileName = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSSa")
				.format(new Date());
		String filename = screenShotPath;
		filename = filename + "/" + fileName.replaceAll(" ", "_") + ".png";
		String fileNameReport = fileName.replaceAll(" ", "_") + ".png";

		log.debug(fileNameReport);
		log.debug(filename);
		try {
			ImageIO.write(scrFile.getImage(), "PNG", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileNameReport;
	}

	public static String getFullPageScreenshotPath(WebElement element) {
		Screenshot scrFile = null;
		scrFile = new AShot()
				.coordsProvider(new WebDriverCoordsProvider())
				.shootingStrategy(
						ShootingStrategies.viewportRetina(5000, 0, 0, 2))
				.takeScreenshot(webDriver, element);

		String fileName = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSSa")
				.format(new Date());
		String filename = screenShotPath;
		filename = filename + "/" + fileName.replaceAll(" ", "_") + ".png";
		String fileNameReport = fileName.replaceAll(" ", "_") + ".png";

		log.debug(fileNameReport);
		log.debug(filename);
		try {
			ImageIO.write(scrFile.getImage(), "PNG", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileNameReport = Boolean.parseBoolean(GenericWrappers
				.properties("EnableExtentx")) ? filename : fileNameReport;
		return fileNameReport;
	}

	public static void setStartTime() {
		System.setProperty("current.date.time", new SimpleDateFormat(
				"dd-MM-yyyy-hh-mm-ssa").format(new Date()));
	}

	public static boolean isPresentAndDisplayed(final WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static boolean ArePresentAndDisplayed(final List<WebElement> elements) {
		boolean isDisplayed = false;
		try {
			for (WebElement element : elements)
			{
				isDisplayed = element.isDisplayed();
				log.debug("Is dropdown displayed ? "+ element.isDisplayed());
				if(isDisplayed==false)
				{
					log.debug("element is not displayed.."+element);
					break;
				}
			}
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
			
	public static void sendKeysOnMobile(String keysToSend) {
		((AppiumDriver<WebElement>) GenericWrappers.webDriver).getKeyboard()
				.sendKeys(keysToSend);
	}

	public static void seconds(int i) {
		// TODO Auto-generated method stub

	}
	public static void drawOnCanvas(WebElement element) {
		// Actions builder = new Actions(webDriver);
		// builder.moveToElement(element, 135, 15) // start point in the canvas
		// .click().moveByOffset(200, 60) // second point
		// .click().moveByOffset(100, 70) // third point
		// .doubleClick().build().perform();

		TouchAction action = new TouchAction(
				(AppiumDriver<WebElement>) webDriver);
		action.longPress(element, 135, 15).waitAction(10).moveTo(100, 70)
				.release().perform();

	}

	
	
	public static String getProductName(WebElement product)
	{
		String productName;
		String xpathProductName = "./../../preceding-sibling::td/span";
		productName = product.findElement(By.xpath(xpathProductName)).getText();
		return productName;
	}
	
	public static WebElement getApplyBtnFromProductName(WebElement productName)
	{
		WebElement ApplyBtn;
		String xpathProductApplyBtn = "./../following-sibling::td/span/input";
		ApplyBtn = productName.findElement(By.xpath(xpathProductApplyBtn));
		return ApplyBtn;
	}
	public static List<WebElement> getWebElementsByXpath(String locators) {
		return webDriver.findElements(By.xpath(locators));
	}

	public static boolean waitForElementToBeEnabled(WebElement submit, int i) {
		// TODO Auto-generated method stub
		return false;
	}
}
