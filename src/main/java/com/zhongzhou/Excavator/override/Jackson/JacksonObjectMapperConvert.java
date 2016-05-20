
package com.zhongzhou.Excavator.override.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
/**
 * 
 * @author Grry Zhang
 */

public class JacksonObjectMapperConvert extends ObjectMapper{
	
	public JacksonObjectMapperConvert(){
		
		this.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		this.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		this.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
	}
}
