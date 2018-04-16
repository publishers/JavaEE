package com.epam.service;

import com.epam.database.dao.GoodsDAO;
import com.epam.database.entity.Goods;
import com.epam.database.search.SearchCriteria;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    public Goods selectById(Goods goods) {
        return goodsDAO.findOne(goods.getId());
    }

    public List<Goods> selectAllForPage(List<SearchCriteria> criteria) {
        PageRequest pageRequest = buildPageRequest(criteria);
        Specification<Goods> spec = buildSpecification(criteria);
        return goodsDAO.findAll(spec, pageRequest).getContent();
    }

    private PageRequest buildPageRequest(List<SearchCriteria> criteria) {
        int size = extractInt("numberGoods", criteria);
        int page = extractInt("currentPage", criteria);
        Direction direction = extractDirection(criteria);
        updateListCriteria(criteria);
        return new PageRequest(page - 1 < 0 ? 0 : page - 1, size, direction, "price");
    }

    private int extractInt(String fieldName, List<SearchCriteria> searchCriteria) {
        SearchCriteria criteria = findCriteria(fieldName, searchCriteria);
        return Integer.parseInt(criteria.getValue());
    }

    private Direction extractDirection(List<SearchCriteria> searchCriteria) {
        SearchCriteria criteria = findCriteria("sortBy", searchCriteria);
        return criteria == null || criteria.getValue().equals("+") ? Direction.ASC : Direction.DESC;
    }

    private void updateListCriteria(List<SearchCriteria> criteria) {
        updateListCriteria("numberGoods", criteria);
        updateListCriteria("currentPage", criteria);
        updateListCriteria("sortBy", criteria);
    }

    private void updateListCriteria(String fieldName, List<SearchCriteria> searchCriteria) {
        List<SearchCriteria> updatedListCriteria = searchCriteria.stream()
                .filter(criteria -> !criteria.getFieldName().equals(fieldName))
                .collect(Collectors.toList());
        searchCriteria.retainAll(updatedListCriteria);
    }

    private SearchCriteria findCriteria(String fieldName, List<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .filter(criteria -> criteria.getFieldName().equals(fieldName))
                .findFirst()
                .orElse(null);
    }

    public long countGoods(List<SearchCriteria> criteria) {
        updateListCriteria(criteria);
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

    private Specification<Goods> specification(SearchCriteria searchCriteria) {
        return (root, query, builder) -> {
            if (searchCriteria.getFieldName().equals("priceFrom")) {
                return builder.greaterThanOrEqualTo(root.get("price"), searchCriteria.getValue());
            }

            if (searchCriteria.getFieldName().equals("priceTo")) {
                return builder.lessThanOrEqualTo(root.get("price"), searchCriteria.getValue());
            }

            if (searchCriteria.getFieldName().equals("title")) {
                return builder.like(root.get(searchCriteria.getFieldName()), searchCriteria.getValue());
            }

            if (StringUtils.isEmpty(searchCriteria.getValue())) {
                return null;
            }
            return builder.equal(root.get(searchCriteria.getFieldName()), searchCriteria.getValue());
        };
    }
}
