package com.epam.malykhin.database.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serhii_Malykhin on 18.12.16.
 */
public class Cart implements Serializable {
    private Map<Goods, Integer> cart;
    private int sumOfOrder;
    private int countOrder;

    public Cart() {
        cart = new HashMap<>();
    }

    public int getSumOfOrder() {
        return sumOfOrder;
    }

    public synchronized void add(Goods goods) {
        if (goods == null) return;
        if (cart.containsKey(goods)) {
            int countGoods = cart.get(goods);
            cart.put(goods, ++countGoods);
        } else {
            cart.put(goods, 1);
        }
        countOrder++;
        sumOfOrder += goods.getPrice();
    }

    public synchronized void delete(Goods goods) {
        if (cart.containsKey(goods)) {
            int countGoods = cart.get(goods);
            if (countGoods <= 1) {
                clear(goods);
            } else {
                cart.put(goods, --countGoods);
                sumOfOrder -= goods.getPrice();
                countOrder--;
            }
        }
    }

    public synchronized void clear(Goods goods) {
        if (cart.containsKey(goods)) {
            countOrder -= cart.get(goods);
            sumOfOrder -= cart.get(goods) * goods.getPrice();
            cart.remove(goods);
        }
    }

    public Map<Goods, Integer> getCart() {
        return cart;
    }

    public int countGoods() {
        return countOrder;
    }
}
