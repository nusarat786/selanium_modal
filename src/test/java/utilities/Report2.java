package utilities;

//Listener class used to generate Extent reports

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

import javax.mail.Multipart;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.aventstack.extentreports.model.Test;

public class Report2 extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public int testCaseNumber;

	public String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
	public String repName = "Test-Report-" + timeStamp + ".html";

	public String _URL ;

	public List<String> _spellErrors = new ArrayList<>();
	//public Map<String, String> globalFont = new HashMap<>();
	
	public List<String> _h1Texts = new ArrayList<>();
	
	public List<String> _headerList = new ArrayList<>();
	
	public String _page_title = "_";
	public String _meta_description = "_";
	
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
	
	public void onStart(ITestContext testContext) {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + repName);// specify
																											// location
																											// of the
																											// report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "Nusarat");
		
		
		htmlReporter.config().setDocumentTitle("Project"); // Tile of report
		htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
		
		//htmlReporter.config().setCSS("custom-styles.css");
		
	}

	public void onTestSuccess(ITestResult tr) {
		
		// 1. Getting All Test Data
		try {
			_page_title = (String) tr.getTestClass().getRealClass().getField("_page_title").get(tr.getInstance());
			_meta_description = (String) tr.getTestClass().getRealClass().getField("_meta_description").get(tr.getInstance());
			
			_spellErrors = (List<String>) tr.getTestClass().getRealClass().getField("_spellErrors").get(tr.getInstance());
			
			_h1Texts = (List<String>) tr.getTestClass().getRealClass().getField("_h1Texts").get(tr.getInstance());
			
			_headerList = (List<String>) tr.getTestClass().getRealClass().getField("_headerList").get(tr.getInstance());
			
			_ogType = (String) tr.getTestClass().getRealClass().getField("_ogType").get(tr.getInstance());
			
			_ogTitle = (String) tr.getTestClass().getRealClass().getField("_ogTitle").get(tr.getInstance());
			
			_ogUrl = (String) tr.getTestClass().getRealClass().getField("_ogUrl").get(tr.getInstance());
			
			_ogImage = (String) tr.getTestClass().getRealClass().getField("_ogImage").get(tr.getInstance());
			
			_is404 = (boolean) tr.getTestClass().getRealClass().getField("_is404").get(tr.getInstance());
			
			_is302 = (boolean) tr.getTestClass().getRealClass().getField("_is302").get(tr.getInstance());
			

			_resTime = (double) tr.getTestClass().getRealClass().getField("_resTime").get(tr.getInstance());
			
			_URL = (String) tr.getTestClass().getRealClass().getField("_URL").get(tr.getInstance());
			
			_alt_faildImageUrls = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_alt_faildImageUrls").get(tr.getInstance());
			_alt_faildImageUrls_red = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_alt_faildImageUrls_red").get(tr.getInstance());
			
			_res_faildImageUrls = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_res_faildImageUrls").get(tr.getInstance());
			_res_faildImageUrls_red = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_res_faildImageUrls_red").get(tr.getInstance());

			_res_faildLinkUrls = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_res_faildLinkUrls").get(tr.getInstance());
			_res_faildLinkUrls_red = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_res_faildLinkUrls_red").get(tr.getInstance());
			
			_title_faildLinkUrls = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_title_faildLinkUrls").get(tr.getInstance());
			_title_faildLinkUrls_red = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_title_faildLinkUrls_red").get(tr.getInstance());
			
			_red302Urls = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_red302Urls").get(tr.getInstance());
			_red302Urls_red = (ArrayList<String>) tr.getTestClass().getRealClass().getField("_red302Urls_red").get(tr.getInstance());
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			System.out.print("error");
		}
		
		
		// 2 . Printing Data
		
		System.out.println("_page_title: " + _page_title);
		System.out.println("_meta_description: " + _meta_description);
		System.out.println("_ogType: " + _ogType);
		System.out.println("_ogTitle: " + _ogTitle);
		System.out.println("_ogUrl: " + _ogUrl);
		System.out.println("_ogImage: " + _ogImage);
		System.out.println("_is404: " + _is404);
		System.out.println("_is302: " + _is302);
		System.out.println("alt fai" + _alt_faildImageUrls_red.size());
		
		
		// Q3. Rsponse Code 
		
		String resCode = getResponseStatusCode();
		
		
		
		
		
		
		//4. Test Created
        
		test = extent.createTest("Test with Table");
        
     
		
		//5. Info Div Is Created
		
        String infoDiv = "<div>" +
                "<span class=\"label start-time\" >Responesecode : " + resCode  + " </span> &nbsp&nbsp"  +
                "<span  class=\"label  end-time white-text\"   >Response Time : " + _resTime +  " (sec) </span>&nbsp&nbsp" +
                "<span class=\"label start-time white-text\">" +
                "<a class= \" white-text \"href="+_URL +" target=\"_blank\">Page Url</a>" +
                "</span>" +
                "</div>";
        
        test.log(Status.INFO, infoDiv);
        
        
        
        // 6. Table Is Created
        
        boolean isHeaderSeq = areHeadersInSequence(_headerList);
        
        
        // Create a table using HTML markup
        StringBuilder tableMarkup = new StringBuilder();
        
        
        
        tableMarkup.append("<table>");
        tableMarkup.append("<tr>");
        tableMarkup.append("<th>No</th>");
        tableMarkup.append("<th class=\"wid-40\">Check Point Name</th>");
        tableMarkup.append("<th class=\"wid-40\">Check Point Value</th>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_page_title) + ";'>");
        tableMarkup.append("<td>1</td>");
        tableMarkup.append("<td>" + escapeHtml("Page Title") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_page_title) + "</td>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_meta_description) + ";'>");
        tableMarkup.append("<td>2</td>");
        tableMarkup.append("<td>" + escapeHtml("Meta Description") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_meta_description) + "</td>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_ogType) + ";'>");
        tableMarkup.append("<td>3</td>");
        tableMarkup.append("<td>" + escapeHtml("OG Type") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_ogType) + "</td>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_ogTitle) + ";'>");
        tableMarkup.append("<td>4</td>");
        tableMarkup.append("<td>" + escapeHtml("OG Title") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_ogTitle) + "</td>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_ogUrl) + ";'>");
        tableMarkup.append("<td>5</td>");
        tableMarkup.append("<td>" + escapeHtml("OG URL") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_ogUrl) + "</td>");
        tableMarkup.append("</tr>");
        tableMarkup.append("<tr style='background-color: " + getBackgroundColor(_ogImage) + ";'>");
        tableMarkup.append("<td>6</td>");
        tableMarkup.append("<td>" + escapeHtml("OG Image") + "</td>");
        tableMarkup.append("<td>" + escapeHtml(_ogImage) + "</td>");
        tableMarkup.append("</tr>");
        
        
        
        tableMarkup.append("<tr style='background-color: " + getColor(isHeaderSeq) + ";'>");
        tableMarkup.append("<td>7</td>");
        tableMarkup.append("<td>" + escapeHtml("Is Heading in Seq") + "</td>");
        tableMarkup.append("<td>" + isHeaderSeq + "</td>");
        tableMarkup.append("</tr>");
        
        tableMarkup.append("<tr style='background-color: " + getColor(_spellErrors.size()==0) + ";'>");
        tableMarkup.append("<td>8</td>");
        tableMarkup.append("<td>" + escapeHtml("No Spell Err") + "</td>");
        tableMarkup.append("<td>" + (_spellErrors.size()==0 )+ "</td>");
        tableMarkup.append("</tr>");
        
      
        tableMarkup.append("<tr style='background-color: " + getColor(_h1Texts.size()==1)  + ";'>");
        tableMarkup.append("<td>9</td>");
        tableMarkup.append("<td>" + escapeHtml("H1 Value") + "</td>");
       
        if (_h1Texts.isEmpty()) {
        	  tableMarkup.append("<td>No H1</td>");
        	} else {
        	  for (String h1 : _h1Texts) {
        	    tableMarkup.append("<td>" + h1 + "</td>");
        	  }
        	}

        
        tableMarkup.append("</tr>");
        
        tableMarkup.append("</table>");

        // Add the table to the report
        test.log(com.aventstack.extentreports.Status.INFO, tableMarkup.toString());

        
        
        // 7. Header Node
        ExtentTest headerNode = test.createNode("Header List", "This is Header Node");
        
        
        if(_headerList.size()>=1) { 
     // Create a new node under the existing test
        
        System.out.println(formatStringHierarchy(_headerList));
        
        headerNode.log(Status.INFO, formatStringHierarchy(_headerList));
        }else {
        	headerNode.log(Status.INFO, "No Header Found");
        }
        
        
        // newTest.setInternalTest(true);
        //newTest.setLog(newTest.getLog().get(0));
        
        
       // 8 Spell Table
        
        ExtentTest spellNode = test.createNode("Spelling Mistakes" + " ( " + _spellErrors.size() +" ) ", "This is Spellink Mistake Node");
        StringBuilder spellTable = new StringBuilder();
        
        if(_spellErrors.size()>=1) { 
        
        
        spellTable.append("<table>");
        spellTable.append("<tr>");
        spellTable.append("  <th>No</th>");
        spellTable.append("  <th>Spell Mistake</th>");
        spellTable.append("  <th>Spell Sug</th>");
        spellTable.append("</tr>");

        // Iterate over the _spell_errors list and append table rows
        for (int i = 0; i < _spellErrors.size(); i++) {
        	int startIndex = _spellErrors.get(i).indexOf(":") + 1;
        	int endIndex = _spellErrors.get(i).lastIndexOf(":");
        	String word_sm = _spellErrors.get(i).substring(startIndex, endIndex).trim();
        	String word_sg = _spellErrors.get(i).substring(endIndex+1).trim();
        	
            spellTable.append("<tr class= \"end-time white-text\">");
            spellTable.append("  <td>").append(i + 1).append("</td>"); // Number column
            spellTable.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(word_sm).append("</td>"); // Spell Mistake column
            spellTable.append("  <td >").append(word_sg).append("</td>"); // Spell Mistake column
            spellTable.append("</tr>");
            

        }
        
        spellTable.append("</table>");
        
        
        spellNode.log(Status.INFO, spellTable.toString());
        }else {
        	 
        	spellTable.append("No Spelling Mistake Found");
            spellNode.log(Status.INFO, spellTable.toString());   
        }
        
        setNodeStatus(spellNode,_spellErrors.size());
        // 9. Image Alt Node 
        
        ExtentTest imgAltNode = test.createNode("Image Alt"  + " ( " + _alt_faildImageUrls.size() +" ) ", "Image Alt Node");
        
        StringBuilder imgAltTable = new StringBuilder();
       
       if(_alt_faildImageUrls.size()>=1) { 
        
        imgAltTable.append("<table>");
        imgAltTable.append("<tr>");
        imgAltTable.append("  <th>No</th>");
        imgAltTable.append("  <th>Img Link</th>");
        imgAltTable.append("  <th>X path</th>");
        imgAltTable.append("</tr>");

        
        for (int i = 0; i < _alt_faildImageUrls.size(); i++) {
        	
        	String imgLink = _alt_faildImageUrls.get(i);
        	String redLink = _alt_faildImageUrls_red.get(i);
        	
        	//imgAltTable.append("  <td>").append(i + 1).append("</td>"); // Number column
        	
        	imgAltTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
            .append("<a href=\"").append(_URL).append("\" target=\"_blank\" class=\"white-text\">"+ (i + 1) + "</a>")
            .append("</td>");
        	
        	    // Image URL with link that opens in a new window
        	imgAltTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
        	            .append("<a href=\"").append(imgLink).append("\" target=\"_blank\" class=\"white-text\">Click 4 Image</a>")
        	            .append("</td>");
        	   
        	
        	imgAltTable.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(escapeHtml(redLink)).append("</td>"); // Spell Mistake column
            
        	
        	
        	imgAltTable.append("</tr>");
        	    // Red URL with link that opens in a new window
        	//copyTextToClipboard(text)
			/*
			 * imgAltTable.append("  <td onclick=\"copyTextToClipboard('" +
			 * escapeHtml(redLink) +
			 * "')\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">"
			 * ) .append("<a href=\"").append(_URL + escapeHtml(redLink)).
			 * append("\" target=\"_blank\" class=\"white-text\">Click 4 Xpath</a>")
			 * .append("</td>");
			 */
        	
        	
            

        }
        
        imgAltTable.append("</table>");
        
        imgAltNode.log(Status.INFO, imgAltTable.toString());
		
       }else {
    	   
    	   imgAltTable.append("No Image Without Alt Found");
    	   imgAltNode.log(Status.INFO, imgAltTable.toString()); 
       }
       
       setNodeStatus(imgAltNode,_alt_faildImageUrls.size());
       
       // 9. Image Blank Node 
       
       ExtentTest imgBlankNode = test.createNode("Blank Image  " + " ( " + _res_faildImageUrls.size() +" ) ", "Blank Image Node");
       
       StringBuilder imgBlankTable = new StringBuilder();
      
      if(_res_faildImageUrls.size()>=1) { 
       
    	  imgBlankTable.append("<table>");
    	  imgBlankTable.append("<tr>");
    	  imgBlankTable.append("  <th>No</th>");
    	  imgBlankTable.append("  <th>Img Link</th>");
    	  imgBlankTable.append("  <th>X path</th>");
    	  imgBlankTable.append("</tr>");

       
       for (int i = 0; i < _res_faildImageUrls.size(); i++) {
       	
       	String imgLink = _res_faildImageUrls.get(i);
       	String redLink = _res_faildImageUrls_red.get(i);
       	
       	//imgAltTable.append("  <td>").append(i + 1).append("</td>"); // Number column
       	
       	imgBlankTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
           .append("<a href=\"").append(_URL).append("\" target=\"_blank\" class=\"white-text\">"+ (i + 1) + "</a>")
           .append("</td>");
       	
       	    // Image URL with link that opens in a new window
       	imgBlankTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
       	            .append("<a href=\"").append(imgLink).append("\" target=\"_blank\" class=\"white-text\">Click 4 Image</a>")
       	            .append("</td>");
       	   
       	
       	imgBlankTable.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(escapeHtml(redLink)).append("</td>"); // Spell Mistake column
           
       	
       	
       	imgBlankTable.append("</tr>");
       	    // Red URL with link that opens in a new window
       	//copyTextToClipboard(text)
			/*
			 * imgAltTable.append("  <td onclick=\"copyTextToClipboard('" +
			 * escapeHtml(redLink) +
			 * "')\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">"
			 * ) .append("<a href=\"").append(_URL + escapeHtml(redLink)).
			 * append("\" target=\"_blank\" class=\"white-text\">Click 4 Xpath</a>")
			 * .append("</td>");
			 */
       	
       	
           

       }
       
       imgBlankTable.append("</table>");
       
       imgBlankNode.log(Status.INFO, imgBlankTable.toString());
		
      }else {
   	   
    	imgBlankTable.append("All Image With 200 Response Code");
    	imgBlankNode.log(Status.INFO, imgBlankTable.toString()); 
      }
       
      setNodeStatus(imgBlankNode,_res_faildImageUrls.size());
      
      // 9. Link Title 
      
      ExtentTest linkTitleNode = test.createNode("Link Without Title  " + " ( " + _title_faildLinkUrls.size() +" ) ", "Link Without Title Node");
      
      StringBuilder linkTitleTable = new StringBuilder();
     
     if(_title_faildLinkUrls.size()>=1) { 
      
    	 linkTitleTable.append("<table>");
    	 linkTitleTable.append("<tr>");
    	 linkTitleTable.append("  <th>No</th>");
    	 linkTitleTable.append("  <th>Link URL</th>");
    	 linkTitleTable.append("  <th>X path</th>");
    	 linkTitleTable.append("</tr>");

      
      for (int i = 0; i < _title_faildLinkUrls.size(); i++) {
      	
      	String imgLink = _title_faildLinkUrls.get(i);
      	String redLink = _title_faildLinkUrls_red.get(i);
      	
      	//imgAltTable.append("  <td>").append(i + 1).append("</td>"); // Number column
      	
      	linkTitleTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
          .append("<a href=\"").append(_URL).append("\" target=\"_blank\" class=\"white-text\">"+ (i + 1) + "</a>")
          .append("</td>");
      	
      	    // Image URL with link that opens in a new window
      	linkTitleTable.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
      	            .append("<a href=\"").append(imgLink).append("\" target=\"_blank\" class=\"white-text\">Click 4 Image</a>")
      	            .append("</td>");
      	   
      	
      	linkTitleTable.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(escapeHtml(redLink)).append("</td>"); // Spell Mistake column
          
      	
      	
      	linkTitleTable.append("</tr>");
      	    // Red URL with link that opens in a new window
      	//copyTextToClipboard(text)
			/*
			 * imgAltTable.append("  <td onclick=\"copyTextToClipboard('" +
			 * escapeHtml(redLink) +
			 * "')\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">"
			 * ) .append("<a href=\"").append(_URL + escapeHtml(redLink)).
			 * append("\" target=\"_blank\" class=\"white-text\">Click 4 Xpath</a>")
			 * .append("</td>");
			 */
      	
      	
          

      }
      
      linkTitleTable.append("</table>");
      linkTitleNode.log(Status.INFO, linkTitleTable.toString());
		
     }else {
  	   
       linkTitleTable.append("All Link With title ");
       linkTitleNode.log(Status.INFO, linkTitleTable.toString()); 
     }
     
     setNodeStatus(linkTitleNode,_title_faildLinkUrls.size());
     
     
     
     // 10. Link 404
     
     ExtentTest link404Node = test.createNode("Link with 404  " + " ( " + _res_faildLinkUrls.size() +" ) ", "404 Link ");
     
     StringBuilder link404Table = new StringBuilder();
    
    if(_res_faildLinkUrls.size()>=1) { 
     
    	link404Table.append("<table>");
    	link404Table.append("<tr>");
    	link404Table.append("  <th>No</th>");
    	link404Table.append("  <th>Link URL</th>");
    	link404Table.append("  <th>X path</th>");
    	link404Table.append("</tr>");

     
     for (int i = 0; i < _res_faildLinkUrls.size(); i++) {
     	
     	String imgLink = _res_faildLinkUrls.get(i);
     	String redLink = _res_faildLinkUrls_red.get(i);
     	
     	//imgAltTable.append("  <td>").append(i + 1).append("</td>"); // Number column
     	
     	link404Table.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
         .append("<a href=\"").append(_URL).append("\" target=\"_blank\" class=\"white-text\">"+ (i + 1) + "</a>")
         .append("</td>");
     	
     	    // Image URL with link that opens in a new window
     	link404Table.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
     	            .append("<a href=\"").append(imgLink).append("\" target=\"_blank\" class=\"white-text\">Click 4 Image</a>")
     	            .append("</td>");
     	   
     	
     	link404Table.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(escapeHtml(redLink)).append("</td>"); // Spell Mistake column
         
     	
     	
     	link404Table.append("</tr>");
     	    // Red URL with link that opens in a new window
     	//copyTextToClipboard(text)
			/*
			 * imgAltTable.append("  <td onclick=\"copyTextToClipboard('" +
			 * escapeHtml(redLink) +
			 * "')\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">"
			 * ) .append("<a href=\"").append(_URL + escapeHtml(redLink)).
			 * append("\" target=\"_blank\" class=\"white-text\">Click 4 Xpath</a>")
			 * .append("</td>");
			 */
     	
     	
         

     }
     
     link404Table.append("</table>");
     link404Node.log(Status.INFO, link404Table.toString());
		
    }else {
 	   
    	link404Table.append("No Link With 404 Response Code");
    	link404Node.log(Status.INFO, link404Table.toString()); 
    }
    
    setNodeStatus(link404Node,_res_faildLinkUrls_red.size());
    
    
    
 // 11. Link 302
    
    ExtentTest res302Node = test.createNode("Resource With 302 RC  " + " ( " + _red302Urls.size() +" ) ", "Res 302 RC");
    
    StringBuilder res302Table = new StringBuilder();
   
   if(_red302Urls.size()>=1) { 
    
	   res302Table.append("<table>");
	   res302Table.append("<tr>");
	   res302Table.append("  <th>No</th>");
	   res302Table.append("  <th>Resource URL</th>");
	   res302Table.append("  <th>X path</th>");
	   res302Table.append("</tr>");

    
    for (int i = 0; i < _red302Urls.size(); i++) {
    	
    	String imgLink = _red302Urls.get(i);
    	String redLink = _red302Urls_red.get(i);
    	
    	//imgAltTable.append("  <td>").append(i + 1).append("</td>"); // Number column
    	
    	res302Table.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
        .append("<a href=\"").append(_URL).append("\" target=\"_blank\" class=\"white-text\">"+ (i + 1) + "</a>")
        .append("</td>");
    	
    	    // Image URL with link that opens in a new window
    	res302Table.append("  <td onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">")
    	            .append("<a href=\"").append(imgLink).append("\" target=\"_blank\" class=\"white-text\">Click 4 Image</a>")
    	            .append("</td>");
    	   
    	
    	res302Table.append("  <td onclick=\"copyCell(this)\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\" >").append(escapeHtml(redLink)).append("</td>"); // Spell Mistake column
        
    	
    	
    	res302Table.append("</tr>");
    	    // Red URL with link that opens in a new window
    	//copyTextToClipboard(text)
			/*
			 * imgAltTable.append("  <td onclick=\"copyTextToClipboard('" +
			 * escapeHtml(redLink) +
			 * "')\" onmouseover=\"highlightCell(this)\" onmouseout=\"unhighlightCell(this)\">"
			 * ) .append("<a href=\"").append(_URL + escapeHtml(redLink)).
			 * append("\" target=\"_blank\" class=\"white-text\">Click 4 Xpath</a>")
			 * .append("</td>");
			 */
    	
    	
        

    }
    
    res302Table.append("</table>");
    res302Node.log(Status.INFO, res302Table.toString());
		
   }else {
	   
	   res302Table.append("No Resource Liink With 302 Response Code");
	   res302Node.log(Status.INFO, res302Table.toString()); 
   }
      
   setNodeStatus(res302Node,_red302Urls.size());
   
   
        StringBuilder testName = new StringBuilder(tr.getMethod().getMethodName());

		Object[] parameters = tr.getParameters();

//		try {
//			String page_title = (String) tr.getTestClass().getRealClass().getField("_page_title").get(tr.getInstance());
//			testName.append("_" + _page_title);
//			System.out.println(_page_title);
//
//		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//			System.out.print("error");
//		}

		
		
		test = extent.createTest(testName.toString()); // create new entry in th report
		
		
        

        

		
		test.log(Status.PASS, MarkupHelper.createLabel(testName.toString(), ExtentColor.GREEN)); // send the passed
		test.log(Status.INFO, "Parameter Details:");
		
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		StringBuilder testName = new StringBuilder(tr.getMethod().getMethodName());

		Object[] parameters = tr.getParameters();

		StringBuilder p = new StringBuilder();
		StringBuilder y = new StringBuilder();


		

		// System.out.print("String Is " + testName );
		// System.out.println("String Is " + p.toString() );
		test = extent.createTest(testName.toString()); // create new entry in th report

		String linkText = "URL";
		String linkUrl = p.toString();
		String link = "<a href='" + linkUrl + "' target='_blank' style='color:#00c853'>" + linkText + "</a>";
		String message = "Please Click " + link + " to view the failed Url Page.";

		test.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED)); // send the passed information to
																						// the report with GREEN color

		//ExtentTest descriptionNode = logger.createNode("Image Urls").fail("Assertion Failed");
		//descriptionNode.assignCategory(""); // add a category to the node
		StringBuilder sb = new StringBuilder();

		

		
		
		
		
		

		
		

	}

	/*
	 * public void onTestSkipped(ITestResult tr) { test =
	 * extent.createTest(tr.getName()); // create new entry in th report
	 * test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(),
	 * ExtentColor.ORANGE)); }
	 */

	public void onFinish(ITestContext testContext) {
		extent.flush();
		Class<?> currentClass = testContext.getCurrentXmlTest().getClasses().get(0).getSupportClass();
		String className = currentClass.getSimpleName();
		//sendEmail(className);

	}

	public void sendEmail(String Subject) {

		// Set up email authentication credentials
		final String username = "bharatkhand370@gmail.com";
		final String password = "rodtowncrzcglzdq";

		// Set up the email properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Set up the session with the email authentication
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Set up the email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bharatkhand370@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Nusarat.Haveliwala@investisdigital.com"));
			message.setSubject(Subject + "__Report Finland");
			message.setText("Please find the report attached.");

			// Set up the email attachment
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			String filename = System.getProperty("user.dir") + "/test-output/" + repName;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the email with the attached report
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Email sent with attachment successfully.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	// Helper method to escape special characters in HTML
    private static String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
    
    
 // Helper method to determine the background color based on checkpoint value
    private static String getBackgroundColor(String checkpointValue) {
        return checkpointValue.substring(0, 2).equalsIgnoreCase("NO") ? "#ef5350" : "green";
    }
    
    
    public String getResponseStatusCode() {
        if (_is404) {
            return "400+";
        } else if (_is302) {
            return "302";
        } else {
            return "200";
        }
    }
    
    
    public static String formatStringHierarchy(List<String> data) {
        StringBuilder output = new StringBuilder();

        for (String entry : data) {
            int level = getHierarchyLevel(entry);
            String content = entry.substring(3); // Remove the "hn:" part

            String indentation = "&nbsp;&nbsp;".repeat(level - 1);
            String tagName = "h" + level;
            output.append(indentation).append(tagName).append(": ").append(content).append("<br>").append("\n");
        }

        return output.toString();
    }

    private static int getHierarchyLevel(String entry) {
        return entry.charAt(1) - '0'; // Extract the hierarchy level
    }
    
    
    public static boolean areHeadersInSequence(List<String> headerList) {
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

        return areHeadersInSequence;
    }

    public static String getColor(boolean value) {
        if (value) {
            return "green";
        } else {
            return "#ef5350";
        }
        
        
    }
    
    public void setNodeStatus(ExtentTest node, int arraySize) {
    	  if (arraySize == 0) {
    	    node.pass("pass");
    	  } else {
    	    node.fail("fail");
    	  }
    	}
}
