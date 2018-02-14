package com.epam.malykhin.database.entity;

public interface Action {
    void doAction(Cart cart, Goods goods);
}