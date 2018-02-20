package com.epam.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_ban")
public class UserBan {
    @Id
    @Column(name = "iduser")
    private int idUser;
    @Column(name = "timestart")
    private Timestamp date;
    @Column(name = "block", columnDefinition = "TINYINT(1)", length = 1, nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean block;
    @Column(name = "attempt")
    private int attempt;
}
