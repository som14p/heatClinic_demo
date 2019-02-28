package com.atmecs.testscript;

import org.openqa.selenium.By;
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

public class InvalidLogin extends PageObject {

	private TextField textField = null;
	private Click click = null;
	private Browser browser = null;
	private String browserName = null;
	private String browserVersion = null;
	private String os = null;
	private WaitElement wait = null;
	private String osVersion = null;
	private Find find;
	private Verify verify;
	private final ReportLogService logService = new ReportLogServiceImpl(InvalidLogin.class);

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
		find = browser.getFindFromBrowser();
		verify = new Verify();
		verify.setFind(find);
		textField = browser.getTextField();
		click = browser.getClick();
	}


	@Test(dataProvider = "loginData2",
			dataProviderClass = com.atmecs.testfunction.utils.DataProvider.class,priority=2)
	public void loginInvalid(String email, String password, String name) {
		logService.info("INFO: Reading data from Excel file");
		logService.info("INFO: Opening url " + url);
		browser.openURL(url, os, osVersion, browserName, browserVersion);
		wait = browser.getWait();
		logService.info("INFO: Maximizing window ");
		browser.maximizeWindow();
		wait.waitFor(LocatorType.XPATH, loginLinkXpath);
		accountLogin(email, password, name);
		updateAccount(name);
		addAndRemoveFromCart();
		logService.info("Clicking on logout button");
		wait.waitFor(LocatorType.XPATH, logoutXpath);
		click.performClick(LocatorType.XPATH, logoutXpath);
		logService.info("Logged out successfully");
	}
	
	private void accountLogin(String email, String password, String name) {
		logService.info("INFO: Clicking login link ");
		click.performClick(LocatorType.XPATH, loginLinkXpath);
		wait.waitFor(LocatorType.XPATH, emailXpath);
		logService.info("INFO: Entering invalid email ID in email field " + email);
		textField.enterTextField(LocatorType.XPATH, emailXpath, email);
		logService.info("INFO: Entering data in password field ");
		textField.enterTextField(LocatorType.XPATH, passwordXpath, password);
		logService.info("INFO: Clicking login button");
		click.performClick(LocatorType.XPATH, loginButtonXpath);
		browser.getWait().waitFor(LocatorType.XPATH, nameXpath);
		boolean status = VerificationManager.verifyString(find.findElementByXpath(nameXpath)
				.getText(), name, "Verifying username");
		VerificationManager.verifyTrueAndStopTest(status, " Account Verified");
	}
	
	private void updateAccount(String name) {
		logService.info("Clicking Account Name " + name);
		click.performClick(LocatorType.XPATH, nameXpath);
		logService.info("Clicking on Submit button in Account Information");
		wait.waitFor(LocatorType.XPATH, accountInfoSubmitXpath);
		click.performClick(LocatorType.XPATH, accountInfoSubmitXpath);
		wait.waitFor(LocatorType.XPATH, submitSuccessXpath);
		logService.info("Verifying account updation");
		boolean statusUpdateAccount = VerificationManager.verifyString(find.findElementByXpath(
				submitSuccessXpath).getText(), " Account successfully updated",
				"Verifying Account Updation");
		VerificationManager.verifyTrueAndContinueTest(statusUpdateAccount, "Account updated");
	}

	private void addAndRemoveFromCart() {
		logService.info("Clicking on Hot Sauces Tab");
		click.performClick(LocatorType.XPATH, hotSaucesTabXapth);
		logService.info("Clicking on Sauce");
		wait.waitFor(LocatorType.XPATH, imageSaucesXpath);
		click.performClick(LocatorType.XPATH, imageSaucesXpath);
		wait.HardPause(2000L);
		boolean buyRedButtonStatus = browser.getDriver().findElement(By.xpath(buySaucesButtonXpath))
				.isDisplayed();
		if (buyRedButtonStatus) {
			logService.info("Clicking on buy button");
			wait.waitFor(LocatorType.XPATH, buySaucesButtonXpath);
			click.performClick(LocatorType.XPATH, buySaucesButtonXpath);
			logService.info("Checking cart item");
			wait.HardPause(1000L);
			wait.waitFor(LocatorType.XPATH, cartImageXpath);
			click.performClick(LocatorType.XPATH, cartImageXpath);
		} else {
			wait.waitFor(LocatorType.XPATH, inCartButtonXpath);
			click.performClick(LocatorType.XPATH, inCartButtonXpath);
			logService.info("item already added removing");
		}

		logService.info("Verifying item in cart");
		wait.waitFor(LocatorType.XPATH, cartItemXpath);
		VerificationManager.verifyString(find.findElementByXpath(cartItemXpath).getText(),
				"Roasted Garlic Hot Sauce", "Cart Item");
		// VerificationManager.verifyTrueAndStopTest(cartItemStatus, "Item Found");
		logService.info("Removing Cart Item");
		click.performClick(LocatorType.XPATH, removeCartItemXpath);
		logService.info("Item Removed Successfully");
		logService.info("Closeing Cart Window");
		click.performClick(LocatorType.XPATH, closeCartModalXpath);
	}


}
