package com.epam.malykhin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii_Malykhin on 12/29/2016.
 */
public class Roles {
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

    public List<Integer> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "{" + roles + '}';
    }
}
