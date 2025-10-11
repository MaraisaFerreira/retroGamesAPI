package com.study.mf.exceptions;

import java.time.Instant;

public record ResponseException(Instant timestamp, String message, String path) {
}
