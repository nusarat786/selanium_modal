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

public class TC_005_alt_in_image_test extends BaseClass {

	public ArrayList<String> faildImageUrls = new ArrayList<String>();
	
	public String page_title = "_";
	@Test(dataProvider = "GetUrlData")
	public void TC_005_alt_in_image(String Url,String Number

	) throws InterruptedException, IOException {

		faildImageUrls.clear();
		
		logger.info("TC005  Alt Check Running" );
		
		
		logger.info("Web Page URl Is :  " + Url);
		driver.get(Url);

		
		
		logger.info("Waiting For 2000 ms");
		Thread.sleep(2000);
		
		logger.info("Getting Page Title");
		page_title = driver.getTitle();
		
		
		try {	
			
			logger.info("Getting All The Img");
			List<WebElement> imageElements = driver.findElements(By.tagName("img"));
			
			logger.info("Total Imges Are : " + imageElements.size());
			int a =1;
			// iterate through each image element and retrieve the value of its alt attribute
			logger.info("Cheking Images One by One");
			
			for (WebElement imageElement : imageElements) {
			    
				String altText = imageElement.getAttribute("alt");
			    String src = imageElement.getAttribute("src");
			   
			    
			   
			    if (altText != null && !altText.isEmpty()) {
			    	Thread.sleep(1000);
					a++;	
			    } else {
			    	
			    	Thread.sleep(1000);
					faildImageUrls.add(src);
					a++;
				}
			    
			    logger.info("Image NO: " + ( a-1 )+ " Is Checked :"  );
			    		    
			}
			  
		} catch (Exception e) {
		    // Handle any exceptions that occur during the execution of the test
		    //System.out.println("An error occurred: " + e.getMessage());
		    
		    logger.warn("Failed to Get All Images" );
			LocalDateTime currentTime = LocalDateTime.now();
			writeXml("Skiped_Image_Alt_Url","without title",Url, currentTime);
			Thread.sleep(1000);
			logger.info("write to file");
			logger.info("Waiting For 1000 ms");
			Thread.sleep(2000);
			//captureScreen(driver,"TC_003_Meta_Description_Test");
			//Assert.assertTrue(false);
			logger.info("#########################################");
		}
		
		logger.info("There Are Total " + faildImageUrls.size()  +" Images Withou Alt on " + Url );
		
		if(faildImageUrls.size() >0) {
			
			Thread.sleep(1000);
			
			logger.info("Url Written To Report");
			logger.info("test case failed");
			captureScreen(driver,"TC_005_alt_in_image" +Number);
			
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

	    
	    locTags.add(new String[]{"https://www.thapatechnical.com/", "0"});
	    
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Element element = (Element) nodeList.item(i);
	        String loc = element.getElementsByTagName("loc").item(0).getTextContent();
	        locTags.add(new String[]{loc, Integer.toString(i+1)});
	    }
	    
	    connection.disconnect();

	    return locTags.toArray(new String[0][]);
	}

}
