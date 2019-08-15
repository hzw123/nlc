package com.nlc.nraas.tools;

import org.apache.commons.lang.StringUtils;

public class StringTransform {

	/**
	 * 字符转换int类型，转不成功返回-1
	 * @param s
	 * @return
	 */
	public static int TransfromInt(String s){
		int n=-1;
		if(StringUtils.isNotBlank(s)){
			n=Integer.parseInt(s);
		}
		if(n<1){
			n=-1;
		}
		return n;
	}
}
