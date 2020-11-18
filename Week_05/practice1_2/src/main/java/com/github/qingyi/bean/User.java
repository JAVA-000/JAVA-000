package com.github.qingyi.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {

    private Long id;

    private String name;


}
