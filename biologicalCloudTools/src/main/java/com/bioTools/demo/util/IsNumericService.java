package com.bioTools.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsNumericService {

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
		return false;
		}
		return true;
	}
	public static boolean isNumericInt(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
  }

}
