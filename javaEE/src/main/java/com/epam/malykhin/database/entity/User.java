package com.epam.malykhin.database.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private int idUser;
    private String firstName;
    private String secondName;
    private boolean newsletter;
    @NonNull
    private String email;
    private String password;
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
