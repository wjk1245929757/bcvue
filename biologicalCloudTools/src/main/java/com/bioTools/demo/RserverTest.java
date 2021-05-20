package com.bioTools.demo;

import java.net.URL;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

public class RserverTest {
	public static void main(String[] args) {
		
//		URL pathUrl = RserverTest.class.getResource("");
//		String s = pathUrl.toString();
//		String temp[] = s.split("/");
//		String absolutePath = "";
//		for( int i=1; i<temp.length;i++) {
//			if(temp[i].equalsIgnoreCase(new String("biologicalCloudTools"))) {
//				absolutePath += temp[i];
//				absolutePath += "/";
//				break;
//			}
//			absolutePath += temp[i];
//			absolutePath += "/";
//		}
		
//		String filePath = "source('E:/eclipse_workspace/biologicalCloudTools/public/RTools/BaseFunction.R')";
//		String functionPath = "heatmap('E:/eclipse_workspace/biologicalCloudTools/public/UploadFolder/001@qq.com/20210422190404_heatmap.txt',"
//				+ "'E:/eclipse_workspace/biologicalCloudTools/public/UserFolder/001@qq.com/20210422190408_heatmap.png',"
//				+ "c(1,2,4,5),10,'row',TRUE,TRUE,NULL,10,5,5,FALSE,'NA',TRUE,TRUE)";
//		
//		String sourcePath = "public/UploadFolder/test/test/line_data.txt";
//		String savePath = "public/UserFolder/test/test.png";
		
		
		
//		String rPath = "source('"+absolutePath+"public/RTools/baseFunction/test.R"+"')";
//		String functionString = "chartLine(\'" + absolutePath + sourcePath + "','xx','yy','" + absolutePath + savePath + "\')";
		
		String rPath = "source('E:/eclipse_workspace/biologicalCloudTools/public/RTools/FormatChange.R')";
		String functionString = "svg_conversion('E:/eclipse_workspace/biologicalCloudTools/public/UploadFolder/001@qq.com/20210520205915_001.svg','E:/eclipse_workspace/biologicalCloudTools/public/UserFolder/001@qq.com/20210520205915_001.png','png')";
		
		
//		heatmap_line("E:/R/code/heatmap_data.txt",
//	             "E:/code_app/R_code/R_Pic/heatmap_out.png",
//	             c(1,2,4,5),3,"row",TRUE,TRUE,NULL,10,5,5,F,"NA",TRUE,TRUE)
		RConnection rc = null;
//		String RPath = "source('public/RTools/baseFunction/test.R')"
		try {
			rc = new RConnection(); //建立与Rserve的连接
			System.out.println("asd");
			rc.eval(rPath);
			System.out.println("asd");
			rc.eval(functionString); //执行R语句
			System.out.println("asd");
//			REXP x = rc.eval("chartLine("+data_path+","+X_name+","+Y_name+","+save_path+")"); //执行R语句
//			System.out.println(x.asString());
	    } catch (Exception e) {
			e.printStackTrace();
	    } finally{
	    	rc.close();//关闭与Rserve的连接
		}
		System.out.println("asd");
	}

}
