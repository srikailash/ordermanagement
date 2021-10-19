package com.backend.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;

import com.backend.product.Product;
import com.backend.user.User;

import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "requested_quantity")
    private Integer requestedQuantity;

    @Column(name = "placed_quantity")
    private Integer placedQuantity;

    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Product product;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = false,updatable = false)
    private User user;

    public Order() {
    }

    public Order(Integer userId, Integer requestedQuantity,  Integer productId, String status) {
        this.userId = userId;
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.placedQuantity = 0;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPlacedQuantity(Integer placedQuantity) {
        this.placedQuantity = placedQuantity;
    }

    public Integer getRequestedQuantity() {
        return this.requestedQuantity;
    }

    public Integer getPlacedQuantity() {
        return this.placedQuantity;
    }
    
    public User getUser() {
        return this.user;
    }

    public String getProductName() {
        return this.product.getName();
    }

    public Double getProductPrice() {
        return this.product.getPrice();
    }

    public String getStatus() {
        return this.status;
    }

}
