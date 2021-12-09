package com.newts.newtapp.api.application.user;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class ChangePasswordTest {
    TestUserRepository testUserRepository;
    Create create;
    ChangePassword changePassword;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists, InvalidPassword {
        testUserRepository = new TestUserRepository();
        create = new Create(testUserRepository);
        changePassword = new ChangePassword(testUserRepository);
        ArrayList<String> interests = new ArrayList<>();
        RequestModel r = new RequestModel();

        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
    }

    @Test(timeout = 500)
    public void changePasswordTest() throws UserNotFound, InvalidPassword, IncorrectPassword {
        RequestModel requestModel = new RequestModel();
        requestModel.fill(RequestField.USERNAME, "test");
        requestModel.fill(RequestField.PASSWORD, "test123");
        requestModel.fill(RequestField.NEW_PASSWORD, "test1234");
        changePassword.request(requestModel);

        assertTrue(testUserRepository.findById(1).isPresent());
        User u = testUserRepository.findById(1).get();
        assertTrue(BCrypt.checkpw("test1234",u.getPassword()));
    }
}