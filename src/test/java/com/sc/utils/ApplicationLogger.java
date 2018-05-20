package com.sc.utils;

import org.apache.log4j.Logger;

public class ApplicationLogger {

	public static ReportLogger log = (ReportLogger) ReportLogger
			.getLogger(ApplicationLogger.class.getName());

}
