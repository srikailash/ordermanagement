package com.backend.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table(name = "OrderItems")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Order order;

    public OrderItems() {
        System.out.println("Creating OrderItem with default constructor");
    }

    public OrderItems(Integer orderId, Long productId, Integer quantity, Double price) {
        System.out.println("Creating OrderItem with " + orderId  + " and ProductId " + productId);
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return this.productId;
    }
    
    public Integer getQuantity() {
        return this.quantity;
    }

    public Double getPrice() {
        return this.price;
    }
    
    public Integer getOrderId() {
        return this.orderId;
    }

}
