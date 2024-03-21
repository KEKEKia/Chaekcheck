package com.cc.business.domain.dto;

import com.cc.business.domain.entity.ImageEntity;
import lombok.*;

@Getter
@Setter
@ToString
public class BookDto {
    int bookId;
    String title;
    String author;
    String publisher;
    String image;
}
