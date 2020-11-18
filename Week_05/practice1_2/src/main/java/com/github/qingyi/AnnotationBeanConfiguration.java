package com.github.qingyi;

import com.github.qingyi.bean.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(User.class)
public class AnnotationBeanConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册配置类
        applicationContext.register(AnnotationBeanConfiguration.class);
        applicationContext.refresh();
        User user = applicationContext.getBean(User.class);
        System.out.println(user);
    }

}
