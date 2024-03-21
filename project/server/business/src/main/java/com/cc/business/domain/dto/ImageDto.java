package com.cc.business.domain.dto;

import com.cc.business.domain.entity.ImageEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {
    private Long id;
    private String title;
    private String filePath;

    public ImageEntity toEntity(){
        ImageEntity build = ImageEntity.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();
        return build;
    }
    @Builder
    public ImageDto(Long id, String title, String filePath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
    }

}
