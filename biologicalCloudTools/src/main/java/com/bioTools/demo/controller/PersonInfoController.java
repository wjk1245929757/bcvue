package com.bioTools.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.entities.User;
import com.bioTools.demo.service.CheckCodeService;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;
import com.bioTools.demo.service.PersonInfoService;

/*
 * 个人信息类
 * 功能：登陆注册、查看历史文件，允许删除、下载历史数据
 * 路由：
 *     login  post
 *     register  post
 *     clearHistory  post get
 *     sendCheckCode  post
 */

@RestController
public class PersonInfoController {
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CheckCodeService checkCodeService;
	
	@Autowired
	private HistoryService historyService;
	
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String Login(@RequestBody JSONObject jsonObject) {
//		
//		
//		String email = jsonObject.getString("email");
//
////		personInfoService.loadUserByUsername(email);
//        String password = jsonObject.getString("password");
//		User user = new User();
//		user.setEmail(email);
//		user.setPassword(password);
//		System.out.println(user.toString());
//		String temp = personInfoService.login(user);
//		if(temp.equalsIgnoreCase(new String("Success"))) {
//			System.out.println("登录成功");
//		}else {
//			System.out.println(temp);
//		}
//		return temp;
//	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String LoginForGet() {
//		System.out.println("/login  ---get");
//		return "get方法不被允许，请用post方法	";
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginCheck(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session) {
		System.out.println("/dologin");
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		System.out.println(user.toString());
		String temp = personInfoService.login(user);
		if(temp.equalsIgnoreCase(new String("Success"))) {
			System.out.println("登录成功");
		}else {
			System.out.println(temp);
		}
		return temp;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String Register(@RequestBody JSONObject jsonObject) {
		
		System.out.println("register");
		String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String telephone = jsonObject.getString("telephone");
        
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setTelephone(telephone);
		System.out.println(user.toString());
		
		String temp =personInfoService.register(user);
		System.out.println(temp);
		fileService.createFolder(user.getEmail());
		
		return temp;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String RegisterForGet() {
		System.out.println("/register  ---get");
		return "get方法不被允许，请用post方法	";
	}
	
	@RequestMapping(value = "/sendCheckCode", method = RequestMethod.POST)
	public String sendCheckCode(@RequestBody JSONObject jsonObject) {
		String email = jsonObject.getString("email");
		String checkCode = checkCodeService.sendCheckCode(email);

		System.out.println("准备发送验证码，目标邮箱：" + email);
		System.out.println("验证码: "+checkCode);
		return checkCode;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public String UserUpdate(@RequestBody JSONObject jsonObject) {
		System.out.println("user");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName().toString();
		if( email == null) {
			return null;
		}
		String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        String telephone = jsonObject.getString("telephone");
        
		User user = personInfoService.getUserByEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setTelephone(telephone);
		System.out.println(user.toString());
		
		personInfoService.updateUser(user);
		
		return "OK";
	}
	
	@RequestMapping(value = "/user/history/{historyId}", method = RequestMethod.DELETE)
	public void deleteHistory(@PathParam( value = "historyId") String historyId) {
		History history = historyService.selectHistoryByHistoryId(historyId);
		fileService.deleteFile(history);
		historyService.deleteByHistoryId(historyId);
	}
	
	@RequestMapping(value = "/user/historys", method = RequestMethod.GET)
	public List<History> queryAllHistory(@ModelAttribute User user, HttpSession session) {
		System.out.println("/user/historys");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName().toString();
		if( email == null) {
			return null;
		}
		List<History> userHistory = historyService.selectUserHistory(email);
		System.out.println(userHistory.toString());
		
		return userHistory;
	}
	

	@RequestMapping(value = "/clearHistory", method = RequestMethod.GET)
	public String clearHistoryForGet() {
		return "请用post方法";
	}
	
	
	@RequestMapping(value = "/clearHistory", method = RequestMethod.POST)
	public String clearHistory(@ModelAttribute User user, HttpSession session) {
		
		if(personInfoService.isUserExisted(user.getEmail())) {
			
			if(personInfoService.isPasswordMatchEmail(user.getEmail(), 
					user.getPassword())){
				
				fileService.deleteFolder(user.getEmail());
				System.out.println(user.toString());
			}
		}else {
			return "账号不存在";
		}
		System.out.println(session.toString());
		return "删除成功";
	}
	
	
	
}
