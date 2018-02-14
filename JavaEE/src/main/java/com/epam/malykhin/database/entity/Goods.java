package com.epam.malykhin.database.entity;

/**
 * Created by Serhii_Malykhin on 12/14/2016.
 */
public class Goods {
    private int id;
    private String title;
    private String description;
    private int price;
    private String srcImg;
    private int idType;
    private int idManufacturer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdManufacturer() {
        return idManufacturer;
    }

    public void setIdManufacturer(int idManufacturer) {
        this.idManufacturer = idManufacturer;
    }

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
                .append(", \"idManufacturer\":").append(idManufacturer).append("}").toString();
    }
}
