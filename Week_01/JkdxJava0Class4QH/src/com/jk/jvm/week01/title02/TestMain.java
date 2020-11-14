package com.jk.jvm.week01.title02;

import java.lang.reflect.Method;

import com.jk.util.ObjectUtil;

public class TestMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		try {
			
			CusTomClassLoaderA classloader_a = new CusTomClassLoaderA();
			
			Class objc_=Class.forName(PramInfo.CLASSNAME_HELLO,true,classloader_a);
			
			if(ObjectUtil.isNullOrEmpty(objc_, true)) {
				throw new Exception("  CusTomClassLoaderA loading class error ----- ");
			}
			
			Method  method_ = objc_.getDeclaredMethod(PramInfo.METHOD_NAME_HELLO);
//			jdk 8
//			Object obj_ = objc_.newInstance();
//			method_.invoke(obj_);
//			jdk 9
			method_.invoke(objc_.getDeclaredConstructor().newInstance());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
