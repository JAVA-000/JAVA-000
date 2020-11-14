package com.jk.util;
/**
 *  类型转换工具
 * @author qin.hui
 *
 */
public class ConvertUtil {
	
	
	/**
	 * byte 转 int
	 * @param b
	 * @return
	 */
	public static  int byteToInt(byte b){
		
		int x = b & 0xff;
		
		return x;
	}
	
	
	/**
	 * int 转 byte
	 * @param x
	 * @return
	 */
	public static   byte intToByte(int x){
		
		byte b =(byte) (x & 0xff);
		
		return b;
	}
	
}
