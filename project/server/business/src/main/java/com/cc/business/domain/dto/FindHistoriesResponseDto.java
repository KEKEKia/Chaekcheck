package com.cc.business.domain.dto;

import com.cc.business.domain.entity.BookEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FindHistoriesResponseDto {

    private List<BookElement> history;

    public static FindHistoriesResponseDto of(List<BookEntity> books){

        return FindHistoriesResponseDto.builder()
                .history(books.stream()
                        .map(BookElement::of).toList())
                .build();
    }
}