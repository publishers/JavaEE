package com.epam.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "goods")
@Entity
public class Goods {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "srcImg", columnDefinition = "TEXT")
    private String srcImg;
    @Column(name = "idType")
    private int idType;
    @Column(name = "idManufacture")
    private int idManufacture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        return id == goods.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{").append("\"id\":").append(id)
                .append(", \"title\":\"").append(title).append('\"')
                .append(", \"description\":\"").append(description).append("\", \"price\":").append(price)
                .append(", \"srcImg\":\"").append(srcImg).append("\", \"idType\":").append(idType)
                .append(", \"idManufacture\":").append(idManufacture).append("}").toString();
    }
}
