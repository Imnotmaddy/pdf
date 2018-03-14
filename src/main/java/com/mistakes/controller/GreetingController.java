package com.mistakes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mistakes.service.UserService;

@Controller
public class GreetingController {

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model
    ) {
        userService.generateFileWithMistakes();
        model.addAttribute("pdf", "test");

        return "greeting";
    }
}
