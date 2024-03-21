package com.cc.business.domain.dto;

import com.cc.business.domain.entity.BookEntity;
import com.cc.business.domain.entity.BookImageEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookElement {

    private int id;
    private String title;
    private String url;
    private String status;
    private int price;

    public static BookElement of(BookEntity bookEntity){

        return BookElement.builder()
                .id(bookEntity.getBookId())
                .title(bookEntity.getTitle())
                .url(bookEntity.getCoverImage())
                .status(bookEntity.getStatus())
                .price(bookEntity.getEstimatedPrice()).build();
    }
}