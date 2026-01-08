package dev.fakhrads.book.service;

import dev.fakhrads.book.vo.*;

import java.util.List;

public interface BookService {
    BookVoResponse create(BookVoCreateRequest req);
    List<BookVoResponse> findAll();
    BookVoResponse findById(Long id);
    BookVoResponse update(Long id, BookVoUpdateRequest req);
    BookVoResponse patch(Long id, BookVoPatchRequest req);
    void delete(Long id);
}
