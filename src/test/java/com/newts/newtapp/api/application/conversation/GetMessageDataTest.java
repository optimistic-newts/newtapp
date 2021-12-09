package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.MessageData;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.MessageNotFound;
import com.newts.newtapp.api.errors.MessageNotFoundInConversation;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestMessageRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetMessageDataTest {
    TestConversationRepository c;
    TestMessageRepository m;
    Message testMessage;
    Conversation testConversation;
    GetMessageData g;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        m = new TestMessageRepository();

        testConversation = new Conversation();
        testMessage = new Message();

        testConversation.setId(1);
        testMessage.setId(1);

        testConversation.addMessage(testMessage.getId());
        testMessage.setBody("test");
        testMessage.setAuthor(1);
        testMessage.setWrittenAt("testWrittenAt");
        testMessage.setLastUpdatedAt("testLastUpdatedAt");

        c.save(testConversation);
        m.save(testMessage);

        g = new GetMessageData(c, m);
    }

    @Test(timeout=50)
    public void testGetMessagesData() throws MessageNotFound, ConversationNotFound, MessageNotFoundInConversation {
        RequestModel r = new RequestModel();
        r.fill(RequestField.CONVERSATION_ID, 1);
        r.fill(RequestField.MESSAGE_ID, 1);

        MessageData mD = g.request(r);

        Assert.assertEquals(1, mD.id);
        Assert.assertEquals("test", mD.body);
        Assert.assertEquals(1, mD.author);
        Assert.assertEquals("testWrittenAt", mD.writtenAt);
        Assert.assertEquals("testLastUpdatedAt", mD.lastUpdatedAt);
    }

}
