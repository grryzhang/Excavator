package com.zhongzhou.Excavator.override.Jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JacksonIntegerSerializer extends JsonSerializer<Integer>  {

	@Override
	public void serialize(
			Integer value, 
			JsonGenerator jgen,
			SerializerProvider provider
			) throws IOException,
			JsonProcessingException {
		
		String returnValue = null;
		if( value != null ){
			returnValue = value.toString();
		}
		jgen.writeString( returnValue );
		
	}

}
