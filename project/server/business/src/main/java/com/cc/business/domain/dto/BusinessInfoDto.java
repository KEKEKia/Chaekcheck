package com.cc.business.domain.dto;// BusinessInfoDto.java
import lombok.Data;

@Data
public class BusinessInfoDto {
    private String title;
    private String author;
    private String publisher;
    private int status;
    private int price;
    private String imageUrl;
    private int minPrice;
    private int maxPrice;
    private int avgPrice;
    private int originalPrice;
}
