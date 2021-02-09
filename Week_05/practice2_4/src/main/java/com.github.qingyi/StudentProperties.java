package com.github.qingyi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "student")
public class StudentProperties {

    private Integer id;

    private String name;

}
