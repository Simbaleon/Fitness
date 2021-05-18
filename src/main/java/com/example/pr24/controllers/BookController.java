package com.example.pr24.controllers;

import com.example.pr24.models.Book;
import com.example.pr24.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("newBook", new Book());
        return "books/index";
    }

    @DeleteMapping("/books/{id}")
    public String getAll(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute("newBook") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }
}
