package com.epam.service;

import com.epam.bean.BeanFilter;
import com.epam.database.dao.GoodsDAO;
import com.epam.database.entity.Goods;
import com.epam.database.search.SearchCriteria;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    private static final int DEFAULT_GOODS_SIZE = 10;

    @Autowired
    private GoodsDAO goodsDAO;

    public Goods selectById(Goods goods) {
        return goodsDAO.findOne(goods.getId());
    }

    public List<Goods> selectAllForPage(BeanFilter beanFilter) {
        int size = DEFAULT_GOODS_SIZE;
        int page = 0;
        if (isNumber(beanFilter.getNumberGoods())) {
            size = Integer.parseInt(beanFilter.getNumberGoods());
        }
        if (isNumber(beanFilter.getCurrentPage())) {
            page = Integer.parseInt(beanFilter.getCurrentPage());
            page--;
        }

        List<SearchCriteria> criteria = buildSearchCriteria(beanFilter);
        Specification<Goods> spec = buildSpecification(criteria);
        return goodsDAO.findAll(spec, new PageRequest(page, size)).getContent();
    }

    private boolean isNumber(String number) {
        return number != null && !number.trim().equals("") && (Integer.parseInt(number) > 0);
    }

    public long countGoods(BeanFilter beanFilter) {
        List<SearchCriteria> criteria = buildSearchCriteria(beanFilter);
        Specification<Goods> spec = buildSpecification(criteria);
        return goodsDAO.count(spec);
    }

    private Specification<Goods> buildSpecification(List<SearchCriteria> criteria) {
        Specification<Goods> spec = null;
        if (criteria.size() > 0) {
            spec = specification(criteria.get(0));
            for (int i = 1; i < criteria.size(); i++) {
                spec = Specifications.where(spec).and(specification(criteria.get(i)));
            }
        }
        return spec;
    }

    private List<SearchCriteria> buildSearchCriteria(BeanFilter beanFilter) {
        List<SearchCriteria> searchCriteria = new ArrayList<>();
        if (!StringUtils.isEmpty(beanFilter.getIdManufacture())) {
            searchCriteria.add(new SearchCriteria("idManufacture", beanFilter.getIdManufacture()));
        }

        if (!StringUtils.isEmpty(beanFilter.getIdType())) {
            searchCriteria.add(new SearchCriteria("idType", beanFilter.getIdType()));
        }

        if (!StringUtils.isEmpty(beanFilter.getPriceFrom())) {
            searchCriteria.add(new SearchCriteria("priceFrom", beanFilter.getPriceFrom()));
        }

        if (!StringUtils.isEmpty(beanFilter.getPriceTo())) {
            searchCriteria.add(new SearchCriteria("priceTo", beanFilter.getPriceTo()));
        }

        if (!StringUtils.isEmpty(beanFilter.getTitle())) {
            searchCriteria.add(new SearchCriteria("title", "%" + beanFilter.getTitle() + "%"));
        }
        return searchCriteria;
    }

    private Specification<Goods> specification(SearchCriteria searchCriteria) {
        return (root, query, builder) -> {
            if (searchCriteria != null) {
                if (searchCriteria.getFieldName().equals("idManufacture")) {
                    return builder.equal(root.get(searchCriteria.getFieldName()), searchCriteria.getValue());
                }
                if (searchCriteria.getFieldName().equals("idType")) {
                    return builder.equal(root.get(searchCriteria.getFieldName()), searchCriteria.getValue());
                }

                if (searchCriteria.getFieldName().equals("priceFrom")) {
                    return builder.greaterThan(root.get("price"), searchCriteria.getValue());
                }

                if (searchCriteria.getFieldName().equals("priceTo")) {
                    return builder.lessThan(root.get("price"), searchCriteria.getValue());
                }

                if (searchCriteria.getFieldName().equals("title")) {
                    return builder.like(root.get(searchCriteria.getFieldName()), searchCriteria.getValue());
                }
            }
            return null;
        };
    }
}
