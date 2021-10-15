package com.backend.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Order {

    @Id
    @GeneratedValue
    private int id;

    public String name;

    public Order() {
        //default constructor
    }

    public Order(String name) {
        this.name = name;
    }

}
