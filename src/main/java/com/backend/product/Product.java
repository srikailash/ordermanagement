package com.backend.product;

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
@Table(name = "Products")
@OptimisticLocking(type=OptimisticLockType.VERSION)
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

    @Version
    @Column(name = "version")
    private Integer version;

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

	public Double getPrice() {
		return this.price;
	}

    public void setQuantity(Integer updatedQuantity) {
		this.quantity = updatedQuantity;
	}

    public Integer getVersion() {
        return this.version;
      }
    
    public Product setVersion(Integer version) {
        this.version = version;
        return this;
    }

}
