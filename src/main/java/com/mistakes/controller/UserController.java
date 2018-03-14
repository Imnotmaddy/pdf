package com.mistakes.controller;

import com.mistakes.model.InputInformation;
import com.mistakes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mistakes.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/inputInformation")
    public String submit(@ModelAttribute("inputInformation")User user, Model model)
    {
        model.addAttribute("name", user.getName());
        model.addAttribute("telephoneNumber",user.getTelephoneNumber());
        model.addAttribute("address", user.getAddress());
        return "MainView";
    }

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model
    ) {
        userService.generateFileWithMistakes();


        return "greeting";
    }
}
