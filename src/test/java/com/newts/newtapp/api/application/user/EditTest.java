package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class EditTest {
    TestUserRepository testUserRepository;
    Create create;
    Edit edit;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists,
            InvalidPassword {
        testUserRepository = new TestUserRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
        edit = new Edit(testUserRepository);
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "testPassword");
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
    }

    @Test(timeout = 50)
    public void testEdit() throws UserNotFound, UserAlreadyExists, InvalidUsername{
        RequestModel r2 = new RequestModel();
        ArrayList<String> newInterests = new ArrayList<>();
        newInterests.add("newInterests");
        r2.fill(RequestField.USERNAME, "test");
        r2.fill(RequestField.NEW_USERNAME, "newUsername");
        r2.fill(RequestField.INTERESTS, newInterests);
        edit.request(r2);

        User u = testUserRepository.findById(1).orElseThrow(UserNotFound::new);

        assertEquals("newUsername", u.getUsername());
        assertEquals("newInterests", u.getInterests().get(0));
    }
}
