package dev.fakhrads.book.service.impl;

import dev.fakhrads.book.dao.BookDao;
import dev.fakhrads.book.vo.*;
import dev.fakhrads.book.entity.Book;
import dev.fakhrads.book.exception.NotFoundException;
import dev.fakhrads.book.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao dao;

    public BookServiceImpl(BookDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public BookVoResponse create(BookVoCreateRequest req) {
        Book book = new Book();
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setIsbn(req.getIsbn());
        book.setPublishedDate(req.getPublishedDate());

        Book saved = dao.save(book);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookVoResponse> findAll() {
        return dao.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookVoResponse findById(Long id) {
        Book book = dao.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
        return toResponse(book);
    }

    @Override
    @Transactional
    public BookVoResponse update(Long id, BookVoUpdateRequest req) {
        Book book = dao.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setIsbn(req.getIsbn());
        book.setPublishedDate(req.getPublishedDate());

        Book saved = dao.save(book);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public BookVoResponse patch(Long id, BookVoPatchRequest req) {
        Book book = dao.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));

        if (req.getTitle() != null) book.setTitle(req.getTitle());
        if (req.getAuthor() != null) book.setAuthor(req.getAuthor());
        if (req.getIsbn() != null) book.setIsbn(req.getIsbn());
        if (req.getPublishedDate() != null) book.setPublishedDate(req.getPublishedDate());

        Book saved = dao.save(book);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!dao.existsById(id)) throw new NotFoundException("Book not found: " + id);
        dao.deleteById(id);
    }

    private BookVoResponse toResponse(Book b) {
        return new BookVoResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishedDate());
    }
}
