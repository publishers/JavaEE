package com.epam.bean.validation;

import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FilterValidator {
    private static final Logger LOG = LoggerFactory.getLogger(FilterValidator.class);
    private static final List<String> FILTER_FIELDS = Arrays.asList("title", "idType", "idManufacture", "priceFrom",
            "priceTo", "numberGoods", "currentPage", "sortBy");

    private static final String DIGIT_REGEX = "\\d{1,}";

    public void validate(Map<String, String> filterParameters) {
        initDefaultParams(filterParameters);
        Map<String, String> filteredParams = filterParameters.entrySet().stream()
                .filter(s -> FILTER_FIELDS.contains(s.getKey()) && !StringUtils.isEmpty(s.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        filterParameters.entrySet().retainAll(filteredParams.entrySet());
        checkParameters(filterParameters);
    }

    private void initDefaultParams(Map<String, String> filterParameters) {
        if (StringUtils.isEmpty(filterParameters.get(FILTER_FIELDS.get(5)))) {
            filterParameters.put(FILTER_FIELDS.get(5), "8");
        }
        if (StringUtils.isEmpty(filterParameters.get(FILTER_FIELDS.get(6)))) {
            filterParameters.put(FILTER_FIELDS.get(6), "0");
        }
    }

    private void checkParameters(Map<String, String> filters) {
        String numberGoods = filters.get(FILTER_FIELDS.get(5));
        String currentPage = filters.get(FILTER_FIELDS.get(6));
        if (!isDigit(numberGoods, FILTER_FIELDS.get(5)) || !isDigit(currentPage, FILTER_FIELDS.get(6))) {
            filters.put(FILTER_FIELDS.get(6), "0");
            filters.put(FILTER_FIELDS.get(5), "8");
        }
    }

    private boolean isDigit(String digitValue, String fieldName) {
        boolean result;
        if (!(result = digitValue.matches(DIGIT_REGEX))) {
            LOG.info("fieldName: " + fieldName + " must be a digit, but it's: " + digitValue);
        }
        return result;
    }
}
