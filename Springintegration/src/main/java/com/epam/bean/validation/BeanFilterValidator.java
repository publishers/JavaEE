package com.epam.bean.validation;

import com.epam.bean.BeanFilter;
import org.apache.solr.common.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BeanFilterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BeanFilter.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BeanFilter filter = (BeanFilter) target;
        if (StringUtils.isEmpty(filter.getCurrentPage())) {
            filter.setCurrentPage("0");
        }
        if (StringUtils.isEmpty(filter.getNumberGoods())) {
            filter.setNumberGoods("8");
        }

    }
}
