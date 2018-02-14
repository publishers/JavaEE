package com.epam.malykhin.database.dao.filter;

import com.epam.malykhin.bean.BeanFilters;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/16/2016.
 */
public class FilterWhere {
    private static final String[] BEAN_FORM_FIELD_FILTER = {
            FORM_FIELD_FILTER_NAME,
            FORM_FIELD_FILTER_TYPE,
            FORM_FIELD_FILTER_MANUFACTURE,
            FORM_FIELD_FILTER_PRICE_FROM,
            FORM_FIELD_FILTER_PRICE_TO
    };
    private static final String[] ACTIONS = {"=", "=", "=", ">=", "<="};

    private Map<Integer, String> beansWhereFilters;

    private StringBuilder sqlQuertyWhere = new StringBuilder();
    private BeanFilters beanFilters;

    public FilterWhere(BeanFilters beanFilters) {
        beansWhereFilters = new LinkedHashMap<>();
        this.beanFilters = beanFilters;
    }

    public void start() {
        Map<String, String> beans = beanFilters.getBeans();
        for (int i = 0; i < BEAN_FORM_FIELD_FILTER.length; i++) {
            String bean = beans.get(BEAN_FORM_FIELD_FILTER[i]);
            if (i == 0 ? isValidFirstBean(bean) : isValidBeanFilters(bean)) {
                beansWhereFilters.put(i, bean);
            }
        }
        checkBeansWhereFilter();
        createSQL();
    }

    private boolean isValidFirstBean(String bean) {
        return bean != null && !bean.matches("| +");
    }

    private void createSQL() {
        int deleteLastAnd = 0;
        for (int i = 0; i < BEAN_FORM_FIELD_FILTER.length; i++) {
            if (beansWhereFilters.containsKey(i)) {
                String beanName = checkToPrice(BEAN_FORM_FIELD_FILTER[i]);
                sqlQuertyWhere.append(beanName).append(ACTIONS[i]).append("? ");

                deleteLastAnd = sqlQuertyWhere.length();
                sqlQuertyWhere.append(" and ");
            }
        }
        if (deleteLastAnd != 0)
            sqlQuertyWhere.delete(deleteLastAnd, sqlQuertyWhere.length());
    }

    private String checkToPrice(String param) {
        String price = "price";
        return param.contains(price) ? price : param;
    }

    private void checkBeansWhereFilter() {
        if (beansWhereFilters.size() != 0) {
            sqlQuertyWhere.append(" where ");
        }
    }

    private boolean isValidBeanFilters(String bean) {
        return bean != null && bean.matches("\\d+");
    }

    public String getPartSQL() {
        return sqlQuertyWhere.toString();
    }

    public Map<Integer, String> getBeansWhereFilters() {
        return beansWhereFilters;
    }
}
