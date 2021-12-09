package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeleteTest {
    TestUserRepository testUserRepository;
    TestConversationRepository testConversationRepository;
    Create create;
    Delete delete;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists, InvalidPassword {
        testUserRepository = new TestUserRepository();
        testConversationRepository = new TestConversationRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
        delete = new com.newts.newtapp.api.application.user.Delete(testUserRepository);
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
    }

    @Test(timeout = 500)
    public void testDelete() throws UserNotFound, IncorrectPassword, ConversationNotFound{
        RequestModel r2 = new RequestModel();
        r2.fill(RequestField.USERNAME, "test");
        r2.fill(RequestField.PASSWORD, "test123");
        delete.request(r2);
        assertFalse(testUserRepository.findById(1).isPresent());
    }
}
