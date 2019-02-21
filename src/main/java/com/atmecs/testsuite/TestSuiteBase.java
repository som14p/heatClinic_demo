package com.atmecs.testsuite;

import java.io.File;
import java.util.Properties;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.atmecs.falcon.automation.dataprovider.TestDataProvider;
import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.logging.LogLevel;
import com.atmecs.falcon.automation.util.logging.LogManager;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.testfunction.AirBNBCommonUtil;


public class TestSuiteBase {
	private static ReportLogService report = new ReportLogServiceImpl(TestSuiteBase.class);
	public Browser browserObj;
	public static Properties CONFIG, PAGEELEMENTS;
	public  TestDataProvider dataProvider;
	public static String basePath = System.getProperty("user.dir");
	public  AirBNBCommonUtil commonUtil = new AirBNBCommonUtil();
	public WebDriverWait stay;
	/**
	 * Purpose: This method is to load all configurations & page elements of application.
	 * 1. Load configurations list
	 * 2. Load all the page objects as properties
	 * 3. Create an instance for Browser class
	 * 4. Open URL
	 */
	
	@BeforeTest
	public void setup(){
		
		dataProvider = TestDataProvider.getInstance(); 
		try{
			CONFIG = dataProvider.loadProperties(basePath + File.separator + 
					"src" + File.separator + "main" + File.separator + "resources" +
					File.separator + "config.properties");
		}catch(Exception exception){
			report.debug("File not found" + exception.getMessage());
			throw new IllegalStateException();
		}
		LogManager.setLogLevel(LogLevel.INFO);
		
		
		
	}
	
	/**
	 * Purpose: This method is to close browser instance after every @Test
	 */
	@AfterTest
	public void tearDown(){
		browserObj.clearAllCookies();
		
		browserObj.closeBrowser();
	}
}
