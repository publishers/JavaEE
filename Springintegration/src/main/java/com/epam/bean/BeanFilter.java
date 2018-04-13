package com.epam.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BeanFilter implements Serializable {
    private String title;
    private String idType;
    private String idManufacture;
    private String priceFrom;
    private String priceTo;
    private String numberGoods;
    private String currentPage;
}
