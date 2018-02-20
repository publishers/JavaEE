package com.epam.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "manufacturer")
@Entity
public class Manufacturer {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
}
