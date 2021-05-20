package com.bioTools.demo;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bioTools.demo.dao.HistoryDAO;
import com.bioTools.demo.service.FileService;
import com.bioTools.demo.service.HistoryService;
import com.bioTools.demo.service.ToolsService;
import com.bioTools.demo.util.IsNumericService;

import junit.framework.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)
class BiologicalCloudToolsApplicationTests {
	
//	@Autowired
//	private UpLoadFileService upLoadFileService;
	@Autowired
	private HistoryDAO historyDao;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ToolsService toolService;
		
	
	@SuppressWarnings("deprecation")
	@Test
	void contextLoads(){
//		String s = "test.txt";
//		System.out.println(s.length());
//		String temp = s.substring(0,s.length()-4);
//		System.out.println(temp);
//		historyService.isHistoryNumOver10("001@qq.com");
//		historyService.deleteByHistoryId("00154is20210428161009mb8L");
//		fileService.deleteFile("public/UploadFolder/001@qq.com/20210428161009_heatmap.txt",null);
//		beeswarm_chart('E:/eclipse_workspace/biologicalCloudTools/public/UploadFolder/001@qq.com/20210503003846_fengqun.txt',
//		'E:/eclipse_workspace/biologicalCloudTools/public/UserFolder/001@qq.com/20210503003846_fengqun.svg',
//		'蜂群图','X轴','Y轴',c("#FF0000","#00FF95","#3C00FF"))
		
		
		
//		String functionString =  "beeswarm_chart('E:/eclipse_workspace/biologicalCloudTools/public/UploadFolder/001@qq.com/20210503003846_fengqun.txt','E:/eclipse_workspace/biologicalCloudTools/public/UserFolder/001@qq.com/20210503003846_fengqun.svg','蜂群图','X轴','Y轴',c('#FF0000','#00FF95','#3C00FF'))";
//		String fileString = "'E:/eclipse_workspace/biologicalCloudTools/public/RTools/BaseFunction.R";
//		String function ="";
//		try {
//			function = new String( functionString.getBytes("utf-8") , "utf-8");
//			System.out.println(function);
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		toolService.toolsImpleFunction(fileString, functionString);
		
		
		
		
//		for(int i = 0;i<temp.length;i++) {
//			System.out.println(temp[i]);
//		}
		
		
		
//		int sum = Math.addExact(3, 2);
//		System.out.println(sum);
//		Assert.assertEquals(5, sum);

		String[] pngs = {"regressionline","asd"};
		for(int i=0;i<pngs.length;i++) {
			System.out.println(pngs[i]);
		}

		System.out.println(IsNumericService.isNumeric("asd"));
		System.out.println(IsNumericService.isNumeric("1"));
		System.out.println(IsNumericService.isNumeric("1.0"));
		
	}
	

}
