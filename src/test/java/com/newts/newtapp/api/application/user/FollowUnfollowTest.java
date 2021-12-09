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
public class FollowUnfollowTest {
    TestUserRepository testUserRepository;
    Create create;
    Follow follow;
    Unfollow unfollow;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists,
            InvalidPassword {
        testUserRepository = new TestUserRepository();
        create = new com.newts.newtapp.api.application.user.Create(testUserRepository);
        follow = new com.newts.newtapp.api.application.user.Follow(testUserRepository);
        unfollow = new com.newts.newtapp.api.application.user.Unfollow(testUserRepository);
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
    public void testFollow() throws UserNotFound, SameUser, AlreadyFollowingUser, UserBlocked, BlockedByUser {
        RequestModel r3 = new RequestModel();
        User user1 = testUserRepository.findByUsername("test").orElseThrow(UserNotFound::new);
        User user2 = testUserRepository.findByUsername("test2").orElseThrow(UserNotFound::new);
        r3.fill(RequestField.USERNAME, "test");
        r3.fill(RequestField.USERNAME_TWO, "test2");
        follow.request(r3);
        assertTrue(user1.getFollowing().contains(2));
        assertTrue(user2.getFollowers().contains(1));
        unfollow.request(r3);
        assertFalse(user1.getFollowing().contains(2));
        assertFalse(user2.getFollowers().contains(1));
    }
}
