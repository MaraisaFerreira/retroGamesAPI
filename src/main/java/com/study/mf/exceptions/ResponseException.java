package com.study.mf.exceptions;

import java.time.Instant;

public record ResponseException(Long timestamp, String message, String details) {
}
