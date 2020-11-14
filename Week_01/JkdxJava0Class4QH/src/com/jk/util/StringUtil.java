package com.jk.util;




public class StringUtil {
	
	
	
	public static String isNullDefault(String pram,String value) {
		if(isNullOrEmpty(pram, true)) {
			return value;
		}
		return pram;
	}
	public static boolean equals(String pram,String value,boolean dv) {
		if(isNullOrEmpty(pram, true)&&isNullOrEmpty(value, true)) {
			return dv;
		}
		if(isNullOrEmpty(pram, true)) {
			return !dv;
		}
		if(isNullOrEmpty(value, true)) {
			return !dv;
		}
		if(pram.equals(value)) {
			return dv;
		}
		return !dv;
	}
	
	
	
	
	
	/**
	 * 判断字符串是否为空值
	 * @param strvals
	 * @param dval
	 * @return
	 */
	public static boolean isNullOrEmpty(String[] strvals,boolean dval) {
		for(String v : strvals) {
			boolean bool =  isNullOrEmpty(v, dval);
			if(String.valueOf(bool).equals(String.valueOf(dval))) {
				return dval;
			}
		}
		return !dval;
		
	}
	
	
	
	public static boolean isNullOrEmpty(String strval,boolean dval) {
		if(null==strval || "".equals(strval) || strval.length()==0||"null".contentEquals(strval)) {
			return dval;
		}
		else
			return !dval;
		
	}
	
	
	
}
