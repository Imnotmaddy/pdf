package com.mistakes.service;

import com.itextpdf.text.DocumentException;
import com.mistakes.controller.FileDownloadController;
import com.mistakes.model.InputInformation;
import com.mistakes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Random random = new Random();

    @Autowired
    private FileService fileService;

    private List<User> users;
    private List<User> englishUsers;
    private List<User> russianUsers;
    private List<User> belorussianUsers;
    private String filename;
    private static Logger logger = Logger.getLogger(FileService.class.getName());

    @PostConstruct
    private void initUsersCache() throws DocumentException {
        englishUsers = fileService.readUsers("EnglishNames.txt");
        russianUsers = fileService.readUsers("RussianNames.txt");
        belorussianUsers = fileService.readUsers("BelorussianNames.txt");
    }


    public void generateFileWithMistakes(InputInformation inputInformation) throws DocumentException {

        if (inputInformation.getRegion().equals("USA")) {
            users = englishUsers;
            filename = "EnglishNamesWithMistakes";
        }
        if (inputInformation.getRegion().equals("Russia")) {
            users = russianUsers;
            filename = "RussianNamesWithMistakes";
        }
        if (inputInformation.getRegion().equals("Belarus")) {
            users = belorussianUsers;
            filename = "BelorussianNamesWithMistakes";
        }

        float mistakeStorage = inputInformation.getPercentOfMistakes();
        int mistakesPerRow = 0;
        while (mistakeStorage >= 100) {
            mistakesPerRow++;
            mistakeStorage = mistakeStorage - 100;
        }
        mistakeStorage = mistakeStorage / 100;
        float storage = mistakeStorage;

        List<User> usersWithMistakes = new ArrayList<>((int) inputInformation.getNumberRows());

        for (int i = 0; i < inputInformation.getNumberRows(); i++) {
            int allMistakes = mistakesPerRow;
            int j = i;
            if (j >= users.size()) {
                j = j % users.size();
            }

            User current = users.get(j);
            User changed = new User();
            if (mistakeStorage > 1) {
                allMistakes++;
                mistakeStorage = mistakeStorage - 1;
            }
            mistakeStorage += storage;

            switch (random.nextInt(3)) {
                case 0:
                    changed.setName(corruptName(current.getName(), inputInformation.getMistakes(), allMistakes));
                    changed.setTelephoneNumber(current.getTelephoneNumber());
                    changed.setAddress(current.getAddress());
                    break;
                case 1:
                    changed.setAddress(corruptAddress(current.getAddress(), inputInformation.getMistakes(), allMistakes));
                    changed.setTelephoneNumber(current.getTelephoneNumber());
                    changed.setName(current.getName());
                    break;
                case 2:
                    changed.setTelephoneNumber(corruptTelephoneNumber(current.getTelephoneNumber(), inputInformation.getMistakes(), allMistakes));
                    changed.setName(current.getName());
                    changed.setAddress(current.getAddress());
                    break;
            }
            usersWithMistakes.add(changed);
        }
        try {
            if (inputInformation.getDocumentType().equals("PDF")) {
                fileService.writeUsersPDF(usersWithMistakes, filename);
            }
            if (inputInformation.getDocumentType().equals("CSV")) {
                fileService.writeUsersCSV(usersWithMistakes, filename);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public String addSymbolMistake(String value, String isRequired) {
        if (isRequired == null) {
            return value;
        }
        int position = random.nextInt(value.length());
        char c = (char) (random.nextInt(26) + 'a');
        return value.subSequence(0, position) + String.valueOf(c) + value.subSequence(position, value.length());

    }

    public String deleteSymbolMistake(String value, String isRequired) {
        if (isRequired == null) return value;
        int position = random.nextInt(value.length());
        if (position == 0) {
            return value.substring(1);
        }
        return value.substring(0, position) + value.substring(position + 1, value.length());

    }

    public String replaceSymbolMistake(String value, String isRequired) {
        if (isRequired == null) return value;
        int position = random.nextInt(value.length());
        if (position == value.length() - 1) {
            return (value.substring(0, position - 2) + value.charAt(position - 1) + value.charAt(position - 2));
        }
        return value.substring(0, position) + value.charAt(position + 1) + value.charAt(position) + value.substring(position + 2);
    }

    public String corruptName(String value, List<String> isRequired, int numberOfMistakes) {
        for (int i = 0; i < numberOfMistakes; i++) {
            value = addSymbolMistake(value, isRequired.get(0));
            value = deleteSymbolMistake(value, isRequired.get(1));
            value = replaceSymbolMistake(value, isRequired.get(2));
        }
        return value;
    }

    public String corruptAddress(String value, List<String> isRequired, int numberOfMistakes) {
        for (int i = 0; i < numberOfMistakes; i++) {
            value = addSymbolMistake(value, isRequired.get(0));
            value = deleteSymbolMistake(value, isRequired.get(1));
            value = replaceSymbolMistake(value, isRequired.get(2));
        }
        return value;
    }

    public String corruptTelephoneNumber(String value, List<String> isRequired, int numberOfMistakes) {
        for (int i = 0; i < numberOfMistakes; i++) {
            value = addSymbolMistake(value, isRequired.get(0));
            value = deleteSymbolMistake(value, isRequired.get(1));
            value = replaceSymbolMistake(value, isRequired.get(2));
        }
        return value;
    }


}
