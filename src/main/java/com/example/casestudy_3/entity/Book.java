package com.example.casestudy_3.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Book {
    private int id;
    private String title;
    private String author;
    private long price;
    private String description;
    private String publishedDate;
    private String publisher;
    private String category;
    private int quantity;
    private String imageURL;

    public Book(int id, String title, String author, long price, String description, String publishedDate, String publisher, String category, int quantity, String imageURL) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.category = category;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public Book(int id, String title, String author, long price, String description, String publishedDate, String publisher, String category, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.category = category;
        this.quantity = quantity;
    }

    public Book(String title, String author, long price, String description, String publishedDate, String publisher, String category, int quantity,String imageURL) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.category = category;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public Book(String title, String author, long price, String description, String publishedDate, String publisher, String category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
