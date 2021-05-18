package com.example.pr24.dao;

import com.example.pr24.models.Book;
import com.example.pr24.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<Book, Integer> {
    Book findByName(String name);
}
