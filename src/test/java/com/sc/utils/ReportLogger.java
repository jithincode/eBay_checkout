package com.sc.utils;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.cucumber.listener.Reporter;

public class ReportLogger extends Logger {
	protected ReportLogger(String name) {
		super(name);
	}

	static String FQCN = ReportLogger.class.getName() + ".";
	private static AppLoggerFactory myFactory = new AppLoggerFactory();

	public void debug(Object message) {
		super.log(FQCN, Level.DEBUG, message, null);
	}

	public void error(Object message) {
		super.log(FQCN, Level.ERROR, message, null);
		message = message.toString().contains("<table>") ? "[ERROR]: "
				+ message.toString() : "<div><font color=\"red\">" + "[ERROR]: "
				+ message.toString() + "</font></div>";
		Reporter.addStepLog(message.toString());
	}

	public void info(Object message) {
		super.log(FQCN, Level.INFO, message, null);
		Reporter.addStepLog("<div style=\"white-space: pre-wrap;overflow-x: auto\">[INFO]: "
				+ message.toString() + "</div>");
	}

	public static Category getInstance(String name) {
		return Logger.getLogger(name, myFactory);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name, myFactory);
	}

	public void trace(Object message) {
		super.log(FQCN, Level.TRACE, message, null);
	}
}

class AppLoggerFactory implements LoggerFactory {

	public AppLoggerFactory() {
	}

	public Logger makeNewLoggerInstance(String name) {
		return new ReportLogger(name);
	}
}