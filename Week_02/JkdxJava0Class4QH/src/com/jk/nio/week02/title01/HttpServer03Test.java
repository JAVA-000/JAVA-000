package com.jk.nio.week02.title01;

import com.jk.util.HttpUtil;

public class HttpServer03Test {

	public static void main(String[] args) {
		String utl = "http://localhost:8803/";
		String result = HttpUtil.doGet(utl);
		System.out.println(result);
		
	}
	
}
