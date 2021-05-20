package com.bioTools.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bioTools.demo.service.AdminInfoService;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;
import com.bioTools.demo.service.PersonInfoService;

/*
 * 面向登录页面
 */

@RestController
@RequestMapping("/admin")
public class AdminInfoController {
	
	@Autowired
	private AdminInfoService adminInfoService;
	
	@GetMapping("/users")
	public String allUsers() {
		return adminInfoService.selectAllUsers().toString();
	}
	
	@GetMapping("/history")
	public String allData() {
		return adminInfoService.selectAllHistorys().toString();
	}
	
	@GetMapping("/clearHistory")
	public String clearHistory() {
		adminInfoService.clearHistory();
		return "Success";
	}
	
	

}
