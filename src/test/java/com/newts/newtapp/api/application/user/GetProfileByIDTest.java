package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class GetProfileByIDTest {
    TestUserRepository testUserRepository;
    Create create;
    GetProfileById getProfileById;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists,
            InvalidPassword {
        testUserRepository = new TestUserRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
        getProfileById = new com.newts.newtapp.api.application.user.GetProfileById(testUserRepository);
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
    }

    @Test(timeout = 500)
    public void testGetProfileByID() throws UserNotFound{
        RequestModel r2 = new RequestModel();
        r2.fill(RequestField.USER_ID, 1);
        UserProfile userProfile = getProfileById.request(r2);
        assertEquals(1, userProfile.id);
    }
}
