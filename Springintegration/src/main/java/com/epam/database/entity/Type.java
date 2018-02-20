package com.epam.database.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "type")
@Getter
@Setter
@ToString
@Entity
public class Type {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
}
