package com.mistakes.controller;

import com.itextpdf.text.DocumentException;
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

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/inputInformation")
    public String submit(@ModelAttribute InputInformation inputInformation) throws DocumentException {
        userService.generateFileWithMistakes(inputInformation);
        return "redirect:/greeting";
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("inputInformation", new InputInformation());
        return "MainView";
    }
}
