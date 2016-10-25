package com.zhongzhou.Excavator.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.service.system.UserLoginService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.UserPermissionException;

@Controller
public class UserLoginController {;
	
	@Resource( name=ServiceNameList.SYSTEM_USER_LOGIN_Service )
	private UserLoginService userLoginService;
	
	@RequestMapping("/MD/isLogin") 
	@ResponseBody
	public String login( HttpServletRequest request, 
			   HttpServletResponse response, 
			   HttpSession session ) throws UserPermissionException{  
		
		
		boolean result =  userLoginService.checkIfUserLogined();
		
		if( result ){
			
			return ( "{\"success\":\"" + result + "\"}" );
			
		}else{
			
			String redirectUrl = "/MDP/login.html";
			return ( "{\"redirecturl\":\"" + redirectUrl + "\",\"success\":" + result + "}" );
		}  
					
	}
	
	@RequestMapping("/MD/loginOut") 
	@ResponseBody
	public String loginOut( HttpServletRequest request, 
			   HttpServletResponse response, 
			   HttpSession session ) throws Exception{  
		
		userLoginService.logOut();
		request.getSession().invalidate();

		return("User logins out");
	}
	
	@RequestMapping("/MD/userLogin")  
	@ResponseBody
	public String userLogin( HttpServletRequest request, 
							   HttpServletResponse response, 
							   HttpSession session ){  
 
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		boolean result;
		
		if( userName != null && password != null  ){
			try {
				
				result =  userLoginService.userLogin(userName, password);
	                
				if( result ){
					
					String redirectUrl = "/MDP/index.html";
					return ( "{\"redirecturl\":\"" + redirectUrl + "\"}" );
					
				}else{
					
					String redirectUrl = "/MDP/login.html";
					return ( "{\"redirecturl\":\"" + redirectUrl + "\"}" );
				}
               

			} catch (UserPermissionException e ) {
				return( "error" );
			}
		}
		
		String redirectUrl = "/MDP/login.html";
		return ( "{\"redirecturl\":\"" + redirectUrl + "\"}" );
	}
}
