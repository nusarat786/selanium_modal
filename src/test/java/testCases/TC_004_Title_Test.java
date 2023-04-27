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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TC_004_Title_Test extends BaseClass {

	public String page_title = "_";
	@Test(dataProvider = "GetUrlData")
	public void TC_004_Title(String Url

	) throws InterruptedException, IOException {

		logger.info("TC004  Title is There Or Not Check " );
		
		logger.info("URl Is :  " + Url);
		driver.get(Url);

		

		logger.info("Waiting For 2000 ms");
		Thread.sleep(2000);
		
		page_title = driver.getTitle();
		
		try {	
		    
			String pageTitle = driver.getTitle(); // Get the page title

		    logger.info("fetching page title");
			
		    logger.warn("page title is: " + pageTitle);
		    
		    // Check if the description exists and print the appropriate message
		    if (pageTitle != null && !pageTitle.isEmpty()) {
		    	logger.info("URl With Title :  " + Url);
				Thread.sleep(2000);
				Assert.assertTrue(true);
		    
		    } else {
		    	
		    	logger.error("URl Without Title :  " + Url);
				LocalDateTime currentTime = LocalDateTime.now();
				writeXml("Failed_Meta_Title","without title",Url, currentTime);
				Thread.sleep(1000);
				logger.info("write to file");
				logger.info("Waiting For 1000 ms");
				Thread.sleep(2000);
				Assert.assertTrue(false);
		    }
		    
		} catch (Exception e) {
		    // Handle any exceptions that occur during the execution of the test
		    System.out.println("An error occurred: " + e.getMessage());
		    logger.error("URl Without Title :  " + Url);
			LocalDateTime currentTime = LocalDateTime.now();
			writeXml("Failed_Meta_Title","without title",Url, currentTime);
			Thread.sleep(1000);
			logger.info("write to file");
			logger.info("Waiting For 1000 ms");
			Thread.sleep(2000);
			captureScreen(driver,"TC_004_Title_Test");
			Assert.assertTrue(false);
		}
		
		
		
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

	    locTags.add(new String[]{"https://nusaratmehdihaveliwala.web.app/", "0"});
	    
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Element element = (Element) nodeList.item(i);
	        String loc = element.getElementsByTagName("loc").item(0).getTextContent();
	        locTags.add(new String[]{loc, Integer.toString(i+1)});
	    }
	    
	    connection.disconnect();

	    return locTags.toArray(new String[0][]);
	}




}
