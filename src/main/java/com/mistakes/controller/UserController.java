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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/inputInformation")
    public void submit(@ModelAttribute InputInformation inputInformation,
                         HttpServletRequest request,
                         HttpServletResponse response) throws DocumentException, IOException {

        userService.generateFileWithMistakes(inputInformation);
        userService.downloadFile(request,response,inputInformation.getRegion(), inputInformation.getDocumentType());
        }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("inputInformation", new InputInformation());
        return "MainView";
    }
}
