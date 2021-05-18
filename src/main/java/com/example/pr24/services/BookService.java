package com.example.pr24.services;

import com.example.pr24.annotations.LogTime;
import com.example.pr24.dao.AuthorDAO;
import com.example.pr24.dao.BookDAO;
import com.example.pr24.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final EmailService emailService;

    @Autowired
    public BookService(BookDAO bookDAO, AuthorDAO authorDAO, EmailService emailService) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.emailService = emailService;
    }
    @LogTime
    @Transactional
    public List<Book> getAll() {
        log.info("Find all books");
        return bookDAO.findAll();
    }
    @LogTime
    @Transactional
    public void delete(int id) {
        log.info("Delete book by id: " + id);
        Optional<Book> optionalBook = bookDAO.findById(id);

        if (optionalBook.isEmpty()) {
            return;
        }

        authorDAO.deleteAllByBook(optionalBook.get());
        bookDAO.deleteById(id);
    }
    @LogTime
    @Transactional
    public void save(Book book) {
        log.info("Save book with id: " + book.getId());
        emailService.sendInfoAboutSaveObject("Save: " + book.toString());
        bookDAO.save(book);
    }
}
