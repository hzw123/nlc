package com.nlc.nraas.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Method {

	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
	
	public static String  getTaskId(String code,String userCode,int n){
		return code+sdf.format(new Date())+userCode+getSerialNumber(n);
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	private static String getSerialNumber(int n){
		if(n<10){
			return "00"+n;
		}else if(n<100){
			return "0"+n;
		}else{
			return (""+n).trim();
		}
	}
}