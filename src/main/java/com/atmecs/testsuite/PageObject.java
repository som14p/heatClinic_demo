/***
 *
 */
package com.atmecs.testsuite;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.atmecs.falcon.automation.util.logging.LogLevel;
import com.atmecs.falcon.automation.util.logging.LogManager;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.testfunction.utils.PathLocator;

public class PageObject {

    protected static String emailXpath = null;
    protected static String loginLinkXpath = null;
    protected static String passwordXpath = null;
    protected static String logoutXpath = null;
    protected static String loginButtonXpath = null;
    protected static String nameXpath = null;
    protected static String accountInfoSubmitXpath = null;
    protected static String submitSuccessXpath = null;
    protected static String hotSaucesTabXapth = null;
    protected static String findSaucesXpath = null;
    protected static String imageSaucesXpath = null;
    protected static String buySaucesButtonXpath = null;
    protected static String inCartButtonXpath = null;
    protected static String cartImageXpath = null;
    protected static String cartItemXpath = null;
    protected static String removeCartItemXpath = null;
    protected static String closeCartModalXpath = null;
    protected static String url = null;
    protected static String HomeXpath=null;
    protected static String Hot_sauceXpath=null;
    protected static String MerchandiseXpath=null;
    protected static String MenXpath=null;
    protected static String WomenXpath=null;
    protected static String ClearenceXpath=null;
    protected static String New_to_hot_sauceXpath=null;
    protected static String FAQXpath=null;
    protected static String RegisterLinkXpath=null;
    protected static String RegisterEmailXpath=null;
    protected static String RegisterfirstNameXpath=null;
    protected static String RegisterLastNameXpath=null;
    protected static String RegisterPasswordXpath=null;
    protected static String RegisterConfirmPasswordXpath=null;
    protected static String RegisterButtonXpath=null;
    
    protected PathLocator pathLocator = new PathLocator();
    
    protected Properties configProperty = null;
    protected Properties xpathProperty = null;
   
    private final ReportLogService logService = new ReportLogServiceImpl(PageObject.class);

    @BeforeSuite
    public void preSetup() {
        try {
            configProperty = new PropertyParser().loadProperty(pathLocator.getSrcMainResourcePath(
                    "config.properties"));
            xpathProperty = new PropertyParser().loadProperty(pathLocator.getSrcMainResourcePath(
                    "xpath.properties"));
            emailXpath = xpathProperty.getProperty("email");
            loginLinkXpath = xpathProperty.getProperty("loginlink");
            logoutXpath = xpathProperty.getProperty("logout");
            loginButtonXpath = xpathProperty.getProperty("loginbutton");
            nameXpath = xpathProperty.getProperty("name");
            passwordXpath = xpathProperty.getProperty("password");
            accountInfoSubmitXpath = xpathProperty.getProperty("accountsubmit");
            submitSuccessXpath = xpathProperty.getProperty("submitsuccess");
            hotSaucesTabXapth = xpathProperty.getProperty("hotsaucestab");
            findSaucesXpath = xpathProperty.getProperty("findsauces");
            imageSaucesXpath = xpathProperty.getProperty("clicksauces");
            buySaucesButtonXpath = xpathProperty.getProperty("buysaucesbutton");
            inCartButtonXpath = xpathProperty.getProperty("incartbutton");
            cartImageXpath = xpathProperty.getProperty("cartimage");
            cartItemXpath = xpathProperty.getProperty("cart_item_verify");
            removeCartItemXpath = xpathProperty.getProperty("remove_cart_item");
            closeCartModalXpath = xpathProperty.getProperty("close_cart_modal");
            HomeXpath=xpathProperty.getProperty("Home");
            Hot_sauceXpath=xpathProperty.getProperty("Hot-Sauces");
            MerchandiseXpath=xpathProperty.getProperty("Merchandise");
            MenXpath=xpathProperty.getProperty("Men");
            WomenXpath=xpathProperty.getProperty("Women");
            ClearenceXpath=xpathProperty.getProperty("Clearence");
            New_to_hot_sauceXpath=xpathProperty.getProperty("New_to_Hot_Sauce");
            FAQXpath=xpathProperty.getProperty("FAQ");
            RegisterLinkXpath=xpathProperty.getProperty("RegisterLink");
            RegisterEmailXpath=xpathProperty.getProperty("RegisterEmail");
            RegisterfirstNameXpath=xpathProperty.getProperty("RegisterfirstName");
            RegisterLastNameXpath=xpathProperty.getProperty("RegisterLastName");
            RegisterPasswordXpath=xpathProperty.getProperty("RegisterPassword");
            RegisterConfirmPasswordXpath=xpathProperty.getProperty("RegisterConfirmPassword");
            RegisterButtonXpath=xpathProperty.getProperty("RegisterButton");
            url = configProperty.getProperty("url");
        } catch (IOException e) {
            logService.info("Properties file not found" + e.getMessage());
        }
        LogManager.setLogLevel(LogLevel.INFO);
    }

    @AfterSuite
    public void teardown() {
        // configProperty.clear();
        // xpathProperty.clear();
    }

}