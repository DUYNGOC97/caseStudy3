package com.example.casestudy_3.entity;

import lombok.Builder;

import java.sql.Date;
import java.time.LocalDate;

@Builder
public class Order {
    private int id;
    private int customerId;
    private Date orderDate; // Change orderDate to java.sql.Date
    private long totalAmount;
    private String status;
    private String shippingAddress;
    private String phoneNumber;

    public Order() {
    }

    public Order(int customerId, Date orderDate, long totalAmount, String shippingAddress,String phoneNumber) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
    }

    public Order(int customerId) {
        this.customerId = customerId;
        this.orderDate = Date.valueOf(LocalDate.now()); // Initialize orderDate with the current date
    }

    public Order(int id, int customerId) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = Date.valueOf(LocalDate.now()); // Initialize orderDate with the current date
    }

    public Order(int id, int customerId, long totalAmount, String status, String shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderDate = Date.valueOf(LocalDate.now()); // Initialize orderDate with the current date
    }

    public Order(int id, int customerId, Date orderDate, long totalAmount, String status, String shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public Order(int id, int customerId, Date orderDate, long totalAmount, String status, String shippingAddress, String phoneNumber) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return Date.valueOf(LocalDate.now());
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}