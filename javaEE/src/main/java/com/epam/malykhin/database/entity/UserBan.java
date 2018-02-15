package com.epam.malykhin.database.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserBan {
    private int idUser;
    private Timestamp date;
    private boolean block;
    private int attempt;
}
