package com.bioTools.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.bioTools.demo.service.PersonInfoService;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
 
	@Autowired
	private PersonInfoService personInfoService;
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        
    	System.out.println("CustomizeAuthenticationFailureHandler");
    	String email = httpServletRequest.getParameter("email");
//    	String password = httpServletRequest.getParameter("password");
    	String result = "";
    	if( !personInfoService.isUserExisted(email)) {
    		result =  "账号不存在";
		}else {
			result =  "密码错误";
		}
		System.out.println(result);
        httpServletResponse.setContentType("text/plain;charset=utf-8");
       //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(result);
    }
}
