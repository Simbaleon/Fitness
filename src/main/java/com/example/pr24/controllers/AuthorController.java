package com.example.pr24.controllers;

import com.example.pr24.dto.AuthorDTO;
import com.example.pr24.dto.AuthorFilterDTO;
import com.example.pr24.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("newAuthor", new AuthorDTO());
        model.addAttribute("filter", new AuthorFilterDTO());
        return "authors/index";
    }

    @DeleteMapping("/authors/{id}")
    public String getAll(@PathVariable("id") int id) {
        authorService.delete(id);
        return "redirect:/authors";
    }

    @PostMapping("/authors")
    public String create(@ModelAttribute("newAuthor") AuthorDTO authorDTO) {
        authorService.save(authorDTO);
        return "redirect:/authors";
    }

    @PostMapping ("/authors/filter")
    public String filter(@ModelAttribute("filter") AuthorFilterDTO authorFilter, Model model) {
        model.addAttribute("authors",
                authorService.filterAuthors(
                        authorFilter.getBookName(),
                        authorFilter.getFirstName(),
                        authorFilter.getMiddleName(),
                        authorFilter.getLastName()));
        model.addAttribute("newAuthor", new AuthorDTO());
        model.addAttribute("filter", authorFilter);
        return "authors/index";
    }
}