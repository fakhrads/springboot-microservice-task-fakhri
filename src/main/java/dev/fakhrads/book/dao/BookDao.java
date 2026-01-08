package dev.fakhrads.book.dao;

import dev.fakhrads.book.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}
