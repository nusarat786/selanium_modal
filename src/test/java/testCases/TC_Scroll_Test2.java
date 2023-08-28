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

public class TC_Scroll_Test2 extends BaseClass {

	public ArrayList<String> faildImageUrls = new ArrayList<String>();
	
	public String page_title = "_";
	
	
	
	@Test
	public void TC_Content_Check() throws InterruptedException, IOException {
		
		// Navigate to the desired webpage
        driver.get("https://www.prosus.com/news-insights/group-news");
        // Find elements with the class name "tagSmall" and "font-extra-bold"
        List<WebElement> elements = driver.findElements(By.cssSelector(".tagSmall.font-extra-bold"));

        // Create an array to store the extracted text
        List<String> textArray = new ArrayList<>();
     // Create an array to store the extracted text
        
        List<String> xpath_a = new ArrayList<>();

        // Loop through each element and extract its text
        for (WebElement element : elements) {
        	String xp  = getXPath(driver,element);
        	xpath_a.add(xp);
        	logger.info(xp);
            String text = element.getText();
            textArray.add(text);
        }

        // Print the extracted text
        for (String text : textArray) {
            logger.info(text);
        }
        logger.info( "Size "+ textArray.size());

        
		/*
		 * String searchText = textArray.get(0); WebElement element =
		 * driver.findElement(By.
		 * xpath("//div[contains(@class, 'content d-flex flex-column') and contains(text(), '"
		 * + searchText + "')]"));
		 * 
		 * // Perform the click on the element element.click();
		 */
        
        // Find the element using the provided XPath
        WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content-wrapper\"]/section[2]/div[1]/div[2]/div/div[4]/div/div/div[1]/div/a"));

        // Perform the click on the element
        element.click();

		
	}

}