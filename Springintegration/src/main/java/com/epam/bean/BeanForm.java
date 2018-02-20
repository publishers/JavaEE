package com.epam.bean;

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


public class BeanForm {
    private static final String[] BEAN_FORM_FIELD_NAMES = {
            FORM_FIELD_FIRST_NAME, FORM_FIELD_SECOND_NAME,
            FORM_FIELD_EMAIL, FORM_FIELD_PASSWORD,
            FORM_FIELD_PASSWORD2, FORM_FIELD_CAPTCHA, NEWSLETTER
    };
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

    public Map<String, String> getBeans() {
        return beans;
    }
}
