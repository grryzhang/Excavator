package com.zhongzhou.Excavator.override.Jackson;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JacksonBigDecimalDeserializer extends JsonDeserializer<BigDecimal>{

	@Override
	public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		if (  jp.getText() == null ||  jp.getText().length() < 1 ){
			return null;
		}

		BigDecimal bigDecimalContent = new BigDecimal( jp.getText()  );
		
		return bigDecimalContent;
	}
}
