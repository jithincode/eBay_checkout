package com.sc.baserunner;

import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.sc.wrappers.GenericWrappers;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "." }, glue = { "com.sc.glue" }, tags = {
		"@EbayLogin","@EbayLoginAndSearchProduct","@EbayLoginAndCheckOutProduct"}, plugin = {     
		"com.cucumber.listener.ExtentCucumberFormatter:",
		"rerun:target/iPadRerun.txt" }, monochrome = true, dryRun = false)

public class AndroidRealdevice {
	@BeforeClass
	public static void setUp() throws MalformedURLException {
		try {
			GenericWrappers.setStartTime();
			GenericWrappers.setDriver("Android");
			System.setProperty("cucumberReportPath",
					GenericWrappers.getReportPath());
			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			extentProperties.setReportPath(GenericWrappers.getReportPath());
			if (Boolean.parseBoolean(GenericWrappers
					.properties("EnableExtentx"))) {
				extentProperties.setExtentXServerUrl("http://localhost:1337");
				extentProperties.setProjectName("RTOB");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		GenericWrappers.closeApp();
		GenericWrappers.webDriver = null;
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("OS", System.getProperty("os.name"));
		Reporter.setSystemInfo("Device", "iPad");
		Reporter.setTestRunnerOutput("RTOB Automation Report");
	}
}
