package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.ConversationData;
import com.newts.newtapp.api.application.datatransfer.MessageData;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
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
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.Objects;

public class GetConversationDataTest {
    TestConversationRepository c;
    TestMessageRepository m;
    TestUserRepository u;
    GetConversationData g;
    Conversation testConversation;
    User testUser;
    Message testMessage;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        m = new TestMessageRepository();
        u = new TestUserRepository();

        testConversation = new Conversation();
        testConversation.setMaxSize(1);
        testUser = new User(1, "testUser", "password", new ArrayList<>());
        testConversation.addUser(testUser.getId());
        testUser.addConversation(testConversation);
        testMessage = new Message(1, "", 1, 1);
        testConversation.addMessage(testMessage.getId());

        u.save(testUser);
        m.save(testMessage);
        c.save(testConversation);

        g = new GetConversationData(c, m, u);
    }

    @Test(timeout = 5000)
    public void testGetConversationData() throws UserNotFound, MessageNotFound, ConversationNotFound,
            MessageNotFoundInConversation {
        RequestModel r = new RequestModel();
        r.fill(RequestField.CONVERSATION_ID, 1);

        ConversationData actualData = g.request(r);

        Assert.assertEquals(testConversation.getId(), actualData.id);
        EntityModel<UserProfile> profileModel = actualData.userProfiles.get(0);
        Assert.assertEquals(1, Objects.requireNonNull(profileModel.getContent()).id);
        EntityModel<MessageData> messageModel = actualData.messageData.get(0);
        Assert.assertEquals(1, Objects.requireNonNull(messageModel.getContent()).id);
    }
}
