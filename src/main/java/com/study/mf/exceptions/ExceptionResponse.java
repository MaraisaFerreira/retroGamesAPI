package com.study.mf.exceptions;

import java.time.Instant;

public record ExceptionResponse(Instant timestamp, String message, String path) {
}
