package com.bioTools.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;
import com.bioTools.demo.service.ToolsService;

/*
 * 工具调用类
 * 功能：调用R语言工具
 * 路由：
 *     test/run
 *     {model}/{function}/run
 *     
 */

@RestController
public class ToolsController {
	
	@Autowired
	private ToolsService toolsService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private FileService fileService;
	
	//heatmap_line("whg","E:/code_app/R_code/R_Pic/heatmap_data.txt","row",TRUE,TRUE,c("red","black","green"),10,5,5,F,"NA",TRUE,TRUE)
	@RequestMapping(value = "{model}/{function}/run", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@PathVariable String model,
			@PathVariable String function,
			@RequestBody JSONObject jsonObject,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(model+"/"+function+"功能被调用");
		
		String historyId = null;
		String[] param = new String[30];
		Iterator iter = jsonObject.entrySet().iterator();
		while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if(key.equalsIgnoreCase(new String("historyid"))){
				historyId = entry.getValue().toString();
			}
            else {
				String[] temp = key.split("_");
				String value = entry.getValue().toString();
				param[Integer.parseInt(temp[temp.length - 1])] = value;
//				System.out.println(entry.getKey().toString());
//				System.out.println(temp[temp.length - 1]);
//				System.out.println(value);
			}
        }
		
//        for(int j=0 ; j<param.length;j++) {
//        	System.out.println(param[j]);
//        }
		
		System.out.println("historyID:"+historyId);
        History history = historyService.selectHistoryByHistoryId(historyId);
        fileService.generatePicPathByDataPath(history);
        System.out.println(history.toString());
        historyService.updateHistory(history);
		
		String fileString = toolsService.generateFileString(model);
		String functionString = "";
		if(model.equalsIgnoreCase("FormatChange")) {
			functionString = toolsService.generateFormatChangeFunctionString(history, param);
			historyService.updateHistory(history);
		}else {
			functionString = toolsService.generateFunctionString(history, param);
		}
		System.out.println(fileString);
		System.out.println(functionString);
		String result = toolsService.toolsImpleFunction(fileString, functionString);
		if(result.equalsIgnoreCase(new String("OK"))) {
			String temp[] = history.getPicname().split(",");
			String results = "";
			for(int i=0;i<temp.length;i++) {
				if(i != 0) {
					results +=",";
				}
				results += "public/"+temp[i];
			}
			return results.toString();
		}else {
			return "Error";
		}
    }

}
