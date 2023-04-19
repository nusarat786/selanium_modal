package testCases;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import utilities.ReadConfig;

import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseClass {

	ReadConfig readconfig=new ReadConfig();
	
	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public String siteMapUrl=readconfig.getSitemapXml();
	
	List<String> chromOptionList = Arrays.asList("no-sandbox", "ignore-certificate-errors", "disable-extensions", "disable-infobars");
	ChromeOptions chromeOptions = new ChromeOptions();
	public static WebDriver driver;
	
	public static Logger logger;
	
	@Parameters("browser")
	@BeforeClass
	public void setup(String br)
	{			
		logger = Logger.getLogger("test");
		PropertyConfigurator.configure("Log4j.properties");
		
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
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		//driver.get(baseURL);
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
		System.out.println("Screenshot taken");
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

	
	
	
}
