
package com.zhongzhou.Excavator.AOP;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.DAO.mongo.SysErrorLogDAO;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.model.SysErrorLog;

import util.BeanJsonUtil;

@Aspect
@Component
/**
 * saveXXXXX对应所有逻辑上需要做插入或者更新的流程
 * changeXXX对应所有逻辑上需要做修改的流程
 * removeXXX对应所有逻辑上需要做删除的流程
 * addXXX对应所有逻辑上需要做删除的流程
 * 
 * @author Grry Zhang
 *
 */
public class ServiceExceptionAOPHandler {
	
	private static Logger log = Logger.getLogger(ServiceExceptionAOPHandler.class);  
	
	@Resource( name = "mongo.SysErrorLogDAO" )
	private SysErrorLogDAO sysErrorLogDAO;

	@Pointcut(value="execution(* com.zhongzhou.Excavator.service..*.*(..))")
	public void interceptorException(){
		
	}
	
	@Around(value="interceptorException()")  
	public Object logException( final ProceedingJoinPoint pjp ) throws Throwable{
		
		try{
			
			return pjp.proceed();
			
		}catch(Throwable e){
			
			try{
				Class targetClass = pjp.getTarget().getClass();
				Object targetObject = pjp.getTarget();
				
				MethodSignature menthodObject = (MethodSignature)pjp.getSignature();
				Method targetMethod = menthodObject.getMethod();
				
				Object[] args = pjp.getArgs();

				StringWriter sw = new StringWriter();  
	            PrintWriter pw = new PrintWriter(sw);  
	            e.printStackTrace(pw);
	            String statckTrace = sw.toString();
	            if( statckTrace.length() > 65530 ){
	            	statckTrace = statckTrace.substring(0, 65530);
	            }
	            
				SysErrorLog errorLog = new SysErrorLog();
				if( e instanceof ServiceRuntimeException ){
					errorLog.setExceptionId( ((ServiceRuntimeException)e).getExceptionId()  );
				}
				errorLog.setClassName( targetClass.toString() );
				errorLog.setClassInstanceName( targetObject.toString() );
				errorLog.setMethodName( BeanJsonUtil.beanJaksonSerializer2String( targetMethod )  );
				errorLog.setInputArgs( BeanJsonUtil.beanJaksonSerializer2String( Arrays.asList( args ) ) );
				errorLog.setExceptionTrace( statckTrace );
				errorLog.setExceptionTime( new Timestamp( System.currentTimeMillis() ) );
				
				sysErrorLogDAO.insertLog( errorLog );
				
			}catch( Exception exception ){
				
				log.error( exception );
			}
			
			throw e;
		}
	}
}
