package dev.fakhrads.book.dao.impl;

import dev.fakhrads.book.dao.BookDao;
import dev.fakhrads.book.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b order by b.id asc", Book.class).getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        Long count = em.createQuery("select count(b.id) from Book b where b.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
