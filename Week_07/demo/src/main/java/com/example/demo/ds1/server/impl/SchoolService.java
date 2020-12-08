package com.example.demo.ds1.server.impl;

import com.example.demo.ds1.server.ISchoolService;
import org.springframework.stereotype.Component;

/**
 * @author zenglh
 * @date 2020/11/18 16:47
 */
//@Service
@Component("school")
public class SchoolService implements ISchoolService {


    @Override
    public void sayHi() {
        System.out.println("hello world");
    }
}
