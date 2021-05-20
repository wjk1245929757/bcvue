package com.bioTools.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bioTools.demo.dao.HistoryDAO;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.util.GetRandomStringService;
import com.bioTools.demo.util.GetTimeStringService;

@Service
public class HistoryService {
	
	@Autowired
	private HistoryDAO historyDAO;
	
	@Autowired
	private GetTimeStringService getTimeStringService;
	
	@Autowired
	private GetRandomStringService getRandomStringService;
	
	public History creatHistoryEntity(String email,String model,String function) {
		History history = new History();
		history.setDatapath("null");
		history.setDataname("null");
		history.setPicpath("null");
		history.setPicname("null");
		history.setEmail(email);
		history.setModel(model);
		history.setToolfunction(function);
		history.setHistoryid(this.GenerateHistoryID(email));
		return history;
	}
	
	private String GenerateHistoryID(String email) {
		String id = "";
		String temp[] = email.split("@");
		id += temp[0];
		id += getRandomStringService.getRandomString(4);
		id += getTimeStringService.getTimeString();
		id += getRandomStringService.getRandomString(4);
		return id;
	}

	
	public int historyNum(String email) {
		List<History> historyList = historyDAO.selectUserHistory(email);
		return historyList.size();
	}
	
	public boolean isHistoryNumOver10(String email) {
		List<History> historyList = historyDAO.selectUserHistory(email);
		if(historyList.size() > 10) {
			return true;
		}
		
		return false;
	}
	
	public History findEarlistHistory(String email) {
		List<History> historyList = historyDAO.selectUserHistory(email);
		List<Long> timeList = new ArrayList<Long>();
		for(int i=0;i<historyList.size();i++) {
			String tempHistoryID = historyList.get(i).getHistoryid();
			timeList.add( Long.parseLong(  tempHistoryID.substring(tempHistoryID.length()-18, tempHistoryID.length()-4)) );
		}
		Long min = timeList.get(0);
		int minPosition = 0;
		for(int j=1;j<timeList.size();j++) {
			if(timeList.get(j) < min) {
				minPosition = j;
				min = timeList.get(j);
			}
		}
		System.out.println(min);
		return historyList.get(minPosition);
	}
	
	public void deleteEarliestHistory(String email) {
		List<History> historyList = historyDAO.selectUserHistory(email);
		List<Integer> timeList = new ArrayList<Integer>();
		for(int i=0;i<historyList.size();i++) {
			String tempHistoryID = historyList.get(i).getHistoryid();
			timeList.add( Integer.parseInt(  tempHistoryID.substring(tempHistoryID.length()-18, tempHistoryID.length()-4)) );
		}
		int min = timeList.get(0);
		int minPosition = 0;
		for(int j=1;j<timeList.size();j++) {
			if(timeList.get(j) < min) {
				minPosition = j;
				min = timeList.get(j);
			}
		}
		historyDAO.deleteByHistoryId(historyList.get(minPosition).getHistoryid());
	}
	
	public void addHistory(History history) {
		historyDAO.addHistory(history);
	}
	
	public void deleteByDataPath(String dataPath) {
		historyDAO.deleteByDataPath(dataPath);
	};
	
	public void deleteByHistoryId(String historyID) {
		historyDAO.deleteByHistoryId(historyID);
	};
	
	public void clearHistorys() {
		historyDAO.clearHistroy();
	};
	
	public void updateHistory(History history) {
		historyDAO.updateHistory(history);
	}
	
	public History selectHistoryByDataPath(String dataPath) {
		return historyDAO.selectHistoryByDataPath(dataPath);
	}
	
	public History selectHistoryByHistoryId(String historyId) {
		return historyDAO.selectHistoryByHistoryId(historyId);
	}
	
	public List<History> selectAllHistory(){
		return historyDAO.selectAllHistory();
	}
	
	public List<History> selectUserHistory(String email){
		return historyDAO.selectUserHistory(email);
	}
	

}
