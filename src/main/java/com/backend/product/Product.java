package com.backend.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    public Product() {
        //default constructor
    }

    public Product(String name, Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

    public void setQuantity(Integer updatedQuantity) {
		this.quantity = updatedQuantity;
	}

}
