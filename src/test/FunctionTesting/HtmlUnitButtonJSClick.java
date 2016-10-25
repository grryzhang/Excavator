package FunctionTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HtmlUnitButtonJSClick {

	
	//webClient.waitForBackgroundJavaScript() or
	//webClient.waitForBackgroundJavaScriptStartingBefore()
	//@Test
	public void getData() throws Exception {  
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
        final HtmlPage page = webClient.getPage("http://ebusiness.coscon.com/NewEBWeb/public/inbound/arrivalCargoInfo.xhtml");  
        // Form没有name和id属性  
        final HtmlTextInput textField = page.getElementByName("billReferenceCode");
        final HtmlButton button = page.getElementByName("arrivalCargoInfoFindButton");
        textField.setValueAttribute("4507946710");  
        
        final HtmlPage resultPage = button.click();  
        
        try {
            Thread.sleep(2000);                 //1000 毫秒，也就是1秒.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        String pageAsText = resultPage.asText(); 
        System.out.println( pageAsText );
        
        final HtmlAnchor anchor = (HtmlAnchor)page.getHtmlElementById("banner02"); 
        HtmlPage resultPage1 = anchor.click(); 
        
        try {
            Thread.sleep(2000);                 //1000 毫秒，也就是1秒.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        String test1 = resultPage1.asXml();
        
        System.out.println( test1 );
        
        webClient.close();
    } 
	
	@Test
	public void AlibabaSearch() throws Exception {  
		
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
        HtmlPage page = webClient.getPage("http://www.alibaba.com");  
        
        HtmlTextInput searchTextInput = page.getElementByName("SearchText");
        HtmlSubmitInput searchSubmitButton = page.getFirstByXPath("//input[@class='ui-searchbar-submit']");
        
        System.out.println( searchTextInput );
        System.out.println( searchSubmitButton );
    }  

}
