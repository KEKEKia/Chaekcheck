package com.cc.business.domain.controller;

import com.cc.business.domain.dto.ImageDto;
import com.cc.business.domain.service.S3Service;
import com.cc.business.domain.service.ImageService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ImageController {

    @Autowired
    private S3Service s3Service;
    @Autowired
    private ImageService imageService;

    @GetMapping("/image")
    public String dispWrite() {
        return "/image";
    }

    @PostMapping("/image")
    public String execWrite(@NotNull ImageDto imageDto, MultipartFile file) throws IOException {
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(file);
        List<String> imageUrlList = s3Service.upload(fileList);
        imageDto.setFilePath(imageUrlList.get(0));
//        System.out.println("실행됨??");
        imageService.savePost(imageDto);
        return "redirect:/image";
    }
}