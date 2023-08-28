package testCases;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.openqa.selenium.JavascriptExecutor;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utilities.XLUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TC_Scroll_Test extends BaseClass {

	public ArrayList<String> faildImageUrls = new ArrayList<String>();
	
	public String page_title = "_";
	
	
	
	@Test(dataProvider = "urls")
	public void TC_Content_Check(String Url) throws InterruptedException, IOException {
		
		//driver.get("https://synergia-energy-ltd-d9.sid2-e1.investis.com/sitemap.xml");
		
		driver.get(Url);
		
		// Find all the <p> elements on the page
        List<WebElement> paragraphElements = driver.findElements(By.tagName("p"));

        // Extract the text from each <p> element
        for (WebElement paragraphElement : paragraphElements) {
            String paragraphText = paragraphElement.getText();
            logger.info(paragraphText);
        }
		
		// Get the JavaScript Executor
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Calculate the height of the page
        long scrollHeight = (long) jsExecutor.executeScript("return document.documentElement.scrollHeight");

        // Set the scroll speed (adjust the sleep time in milliseconds)
        long scrollSpeed = 500;

        // Scroll until the length of the page is reached
        long currentPosition = 0;
        while (currentPosition < scrollHeight) {
            jsExecutor.executeScript("window.scrollBy(0, 400)");
            try {
                Thread.sleep(scrollSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentPosition += 400;
        }
        
     // Scroll up until the top of the page is reached
        while (currentPosition > 0) {
            jsExecutor.executeScript("window.scrollBy(0, -400)");
            try {
                Thread.sleep(scrollSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentPosition -= 400;
        }
        
        
	}
	
	
	@DataProvider(name = "urls")
	public Object[][] provideUrls() throws Exception {
	    String urlStr = "https://synergia-energy-ltd-d9.sid2-e1.investis.com/sitemap.xml";
	    String username = "synergia-energy-ltd";
	    String password = "7*s=hYM+CGYM9nfG";

	    URL url = new URL("https://dnb-ir-v3:AmsJ-ur]b3j(BgHR@dnb-ir-v3.dev-mid-euw3.investis.com/sitemap.xml");
	    logger.info(url.toString());
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	    // Set basic authentication header
	    String authString = username + ":" + password;
	    String authHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(authString.getBytes());
	    connection.setRequestProperty("Authorization", authHeaderValue);

	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Accept", "application/xml");

	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(connection.getInputStream());
	    doc.getDocumentElement().normalize();

	    ArrayList<String> urls = new ArrayList<>();

	    NodeList nodeList = doc.getElementsByTagName("url");

	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Element element = (Element) nodeList.item(i);
	        String loc = element.getElementsByTagName("loc").item(0).getTextContent();
	        urls.add(loc);
	    }

	    connection.disconnect();

	    Object[][] data = new Object[urls.size()][1];
	    for (int i = 0; i < urls.size(); i++) {
	        data[i][0] = urls.get(i);
	    }

	    return data;
	}

}