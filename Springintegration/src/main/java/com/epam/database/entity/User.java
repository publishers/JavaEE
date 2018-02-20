package com.epam.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Getter
@Setter
@ToString(exclude = {"idUser", "roleId", "newsletter"})
@Entity
public class User {
    @Id
    @Column(name = "iduser")
    private int idUser;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "secondname")
    private String secondName;
    @Column(name = "newsletter", columnDefinition = "TINYINT(1)", length = 1, nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean newsletter;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role_id")
    private int roleId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

}
