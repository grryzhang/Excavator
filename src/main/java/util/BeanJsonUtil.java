package util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


import javax.annotation.Resource;
import javax.validation.Validator;

//import net.sf.cglib.beans.BeanCopier;
import org.apache.log4j.Logger;
import org.springframework.cglib.beans.BeanCopier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanJsonUtil {
	
	private static Logger log = Logger.getLogger(BeanJsonUtil.class);  
	
	private static ObjectMapper mapper;
	
	@Resource( name="Validator" )
	private static Validator validator;
	
	static{
		BeanJsonUtil.mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
	}
	
	/**
	 * @param sourceBean
	 * @param targetBean
	 */
	public static void beanCopy( Object sourceBean, Object targetBean ){
		
		BeanCopier beanCopier = BeanCopier.create(sourceBean.getClass(), targetBean.getClass(), false);  
		
		beanCopier.copy( sourceBean, targetBean, null);  
	}
	
	public static void beanDataMerge( Object sourceBean, Object targetBean, List<String> mergeParams ){
		
		for( String fieldName : mergeParams ){
			
			try {
				PropertyDescriptor pdSet = new PropertyDescriptor( fieldName, targetBean.getClass() );
				PropertyDescriptor pdGet = new PropertyDescriptor( fieldName, sourceBean.getClass() );
			
				if( pdSet != null && pdGet != null ){
					Method methodSet = pdSet.getWriteMethod();
					Method methodGet = pdGet.getReadMethod();
					
					if( methodSet!= null && methodGet != null ){
						Object value = methodGet.invoke( sourceBean );
						methodSet.invoke( targetBean, value );
					}
				}
			} catch (IntrospectionException e) {
				log.debug( e );
			} catch (IllegalAccessException e) {
				log.debug( e );
			} catch (IllegalArgumentException e) {
				log.debug( e );
			} catch (InvocationTargetException e) {
				log.debug( e );
			} 
		}
	}
	
	public static List<String> splitMergeParams( String mergeParams ){
		
		try{
			String [] splitedString = mergeParams.split(",");
			return java.util.Arrays.asList( splitedString );
		}catch( Exception e ){
			log.debug(e);
			return null;
		}
	}
    
    public static byte[] beanJaksonSerializer( Object object  ) throws Exception{
    	
    	ByteArrayOutputStream byteObjectOutput = new ByteArrayOutputStream();
    	mapper.writeValue(byteObjectOutput, object);
    	
    	return byteObjectOutput.toByteArray();
    }
    
    public static String beanJaksonSerializer2String( Object object  ) throws Exception{
    	
    	StringWriter stringWriter = new StringWriter();
    	mapper.writeValue(stringWriter, object);
    	
    	return stringWriter.toString();
    }

    public static <T> T beanJaksonUnSerializer(  byte[] object , Class<T> className ) throws Exception{
    	
    	return mapper.readValue( object, className );
    }
    
    public static <T> T beanCloneByJakson( Object source , Class<T> sourceClassName ) throws Exception{ 
    	
		byte[] beanSerialize = BeanJsonUtil.beanJaksonSerializer( source );
		return BeanJsonUtil.beanJaksonUnSerializer( beanSerialize, sourceClassName );
    }
}
