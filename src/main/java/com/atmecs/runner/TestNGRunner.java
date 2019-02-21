package com.atmecs.runner;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.atmecs.falcon.automation.dataprovider.TestDataProvider;
import com.atmecs.falcon.automation.ui.selenium.CustomListener;
import com.atmecs.falcon.automation.util.main.AbstractTestNGEngine;
import com.atmecs.falcon.automation.util.main.TestNGEngineFactory;
import com.atmecs.falcon.automation.util.main.TestNGEngineTemplateType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.util.reporter.TestReportUploadClient;
import com.beust.jcommander.internal.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * TestNGRunner is the Main Class of MOJO type generates new Xml Suites on runtime for each client
 * for each child suite in the existing parent Xml Suite and executes
 * @author nv092106
 */
public class TestNGRunner {
    private static AbstractTestNGEngine testNGEngine = new TestNGEngineFactory()
            .getTestNGEngine(TestNGEngineTemplateType.DESIRED_SUITE_FOR_GIVEN_MODULES);
    private static String filename = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator
            + "config.properties";
    @SuppressWarnings("rawtypes")
    private static List<Class> listners = Lists.newArrayList();
    private static TestNG testng = new TestNG();
    private static List<XmlSuite> suitesToRun = null;
    private static Properties props = null;
    private static TestDataProvider dataProvider = TestDataProvider.getInstance();
    private static ReportLogService report = new ReportLogServiceImpl(TestNGRunner.class);

    private static void initialize() throws Exception {
        // Custom Listener to testng
        listners.add(CustomListener.class);
        testng.setListenerClasses(listners);

        // Loading properties file
        props = dataProvider.loadProperties(filename);
       
    }

    /**
     * Purpose: The main method invoked by the Maven plugin that uses the services of TestNGEngine
     * to create new Xml Suites on runtime and executes them
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        initialize();

        suitesToRun =
                testNGEngine.getSuitesToRunFor(readEnvOrConfigProperty("SuiteFileName"),
                        readEnvOrConfigProperty("ClientName"),
                        readEnvOrConfigProperty("ModuleName"),
                        readEnvOrConfigProperty("BrowserCaps"));
        testng.setXmlSuites(suitesToRun);
        testng.setSuiteThreadPoolSize(Integer.parseInt(readEnvOrConfigProperty("instances")));
        testng.run();
        uploadTestNGResultsXml();

    }

    public static String readEnvOrConfigProperty(String key) {
        // first pref for env, next for config file
        String value = System.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            value = props.getProperty(key);
        }
        return value;

    }

    public static void uploadTestNGResultsXml() {
        final String uploadUrl = readEnvOrConfigProperty("testreport.uploadurl");
        String envi=System.getenv("environment");
        String testNGResultsXmlFilePath =
                System.getProperty("user.dir") + File.separator + "test-output" + File.separator
                        + "testng-results.xml";

        TestReportUploadClient testReportUploadClient = new TestReportUploadClient(uploadUrl);
        try {
            String response =
                    testReportUploadClient.upload(dataProvider.getSessionId(), "HEAT CLINIC RESULT", "WEB",
                            envi, "Regression", "Local", "Windows 10", "Desktop", "Chrome v51",
                            testNGResultsXmlFilePath);
            report.info("Response : " + response);
            setRunSessionId(response);

        } catch (Exception e) {
            report.error("Unknown error : : Cannot Upload the testng-results.xml " + e.getMessage());
        }
    }
    
    //Added method to set the Run session id
    private static void setRunSessionId(String response) {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(response);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		int runSessionId = jsonObject.get("runSessionId").getAsInt();
		
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream("reports.properties");

			// set the properties value
			prop.setProperty("RUN_SESSION_ID", String.valueOf(runSessionId));

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
