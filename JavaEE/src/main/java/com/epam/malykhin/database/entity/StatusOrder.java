package com.epam.malykhin.database.entity;

/**
 * Created by Serhii_Malykhin on 12/19/2016.
 */
public enum StatusOrder {
    ADOPTED(4), CONFIRMED(2), FORMED(1), SENT(3), COMPLETED(5), CANCELLED(0);
    private final int mask;

    StatusOrder(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }
}
