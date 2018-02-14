package com.epam.malykhin.database.dao.filter;

import com.epam.malykhin.bean.BeanFilters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii_Malykhin on 12/13/2016.
 */
public class MySQLQueryBuilderFilter {
    private FilterWhere filterWhere;
    private FilterLimit filterLimit;

    public MySQLQueryBuilderFilter(BeanFilters beanFilters) {
        filterLimit = new FilterLimit(beanFilters);
        filterWhere = new FilterWhere(beanFilters);
        startFilters();
    }

    private void startFilters() {
        filterLimit.start();
        filterWhere.start();
    }

    public List<?> getList() {
        List allBeans = new ArrayList<>(filterWhere.getBeansWhereFilters().values());
        allBeans.addAll(filterLimit.getBeansLimitFilters());
        return allBeans;
    }

    public String getQueryFilterWhere() {
        return filterWhere.getPartSQL();
    }

    public String getQueryFilterLimit() {
        return filterLimit.getPartSQL();
    }

    public FilterWhere getFilterWhere() {
        return filterWhere;
    }

    public FilterLimit getFilterLimit() {
        return filterLimit;
    }

    public String getQuery() {
        return filterWhere.getPartSQL() + filterLimit.getPartSQL();
    }
}
