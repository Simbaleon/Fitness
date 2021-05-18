package com.example.pr24.services;


import com.example.pr24.dao.AuthorDAO;
import com.example.pr24.dao.BookDAO;
import com.example.pr24.models.Book;
import com.example.pr24.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookDAO bookDAO;
    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private EmailService emailService;
    @Captor
    ArgumentCaptor<Book> captor;

    @Test
    public void getAll() {
        Book book1 = new Book();
        book1.setName("1");
        Book book2 = new Book();
        book2.setName("2");

        Mockito.when(bookDAO.findAll()).thenReturn(List.of(book1, book2));

        BookService bookService = new BookService(bookDAO,
                authorDAO, emailService);

        Assertions.assertEquals(2, bookService.getAll().size());
        Assertions.assertEquals("1", bookService.getAll().get(0).getName());
    }

    @Test
    public void save() {
        Book book = new Book();
        book.setName("1");

        BookService universityService = new BookService(bookDAO,
                authorDAO, emailService);
        universityService.save(book);

        Mockito.verify(bookDAO).save(captor.capture());

        Book captured = captor.getValue();
        Assertions.assertEquals("1", captured.getName());
    }
}
