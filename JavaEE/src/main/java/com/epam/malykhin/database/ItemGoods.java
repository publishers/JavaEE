package com.epam.malykhin.database;

import com.epam.malykhin.database.entity.Goods;
import com.epam.malykhin.database.entity.Manufacturer;
import com.epam.malykhin.database.entity.Type;

/**
 * Created by Serhii_Malykhin on 12/14/2016.
 */
public class ItemGoods {
    private Goods goods;
    private Type type;
    private Manufacturer manufacturer;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
