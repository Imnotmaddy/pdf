package com.mistakes.service;

import com.mistakes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

            String name;
            while ((name = reader.readLine()) != null) {
                final User user = new User();
                user.setName(name);
                user.setTelephoneNumber(reader.readLine());
                user.setAddress(reader.readLine());
                users.add(user);
            }
        writeUsersCSV(users);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return users;
    }

    public void writeUsersCSV(List<User> users){
        try {
           BufferedWriter writer = new BufferedWriter(new FileWriter("e:\\EnglishFixedNames.csv"));
            char c = '"';

            for (User user:users) {
                 writer.write(c+user.getName()+ c + "\n");

                 writer.write(c+user.getAddress()+c+"\n");

                writer.write(c+user.getTelephoneNumber()+c+"\n");
            }
            writer.close();

        }

        catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
    }
}
