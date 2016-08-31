package com.cdesign.spittr.data.entity;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 *
 * MongoDB entity
 * Nested document for Order
 */
public class OrderItem {

    private Long id;
    private String product;
    private double price;
    private int quantity;

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
