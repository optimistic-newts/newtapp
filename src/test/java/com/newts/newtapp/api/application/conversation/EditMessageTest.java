package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestMessageRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.Message;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class EditMessageTest {
    TestConversationRepository testConversationRepository;
    TestMessageRepository testMessageRepository;
    TestUserRepository testUserRepository;
    Conversation c;
    Message m;
    User u;
    EditMessage editMessage;

    @Before
    public void setUp() {
        testConversationRepository = new TestConversationRepository();
        testMessageRepository = new TestMessageRepository();
        testUserRepository = new TestUserRepository();
        editMessage = new EditMessage(testConversationRepository, testMessageRepository);
        Conversation c = new Conversation();
        c.setMaxSize(1);
        User u = new User();
        Message m = new Message(1, "Testing123", 1, 1);
        c.addUser(1);
        u.addConversation(c);
        c.addMessage(1);

        testConversationRepository.save(c);
        testMessageRepository.save(m);
        testUserRepository.save(u);
    }

    @Test(timeout = 500)
    public void testEditMessage() throws UserNotFoundInConversation, WrongAuthor, MessageNotFound, EmptyMessage, ConversationNotFound, MessageNotFoundInConversation {
        RequestModel r = new RequestModel();
        String s = "Anyone home?";
        r.fill(RequestField.CONVERSATION_ID, 1);
        r.fill(RequestField.MESSAGE_ID, 1);
        r.fill(RequestField.USER_ID, 1);
        r.fill(RequestField.MESSAGE_BODY, s);
        editMessage.request(r);
        Message message = testMessageRepository.findById(1).get();
        assertEquals(message.getBody(), s);
    }
}
