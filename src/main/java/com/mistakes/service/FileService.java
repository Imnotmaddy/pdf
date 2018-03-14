package com.mistakes.service;

import com.mistakes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FileService {

    private static Logger logger = Logger.getLogger(FileService.class.getName());

    public List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:EnglishNames.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            final User user = new User();
            String name;
            while ((name = reader.readLine()) != null) {
                user.setName(name);
                user.setTelephoneNumber(reader.readLine());
                user.setAddress(reader.readLine());
                users.add(user);
            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return users;
    }
}
