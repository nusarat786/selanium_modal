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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC_006_Response_Time_Test extends BaseClass {
	
	public String page_title = "_";
	
	@Test(dataProvider = "GetUrlData")
	public void TC_006_Response_Time(String Url

	) throws InterruptedException, IOException {

		logger.info("TC006 Response_Time Check " );
		
		logger.info("URl Is :  " + Url);
		driver.get(Url);
		
		page_title = driver.getTitle();

		// Get the current system time before the page is fully loaded
	      long startTime = System.currentTimeMillis();

	      // Wait for the page to fully load using WebDriverWait
	      WebDriverWait wait = new WebDriverWait(driver, 30);
	      wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

	      // Get the current system time after the page is fully loaded
	      long endTime = System.currentTimeMillis();

	      // Calculate the total page loading time
	      long totalTime = endTime - startTime;

	      logger.info("Response Time For " + Url + " Is: " + totalTime );
		
	      if (totalTime <= 3000) {
		    	logger.info("Url Response Time is Optimized " + Url );
				Thread.sleep(2000);
				Assert.assertTrue(true);
				
		    } else {
		    	
		    	logger.info("Url Response Time is Not Optimized" + Url );
				LocalDateTime currentTime = LocalDateTime.now();
				writeXml("Failed_ResponseTime","UnOptimized Url",Url, currentTime);
				Thread.sleep(1000);
				logger.info("write to file");
				logger.info("Waiting For 1000 ms");
				Thread.sleep(2000);
				captureScreen(driver,"TC_006_Response_Time");
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
