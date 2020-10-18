package com.hello;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author LinJinhao
 * @date 2020/10/17 2:58 下午
 * @desc 自定义类加载器
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String className) {
        byte[] data = null;
        InputStream in;
        className = className.replace(".", "/");
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            in = new FileInputStream(new File("Week_01/java/com/hello/" + className + ".xlass"));
            int a;
            while ((a = in.read()) != -1) {
                out.write(a);
            }
            data = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
