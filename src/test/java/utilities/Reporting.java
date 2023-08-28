package utilities;

//Listener class used to generate Extent reports

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Reporting extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	public int testCaseNumber;

	
	
	public void onStart(ITestContext testContext) {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		String repName = "Test-Report-" + timeStamp + ".html";

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + repName);// specify
																											// location
																											// of the
																											// report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "Nusarat");

		htmlReporter.config().setDocumentTitle("Project"); // Tile of report
		htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
		
		
	}

	public void onTestSuccess(ITestResult tr) {

		StringBuilder testName = new StringBuilder(tr.getMethod().getMethodName());
		
		Object[] parameters = tr.getParameters();
		// logger.createNode("Test Description").pass("This test method has passed
		// successfully.");
		
		try {
			
			String page_title = (String) tr.getTestClass().getRealClass().getField("globalFont")
			        .get(tr.getInstance());
			
			testName.append("_" +page_title);
			System.out.println(page_title);
			
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			System.out.print("error");
		}
		
		
		logger = extent.createTest(testName.toString()); // create new entry in th report
		logger.log(Status.PASS, MarkupHelper.createLabel(testName.toString(), ExtentColor.GREEN)); // send the passed
																							// information to the report
																							// with GREEN color																							// highlighted
		logger.log(Status.INFO, "Parameter Details:");
		for (int i = 0; i < parameters.length; i++) {
			logger.log(Status.INFO, "Parameter " + (i + 1) + ": " + parameters[i]);
		}
		
		Map<String, String> globalFont;
//		try {
//			globalFont = (Map<String, String>) tr.getTestClass().getRealClass().getField("globalFont")
//			        .get(tr.getInstance());
//			
//			for (Map.Entry<String, String> entry : globalFont.entrySet()) {
//	            String property = entry.getKey();
//	            String value = entry.getValue();
//	            logger.log(Status.INFO, property + " : " + value);  
//	        }
//			
//		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		
		
		StringBuilder testName = new StringBuilder(tr.getMethod().getMethodName());

		Object[] parameters = tr.getParameters();

		StringBuilder p = new StringBuilder();
		StringBuilder y = new StringBuilder();

		int i = 0;
		if (parameters.length > 0) {

			for (i = 0; i < parameters.length; i++)

			{
				// pass the parameter location which you want to add as a testname
				if (i == 0) {
					p.append(Utils.toString(parameters[i]));

				}

				if (i == 1) {
					y.append(Utils.toString(parameters[i]));

				}
			}
		}
		
		try {
			String page_title = (String) tr.getTestClass().getRealClass().getField("page_title")
			        .get(tr.getInstance());
			
			testName.append("_" +page_title);
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.print("String Is " + testName );
		// System.out.println("String Is " + p.toString() );
		logger = extent.createTest(testName.toString()); // create new entry in th report

		String linkText = "URL";
		String linkUrl = p.toString();
		String link = "<a href='" + linkUrl + "' target='_blank' style='color:#00c853'>" + linkText + "</a>";
		String message = "Please Click " + link + " to view the failed Url Page.";

		
		logger.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED)); // send the passed information to
																						// the report with GREEN color
																						// highlighted

		// test.fail(MarkupHelper.createLabel(message, ExtentColor.RED));
		// logger.createNode("Test Description").fail("Url Failed Is:" + p.toString() );

		
		
		
		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + y + ".png";

		logger.log(Status.INFO, "Parameter Details:");
		for (int j = 0; j < parameters.length; j++) {
			logger.log(Status.INFO, "Parameter " + (j + 1) + ": " + parameters[j]);
		}

		File f = new File(screenshotPath);

		

		if (f.exists()) {
			try {
				logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void onTestSkipped(ITestResult tr) {
		logger = extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}
