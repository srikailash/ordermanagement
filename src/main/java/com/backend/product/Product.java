package com.backend.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue
    private int id;

    public String name;

    public Product() {
        //default constructor
    }

    public Product(String name) {
        this.name = name;
    }

}


