package com.qingyi.geektime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsActivemqApplication.class, args);
    }

}
