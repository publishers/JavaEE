package com.epam.database;

import com.epam.database.entity.Goods;
import com.epam.database.entity.Manufacturer;
import com.epam.database.entity.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemGoods {
    private Goods goods;
    private Type type;
    private Manufacturer manufacturer;
}
