package com.epam.bean.validation;

import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FilterValidator {
    private static final Logger LOG = LoggerFactory.getLogger(FilterValidator.class);
    private static final String DIGIT_REGEX = "[1-9]{1}\\d{0,}";

    public void validate(Map<String, String> filterParameters) {
        if (StringUtils.isEmpty(filterParameters.get("currentPage"))) {
            filterParameters.put("currentPage", "0");
        }
        if (StringUtils.isEmpty(filterParameters.get("numberGoods"))) {
            filterParameters.put("numberGoods", "8");
        }
        checkAllParameters(filterParameters);
    }

    private void checkAllParameters(Map<String, String> filters) {
        String numberGoods = filters.get("numberGoods");
        String currentPage = filters.get("currentPage");
        checkDigit(numberGoods, "numberGoods");
        checkDigit(currentPage, "currentPage");
    }

    private void checkDigit(String digitValue, String fieldName) {
        if (!digitValue.matches(DIGIT_REGEX)) {
            LOG.info("fieldName: " + fieldName + " must be a digit, but it's: " + digitValue);
        }
    }
}
