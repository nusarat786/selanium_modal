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

import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class TC_002_Atleast_one_h1_Test extends BaseClass {
	
	private ExtentTest extentTest;
	public String page_title = "_";
	
	@Test(dataProvider = "GetUrlData")
	public void TC_002_Atleast_one_h1(String Url,String ind

	) throws InterruptedException, IOException {

		// extentTest = extent.createTest("ok");
		
		 logger.info("TC002 H1 Present Check " );
		
		logger.info("URl Is :  " + Url);

		
		driver.get(Url);

		logger.info("Waiting For 1000 ms");
		Thread.sleep(3000);

		page_title = driver.getTitle();
		
		logger.info("Validating");
		
		// Find all the H1 elements on the page
	    List<WebElement> h1Elements = driver.findElements(By.tagName("h1"));

	    logger.info("Total H1 Elements Arae " + h1Elements.size());
	
	    

		// boolean is404 = responseCode >= 400;

		logger.info("Waiting For 2000 ms");
		Thread.sleep(2000);
		
		boolean is_atl_H1 = h1Elements.size() > 0;
		
		// Print a message indicating if the page is functioning properly or not if
		if (is_atl_H1) {
			
			logger.info("URl With Atleas 1H :  " + Url);
			Thread.sleep(2000);
			Assert.assertTrue(true);

			
		} else {
			
			logger.error("URl Without atleast One Element :  " + Url);
			LocalDateTime currentTime = LocalDateTime.now();
			writeXml("Failed_H1","without H1",Url, currentTime);
			Thread.sleep(1000);
			logger.info("write to file");
			logger.info("Waiting For 1000 ms");
			Thread.sleep(2000);
			captureScreen(driver,"TC_002_Atleast_one_h1_Test"+Url);
			Assert.assertTrue(false);
		}
		logger.info("---------------------------------------------------------");
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
