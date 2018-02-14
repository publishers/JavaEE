package com.epam.malykhin.bean;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.malykhin.bean.BeanForm.getStringUtil;
import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/13/2016.
 */
public class BeanFilters {
    private static final String[] BEAN_FORM_FIELD_FILTER = {
            FORM_FIELD_FILTER_NAME,
            FORM_FIELD_FILTER_TYPE,
            FORM_FIELD_FILTER_MANUFACTURE,
            FORM_FIELD_FILTER_PRICE_FROM,
            FORM_FIELD_FILTER_PRICE_TO,
            FORM_FIELD_FILTER_NUMBER_GOODS,
            "currentPage"
    };

    private Map<String, String> beans;

    public BeanFilters(HttpServletRequest request) {
        init(request);
    }

    private void init(HttpServletRequest request) {
        beans = new LinkedHashMap();
        for (String fieldName : BEAN_FORM_FIELD_FILTER) {
            String field = getStringUtil(request.getParameter(fieldName));
            beans.put(fieldName, field);
        }
    }

    public String[] getBeanFormFieldFilter() {
        return BEAN_FORM_FIELD_FILTER;
    }

    public Map<String, String> getBeans() {
        return beans;
    }
}
