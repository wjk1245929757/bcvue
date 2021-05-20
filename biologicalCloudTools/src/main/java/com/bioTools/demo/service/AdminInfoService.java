package com.bioTools.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bioTools.demo.dao.HistoryDAO;
import com.bioTools.demo.dao.PersonInfoDAO;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.entities.User;

/*
 * 管理人员信息中心类
 * 功能：登录、注册、删除人员信息。
 */

@Service
public class AdminInfoService {
	
	@Autowired
	private PersonInfoDAO PIDAO;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@Autowired 
	private HistoryDAO DTDAO;
	
	public List<User> selectAllUsers(){
		return PIDAO.selectUsers();
	}
	

	public List<History> selectAllHistorys(){
		return DTDAO.selectAllHistory();
	}
	
	public String clearHistory() {
		historyService.clearHistorys();
		List<String> emailList = personInfoService.getAllUsersEmail();
		for(int i= 0; i<emailList.size();i++) {
			fileService.deleteFolder(emailList.get(i));
			fileService.createFolder(emailList.get(i));
		}
		return "Success";
	}

}
