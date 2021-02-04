package com.gitee.ywj1352;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class CH01ClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception{
        CH01ClassLoader ch01ClassLoader = new CH01ClassLoader();
        Class<?> hello = ch01ClassLoader.findClass("Hello");
        Method hello1 = hello.getDeclaredMethod("hello");
        hello1.invoke(hello.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try (FileInputStream is = new FileInputStream("D:\\learn\\jike\\CH-Demo\\src\\main\\resources\\ch01\\Hello.xlass")){
            bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < bytes.length; i++) {
            byte aByte = bytes[i];
            bytes[i] = (byte)(255 - aByte);
        }
        return defineClass(name,bytes,0,bytes.length);
    }
}
