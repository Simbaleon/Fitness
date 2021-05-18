package com.example.pr24.dao;

import com.example.pr24.models.Author;
import com.example.pr24.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuthorDAO extends JpaRepository<Author, Integer> {
    @Transactional
    void deleteAllByBook(Book book);

    @Query(value = "select authors.* from authors join books on books.id = authors.book_id    " +
            "where ('' = :firstName or first_name = :firstName) and " +
            "('' = :middleName or middle_name = :middleName) and " +
            "('' = :lastName or last_name = :lastName) and " +
            "('' = :bookName or books.name = :bookName)", nativeQuery = true)
    List<Author> findAllAuthorsByBookNameAndFirstNameAndMiddleNameAndLastName(
            String bookName, String firstName, String middleName, String lastName
    );
}
