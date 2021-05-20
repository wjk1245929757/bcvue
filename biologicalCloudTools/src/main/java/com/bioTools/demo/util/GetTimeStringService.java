package com.bioTools.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class GetTimeStringService {

	public String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//    	System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    	return df.format(new Date());
	}
}
