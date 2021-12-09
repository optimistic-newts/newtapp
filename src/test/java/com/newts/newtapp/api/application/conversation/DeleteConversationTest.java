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

import static org.junit.Assert.assertFalse;

public class DeleteConversationTest {
    TestConversationRepository c;
    TestUserRepository u;
    Conversation testConversation;
    User testUser;
    Delete delete;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        testConversation = new Conversation();
        testUser = new User();
        c.save(testConversation);
        delete = new Delete(c,u);
    }

    @Test(timeout = 50)
    public void testDeleteConversation() throws UserNotFound, WrongAuthor, ConversationNotFound {
        RequestModel request = new RequestModel();
        request.fill(RequestField.CONVERSATION_ID, testConversation.getId());
        request.fill(RequestField.USER_ID, testUser.getId());
        delete.request(request);
        assertFalse(c.findById(testConversation.getId()).isPresent());

    }
}
