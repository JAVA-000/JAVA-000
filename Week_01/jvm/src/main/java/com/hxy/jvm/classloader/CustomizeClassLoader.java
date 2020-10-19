package com.hxy.jvm.classloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class CustomizeClassLoader extends ClassLoader {

    private static final String CLASS_PATH = "customizer_class_path/";
    private static final String CLASS_SUFFIX = ".xlass";
    private static final int DECODER = 255;

    @Override
    protected Class<?> findClass(String name) {
        try {
            String path = getPath(name);
            log.info("load class {} from {}", name, path);
            InputStream byteCodeStream = this.getClass().getClassLoader().getResourceAsStream(path);
            byte[] byteCodeArray = IOUtils.toByteArray(byteCodeStream);
            if (ArrayUtils.isEmpty(byteCodeArray)) {
                throw new RuntimeException(String.format("can not load class %s", name));
            }

            byte[] decodeByteCodeArray = decode(byteCodeArray);
            return defineClass(name, decodeByteCodeArray, 0, decodeByteCodeArray.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPath(String name) {
        return CLASS_PATH + name + CLASS_SUFFIX;
    }

    private byte[] decode(byte[] byteCodeArray) {
        byte[] decodeByteCodeArray = new byte[byteCodeArray.length];
        for (int i = 0; i < byteCodeArray.length; i++) {
            decodeByteCodeArray[i] = (byte) (DECODER - byteCodeArray[i]);
        }
        return decodeByteCodeArray;
    }

}
