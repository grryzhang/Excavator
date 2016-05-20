package com.zhongzhou.Excavator.override.Jackson;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JacksonBigDecimalSerializer extends JsonSerializer<BigDecimal>  {

	@Override
	public void serialize(
			BigDecimal value, 
			JsonGenerator jgen,
			SerializerProvider provider
			) throws IOException,
			JsonProcessingException {
		
		String returnValue = null;
		if( value != null ){
			returnValue = value.stripTrailingZeros().toPlainString();
		}
		
		jgen.writeString( returnValue );	
	}

}
