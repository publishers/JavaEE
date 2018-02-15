package com.epam.malykhin.database.entity;


public enum StatusOrder {
    ADOPTED(4) {
        @Override
        public String getDescriptionStatus() {
            return "adopted";
        }
    }, CONFIRMED(2) {
        @Override
        public String getDescriptionStatus() {
            return "confirmed";
        }
    }, FORMED(1) {
        @Override
        public String getDescriptionStatus() {
            return "formed";
        }
    }, SENT(3) {
        @Override
        public String getDescriptionStatus() {
            return "sent";
        }
    }, COMPLETED(5) {
        @Override
        public String getDescriptionStatus() {
            return "completed";
        }
    }, CANCELLED(0) {
        @Override
        public String getDescriptionStatus() {
            return "canceled";
        }
    };
    private final int mask;

    StatusOrder(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public abstract String getDescriptionStatus();
}
