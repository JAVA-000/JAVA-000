package com.jk.jvm.week01.title02;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import com.jk.util.ConvertUtil;
import com.jk.util.FileUtil;
import com.jk.util.StringUtil;

/**
 * java 0期 week-1 1周 2题
 *  自定义类加载器A
 * @author epc-0811
 *
 */
public class CusTomClassLoaderA extends ClassLoader{

	
	
	/**
	 * @param name class文件名称
	 */
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		
		try {
			
//			过滤 hello 以外的类加载器
			if(StringUtil.equals(name, PramInfo.CLASSNAME_HELLO, false)) {
				 return loadClass(name, true);
			}
			
			String pageurl = CusTomClassLoaderA.class.getPackageName();
			
			pageurl = pageurl.replace(".", "/");
			
			File file = new File("src/"+pageurl+"/"+name+".xlass");
			
//			读取file
			byte[] filebytes = FileUtil.getFileByte(file);
			
//			循环转换
			for(int i=0;i<filebytes.length;i++) {
				
				int temp_ = 255-filebytes[i];
				
				filebytes[i] = ConvertUtil.intToByte(temp_);
				
			}
			
			Class<?> class_ =this.defineClass(name,filebytes, 0, filebytes.length);
			
			return class_;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
        
    }
	
	
	
	
	
}
