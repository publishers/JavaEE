package com.epam.malykhin.database.entity;

import java.sql.Timestamp;

/**
 * Created by Serhii_Malykhin on 12/30/2016.
 */
public class UserBan {
    private int idUser;
    private Timestamp date;
    private boolean block;
    private int attempt;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }
}
