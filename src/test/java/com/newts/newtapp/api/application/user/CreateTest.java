package com.newts.newtapp.api.application.user;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.InvalidPassword;
import com.newts.newtapp.api.errors.InvalidUsername;
import com.newts.newtapp.api.errors.UserAlreadyExists;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class CreateTest {
    TestUserRepository testUserRepository;
    Create create;

    @Before
    public void setUp() {
        testUserRepository = new TestUserRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
    }

    @Test(timeout = 500)
    public void testCreate() throws InvalidPassword, InvalidUsername, UserAlreadyExists{
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
        assertTrue(testUserRepository.findById(1).isPresent());
        User u = testUserRepository.findById(1).get();
        assertEquals(u.getUsername(), "test");
    }
}
