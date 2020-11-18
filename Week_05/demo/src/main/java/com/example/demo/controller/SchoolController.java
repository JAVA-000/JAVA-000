package com.example.demo.controller;

import com.example.demo.server.ISchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zenglh
 * @date 2020/11/18 18:39
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    //第一种
    //@Autowired
    //@Qualifier("school")
    //第二种
    @Resource
    private ISchool school;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void Test() {
        school.sayHi();
    }

}
