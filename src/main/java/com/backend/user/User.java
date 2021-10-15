//Entity that matches with mysql row of User table
package com.backend.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    private int id;

    public String name;

    public User() {
        //default constructor
    }

    public User(String name) {
        this.name = name;
    }

}
