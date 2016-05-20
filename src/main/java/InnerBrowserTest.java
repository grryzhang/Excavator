import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.swing.JFrame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;


public class InnerBrowserTest {

	@BeforeClass  
	public static void configTest(){

		try{
			
		
		}catch(Exception e){
			System.out.println("Catch the exception.");
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
	
		try {
			
			String  url="http://baojia.steelcn.cn";//想采集的网址
            
            URL link=new URL( url ); 
            WebClient wc=new WebClient();
            WebRequest request=new WebRequest(link); 
            request.setCharset("UTF-8");
            
            String refer="baojia.steelcn.cn";
            //request.setAdditionalHeader("Referer", refer);
            
            ////设置请求报文头里的User-Agent字段
            request.setAdditionalHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            request.setAdditionalHeader("Accept-Encoding", "gzip, deflate");
            request.setAdditionalHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            request.setAdditionalHeader("Connection", "keep-alive");
            request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            //wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            //wc.addRequestHeader和request.setAdditionalHeader功能应该是一样的。选择一个即可。
            //其他报文头字段可以根据需要添加
            wc.getCookieManager().setCookiesEnabled(true);//开启cookie管理
            wc.getOptions().setJavaScriptEnabled(true);//开启js解析。对于变态网页，这个是必须的
            wc.getOptions().setCssEnabled(true);//开启css解析。对于变态网页，这个是必须的。
            wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
            wc.getOptions().setThrowExceptionOnScriptError(false);
            wc.getOptions().setTimeout(10000);
            //设置cookie。如果你有cookie，可以在这里设置
            Set<Cookie> cookies=null;
            /*
            if( cookies != null ){
            	
            	Iterator<Cookie> i = cookies.iterator();
            	while (i.hasNext()) 
                {
                    wc.getCookieManager().addCookie(i.next());
                }
            }
            */
            HtmlPage page=null;
            page = wc.getPage(request);
			
			String content = page.getWebResponse().getContentAsString("UTF-8");
			
			FileOutputStream fos = null; 
			OutputStreamWriter outputWriter = null;
	  
			String fileName = "d:/test.html";  
	  
			try {  
				// 打开一个已存在文件的输出流  
				fos = new FileOutputStream(fileName);  
				outputWriter = new OutputStreamWriter( fos,"UTF-8");
				outputWriter.write( content );
				outputWriter.flush();
			} catch (IOException e1) {  
				e1.printStackTrace();  
			} finally{  
				outputWriter.close();
				fos.close();  
			}  
		} catch (Exception e) {
			System.out.println("Catch the exception.");
			e.printStackTrace();
		}
	}
}