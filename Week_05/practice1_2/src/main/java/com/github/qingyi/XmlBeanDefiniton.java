package com.github.qingyi;

import com.github.qingyi.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlBeanDefiniton {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        User user = applicationContext.getBean(User.class);
        System.out.println(user);
    }

}
