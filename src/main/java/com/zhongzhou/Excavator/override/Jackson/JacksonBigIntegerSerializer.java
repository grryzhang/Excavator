package com.zhongzhou.Excavator.override.Jackson;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JacksonBigIntegerSerializer extends JsonSerializer<BigInteger>  {

	@Override
	public void serialize(
			BigInteger value, 
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
