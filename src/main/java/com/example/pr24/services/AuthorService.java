package com.example.pr24.services;

import com.example.pr24.annotations.LogTime;
import com.example.pr24.dao.AuthorDAO;
import com.example.pr24.dao.BookDAO;
import com.example.pr24.dto.AuthorDTO;
import com.example.pr24.models.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AuthorService {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final EmailService emailService;

    @Autowired
    public AuthorService(AuthorDAO authorDAO, BookDAO bookDAO, EmailService emailService) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.emailService = emailService;
    }
    @LogTime
    @Transactional
    public List<Author> getAll() {
        log.info("Find all authors");
        return authorDAO.findAll();
    }
    @LogTime
    @Transactional
    public void delete(int id) {
        log.info("Delete author by id: " + id);
        authorDAO.deleteById(id);
    }
    @LogTime
    @Transactional
    public void save(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setMiddleName(authorDTO.getMiddleName());
        author.setBook(bookDAO.findByName(authorDTO.getNameBook()));

        log.info("Save author with id: " + author.getId());
        emailService.sendInfoAboutSaveObject("Save: " + author.toString());

        authorDAO.save(author);
    }
    @LogTime
    @Transactional
    public List<Author> filterAuthors(
            String bookName, String firstName, String middleName, String lastName
    ) {
        log.info("Filter authors by bookName=" + bookName +
                ", firstName=" + firstName +
                ", middleName=" + middleName +
                ", lastName=" + lastName);

        return authorDAO
                .findAllAuthorsByBookNameAndFirstNameAndMiddleNameAndLastName(
                        bookName, firstName, middleName, lastName);
    }
}
