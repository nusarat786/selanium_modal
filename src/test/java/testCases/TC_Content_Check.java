package testCases;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.languagetool.JLanguageTool;
//import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;


import java.util.LinkedHashMap;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.languagetool.language.BritishEnglish;



public class TC_Content_Check extends BaseClass {

	

	public String _URL ;
	
	public String _page_title = "_";
	
	public String _meta_description = "_";
	
	public List<String> _spell_errors = new ArrayList<>();
	//public Map<String, String> globalFont = new HashMap<>();
	
	public List<String> _h1Texts = new ArrayList<>();
	
	public List<String> _headerList = new ArrayList<>();
	
	public List<String> _spellErrors = new ArrayList<>();
	
	public  String _ogType;
	public  String _ogTitle;
	public  String _ogUrl;
	public  String _ogImage;
	 
	public boolean _is404 ;
	public boolean _is302 ;
		
	
	public double _resTime;
	
	
	public ArrayList<String> _alt_faildImageUrls = new ArrayList<String>();
	public ArrayList<String> _alt_faildImageUrls_red = new ArrayList<String>();
	
	
	public ArrayList<String> _res_faildImageUrls = new ArrayList<String>();
	public ArrayList<String> _res_faildImageUrls_red = new ArrayList<String>();
	
	public ArrayList<String> _red302Urls = new ArrayList<String>();
	public ArrayList<String> _red302Urls_red = new ArrayList<String>();
	
	public ArrayList<String> _res_faildLinkUrls = new ArrayList<String>();
	public ArrayList<String> _res_faildLinkUrls_red = new ArrayList<String>();
	
	public ArrayList<String> _title_faildLinkUrls = new ArrayList<String>();
	public ArrayList<String> _title_faildLinkUrls_red = new ArrayList<String>();
	
	@Test(dataProvider = "urls")
	public void TC_Content_Check(String __url) throws InterruptedException, IOException {
		
		ArrayList<String> red302Urls = new ArrayList<String>();
		ArrayList<String> red302Urls_red = new ArrayList<String>();
		
		//driver.get("https://www.angloamerican.com/");
		
		//driver.get("https://www.angloamerican.com/futuresmart/futuresmart-mining");
		
		driver.get(__url);
		// Mesuring Repnse Time
		
		
		// Get the current system time before the page is fully loaded
	      long startTime = System.currentTimeMillis();

	      // Wait for the page to fully load using WebDriverWait
	      WebDriverWait wait = new WebDriverWait(driver, 10);
	      wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

	      // Get the current system time after the page is fully loaded
	      double endTime = System.currentTimeMillis();

	      // Calculate the total page loading time
	      double totalTime = endTime - startTime;


        // Calculate the page load time in seconds
	      double loadTime = (endTime - startTime) / 1000;
        
        logger.info(endTime - startTime);

        _resTime = loadTime;
        // Print the page load time
        
        logger.info("Page load time: " + loadTime + " seconds");
	    
        
        _URL = driver.getCurrentUrl();
        
        logger.info("Site Is Loaded");
		
		
		
		
///////////////////////////////-----1. page title------  ///////////////////////////
		
		try {
		
		logger.info("Getting Title");
			
        String pageTitle = driver.getTitle();
        
        logger.info("Verifying Title.....");
        
        // Check if the title is empty
        if (pageTitle.isEmpty()) {
            logger.info("No title found");
            _page_title = "No title found";
               
        } else {
        	_page_title = pageTitle;
        	logger.info("Page Title Is : " + pageTitle);
        }
        
        logger.info("Title Is Saved");
        
		}catch(Exception e){
			logger.info("Exeption While Getting Title");
        	_page_title = "No Title found";

        }
		
		logger.info("|||||||||||||||||||||||||||||||||||||||||||");
		
	
///////////////////////// ------2. Get the meta description--------///////////////////
        
        try {
        
        	
        logger.info("Getting Meta Des.....");
        
        
        WebElement metaDescriptionElement = driver.findElement(By.xpath("//meta[@name='description']"));
        String metaDescription = metaDescriptionElement.getAttribute("content");

        
        logger.info("Verifying Meta Des.....");
        // Check if the meta description is empty or null
        if (metaDescription == null || metaDescription.isEmpty()) {
        	
        	logger.info("No meta description found");
        	_meta_description = "No meta description found";
        
        } else {
            
        	// Print the meta description
        	_meta_description = metaDescription;
        	logger.info("Meta Description: " + metaDescription); 
        	
        }
        
        logger.info("Meta Description Is Captured");
        }catch(Exception e){
        	logger.info("Exeption While Getting Meta Description");
        	_meta_description = "No meta description found";

        }
        
        
        
        logger.info("|||||||||||||||||||||||||||||||||||||||||||");
        
        
     // 3. Spell Check

        try {
        
     		logger.info("Spelling Check......");

     		// Get the text content of the web page
     		WebElement bodyElement = driver.findElement(By.tagName("body"));
     		String pageText = bodyElement.getText();
     		logger.info("Body Text Fetched");

     		// Perform spell check using LanguageTool library
     		//JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());
     		JLanguageTool languageTool = new JLanguageTool(new BritishEnglish());
     		
     		List<RuleMatch> matches = languageTool.check(pageText);
     		logger.info("Match Code is Executed");

     		logger.info("Logged Spell Errors");

     		List<String> spellErrors = new ArrayList<>();
     		for (RuleMatch match : matches) {
     			
     			 int fromPos = match.getFromPos();
     			 int toPos = match.getToPos();
     			 String misspelledWord = pageText.substring(fromPos, toPos);
     			 
     			logger.info(" Miss Spelled Word" + misspelledWord);
     		    String error = "Word : " + misspelledWord + " : " + match.getMessage();
     		    spellErrors.add(error);

     		    logger.info(error);
     		}

     		_spellErrors = spellErrors ;
     		
     		logger.info( "Total Spell error in this code" + _spellErrors.size());
     		logger.info("Spell Errors Saved");

        }catch(Exception e){
        	logger.info("Exeption While Checking Spelling");
        	

        }
     	
        logger.info("|||||||||||||||||||||||||||||||||||||||||||");
        
        
        
        //4. Find H1 El
        
        logger.info("Checking Only One : H1");
        
        
        List<WebElement> h1Tags;
        List<String> h1Texts = new ArrayList<>();

        try {
        	logger.info("Fetching H1");
            h1Tags = driver.findElements(By.tagName("h1"));
            
            logger.info("Verifying H1");
            if (h1Tags.size() >= 1) {
                for (WebElement h1Tag : h1Tags) {
                    h1Texts.add(h1Tag.getText());
                }
            }

            _h1Texts = h1Texts;
            logger.info("H1 Completed");
        } catch (Exception e) {
        
        	logger.info("Exeption While Getting H1");
        	
        }
        
        
        logger.info("|||||||||||||||||||||||||||||||||||||||||||");
        
        
        //5.  Heading In Sequence
        
        
        try {
        
        logger.info("Getting Page Resource");
        String htmlContent = driver.getPageSource();

        
        

        // Parse the HTML using Jsoup
        org.jsoup.nodes.Document document = Jsoup.parse(htmlContent);

        logger.info("Fethcing Main Data");
        // Extract all H1 to H6 elements from the HTML
        Elements headers = document.select("h1, h2, h3, h4, h5, h6");

		
        
     // Create a list to store the header texts
        List<String> headerList = new ArrayList<>();

        // Iterate over the headers and store their texts in the list
        for (org.jsoup.nodes.Element header : headers) {
        	
            headerList.add(header.tagName() + " : " + header.text());
            
            
        }

        
        
        boolean areHeadersInSequence = true;
        int expectedLevel = 1;

        for (String header : headerList) {
            int level = Integer.parseInt(header.substring(1, 2));

            if (level != expectedLevel) {
                areHeadersInSequence = false;
                break;
            }

            expectedLevel++;
        }
        
        _headerList = headerList;

        if (areHeadersInSequence) {
            logger.info("The header tags (H1 to H6) are in sequence in the HTML.");
        } else {
            logger.info("The header tags (H1 to H6) are not in sequence in the HTML.");
        }
        
        logger.info("Header Completed");

        }catch (Exception e) {
        
        	logger.info("Exeption While Fetching Heading Sequence");
        	
        }
        
       
        logger.info("|||||||||||||||||||||||||||||||||||||||||||");
        
       
        
        
        
		/*
		 * try { // Navigate to the HTML file // Replace with the path to your HTML file
		 * 
		 * // Find all the header elements List<WebElement> headers =
		 * driver.findElements(By.xpath("//h1 | //h2 | //h3 | //h4 | //h5 | //h6"));
		 * 
		 * // Create a map to store the header level and text Map<Integer, List<String>>
		 * headerMap = new LinkedHashMap<>();
		 * 
		 * // Iterate over the headers and store their level and text in the map for
		 * (WebElement header : headers) { String tagName = header.getTagName(); String
		 * text = header.getText(); int level = Integer.parseInt(tagName.substring(1));
		 * 
		 * // Add the header text to the corresponding level in the map if
		 * (!headerMap.containsKey(level)) { headerMap.put(level, new
		 * ArrayList<String>());
		 * 
		 * } headerMap.get(level).add("<" + tagName + "> " + text); }
		 * 
		 * // Print the header level and text from the map for (List<String> headerTexts
		 * : headerMap.values()) { for (String text : headerTexts) { logger.info(text);
		 * } } } catch (Exception e) { e.printStackTrace(); }
		 * 
		 */
       
        // social

        logger.info("fetching socials");
        
		String ogType = getMetaTagContent(driver, "og:type");
		logger.warn("og:type: " + ogType);
		_ogType = ogType;
		
        String ogTitle = getMetaTagContent(driver, "og:title");
        logger.warn("og:title: " + ogTitle);
        _ogTitle = ogTitle;

        String ogUrl = getMetaTagContent(driver, "og:url");
        logger.warn("og:url: " + ogUrl);
        _ogUrl = ogUrl;
        
        String ogImage = getMetaTagContent(driver, "og:image");
        logger.warn("og:image: " + ogImage);
        _ogImage =ogImage ;
		
        logger.info("socials completed");
        
        
        
        logger.info("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        // res code
        
       try {
        logger.info("Checking Response Code");
        String Url = driver.getCurrentUrl();
        URL link = new URL(Url);
		
		logger.info("Sending Http Request");
		
		HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
		httpURLConnection.setRequestMethod("HEAD");
		httpURLConnection.connect();
		int responseCode = httpURLConnection.getResponseCode();

		logger.info("Response Code is : " + responseCode);

		boolean is404 = responseCode >= 400  ;
		boolean is302 = responseCode == 302;
		
		logger.info("Waiting For 1000 ms");
		Thread.sleep(1000);

		// Print a message indicating if the page is functioning properly or not if
		if (is404) {
			
			logger.info("Broken Url ");
			_is404 = true;
			
		}else if(is302) {
			
			_is302 =true;
			logger.info("302 Url ");
			
		} else  {
			logger.info("URl Without 404 :  " + Url);
			Thread.sleep(2000);
			
		}
		logger.info("Response Code Completed");
       }catch(Exception e) {
    	   logger.info(e.getMessage());
       }
		
		logger.info("---------------------------------------------------------");
		
		
///////////////////		Image ALT //////////////////////////////////////
		
		logger.info("check image alt is " +_checkImagealt);
		
		if(_checkImagealt) {
			logger.info("Image Alt Check");
			
			try {	
				
				logger.info("Image Alt Test");
		 		
				ArrayList<String> alt_faildImageUrls = new ArrayList<String>();
				ArrayList<String> alt_faildImageUrls_red = new ArrayList<String>();
				
				logger.info("Getting All The Img");
				List<WebElement> imageElements = driver.findElements(By.tagName("img"));
				
				logger.info("Total Imges Are : " + imageElements.size());
				
				int a =1;
				// iterate through each image element and retrieve the value of its alt attribute
				logger.info("Cheking Images One by One");
				
				for (WebElement imageElement : imageElements) {
				    
					String elementXPath = getXPath(driver, imageElement);
					String urlWithScroll =  elementXPath;
					
					logger.info("xpath" + elementXPath);
					
					String altText = imageElement.getAttribute("alt");
				    String src = imageElement.getAttribute("src");
				   
				    
				    if (altText != null && !altText.isEmpty()) {
				    	Thread.sleep(1000);
						a++;	
				    } else {
				    	
				    	Thread.sleep(1000);
				    	alt_faildImageUrls.add(src);
				    	alt_faildImageUrls_red.add(urlWithScroll);
						a++;
					}
				    
				    logger.info("Image NO: " + ( a-1 )+ " Is Checked :"  );
				    		    
				}
				_alt_faildImageUrls = alt_faildImageUrls;
				_alt_faildImageUrls_red =alt_faildImageUrls_red;
				
				logger.info("There Are Total " + alt_faildImageUrls.size()  +" Images Withou Alt on: " + _URL);
				
			} catch (Exception e) {
			    // Handle any exceptions that occur during the execution of the test
			    //System.out.println("An error occurred: " + e.getMessage());
			    
			    logger.warn("Exeption While Image Alt" );
				
			}
			
			
			logger.info("image alt Finished");
		}else {
			logger.info("image alt skipped");
		}
	
		
		//
		logger.info("check image Blank " + _checkImageblank);
		
		if(_checkImageblank) {
			
			logger.info("Blank Image Check");
			
			try {	
				
				ArrayList<String> res_faildImageUrls_red = new ArrayList<String>();
				
				ArrayList<String> res_faildImageUrls = new ArrayList<String>();
				logger.info("Getting All The Img");
				List<WebElement> imageElements = driver.findElements(By.tagName("img"));
				
				logger.info("Total Imges Are : " + imageElements.size());
				int a =1;
				// iterate through each image element and retrieve the value of its alt attribute
				logger.info("Cheking Images One by One");
				
				for (WebElement imageElement : imageElements) {
				    
					/* String altText = imageElement.getAttribute("alt"); */
				    String src = imageElement.getAttribute("src");
				    String elementXPath = getXPath(driver, imageElement);
				    
					logger.info("xpath" + elementXPath);

				    
			            // Create a URL object from the image source
			            URL url = new URL(src);
			
			            // Open an HTTP connection to the image URL
			            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			            connection.setRequestMethod("GET");
			
			            // Get the response code
			            int responseCode = connection.getResponseCode();
			
			            logger.info(responseCode );
			            
			            // Check if the response code indicates a broken image
			            if (responseCode != 200 && responseCode != 302 ) {
			            	Thread.sleep(1000);
			            	res_faildImageUrls.add(src);
			            	res_faildImageUrls_red.add(elementXPath);
							a++;
			            }else if(responseCode==302){
			            	red302Urls.add(src);
			            	red302Urls_red.add(elementXPath);
			            	a++;
			            }else {
			            	a++;
			            }
			
			            // Close the connection
			            connection.disconnect();
			       
			
			            _res_faildImageUrls = res_faildImageUrls;
			            _res_faildImageUrls_red = res_faildImageUrls_red;
			            logger.info("Image NO: " + ( a-1 )+ " Is Checked :"  );
				    		    
				}
				logger.info("There Are Total " + res_faildImageUrls.size()  +" Images Broken ");
				 
			} catch (Exception e) {
				
			    
			    logger.warn("Exeption Blank Image Alt" );
			}
			
			
			
		}else {
			logger.info("image alt skipped");
		}
		
////////////////////////// IMAGE TITLE ////////////////////////////////////////
		
		logger.info("check link title " + _checkLinktitle);
		
		if(_checkLinktitle) {
			
			logger.info("Link Title Is Checked");
			
			try {	
				
				
				ArrayList<String> title_faildLinkUrls_red = new ArrayList<String>();
				
		 		ArrayList<String> title_faildLinkUrls = new ArrayList<String>();
				logger.info("Getting All The Link");
				List<WebElement> imageElements = driver.findElements(By.tagName("a"));
				
				logger.info("Total Links Are : " + imageElements.size());
				
				int a =1;
				// iterate through each image element and retrieve the value of its alt attribute
				logger.info("Cheking Links One by One");
				
				for (WebElement imageElement : imageElements) {
				    
					String altText = imageElement.getAttribute("title");
				    
					 String src = imageElement.getAttribute("href");
					 String elementXPath = getXPath(driver, imageElement);
						logger.info("xpath" + elementXPath);

				    if (altText != null && !altText.isEmpty()) {
				    	Thread.sleep(1000);
						a++;	
				    } else {
				    	
				    	Thread.sleep(1000);
				    	title_faildLinkUrls.add(src);
				    	title_faildLinkUrls_red.add(elementXPath);
						a++;
					}
				    
				    logger.info("Link NO: " + ( a-1 )+ " Is Checked :"  );
				    		    
				}
				_title_faildLinkUrls = title_faildLinkUrls;
				_title_faildLinkUrls_red = title_faildLinkUrls_red;
				logger.info("There Are Total " + title_faildLinkUrls.size()  +" Links Withou Title ");
				
			} catch (Exception e) {
			    // Handle any exceptions that occur during the execution of the test
			    //System.out.println("An error occurred: " + e.getMessage());
			    
				logger.warn("Exeption While Link Title Check" );
			}
			
			
		}else {
			
			logger.info("Skip Title Link Image");
		}
		
		
		
logger.info("check blank link " + _checkLinkblank);



////////////////////// Blank Link //////////////////////////////
		if(_checkLinkblank) {
			
			try {	
				
				logger.info("Broken Link Check");
				
				ArrayList<String> res_faildLinkUrls_red = new ArrayList<String>();
				
				ArrayList<String> res_faildLinkUrls = new ArrayList<String>();
				logger.info("Getting All The Img");
				List<WebElement> linkElements = driver.findElements(By.tagName("a"));
				
				logger.info("Total Links Are : " + linkElements.size());
				int a =1;
				// iterate through each image element and retrieve the value of its alt attribute
				logger.info("Cheking Links One by One");
				
				for (WebElement imageElement : linkElements) {
					
					
					
					/* String altText = imageElement.getAttribute("alt"); */
				    String src = imageElement.getAttribute("href");
				   
				    String elementXPath = getXPath(driver, imageElement);
					logger.info("xpath" + elementXPath);

				    
			            // Create a URL object from the image source
			            URL url = new URL(src);
			
			            // Open an HTTP connection to the image URL
			            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			            connection.setRequestMethod("GET");
			
			            // Get the response code
			            int responseCode = connection.getResponseCode();
			
			            logger.info(responseCode );
			            
			            // Check if the response code indicates a broken image
			            if (responseCode != 200 && responseCode != 302 ) {
			            	Thread.sleep(1000);
			            	res_faildLinkUrls.add(src);
			            	res_faildLinkUrls_red.add(elementXPath);
							a++;
			            }else if(responseCode==302){
			            	red302Urls.add(src);
			            	red302Urls_red.add(elementXPath);
			            	a++;
			            }else {
			            	a++;
			            }
			
			            // Close the connection
			            connection.disconnect();
			       
			
			            logger.info("Link NO: " + ( a-1 )+ " Is Checked :"  );
				    		    
				}
				
				_res_faildLinkUrls = res_faildLinkUrls;
				_res_faildLinkUrls_red =res_faildLinkUrls_red;
	            _red302Urls =red302Urls;
	            
				logger.info("There Are Total " + res_faildLinkUrls.size()  +" Link Broken ");  
			} catch (Exception e) {
				
			    logger.warn("Exeption While Blank Link Check" );
			    
			}
		}else {
			
			logger.info("blank lin ignored");
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
	
	private static String getMetaTagContent(WebDriver driver, String property) {
        try {
            WebElement metaTag = driver.findElement(By.cssSelector("meta[property='" + property + "']"));
            return metaTag.getAttribute("content");
        } catch (Exception e) {
            return "NO " + property + " found"; // Return an empty string if meta tag is not found
        }
	} 
	
	
	
	
	
}