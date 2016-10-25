package com.zhongzhou.Excavator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class PDFParserTest {
		private static XmlWebApplicationContext  context;
		private static String[] configs = { "classpath:applicationContext.xml" };  
		
		
		public static final String PURCHASE_ORDER_NUMBER = "Purchase Order Number: ";
		public static final String PURCHASE_ORDER_DATE = "Purchase Order Date: ";
		public static final String PHONE = "Phone: ";
		public static final String FAX = "Fax: ";
		public static final String FAX2 = "Fax : ";
		public static final String E_MAIL = "E-mail:";
		public static final String FREIGHT = "Freight: ";
		public static final String TERMS = "Terms: ";
		public static final String EXPECTED_RECEIPT_DATE = "Expected Receipt Date: ";
		public static final String GST = "# GST: ";
		public static final String QST = "# QST: ";
		public static final String BUYER = "Buyer:";
		public static final String USER = "User: ";
		public static final String PHONE_NO = "Phone No.: ";
		public static final String SUBTOTAL = "Subtotal: ";
		public static final String TOTAL = "Total: ";
		public static final String BY = "BY: ";
		public static final String DATE = "DATE : ";
		public static final String BEGIN = "#LN Item No. Description Unit Quantity Receive Unit Price Total Price";
		public static final String BEGIN1 = "Item No. UnitDescription Quantity Unit Price Total Price#LN Receive";
		public static final String END = "PLEASE CONFIRM RECEIPT & SHIP DATE";
		public static final String TOUBEGIN = "PURCHASE ORDER Page: 1";
		public static final String TO = "To: ";
		public static final String SHIP = " Ship";
		public static final String PURCHASE_ORDER = "PURCHASE ORDER";
		public static final String PURCHASE_ORDER_NUMBER2 = "Purchase Order Number";
		
		@BeforeClass  
		public static void configTest(){
			try{
				//首先new一个context
				//实例化spring容器
				context = new XmlWebApplicationContext ();
				//记得放开    
			    //对Bean定义资源文件的定位,Bean定义资源文件就是applicationContext.xml,然后就可以通过context.getBean（）方法得到applicationContext.xml文件定义的bean
				context.setConfigLocations(configs);

				// 执行完refresh，bean工厂有值了。所以它才是真正生成bean的,bean的来源有两处，一是applicationContext.xml，二是标签。
				context.refresh();
		
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		@Test
		public void testGetTemplateFileInPackage() throws Exception{
			
			URL fileURL=this.getClass().getResource("/Template/xml/NCSaleOrder.xml"); 
			InputStream is=this.getClass().getResourceAsStream("/Template/xml/NCSaleOrder.xml");   
	        System.out.println( is );    
		}

		//@Test
		public void testPurchaseOrder() throws Exception{
				String fileName= "C:/Users/lanyingxuan.ZZGRP/Desktop/YingShouXiaoShou.xml";
				File xmlFile = new File(fileName);
				
				SAXReader saxReader = new SAXReader();
				FileInputStream in = new FileInputStream(xmlFile);
				Reader reader = new InputStreamReader(in,"utf-8");
				Document doc = saxReader.read(reader);

				Element root = doc.getRootElement();
               
				//HEAD
				Element head=(Element)root.selectSingleNode("so_order/header");
				//BODY  
				Element body= (Element)root.selectSingleNode("/ufinterface/so_order/body");
				Element entry=(Element)body.selectSingleNode("entry");
				body.remove(entry);			
//				//单据号=>>单据编号
//				Element nodeObj=(Element)so_order_head.selectSingleNode("pk_corp");
//				nodeObj.setText("a");
//				String ss = doc.asXML();
//					
//				System.out.println( ss );
				
				

                // pdf-start
				String path = "E:/pdf导入NC/PO-46533-25-03-14-154959.pdf";					
				FileInputStream is = new FileInputStream(path);	

				//Map<String, Object> map = new HashMap<String, Object>();    	     
		        PDDocument document = null;        
		        System.out.println("开始转换pdf");
		        //long startTime=System.currentTimeMillis();
		        try {      
		        	RandomAccessBufferedFileInputStream pdFileInputStream = new RandomAccessBufferedFileInputStream( is );  
		            PDFParser parser = new PDFParser( pdFileInputStream );  
		            parser.parse();  
		            document = parser.getPDDocument();  
		            
		            PDFTextStripper pdfTextStripper = new PDFTextStripper();// new PDFText2HTML();
		            String result1 = pdfTextStripper.getText( document );
		            pdfTextStripper.setSortByPosition(true);    
		            String result = pdfTextStripper.getText( document );
		          	//System.out.println( result );

		          	result = result.replace(PURCHASE_ORDER_NUMBER, "\n" + PURCHASE_ORDER_NUMBER);
		        	result = result.replace(PURCHASE_ORDER_DATE, "\n" + PURCHASE_ORDER_DATE);
		        	result = result.replace(PHONE, "\n" + PHONE);
		        	result = result.replace(FAX, "\n" + FAX);
		        	result = result.replace(E_MAIL, "\n" + E_MAIL);
		        	result = result.replace(FREIGHT, "\n" + FREIGHT);
		        	result = result.replace(TERMS, "\n" + TERMS);
		        	result = result.replace(EXPECTED_RECEIPT_DATE, "\n" + EXPECTED_RECEIPT_DATE);
		        	result = result.replace(GST, "\n" + GST);
		        	result = result.replace(QST, "\n" + QST);
		        	result = result.replace(BUYER, "\n" + BUYER);
		        	result = result.replace(USER, "\n" + USER);
		        	result = result.replace(PHONE_NO, "\n" + PHONE_NO);
		        	result = result.replace(FAX2, "\n" + FAX2);
		        	result = result.replace(SUBTOTAL, "\n" + SUBTOTAL);
		        	result = result.replace(TOTAL, "\n" + TOTAL);
		        	result = result.replace(BY, "\n" + BY);
		        	result = result.replace(DATE, "\n" + DATE);        	
		        	String[] str = result.split("\n");
		        	
		        	String str11 = "";
		        	
		        	for (String string : str) {
						if (string.indexOf(PURCHASE_ORDER_NUMBER) >= 0) {
							//po.setPurchaseordernumber();
							System.out.print( "#####Purchase Order Number:" );
							System.out.println( string.substring(PURCHASE_ORDER_NUMBER.length()).replaceAll("\\s*", "") );
							
							String orderNumber = string.substring(PURCHASE_ORDER_NUMBER.length()).replaceAll("\\s*", "");
							//lanyingxuan-start
	        				//对方单据号=>>客户订单号
	        				processValueReplace(head, "vdef1", orderNumber);
	        				//lanyingxuan-end
						} 
						if (string.indexOf(PURCHASE_ORDER_DATE) >= 0) {
							//po.setPurchaseorderdate("20"+string.substring(PURCHASE_ORDER_DATE.length()));
							System.out.print( "#####Purchase Order Date:" );
							System.out.println( "20"+string.substring(PURCHASE_ORDER_DATE.length()) );
						} 
						if (string.indexOf(PHONE) >= 0) {
							//po.setPhone(string.substring(PHONE.length()));
							System.out.print( "#####Phone:" );
							System.out.println( string.substring(PHONE.length()) );
						} 
						if (string.indexOf(FAX) >= 0) {
							//po.setFax(string.substring(FAX.length()));
							System.out.print( "#####Fax:" );
							System.out.println( string.substring(FAX.length()) );
						} 
						if (string.indexOf(E_MAIL) >= 0) {
							//po.setEmail(string.substring(E_MAIL.length()));
							System.out.print( "#####Email:" );
							System.out.println( string.substring(E_MAIL.length()) );
						} 
						if (string.indexOf("Ship ") >= 0 && string.indexOf(" Via:") >= 0) {
							if(string.indexOf("Ship Via:") >= 0){
								//po.setShipvia(string.substring(string.indexOf("Ship Via:") + 9));
								System.out.print( "#####Ship Via:" );
								System.out.println( string.substring(string.indexOf("Ship Via:") + 9) );
							} else {
								//po.setShipvia(string.substring(string.indexOf("Ship ") + 5,string.indexOf(" Via:")));
								System.out.print( "#####Ship Via:" );
								System.out.println( string.substring(string.indexOf("Ship ") + 5,string.indexOf(" Via:"))  );
							}
						} 
						if (string.indexOf(FREIGHT) >= 0) {
							//po.setFreight(string.substring(FREIGHT.length()));
							System.out.print( "#####FREIGHT:" );
							System.out.println( string.substring(FREIGHT.length())  );
							String freight = string.substring(FREIGHT.length());
							//lanyingxuan-start
	        				//专业术语=>>贸易术语
	        				processValueReplace(head, "vdef7", freight);
	        				//lanyingxuan-end
						} 
						if (string.indexOf(TERMS) >= 0) {
							//po.setTerms(string.substring(TERMS.length()));
							System.out.print( "#####TERMS:" );
							System.out.println( string.substring(TERMS.length()) );
						} 
						if (string.indexOf(GST) >= 0) {
							//po.setGst(string.substring(GST.length()));
							System.out.print( "#####GST:" );
							System.out.println( string.substring(GST.length())  );
						} 
						if (string.indexOf(QST) >= 0) {
							//po.setQst(string.substring(QST.length()));
							System.out.print( "##### QST:" );
							System.out.println( string.substring(GST.length()) );
						} 
						if (string.indexOf(BUYER) >= 0) {
							//po.setBuyer(string.substring(BUYER.length()));
							System.out.print( "##### BUYER:" );
							System.out.println( string.substring(BUYER.length()) );
							String buyer = string.substring(BUYER.length());
							//lanyingxuan-start
	        				//客户采购员=>>采购员
	        				processValueReplace(head, "vdef11", buyer);
	        				//lanyingxuan-end
						} 
						if (string.indexOf(USER) >= 0) {
							//po.setFuser(string.substring(USER.length()));
							System.out.print( "##### USER:" );
							System.out.println( string.substring(USER.length()) );
						} 
						if (string.indexOf(PHONE_NO) >= 0) {
							//po.setPhoneno(string.substring(PHONE_NO.length()));
							System.out.print( "##### PHONE_NO:" );
							System.out.println( string.substring(PHONE_NO.length()) );
						} 
						if (string.indexOf(FAX2) >= 0) {
							//po.setFax2(string.substring(FAX2.length()));
							System.out.print( "##### FAX2:" );
							System.out.println( string.substring(FAX2.length()) );
						} 
						if (string.indexOf(SUBTOTAL) >= 0) {
							//po.setSubtotal(string.substring(SUBTOTAL.length()));
							System.out.print( "##### SUBTOTAL:" );
							System.out.println( string.substring(FAX2.length()) );
						} 
						if (string.indexOf("Invoice ") >= 0 && string.indexOf(" Discount:") >= 0) {
							if (string.indexOf("Invoice Discount:") >= 0) {
								//po.setInvoicediscount(string.substring(string.indexOf("Invoice Discount:") + 17));
								System.out.print( "##### Invoice Discount:" );
								System.out.println( string.substring(string.indexOf("Invoice Discount:") + 17)  );
							} else {
								//po.setInvoicediscount(string.substring(string.indexOf("Invoice ") + 8,string.indexOf(" Discount:")));
								System.out.print( "##### Invoice Discount:" );
								System.out.println( string.substring(string.indexOf("Invoice ") + 8,string.indexOf(" Discount:"))  );
							}
						} 
						if (string.indexOf("Total ") >= 0 && string.indexOf(" Tax:") >= 0) {
							if (string.indexOf("Total Tax:") >= 0) {
								//po.setTotaltax(string.substring(string.indexOf("Total Tax:") + 10));
								System.out.print( "##### Total Tax:" );
								System.out.println( string.substring(string.indexOf("Total Tax:") + 10)  );
							} else {
								//po.setTotaltax(string.substring(string.indexOf("Total ") + 6,string.indexOf(" Tax:")));
								System.out.print( "##### Total Tax:" );
								System.out.println( string.substring(string.indexOf("Total ") + 6,string.indexOf(" Tax:"))  );
							}
						} 
						if (string.indexOf(TOTAL) >= 0) {
							//po.setTotal(string.substring(TOTAL.length()));
							System.out.print( "##### Total:" );
							System.out.println( string.substring(TOTAL.length())  );
						} 
						if (string.indexOf(BY) >= 0) {
							//po.setFby(string.substring(BY.length()));
							System.out.print( "##### BY:" );
							System.out.println( string.substring(TOTAL.length())   );
						} 
						if (string.indexOf(DATE) >= 0) {
							//po.setFdate(string.substring(DATE.length()));
							System.out.print( "##### DATE:" );
							System.out.println( string.substring(DATE.length())  );
						}
						str11 = str11 + string + "\n";
					}
		        	
		        	//po.setTou(str11.substring(str11.indexOf(TOUBEGIN) + TOUBEGIN.length(),str11.indexOf(TO)).trim());
		        	String tou = str11.substring(str11.indexOf(TOUBEGIN) + TOUBEGIN.length(),str11.indexOf(TO)).trim();
		        	System.out.print( "##### Tou:" );
		        	System.out.println( str11.substring(str11.indexOf(TOUBEGIN) + TOUBEGIN.length(),str11.indexOf(TO)).trim()  );
		        	
		        	//String haoma = str11.substring(str11.indexOf(TO) + TO.length(),str11.indexOf(SHIP));
		        	String haoma = str11.substring(str11.indexOf(TO) + TO.length(),str11.indexOf(SHIP));
		        	System.out.print( "##### haoma:" );
		        	System.out.println( str11.substring(str11.indexOf(TO) + TO.length(),str11.indexOf(SHIP)) );
		        	
		        	result1 = result1.substring(5);
		        	//po.setShipto(result1.substring(result1.indexOf(PURCHASE_ORDER) + PURCHASE_ORDER.length(),result1.indexOf(PURCHASE_ORDER_NUMBER2)).trim());
		        	System.out.print( "##### Shipto:" );
		        	System.out.println( result1.substring(result1.indexOf(PURCHASE_ORDER) + PURCHASE_ORDER.length(),result1.indexOf(PURCHASE_ORDER_NUMBER2)) );
		        	String destination= result1.substring(result1.indexOf(PURCHASE_ORDER));
		        	String[] destinations = destination.split("\n");
		        	//lanyingxuan-start
    				//目的港=>>目的港
    				processValueReplace(head, "vdef3", destinations[3]);
    				//lanyingxuan-end

		        	//po.setFto(haoma + "\n" + result1.substring(po.getTou().length(),result1.indexOf("To:")).trim());
		        	
		        	/*
		        	if(po.getPurchaseordernumber().indexOf("PO-")>-1){
		        		String ordernumber = po.getPurchaseordernumber();
		        		po.setPurchaseordernumber(ordernumber.substring(ordernumber.indexOf("PO-")+"PO-".length()));
		        	}*/
		            
		        	System.out.println( "#############new order start################" );
		            for (int i = 0; i < document.getPages().getCount(); i++) {
		            	
		            	PDPage page = (PDPage) document.getPages().get(i); 
		            	
		            	if( page != null ){
		            		
		            		StringWriter sw = new StringWriter(); 
		            		PDFTextStripper pst = new PDFTextStripper();
		            		pst.setStartPage(i+1);
		            		pst.setEndPage(i+1);
		            		pst.writeText(document, sw); 
		            		String content = sw.getBuffer().toString();
		            		
		            		pst.setSortByPosition(false);    
		                    result1 = pst.getText( document );
		                    pst.setSortByPosition(true);    
		                    result = pst.getText( document );
		                    
		                    String newOrderString = result1.substring(result1.indexOf(BEGIN1) + BEGIN1.length() + 1);
		            		String[] newOrderStr = newOrderString.split("\n");

		            		String newOrderString1 = result.substring(result.indexOf(BEGIN) + BEGIN.length() + 1);
		            		String[] newOrderStr1 = newOrderString1.split("\n");
		            		List<String> strList = new ArrayList<String>();
		            		for (String string : newOrderStr) {
		            			if( string == null || string.length() < 1 ) continue;
		            			if( string.split(" ").length < 3 ) continue;
		            			if (string.indexOf("Transferred") >= 0) continue;
		            			strList.add(string);
		        			}
		            		List<String> strList1 = new ArrayList<String>();
		            		for (String string : newOrderStr1) {
		            			if( string == null || string.length() < 1 ) continue;
		            			if( string.split(" ").length < 3 ) continue;
		            			if (string.indexOf("Transferred") >= 0) continue;
		        				strList1.add(string);
		        			}
		            		
		            		for(int index = 0;index<strList.size();index++){
		            			//lanyingxuan-start
				            	Element cloneEntry=(Element)entry.clone();
				            	//lanyingxuan-end
		            			String string = strList.get(index);
		            			String string1 = strList1.get(index);
		        				String[] string1s = string1.split(" ");
		        				
		        				String ln = string1s[0];
		        				System.out.println( "LN:" + ln );
		        				//lanyingxuan-start
		        				//行号=>>行号
		        				processValueReplace(cloneEntry, "crowno", ln);
		        				//lanyingxuan-end
		        				
		        				string = string.substring(0,string.length()-ln.length());
		        				String itemno = string1s[1];
		        				System.out.println( "itemno:" + itemno );
		        				//lanyingxuan-start
		        				//客户产品编码=>>存货ID
		        				processValueReplace(cloneEntry, "cinventoryid", itemno);
//		        				Element nodeObj=(Element)cloneEntry.selectSingleNode("cinventoryid");
//		        				nodeObj.setText(itemno);
		        				//lanyingxuan-end
		        				string = string.substring(0,string.length()-itemno.length());
		        				String description = string.substring(string.indexOf(".",string.indexOf(".")+1) + 3);
		        				System.out.println( "description:" + description );

		        				string = string.substring(0,string.length() - description.length());
		        				String totalPrice = string.substring(string.indexOf(" ",string.indexOf(".")+1) + 1);
		        				//lanyingxuan-start
		        				//价税合计=>>总价
		        				processValueReplace(cloneEntry, "noriginalcursummny", totalPrice);
		        				//lanyingxuan-end
		        				System.out.println( "TotalPrice:" + totalPrice );
		        				
		        				if( !isPositiveDecimal(totalPrice) ){
		        					System.out.println( "Not a vailded order record########" );
		        					continue ;
		        				}
		        				
		        				string = string.substring(0,string.length() - totalPrice.length());
		        				//判断unit是否为空
		        				if (!Character.isDigit(string.charAt(0))) {
		        					string = string.substring(string.indexOf(" ") + 1);
		        					String unit = "pcs";
		        					System.out.println( "unit:" + unit );
		        				}	
		        				
		        				String[] strings = string.split(" ");
		        				boolean b = false;
		        				String quantity = null;
		        				String unitPrcie = null;
		        				while (!b) {
		        					int j = 1;
		        					String tmp = "";
		        					for(int k = 0;k< strings.length - k; k++){
		        						tmp = tmp + strings[k];
		        					}
		        					BigDecimal bd = new BigDecimal(Double.valueOf(strings[strings.length -j]) * Double.valueOf(tmp));
		        					b = Double.valueOf( totalPrice.replace(" ", "")) == bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		        					if (b) {
		        						
		        						quantity = Integer.valueOf(tmp).toString();
		        						//lanyingxuan-start
				        				//数量=>>数量
				        				processValueReplace(cloneEntry, "nnumber", quantity);
				        				//lanyingxuan-end
		        						unitPrcie = strings[strings.length -j];
		        						//lanyingxuan-start
				        				//含税单价=>>单价
				        				processValueReplace(cloneEntry, "noriginalcurtaxprice", unitPrcie);
				        				//lanyingxuan-end
		        						System.out.println( "quantity:" + quantity );
		        						System.out.println( "unitPrcie:" + unitPrcie );
		        					}
		        				}
		        				//if( !isPositiveDecimal(quantity) ) continue ;
		        				//if( !isPositiveDecimal(unitPrcie) ) continue ;
		        				//lanyingxuan-start
				            	body.add(cloneEntry);
				            	//lanyingxuan-end
		        			}
		            	}
		            	
		            }
		            
		    		System.out.println( "#############new order end################" );

		        	
		        } catch (FileNotFoundException e) {  
		            e.printStackTrace();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        } 
		        finally {  
		            if (is != null) {  
		                try {  
		                    is.close();  
		                } catch (IOException e) {  
		                    e.printStackTrace();  
		                }  
		            }  
		            if (document != null) {  
		                try {  
		                    document.close();  
		                } catch (IOException e) {  
		                    e.printStackTrace();  
		                }  
		            }  
		        }
		        String ss = doc.asXML();
		        System.out.println(ss);
			}
		
			//pdf-end
				
//				String fileName= "C:/Users/lanyingxuan.ZZGRP/Desktop/应收-销售测试数据.xml";
//				File xmlFile = new File(fileName);
//				
//				Document doc = FileUtil.getDom4JDocument( xmlFile );
//				
//				String receiver = "103";
//				
//				String ss = doc.asXML();
				
//				String resMessage = HttpClient.post("http://192.168.0.38:8099/service/XChangeServlet?receiver="+receiver+"", ss);
//				
//				System.out.println( resMessage );
				

			private static boolean isPositiveDecimal(String orginal){  
	            return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);  
	        }
	        private static boolean isMatch(String regex, String orginal){  
	            if (orginal == null || orginal.trim().equals("")) {  
	                return false;  
	            }  
	            Pattern pattern = Pattern.compile(regex);  
	            Matcher isNum = pattern.matcher(orginal);  
	            return isNum.matches();  
	        }
	        
	        public void processValueReplace(Element parent,String nodeName,String value){
	    		Element nodeObj=(Element)parent.selectSingleNode(nodeName);
	    		nodeObj.setText(value);
	    	}
		}