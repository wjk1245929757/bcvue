package com.bioTools.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;

@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private HistoryService historyService;
	
	@RequestMapping(value = "/{model}/{function}/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file,
			@PathVariable String model,
			@PathVariable String function,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName().toString();
		if( email == null) {
			return "未登录";
		}
		History history = historyService.creatHistoryEntity(email,model,function);
		String result = fileService.upload(file, history);
		System.out.println(history.toString());
		if(result.equals(new String("上传成功"))) {
			
			historyService.addHistory(history);
//			System.out.println("historyNum:"+ historyService.historyNum(email));
			if(historyService.isHistoryNumOver10(email)) {
				History history_earlist = historyService.findEarlistHistory(email);
				fileService.deleteFile(history_earlist);
				historyService.deleteByHistoryId(history_earlist.getHistoryid());
//				or
//				historyService.deleteEarliestHistory(email);
			}
			
			return history.getHistoryid();
		}else {
			return result;
		}
    }
	
	@RequestMapping(value = "/{model}/{function}/multiUpload", method = RequestMethod.POST)
	@ResponseBody
	public String multiUpload(@RequestParam("files") MultipartFile[] files,
			@PathVariable String model,
			@PathVariable String function,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("/"+model +"/"+ function    +"/multiUpload");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName().toString();
		if( email == null) {
			return "未登录";
		}
		History history = historyService.creatHistoryEntity(email,model,function);
		
		String result = fileService.multiUpload(files, history);
		System.out.println(history.toString());
		if(result.equals(new String("上传成功"))) {
			
			historyService.addHistory(history);
			
			if(historyService.isHistoryNumOver10(email)) {
				History history_earlist = historyService.findEarlistHistory(email);
				fileService.deleteFile(history_earlist);
				historyService.deleteByHistoryId(history_earlist.getHistoryid());
//				or
//				historyService.deleteEarliestHistory(email);
			}
			return history.getHistoryid();
		}else {
			return result;
		}
		
    }
	
	
	@RequestMapping(value = "/{model}/{function}/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestParam("files") MultipartFile[] files,
			@RequestParam("historyid")String historyid,
			@PathVariable String model,
			@PathVariable String function,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(historyid);
		History history = historyService.selectHistoryByHistoryId(historyid);
		String result = fileService.save(files,history);
		if(result.equals(new String("上传成功"))) {
			historyService.updateHistory(history);
			System.out.println("save:" +  history.toString());
			return history.getHistoryid();
		}else {
			return result;
		}
    }

}
