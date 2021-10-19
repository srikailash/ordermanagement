//Entity that matches with mysql row of User table
package com.backend.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name = "Users")
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Double balance;

    @Version
    @Column(name = "version")
    private Integer version;

    // @Column(name = "status")
    // private String status;

    public User() {
        //default constructor
    }
    
    public User(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Double getBalance() {
		return this.balance;
	}

    public void setBalance(Double updatedBalance) {
		this.balance = updatedBalance;
	}

}
