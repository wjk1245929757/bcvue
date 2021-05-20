package com.bioTools.demo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bioTools.demo.entities.History;
import com.bioTools.demo.util.GetAbsolutePathService;
import com.bioTools.demo.util.GetTimeStringService;

/*
 * public String upload(MultipartFile file,History history)
 * public String multiUpload(MultipartFile[] files,History history)
 * public String save(MultipartFile file,History history)
 * public String generatePicPathByDataPath(History history)
 * public void createFolder(String s)
 * public void deleteFolder(String email)
 * public void deleteFile(History history)
 * public void deleteFile(String dataPath, String picPath)
 * public void deleteDir(File file)
 * 
 * */

@Service
public class FileService {
	
	private static String saveFolderPath = "public/UserFolder/";
	private static String updateFolderPath = "public/UploadFolder/";
	
	@Autowired
	private GetTimeStringService getTimeStringService;
	@Autowired
	private GetAbsolutePathService getAbsolutePathService;
	

	public String upload(MultipartFile file,History history) {
    	String dataPath = updateFolderPath + history.getEmail() + "/";
    	String dataName = getTimeStringService.getTimeString() + "_" + file.getOriginalFilename();
		if (!file.isEmpty()) {    
            try {
                BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(new File(dataPath+dataName) ) );    
//                System.out.println(file.getName());  
                out.write(file.getBytes());    
                out.flush();    
                out.close();    
            } catch (FileNotFoundException e) {    
                e.printStackTrace();    
                return "上传失败," + e.getMessage();    
            } catch (IOException e) {    
                e.printStackTrace();    
                return "上传失败," + e.getMessage();    
            }
            history.setDataname( dataName );
            history.setDatapath(dataPath);
            return "上传成功";
    
        } else {    
            return "上传失败，因为文件是空的.";    
        }    
    }
	
	public String multiUpload(MultipartFile[] files,History history) {
    	String dataPath = updateFolderPath + history.getEmail() + "/";
        history.setDatapath(dataPath);
        for(MultipartFile file: files) {
        	String filename =  getTimeStringService.getTimeString() + "_" + file.getOriginalFilename();
    		if (!file.isEmpty()) {    
                try {
                    BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(new File(dataPath+filename) ) );    
//                    System.out.println(file.getName());  
                    out.write(file.getBytes());    
                    out.flush();    
                    out.close();    
                } catch (FileNotFoundException e) {    
                    e.printStackTrace();    
                    return "上传失败," + e.getMessage();    
                } catch (IOException e) {    
                    e.printStackTrace();    
                    return "上传失败," + e.getMessage();    
                }
                history.addDataname( filename );
            } else {    
                return "上传失败，因为文件是空的.";    
            }   
        }
        return "上传成功";
    }
	
	public String save(MultipartFile[] files,History history) {
		String picPath = saveFolderPath + history.getEmail() + "/";
        history.setPicpath(picPath);
        for (int i=0;i<files.length;i++) {
        	String picName = getTimeStringService.getTimeString() + "_" + files[i].getName()+".svg";
    		if (!files[i].isEmpty()) {
            	String temp = getAbsolutePathService.getAbsolutePath()+picPath+picName;
                File saveFile = new File(temp);
                if (!saveFile.exists()){
                    saveFile.mkdirs();
                }
                try {
                    files[i].transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
                } catch (IOException e) {
                    e.printStackTrace();
                }
                history.addPicname(picName);
            } else {    
                return "上传失败，因为文件是空的.";    
            }   
        }
        return "上传成功";
		 
	}
	
	private boolean macthString(String[] strs, String str) {
		for(int i=0;i<strs.length;i++) {
			if(str.equalsIgnoreCase(new String(strs[i]))) {
				return true;
			}
		}
		return false;
	}
	
	public String generatePicPathByDataPath(History history) {
		
		String[] pngs = {"regressionline"};
		String[] htmls = {"freqhistogram","pie","scatter3d","krona"};
		String[] pdfs = {"circos"};
		String[] jpgs = {"groupedviolin","violin"};
		
		String dataFileName = history.getDataname();
		System.out.println(dataFileName);
		String temp[] = dataFileName.split("\\.");
		String temp2[] = temp[0].split("_");
		String picName = "";
		if( macthString(pngs,history.getToolfunction()) ) {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + ".png";
		}else if (macthString(htmls,history.getToolfunction()) ) {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + ".html";
		}else if( macthString(pdfs,history.getToolfunction()) ) {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + ".pdf";
		}else if( macthString(jpgs,history.getToolfunction()) ) {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + ".jpg";
			picName += ",";
			picName += getTimeStringService.getTimeString() + "_" + temp2[1] + ".csv";
		}else if(history.getToolfunction().equalsIgnoreCase("ternaryplot_plot") ) {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + "(1).svg";
			picName += ",";
			picName += getTimeStringService.getTimeString() + "_" + temp2[1] + "(2).svg";
			picName += ",";
			picName += getTimeStringService.getTimeString() + "_" + temp2[1] + ".txt";
		}else {
			picName = getTimeStringService.getTimeString() + "_" + temp2[1] + ".svg";
		}
		String picPath = saveFolderPath + history.getEmail() + "/";
//		String temp3 = 	getTimeStringService.getTimeString() + "_" + picName;
		history.setPicname(picName);
		history.setPicpath(picPath);
		return history.getPicpath();
		
	}
	

	public void createFolder(String s) {
		File file=new File(saveFolderPath+s);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		File file_upload=new File(updateFolderPath+s);
		if(!file_upload.exists()){//如果文件夹不存在
			file_upload.mkdir();//创建文件夹
		}
		
	}
	
	public void deleteFolder(String email) {
		File file = new File(saveFolderPath+email);
		File file_upload=new File(updateFolderPath+email);
		deleteDir(file);
		deleteDir(file_upload);
    }
	
	public void deleteFile(History history) {
		if(history.getDatapath() != null) {
			String[] files = history.getDataname().split(",");
			for(int i=0;i<files.length;i++) {
				File file = new File(history.getDatapath()+files[i]);
				file.delete();
			}
		}
		if(history.getPicpath() != null) {
			String[] files = history.getPicname().split(",");
			for(int i=0;i<files.length;i++) {
				File file = new File(history.getPicpath()+files[i]);
				file.delete();
			}
		}
	}
	
	public void deleteFile(String dataPath, String picPath) {
		if(dataPath != null) {
			File file = new File(dataPath);
			file.delete();
		}
		if(picPath != null) {
			File file2 = new File(picPath);
			file2.delete();
		}
	}
	
//	public void deleteUpLoadFile(String email) {
//		File file = new File(updateFolderPath+email);
//		file.delete();
//	}
	
	public void deleteDir(File file){
        //判断是否为文件夹
        if(file.isDirectory()){
            //获取该文件夹下的子文件夹
            File[] files = file.listFiles();
            //循环子文件夹重复调用delete方法
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        //若为空文件夹或者文件删除，File类的删除方法
        file.delete();
    }

	
	
}
