package com.epam.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BeanForm {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String password2;
    private String captcha;
    private String newletter;
}
