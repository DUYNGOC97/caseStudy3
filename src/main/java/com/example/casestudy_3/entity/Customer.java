package com.example.casestudy_3.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Customer {
    private int id;
    private int user_id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private long wallet;

    public Customer(int user_id, String fullName, String phoneNumber, String address) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer(int id, int user_id, String fullName, String address, String phoneNumber, long wallet) {
        this.id = id;
        this.user_id = user_id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", wallet=" + wallet +
                '}';
    }
}
