package com.model;


import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int productId;
    private int quantity;
    private LocalDateTime orderDate;

    public Order() {}

    public Order(int orderId, int productId,
                 int quantity, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    // getters & setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
