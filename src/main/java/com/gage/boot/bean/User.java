package com.gage.boot.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
