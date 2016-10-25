package com.zhongzhou.Excavator.service.impl.webdata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.zhongzhou.Excavator.service.webdata.HttpService;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public class HttpServiceImpl implements HttpService{

	@Override
	public String HttpGetPage( String url ) throws ServiceRuntimeException {
		
		try{
			
			String html = null;

			WebClient webClient = new WebClient();
			// 1 启动JS
			webClient.getOptions().setJavaScriptEnabled(true);
			// 2 禁用Css，可避免自动二次请求CSS进行渲染
			webClient.getOptions().setCssEnabled(false);
			// 3 启动客户端重定向
			webClient.getOptions().setRedirectEnabled(true);

			// 4 js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// 5 设置超时
			webClient.getOptions().setTimeout(50000);
			
			HtmlPage htmlPage = webClient.getPage(url);
			
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(10000);
			// 网页内容
			html = htmlPage.asXml();
			html = removeTheXmlnsInXhtml(html);
			webClient.close();
			
			return html;
			
		}catch( IOException | FailingHttpStatusCodeException e ){
			
			throw new ServiceRuntimeException( e );
		}
		
		
	}
	
	/**
	 * The html may have some xmlns, and it will prevent the xslt parsing xhtml. 
	 * @param xhtml
	 * @return
	 */
	private static String removeTheXmlnsInXhtml( String xhtml ){
		
		String regex = "\\s?xmlns=\"\\S*\"";
		String newXhtml = xhtml.replaceAll(regex, "");
		
		return newXhtml;
	}
	
	private static String urlEncode( String pendingEncodeUrl ) throws UnsupportedEncodingException{
		
		return URLEncoder.encode( pendingEncodeUrl , "UTF-8");
	} 	        
}
