package com.atmecs.testscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.ui.selenium.Click;
import com.atmecs.falcon.automation.ui.selenium.Find;
import com.atmecs.falcon.automation.ui.selenium.TextField;
import com.atmecs.falcon.automation.ui.selenium.Verify;
import com.atmecs.falcon.automation.ui.selenium.WaitElement;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.testsuite.PageObject;

public class Registration  extends PageObject {
	private TextField textField = null;
	private Click click = null;
	private Browser browser = null;
	private String browserName = null;
	private String browserVersion = null;
	private String os = null;
	private Find find;
	private Verify verify;
	private WaitElement wait = null;
	private String osVersion = null;
	private final ReportLogService logService = new ReportLogServiceImpl(Registration.class);

	@AfterMethod
	public void exit() {
		browser.closeBrowser();
	}

	@Parameters({ "browser", "browserVersion", "os", "osVersion" })
	@BeforeTest
	public void setup(String browserEnv, String browserVer, String os, String osVer) {
		System.out.println(" "+browserEnv+"  "+browserVer +"    " +os+ "   "+osVer);
		browserName = browserEnv;
		browserVersion = browserVer;
		this.os = os;
		osVersion = osVer;
		browser = new Browser();
		textField = browser.getTextField();
		click = browser.getClick();
		find = browser.getFindFromBrowser();
		verify = new Verify();
		verify.setFind(find);
	}

	
	@Test(dataProvider = "RegisterData",
			dataProviderClass = com.atmecs.testfunction.utils.DataProvider.class,priority=1)  
	public void register(String email,String firstName,String LastName,String Password,String Confirm_Password)
	{
		logService.info("Reading data from Register data excel file");
		logService.info("OpeningURL" +url);
		browser.openURL(url, os, osVersion, browserName, browserVersion);
		wait=browser.getWait();
		logService.info("INFO: Maximizing window");
		browser.maximizeWindow();
		RegisterUser(email, firstName, LastName, Password, Confirm_Password);
	}
	private void RegisterUser(String email,String firstName,String LastName,String Password,String Confirm_Password)
	{
		logService.info("Clicking Register link");
		click.performClick(LocatorType.XPATH, RegisterLinkXpath);
		VerificationManager.verifyString(find.findElementByXpath("//span[contains(text(),'Register for an Account')]").getText(), "Register for an Account", " Verify click on Register");
		wait.waitFor(LocatorType.XPATH, RegisterEmailXpath);
		logService.info("Entering data in Email field" +email);
		textField.enterTextField(LocatorType.XPATH, RegisterEmailXpath, email);
		wait.HardPause(1000L);
		logService.info("Entering data in First Name field" +firstName);
		textField.enterTextField(LocatorType.XPATH, RegisterfirstNameXpath, firstName);
		wait.HardPause(1000L);
		logService.info("Entering data in Last Name field" +LastName);
		textField.enterTextField(LocatorType.XPATH, RegisterLastNameXpath, LastName);
		wait.HardPause(1000L);
		logService.info("Entering data in Password field" +Password);
		textField.enterTextField(LocatorType.XPATH, RegisterPasswordXpath, Password);
		wait.HardPause(1000L);
		logService.info("Entering data in Confirm Password field" +Confirm_Password);
		textField.enterTextField(LocatorType.XPATH, RegisterConfirmPasswordXpath, Confirm_Password);
		browser.getPageScroll().down(500);
		wait.HardPause(1000L);
		logService.info("Click to register the user");
		click.performClick(LocatorType.XPATH, RegisterButtonXpath);  	
		boolean status = VerificationManager.verifyString(find.findElementByXpath("//span[@class='error']")
				.getText(), "That email address is already in use.", " Verify user register button clicked");
		VerificationManager.verifyTrueAndStopTest(status, "Account Verified");
	}


}
