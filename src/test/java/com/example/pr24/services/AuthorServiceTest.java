package com.example.pr24.services;

import com.example.pr24.dao.AuthorDAO;
import com.example.pr24.dao.BookDAO;
import com.example.pr24.dto.AuthorDTO;
import com.example.pr24.models.Author;
import com.example.pr24.models.Book;
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
public class AuthorServiceTest {
    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private BookDAO bookDAO;
    @Mock
    private com.example.pr24.services.EmailService emailService;

    @Captor
    ArgumentCaptor<Author> captor;

    @Test
    public void getAll() {
        Author author1 = new Author();
        author1.setFirstName("1");
        Author author2 = new Author();
        author2.setFirstName("2");

        Mockito.when(authorDAO.findAll()).thenReturn(List.of(author1, author2));

        AuthorService studentService = new AuthorService(authorDAO,
                bookDAO, emailService);

        Assertions.assertEquals(2, studentService.getAll().size());
        Assertions.assertEquals("1", studentService.getAll().get(0).getFirstName());
    }

    @Test
    public void save() {
        Book book = new Book();
        book.setName("книга");

        AuthorDTO author = new AuthorDTO();
        author.setFirstName("1");
        author.setNameBook(book.getName());

        Mockito.when(bookDAO.findByName(book.getName())).thenReturn(book);

        AuthorService authorService = new AuthorService(authorDAO,
                bookDAO, emailService);
        authorService.save(author);

        Mockito.verify(authorDAO).save(captor.capture());

        Author captured = captor.getValue();
        Assertions.assertEquals("1", captured.getFirstName());
    }

    @Test
    public void filterAuthors() {
        Author author1 = new Author();
        author1.setFirstName("1");
        Author author2 = new Author();
        author2.setFirstName("2");

        Mockito.when(authorDAO
                .findAllAuthorsByBookNameAndFirstNameAndMiddleNameAndLastName("", "1", "", "")
        ).thenReturn(List.of(author1));

        Mockito.when(authorDAO
                .findAllAuthorsByBookNameAndFirstNameAndMiddleNameAndLastName("", "2", "", "")
        ).thenReturn(List.of(author2));

        AuthorService authorService = new AuthorService(authorDAO,
                bookDAO, emailService);

        List<Author> authors1 = authorService.filterAuthors("", "1", "", "");
        List<Author> authors2 = authorService.filterAuthors("", "2", "", "");

        Assertions.assertEquals(1, authors1.size());
        Assertions.assertEquals("1", authors1.get(0).getFirstName());
        Assertions.assertEquals(1, authors2.size());
        Assertions.assertEquals("2", authors2.get(0).getFirstName());
    }
}
