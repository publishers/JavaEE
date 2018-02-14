package com.epam.malykhin.database.dao.filter;

import com.epam.malykhin.bean.BeanFilters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.FORM_FIELD_FILTER_NUMBER_GOODS;

/**
 * Created by Serhii_Malykhin on 12/16/2016.
 */
public class FilterLimit {
    private static final String[] BEAN_FORM_FIELD_FILTER = {
            FORM_FIELD_FILTER_NUMBER_GOODS,
            "currentPage"
    };
    private StringBuilder sqlQuertyLimit = new StringBuilder();
    private List<Integer> beansLimitFilters;
    private BeanFilters beanFilters;

    public FilterLimit(BeanFilters beanFilters) {
        beansLimitFilters = new ArrayList<>();
        this.beanFilters = beanFilters;
    }

    public void start() {
        Map<String, String> beans = beanFilters.getBeans();
        String numberGoods = beans.get(BEAN_FORM_FIELD_FILTER[0]);
        String currentPage = beans.get(BEAN_FORM_FIELD_FILTER[1]);
        int numberGoodsInt = 8;
        int currentPageInt = 1;

        if (isValidBeanFilters(numberGoods)) {
            numberGoodsInt = Integer.parseInt(numberGoods);
        }

        if (isValidBeanFilters(currentPage)) {
            currentPageInt = Integer.parseInt(currentPage);
        }

        beansLimitFilters.add(currentPageInt * numberGoodsInt - numberGoodsInt);
        beansLimitFilters.add(numberGoodsInt);

        checkBeansLimitFilters();
        createSQL();
    }

    private void checkBeansLimitFilters() {
        if (beansLimitFilters.size() == 0) {
            beansLimitFilters.clear();
            beansLimitFilters.add(0);
            beansLimitFilters.add(8);
        }
    }

    private void createSQL() {
        sqlQuertyLimit.append(" limit ?, ?");
    }

    private boolean isValidBeanFilters(String bean) {
        return bean != null && bean.matches("\\d+");
    }

    public String getPartSQL() {
        return sqlQuertyLimit.toString();
    }

    public List<Integer> getBeansLimitFilters() {
        return beansLimitFilters;
    }
}
