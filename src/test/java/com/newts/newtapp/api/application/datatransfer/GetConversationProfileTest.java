package com.newts.newtapp.api.application.datatransfer;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.conversation.GetConversationProfile;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.entities.Conversation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
public class GetConversationProfileTest {
    Conversation c;
    TestConversationRepository testConversationRepository;
    GetConversationProfile getConversationProfile;

    @Before
    public void setup(){
        testConversationRepository = new TestConversationRepository();
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Tests");

        c = new Conversation(1,
                "Test",
                topics,
                "New Test City",
                500,
                1,
                5,
                1);

        testConversationRepository.save(c);
        getConversationProfile = new GetConversationProfile(testConversationRepository);
    }

    @Test(timeout=500)
    public void testGetConversationProfile() throws ConversationNotFound {
        RequestModel r = new RequestModel();
        r.fill(RequestField.CONVERSATION_ID,1);
        ConversationProfile conversationProfile = getConversationProfile.request(r);
        assertEquals(conversationProfile.title, "Test");
        assertEquals(conversationProfile.id, 1);
        assertEquals(conversationProfile.location, "New Test City");
        assertEquals(conversationProfile.maxSize, 5);
    }
}
