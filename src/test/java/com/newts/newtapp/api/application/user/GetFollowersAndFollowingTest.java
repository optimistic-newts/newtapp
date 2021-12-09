package com.newts.newtapp.api.application.user;

import java.util.ArrayList;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class GetFollowersAndFollowingTest {
    TestUserRepository testUserRepository;
    Create create;
    Follow follow;
    GetFollowing getFollowing;
    GetFollowers getFollowers;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists,
            InvalidPassword {
        testUserRepository = new TestUserRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
        follow = new com.newts.newtapp.api.application.user.Follow(testUserRepository);
        getFollowers = new GetFollowers(testUserRepository);
        getFollowing = new GetFollowing(testUserRepository);

        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
        RequestModel r2 = new RequestModel();
        r2.fill(RequestField.USERNAME, "test2");
        r2.fill(RequestField.PASSWORD, "test123");
        r2.fill(RequestField.INTERESTS, interests);
        create.request(r2);
    }

    @Test(timeout = 500)
    public void getFollowersFollowingTest() throws UserNotFound {
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        ArrayList<UserProfile> followers = getFollowers.request(r);
        ArrayList<UserProfile> following = getFollowing.request(r);
        User user = testUserRepository.findById(1).get();

        for(UserProfile userProfile:followers){
            assertTrue(user.getFollowers().contains(userProfile.id));
        }
        for(UserProfile userProfile:following){
            assertTrue(user.getFollowing().contains(userProfile.id));
        }
    }


}
