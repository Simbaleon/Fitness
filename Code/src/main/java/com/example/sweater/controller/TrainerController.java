package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    @Autowired
    private UserSevice userSevice;
    private MessageRepo messageRepo;

    @GetMapping("")
    public String main(Model model) {
        System.out.println("--------------------Вошел--------------------");
        Iterable<Message> messages = messageRepo.findAll();
        messages = messageRepo.findAll();


        model.addAttribute("messages", messages);

        return "main";
    }



    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "trainer";
    }
}
