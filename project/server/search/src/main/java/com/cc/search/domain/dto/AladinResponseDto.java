package com.cc.business.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class AladinResponseDto {
    String title;
    String link;
    String author;
    String pubDate;
    String description;
    String isbn;
    String isbn13;
    int itemId;
    int priceSales;
    int priceStandard;
    String mallType;
    String stockStatus;
    int mileage;
    String cover;
    int categoryId;
    String categoryName;
    String publisher;
    int salesPoint;
    boolean adult;
    boolean fixedPrice;
    int customerReviewRank;
    HashMap<String, Object> seriesInfo;
    HashMap<String, Object> subInfo;

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", itemId=" + itemId +
                ", priceSales=" + priceSales +
                ", priceStandard=" + priceStandard +
                ", mallType='" + mallType + '\'' +
                ", stockStatus='" + stockStatus + '\'' +
                ", mileage=" + mileage +
                ", cover='" + cover + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", publisher='" + publisher + '\'' +
                ", salesPoint=" + salesPoint +
                ", adult=" + adult +
                ", fixedPrice=" + fixedPrice +
                ", customerReviewRank=" + customerReviewRank +
                ", seriesInfo=" + seriesInfo +
                ", subInfo=" + subInfo +
                '}';
    }
}
