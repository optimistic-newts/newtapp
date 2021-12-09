package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class RemoveUserTest {
    TestConversationRepository c;
    TestUserRepository u;
    RemoveUser r;
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
        testConversation.addUser(testUser.getId());
        testConversation.setAuthorId(2);

        c.save(testConversation);

        r = new RemoveUser(c, u);
    }

    @Test(timeout = 50)
    public void testRemoveUser() throws UserNotFound, UserNotFoundInConversation, ConversationNotFound {
        RequestModel testRequest = new RequestModel();

        testRequest.fill(RequestField.USER_ID, 2);
        testRequest.fill(RequestField.CONVERSATION_ID, testConversation.getId());

        r.request(testRequest);

        assertTrue(c.findById(testConversation.getId()).isPresent());
        Conversation checkConversation = c.findById(testConversation.getId()).get();
        ArrayList<Integer> userList = checkConversation.getUsers();
        int actualUserId = testUser.getId();

        assertTrue(userList.contains(actualUserId));
    }

}
