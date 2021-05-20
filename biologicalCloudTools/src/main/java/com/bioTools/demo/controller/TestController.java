package com.bioTools.demo.controller;

import java.util.Iterator;
import java.util.Map;

import org.rosuda.REngine.REXPMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bioTools.demo.service.ToolsService;
import com.bioTools.demo.entities.History;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	

	@Autowired
	private ToolsService toolsService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private HistoryService historyService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String testUpload(@RequestParam("file") MultipartFile file) {
		System.out.println("/test/upload");
		History history = historyService.creatHistoryEntity("test","test","test");
		String dataPath = fileService.upload(file,history);
		return dataPath;
    }
	
	@RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
	@ResponseBody
	public String testMultiUpload(@RequestParam("files") MultipartFile[] files) {
		System.out.println("/test/multiUpload");
		for (MultipartFile file: files){
			// 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
        }
		return "sad";
    }
	
	@RequestMapping(value = "/createHistory", method = RequestMethod.GET)
	@ResponseBody
	public String testCreateHistory() {
		System.out.println("/test/createHistory");
		History history = historyService.creatHistoryEntity("test","test","test");
		System.out.println(history.toString());
//		String dataPath = fileService.upload(file,history);
		historyService.addHistory(history);
		return "test";
    }
	
	//heatmap_line("whg","E:/code_app/R_code/R_Pic/heatmap_data.txt","row",TRUE,TRUE,c("red","black","green"),10,5,5,F,"NA",TRUE,TRUE)
	@RequestMapping(value = "/run", method = RequestMethod.POST)
	@ResponseBody
	public String upload(
			@RequestBody JSONObject jsonObject) {
		String model = "BaseFunction";
		String function = "test";
		System.out.println(model+"/"+function+"功能被调用");
		
		String[] param = new String[30];
		Iterator iter = jsonObject.entrySet().iterator();
		int i = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println(entry.getKey().toString());
            System.out.println(entry.getValue().toString());
            param[i] = entry.getValue().toString();
            i++;
        }
		
		String fileString = toolsService.generateFileString(model);
		String functionString = toolsService.generateFunctionString( null , param);
//		System.out.println(fileString);
//		System.out.println(functionString);
		
		return "OK";
    }

}
