package com.zhongzhou.Excavator.override.Jackson;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MappingJackson2HttpMessageConverter extends org.springframework.http.converter.json.MappingJackson2HttpMessageConverter{

	public MappingJackson2HttpMessageConverter() {
		
		this( Jackson2ObjectMapperBuilder.json().build() );
	}
	
	public MappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		
		super( objectMapper );
		
		objectMapper.configure( DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure( DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true );
		objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objectMapper.configure( DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true );
	}
}
