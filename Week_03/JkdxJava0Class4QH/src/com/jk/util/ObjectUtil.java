package com.jk.util;

public class ObjectUtil {

	public static boolean isNullOrEmpty(Object strval,boolean dval) {
		if(null==strval) {
			return dval;
		}
		else
			return !dval;
	}
	
}
