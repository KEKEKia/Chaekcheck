package com.cc.business.domain.dto;

import com.cc.business.domain.entity.BookEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDetailElement {

    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String coverImage;
    private String status;
    private int originalPrice;
    private int estimatedPrice;

    public static BookDetailElement of (BookEntity bookEntity){
        return BookDetailElement.builder()
                .bookId(bookEntity.getBookId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .publisher(bookEntity.getPublisher())
                .coverImage(bookEntity.getCoverImage())
                .status(bookEntity.getStatus())
                .originalPrice(bookEntity.getOriginalPrice())
                .estimatedPrice(bookEntity.getEstimatedPrice())
                .build();
    }
}
