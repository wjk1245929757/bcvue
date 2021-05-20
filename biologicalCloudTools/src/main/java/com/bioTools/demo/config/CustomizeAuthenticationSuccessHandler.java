package com.bioTools.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bioTools.demo.util.JwtTokenUtils;


@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	JwtTokenUtils jwtTokenUtils;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
       
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
    	
    	
    	String email = authentication.getName();
    	List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        
    	System.out.println(email+"   "+roles.get(0));

    	System.out.println("CustomizeAuthenticationSuccessHandler");
    	
    	String jwt = jwtTokenUtils.createToken(email, roles.get(0), true);
    	System.out.println(jwt);
    	
    	JSONObject result = new JSONObject();
    	result.put("data", "Success");
    	result.put("jwtToken", jwt);
    	
    	
       //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");
       //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(new String("登录成功"));
        httpServletResponse.getWriter().write(result.toJSONString());
    }

}
