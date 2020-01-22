package com.airtel.test.service;

import com.airtel.test.controller.AbstractController;
import com.airtel.test.entity.User;
import com.airtel.test.repository.UserRepository;
import com.airtel.test.util.EncryptionUtil;
import com.airtel.test.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.airtel.test.util.StandardResponse.SigninResult.*;


@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public User getByUserName(String userName) {
        return userRepository.findById(userName).orElse(null);
    }

    public boolean doesUserWithUserIdExist(String userName) {
        return getByUserName(userName) != null;
    }

    public boolean signUp(AbstractController controller, HttpSession session, User user) {
        if (!doesUserWithUserIdExist(user.getUserName())) {
            userRepository.save(new User(user.getUserName(), user.getName(), EncryptionUtil.encrypt(user.getUserName(), user.getEncryptedPassword())));
            return true;
        }
        return false;
    }

    public StandardResponse.SigninResult signIn(AbstractController controller, HttpSession session, String userName, String password) {
        User user = getByUserName(userName);
        if (user != null) {
            if (EncryptionUtil.isEcryptedKeyCorrect(userName, password, user.getEncryptedPassword())) {
                controller.saveUserInSession(session, user.getUserName());
                return SUCCESS;
            }
            return WRONG_PASSWORD;
        }
        return USER_NOT_EXIST;
    }

    public void signOut(AbstractController controller, HttpSession session) {
        controller.removeUserIdFromSession(session);
    }
}


