package com.epam.malykhin.database;

import com.epam.malykhin.database.entity.Goods;
import com.epam.malykhin.database.entity.Manufacturer;
import com.epam.malykhin.database.entity.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemGoods {
    private Goods goods;
    private Type type;
    private Manufacturer manufacturer;
}
