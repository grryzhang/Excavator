import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



import org.junit.BeforeClass;
import org.junit.Test;

import org.w3c.tidy.Tidy;


public class Html2XhtmlTest {

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
			FileInputStream ins = null;
			InputStreamReader inputReader = null;
			OutputStreamWriter outputWriter = null;
	  
			String fileName = "d:/test.html";  
			String xhtmlFileName = "d:/test.xhtml";
	  
			try {  
				File xhtmlFile = new File( xhtmlFileName );
				if ( !xhtmlFile.exists() ){
					
					xhtmlFile.getParentFile().mkdirs();
				}
				outputWriter = new OutputStreamWriter ( new FileOutputStream(xhtmlFileName) , "UTF-8" );  
				
				//TODO if input file not existed
				File htmlFile = new File( fileName );
				ins = new FileInputStream( htmlFile );
				inputReader = new InputStreamReader( ins,"UTF-8");
				
				Tidy tidy = new Tidy();
				tidy.setXmlOut(true); 
		        //tidy.setXmlPi(true);
		        tidy.setInputEncoding("UTF-8");
	            //tidy.setQuiet(true);                   
	            tidy.setOutputEncoding("UTF-8");
	            tidy.setForceOutput(true); //get error, still output to target
	            //tidy.setShowWarnings(false);
		        
	            tidy.parse( inputReader , outputWriter );
	            //tidy.parse(stream, tidyOutStream);
	            //tidyOutStream.writeTo(to);
	            
	            outputWriter.flush();
				
			} catch (FileNotFoundException e) {  
				
				e.printStackTrace();  
			} finally{  

				ins.close();
				inputReader.close();
				outputWriter.close();
			}  
		} catch (Exception e) {
			System.out.println("Catch the exception.");
			e.printStackTrace();
		}
	}
}