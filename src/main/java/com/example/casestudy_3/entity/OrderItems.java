package com.example.casestudy_3.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class OrderItems {
    private int id;
    private String bookTitle;
    private int orderId;
    private int bookId;
    private int quantity;
    private long price;
    private long subTotalPrice;

    public OrderItems(int id, String bookTitle, int orderId, int bookId, int quantity, long price, long subTotalPrice) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.subTotalPrice = subTotalPrice;
    }

    public OrderItems(String bookTitle, int orderId, int bookId, int quantity, long price, long subTotalPrice) {
        this.bookTitle = bookTitle;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.subTotalPrice = subTotalPrice;
    }

}