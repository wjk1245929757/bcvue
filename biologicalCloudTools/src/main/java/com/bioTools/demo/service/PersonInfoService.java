package com.bioTools.demo.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bioTools.demo.dao.PersonInfoDAO;
import com.bioTools.demo.entities.User;

/*
 * 个人信息管理服务类
 * 功能：登录、注册、查看历史记录
 */
@Service
public class PersonInfoService implements UserDetailsService {
	
	@Autowired
	private PersonInfoDAO PIDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//登录成功逻辑
	public String login(User user) {
		System.out.print("use  /login  url   ");
		if(isUserExisted(user.getEmail())) {
			if(isPasswordMatchEmail(user.getEmail(),user.getPassword())) {
				System.out.println("Success");
				return "Success";
			}else {
				System.out.println("密码错误");
				return "密码错误";
			}
		}
		System.out.println("账号不存在");
		return "账号不存在";
	}
	
	//注册逻辑
	public String register(User user) {
		if(isUserExisted(user.getEmail())) {
			return "账号已存在";
		}
		user.setRole("ROLE_USER");
		String psw =user.getPassword();
		String encodePsw = passwordEncoder.encode(psw);
		user.setPassword(encodePsw);
		PIDAO.create(user);
		return "注册成功";
	}
	
	
	public void updateUser(User user) {
		PIDAO.updateUser(user);
	}
	
	public boolean isUserExisted(String email) {
		if(PIDAO.selectUserByEmail(email) == null) {
			return false;
		}
		return true;
	}
	
	public boolean isPasswordMatchEmail(String email,String password) {
		String psw = PIDAO.selectUserByEmail(email).getPassword();
//		System.out.println(password);
//		System.out.println(psw);
//		System.out.println(passwordEncoder.matches(password, psw));
		if(passwordEncoder.matches(password, psw)) {
			return true;
		}
		return false;
//		System.out.println("DB password:" + PIDAO.selectUserByEmail(email).toString());
//		System.out.println("user send password:"+ password);
//		if(PIDAO.selectUserByEmail(email).getPassword().equals(password)) {
//			return true;
//		}
//		return false;
	}
	
	public User getUserByEmail(String email){
		return PIDAO.selectUserByEmail(email);
	}
	
	public List<String> getAllUsersEmail(){
		List<User> userList = PIDAO.selectUsers();
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<userList.size();i++) {
			emailList.add(userList.get(i).getEmail());
		}
		return emailList;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User myUser = PIDAO.selectUserByEmail(email);
        if (myUser == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        String role = myUser.getRole();
        Collection<GrantedAuthority> authorities = new ArrayList<>();         
        authorities.add(new SimpleGrantedAuthority(role));
        org.springframework.security.core.userdetails.User user = 
        		new org.springframework.security.core.userdetails.User(myUser.getEmail(),myUser.getPassword(),authorities);
        System.out.println("UserDetailsService work!   账号："+myUser.getEmail()+"   加密密码"+myUser.getPassword()+"  权限"+user.getAuthorities());
//        System.out.println(myUser.getPassword());
        return user;

//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return new org.springframework.security.core.userdetails.User(user.getUid(),
//                user.getPassword(),
//                authorities);
	}
	
}
