package com.cc.business.domain.dto;

import com.cc.business.domain.entity.BookEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindHistoryResponseDto {

    private BookDetailElement bookInfo;

    public static FindHistoryResponseDto of(BookEntity bookEntity){

        return FindHistoryResponseDto.builder()
                .bookInfo(BookDetailElement.of(bookEntity))
                .build();
    }
}
