package com.cc.business.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "book_image")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int imageId;
    @Column(name = "image_path")
    String imagePath;
    @Column(name = "image_url")
    String imageUrl;
    @Column(name = "book_id")
    int bookId;
}
