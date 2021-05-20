package com.bioTools.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
//import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bioTools.demo.entities.History;
import com.bioTools.demo.util.GetAbsolutePathService;
import com.bioTools.demo.util.IsNumericService;


/*
 * R语言后端服务类
 * 功能：实现对135个工具的R语言调用
 */

/*
 * BaseFunction
 * # 1. sankey               桑基图
 * # 2. polarbar       极坐标柱状图
 * # 3. beeswarm             蜂群图
 * # 4. binaryheatmap      二值热图
 * # 5. podfigure            豆荚图
 * # 6. brokencolumn     断轴柱状图
 * # 7. ternaryplot          三元图
 * # 8. upsetvenn       UpSet维恩图
 * # 9. circos     circos物种关系图
 * # 10. jxx               九象限图
 * # 11. groupedviolin 分组小提琴图
 * # 12. violin            小提琴图
 * # 13. freqhistogram   频率直方图
 * # 14. pie                   饼图
 * # 15. scatter3d         3D散点图
 * # 16. regressionline  线性回归图
 * # 17. weightednetwork 权重网络图
 * # 18. directednetwork 有向网络图
 * # 19. seniorbubble    高级气泡图
 * # 20. seniorscatter   高级散点图
 * # 21. vn_plot             维恩图
 * # 22. krona        Krona层级饼图
 * # 23. maplot                MA图
 * # 24. volcano             火山图
 * # 25. heatmap               热图
 * # 26. bubble              气泡图
 * # 27. box                 盒形图
 * # 28. area                面积图
 * # 29. histogram           柱状图
 * # 30. line                折线图
 * # 31. scatterplot         散点图
 * 
 */

@Service
public class ToolsService{

	private String absolutePath = "";
	
	@Autowired
	GetAbsolutePathService getABPathService;
	
	
	public String generateFileString(String model) {
		this.absolutePath = getABPathService.getAbsolutePath(); 
		String rFilePath = "public/RTools" + "/" + model + ".R";
		String fileStringTemp = "source('"+this.absolutePath+rFilePath+ "')";
//		System.out.println(fileStringTemp);
		return fileStringTemp;
	}
	
	public String generateFunctionString(History history, String[] param) {
		
		this.absolutePath = getABPathService.getAbsolutePath();
		String[] dataNames = history.getDataname().split(",");
		String[] picNames = history.getPicname().split(",");
		
		
		String functionStringTemp = history.getToolfunction() + "(";
		

		if(dataNames.length == 1) {
			functionStringTemp += "'" + this.absolutePath+history.getDatapath()+dataNames[0] + "'";
		}else {
			functionStringTemp += "c(";
			for (int i=0; i<dataNames.length;i++) {
				if(i==0) {
					functionStringTemp += "'" +this.absolutePath+history.getDatapath()+ dataNames[i]+"'";
				}else {
					functionStringTemp += ",'" +this.absolutePath+history.getDatapath()+ dataNames[i]+"'";
				}
			}
			functionStringTemp += ")";
		}
		
		
		functionStringTemp += ",";
		if(picNames.length == 1) {
			functionStringTemp += "'"+ this.absolutePath+history.getPicpath()+picNames[0] + "'";
		}else {
			functionStringTemp += "c(";
			for (int i=0; i<picNames.length;i++) {
				if(i==0) {
					functionStringTemp += "'" +this.absolutePath+history.getPicpath()+ picNames[i]+"'";
				}else {
					functionStringTemp += ",'" +this.absolutePath+history.getPicpath()+ picNames[i]+"'";
				}
			}
			functionStringTemp += ")";
		}
		
		for (int i = 0; i<param.length; i++) {
			if(param[i] == null) {break;}
			if( param[i].equalsIgnoreCase(new String("TRUE")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].equalsIgnoreCase(new String("FALSE")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].equals(new String("NULL")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].charAt(0) == 'c' && param[i].charAt(1) == '(' && param[i].charAt(param[i].length()-1)==')') {
//				String cTemp = param[i].substring(1, param[i].length()-1);
				functionStringTemp +=  ","+param[i];
			}else if( IsNumericService.isNumeric(param[i]) ||  IsNumericService.isNumericInt(param[i])) {
				functionStringTemp +=  ","+param[i];
			}else {
				functionStringTemp +=  ",'"+param[i] + "'";
			}
		}
		
		functionStringTemp += ")";
		
//		System.out.println(functionStringTemp);
		return functionStringTemp;
	}
	
	public String generateFormatChangeFunctionString(History history, String[] param) {

		this.absolutePath = getABPathService.getAbsolutePath();
		String[] dataNames = history.getDataname().split(",");
		String[] picNames = history.getPicname().split(",");
		
		
		String functionStringTemp = history.getToolfunction() + "(";
		
		functionStringTemp += "'" + this.absolutePath+history.getDatapath()+dataNames[0] + "',";
		
		String temp[] = picNames[0].split("\\.");
		functionStringTemp += "'"+ this.absolutePath+history.getPicpath()+temp[0]+"."+param[0] + "'";
		history.setPicname(temp[0]+"."+param[0]);
		
		
		for (int i = 0; i<param.length; i++) {
			if(param[i] == null) {break;}
			if( param[i].equalsIgnoreCase(new String("TRUE")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].equalsIgnoreCase(new String("FALSE")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].equals(new String("NULL")) ) {
				functionStringTemp +=  ","+param[i];
			}else if( param[i].charAt(0) == 'c' && param[i].charAt(1) == '(' && param[i].charAt(param[i].length()-1)==')') {
//				String cTemp = param[i].substring(1, param[i].length()-1);
				functionStringTemp +=  ","+param[i];
			}else if( IsNumericService.isNumeric(param[i]) ||  IsNumericService.isNumericInt(param[i])) {
				functionStringTemp +=  ","+param[i];
			}else {
				functionStringTemp +=  ",'"+param[i] + "'";
			}
		}
		
		functionStringTemp += ")";
		
//		System.out.println(functionStringTemp);
		return functionStringTemp;
	}
	
	public String test() {
		String result = "";
		RConnection rc = null;
		try {
			rc = new RConnection();
			REXP x = rc.eval("R.version.string");
			result = x.asString();
			System.out.println(result);
	    } catch (Exception e) {
			e.printStackTrace();
	    } finally{
	    	rc.close();
		}
		return result;
	}
	
	
	public String toolsImpleFunction(String fileString, String functionString){
		String result = "";
		RConnection connection = null;
		String function = "";
		try {
			function = new String( functionString.getBytes("GBK") , "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			connection = new RConnection(host, port);
//			connection = new RConnection();
		} catch (RserveException e) {
			e.printStackTrace();
		}
		
		try {
			connection.eval(fileString);
			connection.eval(function);
			result = "OK";
		} catch (RserveException e) {
			result = "Error";
			e.printStackTrace();
		}
		
		connection.close();
		return result;
	}
	
	
	
	public String toolTest(String sourceFilePath, String destFilePath) 
			throws REXPMismatchException{
		
		String result = "";
		String rFilePath = "public/RTools/Test.R";
		String fileString = "source('"+this.absolutePath+rFilePath+"')";
		String functionString = "chartLine(\'" + this.absolutePath + sourceFilePath + "','xx','yy','" + this.absolutePath + destFilePath + "\')";
		
		RConnection connection = null;
		try {
			connection = new RConnection();
		} catch (RserveException e) {
			e.printStackTrace();
		}
		try {
			connection.eval(fileString);
			REXP rexp = connection.eval(functionString);
			result = rexp.asString();
			System.out.println(result);
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return result;
	}
	
	
	
}
