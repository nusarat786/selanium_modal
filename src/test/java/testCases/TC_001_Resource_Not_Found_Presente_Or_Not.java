package testCases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class TC_001_Resource_Not_Found_Presente_Or_Not extends BaseClass {

	@Test(dataProvider = "GetUrlData")
	public void TC_001_404_Check(String Url

	) throws InterruptedException, IOException {

		String Urli = "https://anglo-american-group-plc-v5.cd.invdcloud-is.co.uk/en/yy";

		logger.info("URl Is :  " + Url);

		// Navigate to the webpage
		//driver.get(Url);

		logger.info("Waiting For 1000 ms");
		Thread.sleep(1000);

		logger.info("Validating");
		
		URL link = new URL(Url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
		httpURLConnection.setRequestMethod("HEAD");
		httpURLConnection.connect();
		int responseCode = httpURLConnection.getResponseCode();

		logger.info("Response Code is : " +responseCode);

		boolean is404 = responseCode >= 400;

		logger.info("Waiting For 1000 ms");
		Thread.sleep(1000);
		
		// Print a message indicating if the page is functioning properly or not if
		if (is404) {

			logger.error("URl With 404 :  " + Url);
			LocalDateTime currentTime = LocalDateTime.now();
			writeXml("FailedUrl_404","FailedUrl",Url, currentTime);
			Thread.sleep(1000);
			logger.info("write to file");
			logger.info("Waiting For 1000 ms");
			Thread.sleep(2000);
			Assert.assertTrue(false);

		} else {
			logger.info("URl Without 404 :  " + Url);
			Thread.sleep(2000);
			Assert.assertTrue(true);
		}

	}

	@DataProvider(name = "GetUrlData")
	public  String[] extractLocTagsFromXmlUrl() throws Exception {
		URL url = new URL(siteMapUrl);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(connection.getInputStream());
		doc.getDocumentElement().normalize();

		ArrayList<String> locTags = new ArrayList<String>();
		
		locTags.add("https://www.angloamericangroupfoundation.org/faq/ulo");

		NodeList nodeList = doc.getElementsByTagName("url");

		
		  for (int i = 0; i < nodeList.getLength(); i++) { Element element = (Element)
		  nodeList.item(i); String loc =
		  element.getElementsByTagName("loc").item(0).getTextContent();
		  locTags.add(loc); }
	
		connection.disconnect();
		return locTags.toArray(new String[0]);
	}


}
