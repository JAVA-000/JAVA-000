package com.github.qingyi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnClass(Student.class)
@EnableConfigurationProperties(StudentProperties.class)
@ConditionalOnProperty(prefix = "student", value = "enabled", havingValue = "true")
@PropertySource("classpath:application.properties")
public class StudentAutoConfiguration {

    @Autowired
    private StudentProperties studentProperties;

    @Bean
    @ConditionalOnMissingBean(StudentAutoConfiguration.class)
    public Student schoolAutoConfiguration() {
        Student student = new Student();
        student.setId(studentProperties.getId());
        student.setName(studentProperties.getName());
        return student;
    }

}
