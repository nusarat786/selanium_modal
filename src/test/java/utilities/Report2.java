package utilities;

//Listener class used to generate Extent reports

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

import javax.mail.Multipart;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class Report2 extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	public int testCaseNumber;

	public String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
	public String repName = "Test-Report-" + timeStamp + ".html";

	public void onStart(ITestContext testContext) {

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

		try {
			String page_title = (String) tr.getTestClass().getRealClass().getField("page_title").get(tr.getInstance());

			testName.append("_" + page_title);
			System.out.println(page_title);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			System.out.print("error");
		}

		logger = extent.createTest(testName.toString()); // create new entry in th report
		logger.log(Status.PASS, MarkupHelper.createLabel(testName.toString(), ExtentColor.GREEN)); // send the passed
		// information to the report
		// with GREEN color // highlighted
		logger.log(Status.INFO, "Parameter Details:");
		for (int i = 0; i < parameters.length; i++) {
			logger.log(Status.INFO, "Parameter " + (i + 1) + ": " + parameters[i]);
		}
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
			String page_title = (String) tr.getTestClass().getRealClass().getField("page_title").get(tr.getInstance());

			testName.append("_" + page_title);

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

		ExtentTest descriptionNode = logger.createNode("Image Urls").fail("Assertion Failed");
		descriptionNode.assignCategory(""); // add a category to the node
		StringBuilder sb = new StringBuilder();

		try {

			ArrayList<String> failedUrlsImage = (ArrayList<String>) tr.getTestClass().getRealClass()
					.getField("faildImageUrls").get(tr.getInstance());

			if (failedUrlsImage.size() > 0) {

				sb.append("Failed image URLs: <br>");
				for (int k = 0; k < failedUrlsImage.size(); k++) {
					String linktext = "Image Link";
					String linkurl = failedUrlsImage.get(k);
					String linked = "<a href='" + linkurl + "' target='_blank' style='color:#00c853'>" + linktext
							+ "</a>";
					String message2 = "Please Click " + linked + " to view the failed Url Page.";

					sb.append("Index " + k + ": " + failedUrlsImage.get(k) + "<br>" + message2 + "<br>" + "<hr>");
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		descriptionNode.info(sb.toString()); // add the details with line breaks between each element

		// highlighted

		// test.fail(MarkupHelper.createLabel(message, ExtentColor.RED));
		// logger.createNode("Test Description").fail("Url Failed Is:" + p.toString() );

		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getMethod().getMethodName() + y.toString() + ".png";

		
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
		Class<?> currentClass = testContext.getCurrentXmlTest().getClasses().get(0).getSupportClass();
		String className = currentClass.getSimpleName();
		sendEmail(className);

	}

	public void sendEmail(String Subject) {

		// Set up email authentication credentials
		final String username = "bharatkhand370@gmail.com";
		final String password = "rodtowncrzcglzdq";

		// Set up the email properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Set up the session with the email authentication
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Set up the email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bharatkhand370@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Nusarat.Haveliwala@investisdigital.com"));
			message.setSubject(Subject + "__Report Finland");
			message.setText("Please find the report attached.");

			// Set up the email attachment
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			String filename = System.getProperty("user.dir") + "/test-output/" + repName;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the email with the attached report
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Email sent with attachment successfully.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
