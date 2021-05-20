package com.bioTools.demo.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bioTools.demo.service.PersonInfoService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private CustomizeAuthenticationSuccessHandler  authenticationSuccessHandler;
	@Autowired
	private CustomizeAuthenticationFailureHandler  authenticationFailureHandler;
	@Autowired
	private CustomizeLogoutSuccessHandler  logoutSuccessHandler;
	@Autowired
	private CustomizeAuthenticationEntryPoint  authenticationEntryPoint;
	@Autowired
    private jwtAccessDeniedHandler jwtAccessDeniedHandler;	
	
	
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();    
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(personInfoService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http
			
			.formLogin()
//				.loginPage("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.and()
			.logout()
				.permitAll()
				.logoutSuccessHandler(logoutSuccessHandler)
				.deleteCookies("JSESSIONID")
				.and()
			
        	.authorizeRequests()
        		.antMatchers("/public/**").permitAll()
        		.antMatchers("/static/**").permitAll()	
        		.antMatchers("/register", "/login","/sendCheckCode").permitAll()	
        		.antMatchers("/test/**").permitAll()
        		.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
        		.anyRequest().authenticated()
        		.and()
        	.exceptionHandling()
        		.authenticationEntryPoint(authenticationEntryPoint)
             	//用户访问没有授权资源
             	.accessDeniedHandler(jwtAccessDeniedHandler)
        		.and()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        	.sessionManagement().maximumSessions(1).expiredUrl("/login");
        		.and()
        	.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

	}
	
	
}
