package com.mistakes.service;

import com.mistakes.model.InputInformation;
import com.mistakes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private FileService fileService;

    private List<User> users;

    @PostConstruct
    private void initUsersCache() {
        users = fileService.readUsers();
    }


    public void generateFileWithMistakes(InputInformation inputInformation) {
        //generate list of x users from req list
        //make mistakes according to %
        //create pdf or doc and return
    }
}
