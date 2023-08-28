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

public class TC_Scroll_Test3 extends BaseClass {

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
	    public static Object[][] provideUrls() {
	        String[] urls = {
	            "https://investors.baesystems.com/",
	            "https://investors.baesystems.com/investment-case",
	            "https://investors.baesystems.com/financial-calendar",
	            "https://investors.baesystems.com/regulatory-news",
	            "https://investors.baesystems.com/disclaimer-country",
	            "https://investors.baesystems.com/disclaimer-country-sep-2020",
	            "https://investors.baesystems.com/financial-information",
	            "https://investors.baesystems.com/results-centre",
	            "https://investors.baesystems.com/financial-information/annual-report",
	            "https://investors.baesystems.com/financial-information/five-year-summary",
	            "https://investors.baesystems.com/financial-information/debt-facilities",
	            "https://investors.baesystems.com/financial-information/credit-rating",
	            "https://investors.baesystems.com/corporate-governance",
	            "https://investors.baesystems.com/corporate-governance/s1721statements-and-corporate-governance-statements",
	            "https://investors.baesystems.com/corporate-governance/board-committees",
	            "https://investors.baesystems.com/shareholder-information",
	            "https://investors.baesystems.com/shareholder-information/shareholder-faqs",
	            "https://investors.baesystems.com/shareholder-information/agm",
	            "https://investors.baesystems.com/reports-and-presentations",
	            "https://investors.baesystems.com/shareholder-information/registrars",
	            "https://investors.baesystems.com/shareholder-information/shareholder-forms",
	            "https://investors.baesystems.com/shareholder-information/dividend-information",
	            "https://investors.baesystems.com/shareholder-information/adr-information",
	            "https://investors.baesystems.com/shareholder-information/foreign-shareholding",
	            "https://investors.baesystems.com/shareholder-information/email-alerts",
	            "https://investors.baesystems.com/shareholder-information/rss-alerts",
	            "https://investors.baesystems.com/shareholder-information/shareholder-alert"
	        };

	        Object[][] data = new Object[urls.length][1];
	        for (int i = 0; i < urls.length; i++) {
	            data[i][0] = urls[i];
	        }

	        return data;
	    }
	
}