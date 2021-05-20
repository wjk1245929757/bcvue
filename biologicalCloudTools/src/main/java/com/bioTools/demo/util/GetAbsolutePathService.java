package com.bioTools.demo.util;

import java.net.URL;

import org.springframework.stereotype.Service;


@Service
public class GetAbsolutePathService {
	
	public String getAbsolutePath() {
		URL pathUrl = GetAbsolutePathService.class.getResource("");
		String s = pathUrl.toString();
		String temp[] = s.split("/");
		String absolutePath = "";
		for( int i=1; i<temp.length;i++) {
			if(temp[i].equalsIgnoreCase(new String("biologicalCloudTools"))) {
				absolutePath += temp[i];
				absolutePath += "/";
				break;
			}
			absolutePath += temp[i];
			absolutePath += "/";
		}
		return absolutePath;
	}

}
