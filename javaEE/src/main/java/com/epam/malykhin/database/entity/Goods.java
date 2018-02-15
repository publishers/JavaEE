package com.epam.malykhin.database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"title", "description","price","srcImg","idType","idManufacturer"})
public class Goods {
    private int id;
    private String title;
    private String description;
    private int price;
    private String srcImg;
    private int idType;
    private int idManufacturer;
}
