package com.study.mf.dto;

public record StorageResponseDto(
    String fileName,
    String fileType,
    String fileSize
) {
}
