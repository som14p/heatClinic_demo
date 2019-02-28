package com.atmecs.testscript;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.ui.selenium.Click;
import com.atmecs.falcon.automation.ui.selenium.Find;
import com.atmecs.falcon.automation.ui.selenium.Verify;
import com.atmecs.falcon.automation.ui.selenium.WaitElement;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.testsuite.PageObject;

public class TraversePages extends PageObject{
	
	    private Click click = null;
	    private Browser browser = null;
	    private String browserName = null;
	    private String browserVersion = null;
	    private String os = null;
	    private Find find;
		private Verify verify;
	    private WaitElement wait = null;
	    private String osVersion = null;
	    private final ReportLogService logService = new ReportLogServiceImpl(TraversePages.class);
	  	
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
			click = browser.getClick();
			find = browser.getFindFromBrowser();
			verify = new Verify();
			verify.setFind(find);
		}

	    @Test(priority=0)
	    public void traverseTheLinks()
	    {
	    	browser.openURL(url, os, osVersion, browserName, browserVersion);
	        wait = browser.getWait();
	       logService.info("INFO: Maximizing window ");
	       browser.maximizeWindow();
	        ClickOnTheLinks();
	        logService.info("Clicking on logout button");
	        click.performClick(LocatorType.XPATH, logoutXpath);
	        wait.waitFor(LocatorType.XPATH, logoutXpath);
	        logService.info("Logged out successfully");
	    }
	    private void ClickOnTheLinks()
	    {
	    	logService.info("Clicking on the Home Tab");
	    	click.performClick(LocatorType.XPATH,HomeXpath );
	    	wait.HardPause(2000L);
	    	VerificationManager.verifyString(browser.getCurrentURL(),
					"http://10.10.10.232:8080/", " Verify click on HOME page");
	    	click.performClick(LocatorType.XPATH, Hot_sauceXpath);
	    	VerificationManager.verifyString(find.findElementByXpath("//section[@id='left_column']//header//h1").getText(),"Viewing Hot Sauces (1 - 15 of 19)", " Verify click on Hot Sauces");
	    	
	    	Actions builder = mouseHover(MerchandiseXpath);
	    	wait.HardPause(5000L);
	    	builder.moveToElement(browser.getDriver().findElement(By.xpath(MenXpath))).click().perform();
	    	wait.HardPause(1000L);
	    	VerificationManager.verifyString(find.findElementByXpath("//section[@id='left_column']//header//h1").getText(),"Viewing Mens (1 - 3 of 3)", " Verify click on Merchandise Men");
	    		    	
	    	Actions builders=mouseHover(MerchandiseXpath);
	    	wait.HardPause(5000L);
	    	builders.moveToElement(browser.getDriver().findElement(By.xpath(WomenXpath))).click().perform();
	    	wait.HardPause(2000L);
	    	VerificationManager.verifyString(find.findElementByXpath("//section[@id='left_column']//header//h1").getText(),"Viewing Womens (1 - 3 of 3)", " Verify click on Merchandise Woman");
	    	
	    	click.performClick(LocatorType.XPATH, New_to_hot_sauceXpath);
	    	wait.HardPause(1000L);
	    	click.performClick(LocatorType.XPATH, FAQXpath);
	    }
		private Actions mouseHover(String elementXpath) {
			Actions builder = new Actions(browser.getDriver());
	    	builder.moveToElement(browser.getDriver().findElement(By.xpath(elementXpath))).perform();
			return builder;
		}

}
