package com.epam.bean;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Roles {
    private List<Integer> roles = new ArrayList<>();

    public void add(int role) {
        roles.add(role);
    }

    public boolean contains(Integer role) {
        return roles.contains(role);
    }
}
