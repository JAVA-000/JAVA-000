package com.example.demo.server.impl;

import com.example.demo.server.ISchool;
import org.springframework.stereotype.Component;

/**
 * @author zenglh
 * @date 2020/11/18 16:47
 */
//@Service
@Component("school")
public class School implements ISchool {


    @Override
    public void sayHi() {
        System.out.println("hello world");
    }
}
