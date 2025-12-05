package com.study.mf.services;

import com.study.mf.config.StorageConfig;
import com.study.mf.dto.StorageResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageService {

    private final Path storagePath;

    public StorageService(StorageConfig config) {
        storagePath = Path.of(config.getDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Directory cannot be created.");
        }
    }

    public StorageResponseDto uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("This file is empty...");
        }
        String fileName = file.getOriginalFilename();
        Path filePath = storagePath.resolve(Objects.requireNonNull(fileName));

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return new StorageResponseDto(
                fileName,
                file.getContentType(),
                file.getSize() + " Kb"
            );
        } catch (IOException ex) {
            throw new RuntimeException("File cannot be saved...");
        }
    }
}
