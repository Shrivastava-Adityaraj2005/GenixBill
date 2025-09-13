package com.myproj.bill_gen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Base64;

/**
 * @author HP
 **/
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int quantity;
    private int cartQuantity;
    private String added;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
    @Transient
    public String getImageBase64() {
        return imageData != null ? Base64.getEncoder().encodeToString(imageData) : null;
    }
}

