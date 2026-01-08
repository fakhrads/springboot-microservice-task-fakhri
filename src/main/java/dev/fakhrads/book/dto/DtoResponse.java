package dev.fakhrads.book.dto;

import java.time.Instant;

public class DtoResponse<T> {
    private boolean ok;
    private String message;
    private T data;
    private Instant timestamp;
    private String path;

    public DtoResponse() {}

    public DtoResponse(boolean ok, String message, T data, Instant timestamp, String path) {
        this.ok = ok;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
        this.path = path;
    }

    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
