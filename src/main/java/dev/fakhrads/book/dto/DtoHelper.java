package dev.fakhrads.book.dto;


import java.time.Instant;

public class DtoHelper {

    private DtoHelper() {}

    public static <T> DtoResponse<T> ok(String message, T data, String path) {
        return new DtoResponse<>(true, message, data, Instant.now(), path);
    }

    public static <T> DtoResponse<T> fail(String message, T data, String path) {
        return new DtoResponse<>(false, message, data, Instant.now(), path);
    }
}
