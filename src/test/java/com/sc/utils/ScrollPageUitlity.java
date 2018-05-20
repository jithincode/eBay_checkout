package com.sc.utils;

import static com.sc.utils.ApplicationLogger.log;

import java.util.List;

import org.apache.xpath.operations.Number;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.sc.wrappers.GenericWrappers;

public class ScrollPageUitlity {
	private static Actions actions = new Actions(GenericWrappers.webDriver);

	public static void scrollToBottom() {
		((JavascriptExecutor) GenericWrappers.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public static void scrollToTop() {
		((JavascriptExecutor) GenericWrappers.webDriver)
				.executeScript("window.scrollTo(document.body.scrollHeight,0);");
	}

	public static void scrollToTop(WebElement element) {
		((JavascriptExecutor) GenericWrappers.webDriver).executeScript(
				"$(arguments[0]).scrollTop('0');", element);
	}

	public static void scrollByPage() {

		ScrollPageUitlity.scrollToBottom();
		int windowHeight = GenericWrappers.getWindowDimention().height;
		Long pageHeight = getHeightOfThePage();
		ScrollPageUitlity.scrollToTop();
		GenericWrappers.sleepInSeconds(2);
		while (pageHeight > 0) {
			int scrollHeight = (windowHeight - 150);
			((JavascriptExecutor) GenericWrappers.webDriver).executeScript(
					"window.scrollBy(0," + (scrollHeight) + ")", "");
			pageHeight = pageHeight - (scrollHeight);
			System.out.println("current height" + pageHeight);
			GenericWrappers.sleepInSeconds(5);

		}
	}

	public static void scrollByPageAndTakeScreenShot() {
		try {
			ScrollPageUitlity.scrollToBottom();
			int windowHeight = GenericWrappers.getWindowDimention().height;
			Long pageHeight = getHeightOfThePage();
			ScrollPageUitlity.scrollToTop();
			GenericWrappers.sleepInSeconds(2);
			while (pageHeight > 0) {
				GenericWrappers.takeScreenShotAndAddtoExtentReport(false);
				int scrollHeight = (windowHeight - 150);
				((JavascriptExecutor) GenericWrappers.webDriver).executeScript(
						"window.scrollBy(0," + (scrollHeight) + ")", "");
				pageHeight = pageHeight - (scrollHeight);
			}
			ScrollPageUitlity.scrollToBottom();
			GenericWrappers.takeScreenShotAndAddtoExtentReport(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void scrollByPageAndTakeScreenShot(WebElement element) {
		try {
			scrollElemenetByOffset(element, Integer.MAX_VALUE);
			int elementHeight = getElementScrollHeight(element);
			int windowHeight = GenericWrappers.getWindowDimention().getHeight();
			//log.debug("Scroll Element Height: " + elementHeight);
			int scrollHeight = 0;
			log.debug("Window Height: " + windowHeight);
			ScrollPageUitlity.scrollToTop(element);
			do {
				GenericWrappers.takeScreenShotAndAddtoExtentReport();
				GenericWrappers.sleepInSeconds(2);
				scrollHeight = scrollHeight + (windowHeight-150);
				scrollElemenetByOffset(element, scrollHeight);
				elementHeight -= windowHeight-150;
				log.debug(elementHeight);
			} while (elementHeight > 150);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void scrollByElementAndTakeScreenShot(List<WebElement> element) {
		
		element.forEach(x -> { GenericWrappers.scrolltoViewElement(x);
		GenericWrappers.takeScreenShotAndAddtoExtentReport();});
		
	}
	
	public static void scrollElemenetByOffset(WebElement element,
			int scrollHeight) {
		//log.debug("Scroll Height" + scrollHeight);
		((JavascriptExecutor) GenericWrappers.webDriver)
				.executeScript("$(arguments[0]).scrollTop('" + (scrollHeight)
						+ "');", element);

	}

	public static int getElementScrollHeight(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) GenericWrappers.webDriver;
		Long value = (Long) executor.executeScript(
				"return arguments[0].scrollHeight;", element);
		return value.intValue();
	}

	public static Long getHeightOfThePage() {
		JavascriptExecutor executor = (JavascriptExecutor) GenericWrappers.webDriver;
		Long value = (Long) executor
				.executeScript("return window.pageYOffset;");
		return value;
	}

}
