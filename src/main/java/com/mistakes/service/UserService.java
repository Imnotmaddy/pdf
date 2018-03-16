package com.mistakes.service;

import com.mistakes.model.InputInformation;
import com.mistakes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private static final Random random = new Random();

    @Autowired
    private FileService fileService;

    private List<User> users;

    @PostConstruct
    private void initUsersCache() {
        users = fileService.readUsers();
    }


    public void generateFileWithMistakes(InputInformation inputInformation) {

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

        if (inputInformation.getDocumentType().equals("PDF")) {
            //fileService.writeUsersPDF(current);
        }
        if (inputInformation.getDocumentType().equals("CSV")) {
            fileService.writeUsersCSV(usersWithMistakes);
        }
    }

    public String addSymbolMistake(String value, String isRequired) {
        if (isRequired == null) {
            return value;
        }
        // select random position
        int position = random.nextInt(value.length());
        // select random simbol
        char c = (char) (random.nextInt(26) + 'a');
        // insert and return
        return value.subSequence(0, position) + String.valueOf(c) + value.subSequence(position, value.length());

    }

    public String deleteSymbolMistake(String value, String isRequired) {
        if (isRequired == null) return value;

        //select random position
        int position = random.nextInt(value.length());
        if (position == 0) {
            return value.substring(1);
        }
        // delete symbol

        return value.substring(0, position) + value.substring(position + 1, value.length());

    }

    public String replaceSymbolMistake(String value, String isRequired) {
        if (isRequired == null) return value;
        return value;
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
