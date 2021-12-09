package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class AddUserTest {
    TestConversationRepository c;
    TestUserRepository u;
    AddUser a;
    User testUser;
    Conversation testConversation;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        testConversation = new Conversation();
        testConversation.setMaxSize(2);
        testUser = new User();
        User testAuthor = new User();

        u.save(testUser);
        u.save(testAuthor);
        testConversation.addUser(testAuthor.getId());
        testConversation.setAuthorId(2);

        c.save(testConversation);

        a = new AddUser(c, u);
    }

    @Test(timeout = 50)
    public void testAddUser()
            throws UserBelowMinimumRating, UserNotFound, ConversationFull, ConversationNotFound, UserBlocked {
        RequestModel r = new RequestModel();
        r.fill(RequestField.CONVERSATION_ID, testConversation.getId());
        r.fill(RequestField.USER_ID, testUser.getId());
        a.request(r);
        assertTrue(c.findById(1).isPresent());
        Conversation checkConversation = c.findById(1).get();
        ArrayList<Integer> userList = checkConversation.getUsers();
        int actualUserId = userList.get(1);
        Assert.assertEquals(1, actualUserId);
    }
}
