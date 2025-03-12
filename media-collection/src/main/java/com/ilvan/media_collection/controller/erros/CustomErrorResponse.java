package com.ilvan.media_collection.controller.erros;

import java.time.Instant;

public record CustomErrorResponse(int status, String message, String timestamp) {
}
