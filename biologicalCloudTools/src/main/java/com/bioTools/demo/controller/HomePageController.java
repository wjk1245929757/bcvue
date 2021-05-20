package com.bioTools.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 主页控制类
 * 功能：（主要功能入口） 调用工具 进入个人中心
 * 路由：
 * 			/
 */

@RestController
public class HomePageController {
	
	@GetMapping("/")
	public String home() {
		return "HomePage";
	}

}
