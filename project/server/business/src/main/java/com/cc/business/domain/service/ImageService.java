package com.cc.business.domain.service;

import com.cc.business.domain.dto.ImageDto;
import com.cc.business.domain.repository.ImageRepository;
import com.cc.business.domain.dto.BookDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void savePost(@NotNull ImageDto imageDto) {
        imageRepository.save(imageDto.toEntity());
    }
}
