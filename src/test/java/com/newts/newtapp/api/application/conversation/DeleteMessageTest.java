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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DeleteMessageTest {
    TestConversationRepository c;
    TestMessageRepository m;
    TestUserRepository u;
    User testUser;
    Message testMessage;
    Message testMessageTwo;
    Conversation testConversation;
    DeleteMessage d;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        m = new TestMessageRepository();
        u = new TestUserRepository();

        //Assume user created and saved
        testUser = new User();
        u.save(testUser);

        //Assume a conversation created and saved
        testConversation = new Conversation();
        testConversation.setMaxSize(1); //To save the testUser in the next step
        c.save(testConversation);

        //Assume the user joined the conversation
        testConversation.addUser(testUser.getId());
        testUser.addConversation(testConversation);

        //The testUser writes messages in a conversation
        testMessage = new Message();
        testMessageTwo = new Message();
        testMessage.setId(1);
        testMessageTwo.setId(2);
        testMessage.setAuthor(1);
        testMessageTwo.setAuthor(1);
        m.save(testMessage);
        m.save(testMessageTwo);

        testConversation.addMessage(testMessage.getId());
        testConversation.addMessage(testMessageTwo.getId());


        d = new DeleteMessage(c, m);
    }

    @Test(timeout=50)
    public void testGetMessages() throws ConversationNotFound, MessageNotFound, WrongAuthor,
            MessageNotFoundInConversation, UserNotFoundInConversation {
        RequestModel r = new RequestModel();

        r.fill(RequestField.CONVERSATION_ID, 1);
        r.fill(RequestField.MESSAGE_ID, 2);
        r.fill(RequestField.USER_ID, 1);

        Assert.assertEquals(2, c.findById(1).get().getMessages().size());

        d.request(r);

        assertTrue(c.findById(1).isPresent());
        Assert.assertEquals(1, c.findById(1).get().getMessages().size());
    }
}