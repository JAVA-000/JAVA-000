package com.hxy.jvm.classloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class ClassLoaderStart implements CommandLineRunner {

    @Autowired
    private CustomizeClassLoader customizeClassLoader;

    @Override
    public void run(String... args) {
        try {
            Class<?> helloClazz = customizeClassLoader.findClass("Hello");
            Object helloObj = helloClazz.newInstance();
            Method helloMethod = helloClazz.getMethod("hello");
            helloMethod.invoke(helloObj);
        } catch (Exception e) {
            log.error("unexpected exception: {}", e);
        }
    }

}

