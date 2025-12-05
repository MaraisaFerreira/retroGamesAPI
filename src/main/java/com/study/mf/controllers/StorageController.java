package com.study.mf.controllers;

import com.study.mf.dto.StorageResponseDto;
import com.study.mf.services.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/files")
public class StorageController {
    private final StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<StorageResponseDto> uploadSingleFile(@RequestParam MultipartFile file){
        return ResponseEntity.status(201).body(
            service.uploadFile(file)
        );
    }
}
