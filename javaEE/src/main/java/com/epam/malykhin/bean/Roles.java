package com.epam.malykhin.bean;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@ToString
public class Roles {
    @Getter
    private List<Integer> roles;

    public Roles() {
        roles = new ArrayList<>();
    }

    public void add(int role) {
        roles.add(role);
    }

    public boolean contains(Integer role) {
        return roles.contains(role);
    }

}
