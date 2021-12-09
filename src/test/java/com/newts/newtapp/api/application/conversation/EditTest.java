package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.InvalidConversationSize;
import com.newts.newtapp.api.errors.InvalidMinRating;
import com.newts.newtapp.api.errors.WrongAuthor;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;


public class EditTest {
    TestConversationRepository c;
    TestUserRepository u;
    User testUser;
    Conversation testConversation;
    Edit e;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        //Assume user created and saved
        testUser = new User();
        u.save(testUser);

        //Assume a conversation created and saved
        testConversation = new Conversation();
        testConversation.setId(1);
        testConversation.setTitle("old");
        testConversation.setLocation("USA");
        testConversation.setAuthorId(1);
        c.save(testConversation);

        e = new Edit(c);
    }

    @Test(timeout=50)
    public void testGetMessages() throws ConversationNotFound, InvalidMinRating, WrongAuthor, InvalidConversationSize {
        RequestModel r = new RequestModel();

        r.fill(RequestField.CONVERSATION_ID, 1);
        r.fill(RequestField.TITLE, "new");
        r.fill(RequestField.TOPICS, new ArrayList<>());
        r.fill(RequestField.LOCATION, "Toronto");
        r.fill(RequestField.LOCATION_RADIUS, 1);
        r.fill(RequestField.MIN_RATING,1);
        r.fill(RequestField.MAX_SIZE, 1);
        r.fill(RequestField.USER_ID, 1);

        assertTrue(c.findById(1).isPresent());
        Assert.assertEquals("old", c.findById(1).get().getTitle());
        Assert.assertEquals("USA", c.findById(1).get().getLocation());

        e.request(r);

        assertTrue(c.findById(1).isPresent());
        Assert.assertEquals("new", c.findById(1).get().getTitle());
        Assert.assertEquals("Toronto", c.findById(1).get().getLocation());
    }
}