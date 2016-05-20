package com.zhongzhou.Excavator.override.Jackson;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JacksonTimestampDeserializer extends JsonDeserializer <Timestamp>{

	@Override
	public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		String timeString = jp.getText();
		
		if ( timeString == null || timeString.length() < 1 ){
			return null;
		}
		
		//����long�ʹ������
		try{
			Long longFormateTime = new Long( timeString );
			
			return new Timestamp( longFormateTime );
		}catch ( Exception e ){
		}
		
		timeString = timeString.replace("/", "-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		//����UTC��ʽ���
		try {
			if( timeString.contains("T") ){
				if( timeString.contains("Z") ){
					
					timeString = timeString.replace("Z", " UTC");
					sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");  
				
					Date dt = sdf.parse(timeString);
					return new Timestamp( dt.getTime() );
				}
				
				sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
				Date dt = sdf.parse(timeString);
				return new Timestamp( dt.getTime() );
			}
			
			if( timeString.contains(":") ){
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			}else{
				sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			}
			
			Date dt = sdf.parse(timeString);
			return new Timestamp( dt.getTime() );
			
		} catch (ParseException e) {
		}
		
		return null;
	}

}
