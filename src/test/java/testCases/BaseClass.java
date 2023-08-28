package testCases;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utilities.ReadConfig;

import org.openqa.selenium.remote.DesiredCapabilities;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
public class BaseClass {
	//TC_SCO_CONTENT
	ReadConfig readconfig=new ReadConfig();
	
	public String _id = "is_express" ;
	public String _password = "express@is!";
	
	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public String siteMapUrl=readconfig.getSitemapXml();
	
	public long Response_Time = (long) 0.00;
	
	//String name = JOptionPane.showInputDialog(null, "Enter Sitemap.xml Link");
	
	public LocalDateTime now = LocalDateTime.now();
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
	
	public String date = formatter.format(now);
	
	List<String> chromOptionList = Arrays.asList("no-sandbox", "ignore-certificate-errors", "disable-extensions", "disable-infobars");
	ChromeOptions chromeOptions = new ChromeOptions();
	public static WebDriver driver;
	
	public static Logger logger;
	
	public static boolean _checkImagealt;
	public static boolean _checkImageblank;
	public static boolean _checkLinktitle;
	public static boolean _checkLinkblank;
	
	@Parameters({"browser", "screen","checkImagealt","checkImageblank","checkLinktitle","checkLinkblank"})
	@BeforeClass
	public void setup(String br,String screen,boolean checkImagealt ,boolean checkImageblank,boolean checkLinktitle,boolean checkLinkblank) throws UnsupportedEncodingException
	{			
		
		_checkImagealt = checkImagealt;
		_checkImageblank = checkImageblank;
		_checkLinktitle = checkLinktitle;
		_checkLinkblank = checkLinkblank;
		
		logger = Logger.getLogger("test");
		PropertyConfigurator.configure("Log4j.properties");
		
		logger.info(checkImagealt);
		
		if(br.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",readconfig.getChromePath());
			chromeOptions.addArguments("--remote-allow-origins=*");
//			chromeOptions.addExtensions(new File(System.getProperty("user.dir") + "/gighmmpiobklfepjocnamgkkbiglidom-5.4.1-Crx4Chrome.com.crx"));
//			chromeOptions.addExtensions(new File(System.getProperty("user.dir") + "/cfhdojbkjhnklbpkdaibdccddilifddb.crx"));
			//chromeOptions.addExtensions(new File("C:\\Users\\Nusarat.Haveliwala\\Desktop\\New folder (2)\\inetbankingV1\\addblock.crx"));
	
			//String dirt = System.getProperty("user.dir");
			chromeOptions.addArguments("load-extension="+"C:\\Users\\Nusarat.Haveliwala\\Desktop\\New folder (2)\\inetbankingV1\\addblock.crx");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			
			driver=new ChromeDriver();			
		}
		else if(br.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver",readconfig.getFirefoxPath());
			driver = new FirefoxDriver();
		}
		else if(br.equals("ie"))
		{
			System.setProperty("webdriver.ie.driver",readconfig.getIEPath());
			driver = new InternetExplorerDriver();
		}
		
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		 Dimension size;
	        switch (screen.toLowerCase()) {
	            case "window":
	                size = new Dimension(800, 600); // Set window size to 800x600
	                break;
	            case "mobile":
	                size = new Dimension(360, 640); // Set mobile size to 360x640
	                break;
	            case "tablet":
	                size = new Dimension(768, 1024); // Set tablet size to 768x1024
	                break;
	            default:
	                size = new Dimension(1280, 1024); // Set default size to 1280x1024
	                break;
	        }
	        
	        
	        driver.manage().window().setSize(size);
		
		
		
		
		
			/*
			 * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 * 
			 * String URL="synergia-energy-ltd-d9.sid2-e1.investis.com"; String
			 * user3="synergia-energy-ltd"; String pass3="7*s=hYM+CGYM9nfG";
			 * 
			 * String username3=URLEncoder.encode(user3,StandardCharsets.UTF_8.toString());
			 * 
			 * String passtest3=URLEncoder.encode(pass3,StandardCharsets.UTF_8.toString());
			 * String url3="https://"+username3+":"+passtest3+"@"+ URL;
			 * 
			 * 
			 * 
			 * 
			 * driver.get(url3);
			 * 
			 * 
			 */
        // Continue with any subsequent actions after authorization
		
		
		
	}
	
	@AfterClass
	public void tearDown()
	{
		//driver.quit();
	}
	
	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		logger.info("screenshot taken");
	}
	
	public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
	
public static void writeXml(String fileName,String CheckName, String url, LocalDateTime time) {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		
		logger.info("Trying To Write Failed Url");
		String filename = fileName+"_" + formatter.format(now) + "_" + ".xml" ; 
		File file = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			if (file.exists()) {
				// If the file already exists, parse it and add a new element to the root
				doc = dBuilder.parse(file);
				Element root = doc.getDocumentElement();
				Element failedURL = doc.createElement( fileName );
				failedURL.appendChild(createElement(doc, "url", url));
				failedURL.appendChild(
						createElement(doc, "time", time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
				root.appendChild(failedURL);
				logger.info("created");
			} else {
				// If the file doesn't exist, create a new document and root element
				doc = dBuilder.newDocument();
				Element root = doc.createElement(fileName + "URLs");
				doc.appendChild(root);
				Element failedURL = doc.createElement(fileName);
				failedURL.appendChild(createElement(doc, "url", url));
				failedURL.appendChild(
						createElement(doc, "time", time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
				root.appendChild(failedURL);
				logger.info("created 2");
			}

			// Write the document to the XML file
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			logger.info( CheckName + " and time written to " + filename);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
			logger.info("Failed");
		}
	}

	public static Element createElement(Document doc, String tagName, String textContent) {
		Element element = doc.createElement(tagName);
		element.setTextContent(textContent);
		return element;
	}
public static void writeXmlnd(String fileName,String CheckName, String url,String src, LocalDateTime time) {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		
		logger.info("Trying To Write Failed Url");
		String filename = fileName+"_" + formatter.format(now) + "_" + ".xml" ; 
		File file = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			if (file.exists()) {
				// If the file already exists, parse it and add a new element to the root
				doc = dBuilder.parse(file);
				Element root = doc.getDocumentElement();
				Element failedURL = doc.createElement( fileName );
				failedURL.appendChild(createElement(doc, "url", url));
				failedURL.appendChild(
						createElement(doc, "time", time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
				root.appendChild(failedURL);
				logger.info("created");
			} else {
				// If the file doesn't exist, create a new document and root element
				doc = dBuilder.newDocument();
				Element root = doc.createElement(fileName + "URLs");
				doc.appendChild(root);
				Element failedURL = doc.createElement(fileName);
				failedURL.appendChild(createElement(doc, "url", url));
				failedURL.appendChild(createElement(doc, "src", src));
				failedURL.appendChild(
						createElement(doc, "time", time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
				root.appendChild(failedURL);
				logger.info("created 2");
			}

			// Write the document to the XML file
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			logger.info( CheckName + " and time written to " + filename);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
			logger.info("Failed");
		}
	}


public static String getXPath(WebDriver driver, WebElement element) {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    return (String) jsExecutor.executeScript(
            "function getElementXPath(element) {" +
                    "   if (element && element.id)" +
                    "       return '//*[@id=\"' + element.id + '\"]';" +
                    "   else {" +
                    "       var path = getPath(element);" +
                    "       return path ? '//' + path.toLowerCase() : null;" +
                    "   }" +
                    "}" +
                    "function getPath(element) {" +
                    "   var path = '';" +
                    "   while (element) {" +
                    "       var elementTag = element.nodeName.toLowerCase();" +
                    "       var siblingIndex = 0;" +
                    "       var sibling = element.previousElementSibling;" +
                    "       while (sibling) {" +
                    "           if (sibling.nodeName.toLowerCase() === elementTag) {" +
                    "               siblingIndex++;" +
                    "           }" +
                    "           sibling = sibling.previousElementSibling;" +
                    "       }" +
                    "       var elementPathIndex = (siblingIndex > 0 ? '[' + (siblingIndex + 1) + ']' : '');" +
                    "       path = elementTag + elementPathIndex + '/' + path;" +
                    "       element = element.parentElement;" +
                    "   }" +
                    "   return path;" +
                    "}" +
                    "return getElementXPath(arguments[0]);", element);
}
	
}
