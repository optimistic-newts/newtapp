package com.newts.newtapp.api.application.user;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class BlockUnblockTest {
    Block block;
    Unblock unblock;
    TestUserRepository testUserRepository;
    TestConversationRepository testConversationRepository;
    Create create;
    Follow follow;

    @Before
    public void setUp() throws InvalidUsername, UserAlreadyExists, InvalidPassword, UserNotFound,
            SameUser, AlreadyFollowingUser, UserBlocked, BlockedByUser {
        testUserRepository = new TestUserRepository();
        testConversationRepository = new TestConversationRepository();
        block = new Block(testUserRepository, testConversationRepository);
        unblock = new Unblock(testUserRepository);
        create = new Create(testUserRepository);
        follow = new Follow(testUserRepository);
        ArrayList<String> interests = new ArrayList<>();
        interests.add("tests");

        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.PASSWORD, "test123");
        r.fill(RequestField.INTERESTS, interests);
        create.request(r);
        r.fill(RequestField.USERNAME, "test2");
        create.request(r);
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.USERNAME_TWO, "test2");
        follow.request(r);
        r.fill(RequestField.USERNAME, "test2");
        r.fill(RequestField.USERNAME_TWO, "test");
        follow.request(r);
    }

    @Test(timeout = 500)
    public void testBlock() throws UserNotFound, UserAlreadyBlocked, UserNotBlocked {
        RequestModel r = new RequestModel();
        r.fill(RequestField.USERNAME, "test");
        r.fill(RequestField.USERNAME_TWO, "test2");

        block.request(r);
        User u1 = testUserRepository.findById(1).get();
        User u2 = testUserRepository.findById(2).get();
        assertFalse((u1.getFollowers().contains(u2.getId())));
        assertFalse((u2.getFollowers().contains(u1.getId())));
        assertTrue(u1.getBlockedUsers().contains(u2.getId()));

        unblock.request(r);
        assertFalse(u1.getBlockedUsers().contains(u2.getId()));


    }

}
