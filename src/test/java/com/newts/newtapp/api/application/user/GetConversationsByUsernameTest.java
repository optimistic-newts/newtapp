package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.conversation.GetConversationsByUsername;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GetConversationsByUsernameTest {
    TestConversationRepository c;
    TestUserRepository u;
    User userOne;
    User userTwo;
    Conversation conversationOne;
    Conversation conversationTwo;
    Conversation conversationThree;
    GetConversationsByUsername g;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        userOne = new User();
        userOne.setId(1);
        userOne.setUsername("UserOne");
        u.save(userOne);

        userTwo = new User();
        userTwo.setId(2);
        userTwo.setUsername("UserTwo");
        u.save(userTwo);


        conversationOne = new Conversation();
        conversationTwo = new Conversation();
        conversationThree = new Conversation();
        conversationOne.setId(1);
        conversationTwo.setId(2);
        conversationThree.setId(3);
        conversationOne.setTitle("a");
        conversationTwo.setTitle("b");
        conversationThree.setTitle("c");
        conversationOne.setMaxSize(5);
        conversationTwo.setMaxSize(5);
        conversationThree.setMaxSize(5);

        conversationOne.addUser(1);
        userOne.addConversation(conversationOne);

        conversationTwo.addUser(2);
        userTwo.addConversation(conversationTwo);

        conversationThree.addUser(1);
        userOne.addConversation(conversationThree);
        conversationThree.addUser(2);
        userTwo.addConversation(conversationThree);
        c.save(conversationThree);
        c.save(conversationTwo);
        c.save(conversationOne);



        g = new GetConversationsByUsername(c, u);
    }

    @Test(timeout=50)
    public void testGetConversationsByUsername() throws UserNotFound, ConversationNotFound {
        RequestModel r = new RequestModel();

        r.fill(RequestField.USERNAME, "UserTwo");

        ArrayList<ConversationProfile> cp = g.request(r);

        Assert.assertEquals(2, cp.size());
        Assert.assertEquals("b", cp.get(0).title);
        Assert.assertEquals("c", cp.get(1).title);
    }
}