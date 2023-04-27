package testCases;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utilities.Reporting;

public class TC_003_Meta_Description_Test extends BaseClass {

	public int Number = 0;

	public String page_title="abc";

	@Test(dataProvider = "GetUrlData")
	public void TC_003_Meta_Description(String Url, String ind, ITestContext context)
			throws InterruptedException, IOException, Exception {

		logger.info("Context Is" + context);

		logger.info(ind);
		
		logger.info("TC003  Meta_Description Check ");

		logger.info("URl Is :  " + Url);
		driver.get(Url);

		logger.info("Waiting For 2000 ms");

		page_title = driver.getTitle();

		System.out.println(page_title);
		
		logger.info(page_title);


		context.setAttribute("page_title",page_title );
		
		//logger.warn(tr.getAttribute("page_title"));
		
		Thread.sleep(2000);

		try {

			// Locate the meta description element using the tag name 'meta' and the
			// attribute name 'name'
			WebElement metaDescription = driver.findElement(By.cssSelector("meta[name='description']"));
			logger.info("Meta Description Is Fetched");

			// Get the content attribute of the meta description element
			String description = metaDescription.getAttribute("content");
			logger.info("Meta Description Content Is Fetched");

			logger.warn(description);

			// Check if the description exists and print the appropriate message
			if (description != null && !description.isEmpty()) {
				logger.info("URl With Meta Description :  " + Url);
				Thread.sleep(2000);
				Assert.assertTrue(true);

			} else {

				logger.error("URl Without Meta Description :  " + Url);
				LocalDateTime currentTime = LocalDateTime.now();
				writeXml("Failed_Meta_Description", "without Meta_Description", Url, currentTime);
				Thread.sleep(1000);
				logger.info("write to file");
				logger.info("Waiting For 1000 ms");
				Thread.sleep(2000);
				captureScreen(driver, "TC_003_Meta_Description" + ind);
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			logger.info(page_title);
			
			// Handle any exceptions that occur during the execution of the test
			logger.info("can not find meta tag");
			logger.error("URl Without Meta Description :  " + Url);
			LocalDateTime currentTime = LocalDateTime.now();
			writeXml("Failed_Meta_Description", "without Meta_Description", Url, currentTime);
			Thread.sleep(1000);
			logger.info("write to file");
			logger.info("Waiting For 1000 ms");
			Thread.sleep(2000);
			captureScreen(driver, "TC_003_Meta_Description" + ind);
			Assert.assertTrue(false);

		}

		logger.info(Number);
		Number++;
		logger.info("--------------------------------------------------");

		
	}

	@DataProvider(name = "GetUrlData")
	public String[][] extractLocTagsFromXmlUrl() throws Exception {
		URL url = new URL(siteMapUrl);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(connection.getInputStream());
		doc.getDocumentElement().normalize();

		ArrayList<String[]> locTags = new ArrayList<String[]>();

		NodeList nodeList = doc.getElementsByTagName("url");

		locTags.add(new String[] { "https://nusaratmehdihaveliwala.web.app/", "0" });

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			String loc = element.getElementsByTagName("loc").item(0).getTextContent();
			locTags.add(new String[] { loc, Integer.toString(i + 1) });
		}

		connection.disconnect();

		return locTags.toArray(new String[0][]);
	}

}
