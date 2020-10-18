package com.hello;

import java.lang.reflect.Method;

/**
 * @author LinJinhao
 * @date 2020/10/17 3:29 下午
 * @desc
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // week01 题目2
        MyClassLoader myClassLoader = new MyClassLoader();
        Object object = myClassLoader.loadClass("com.hello.Hello").newInstance();
        Method method = object.getClass().getMethod("hello");
        method.invoke(object);
    }
}
