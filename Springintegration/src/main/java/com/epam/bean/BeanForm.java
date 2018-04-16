package com.epam.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.util.StaticTransformVariable.FORM_FIELD_CAPTCHA;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_EMAIL;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_FIRST_NAME;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_PASSWORD;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_PASSWORD2;
import static com.epam.util.StaticTransformVariable.FORM_FIELD_SECOND_NAME;
import static com.epam.util.StaticTransformVariable.NEWSLETTER;

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
