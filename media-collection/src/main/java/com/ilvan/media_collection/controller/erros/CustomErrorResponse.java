package com.ilvan.media_collection.controller.erros;

public record CustomErrorResponse(int status, String message, String timestamp) {
}
