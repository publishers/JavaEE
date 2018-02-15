package com.epam.malykhin.bean;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.*;


public class BeanForm {
    private static final String[] BEAN_FORM_FIELD_NAMES = {
            FORM_FIELD_FIRST_NAME, FORM_FIELD_SECOND_NAME,
            FORM_FIELD_EMAIL, FORM_FIELD_PASSWORD,
            FORM_FIELD_PASSWORD2, FORM_FIELD_CAPTCHA, NEWSLETTER
    };
    @Getter
    private Map<String, String> beans;

    public BeanForm(HttpServletRequest request) {
        init(request);
    }

    public static String getStringUtil(String parameter) {
        return parameter == null ? "" : parameter;
    }

    private void init(HttpServletRequest request) {
        beans = new LinkedHashMap<>();
        for (String fieldName : BEAN_FORM_FIELD_NAMES) {
            String field = getStringUtil(request.getParameter(fieldName));
            beans.put(fieldName, field);
        }
    }
}
