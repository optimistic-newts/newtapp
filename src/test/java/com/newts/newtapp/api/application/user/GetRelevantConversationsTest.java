package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.conversation.GetRelevantConversations;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GetRelevantConversationsTest {
    TestConversationRepository c;
    TestUserRepository u;
    User user;
    Conversation conversationOne;
    Conversation conversationTwo;
    Conversation conversationThree;
    GetRelevantConversations g;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        user = new User();
        ArrayList<Integer> blockedUser = new ArrayList<>();
        ArrayList<String> interest = new ArrayList<>();
        interest.add("a");
        ArrayList<String> notInterest = new ArrayList<>();
        notInterest.add("b");
        user.setInterests(interest);
        user.setLocation("Toronto");
        user.setBlockedUsers(blockedUser);
        user.setUsername("testuser");

        conversationOne = new Conversation();
        conversationTwo = new Conversation();
        conversationThree = new Conversation();
        conversationOne.setId(1);
        conversationTwo.setId(2);
        conversationThree.setId(3);
        conversationOne.setTitle("a");
        conversationTwo.setTitle("a");
        conversationThree.setTitle("b");
        conversationOne.setTopics(interest);
        conversationTwo.setTopics(interest);
        conversationThree.setTopics(notInterest);
        conversationOne.setLocation("a");
        conversationTwo.setLocation("a");
        conversationThree.setLocation("b");
        conversationOne.setMaxSize(5);
        conversationTwo.setMaxSize(5);
        conversationThree.setMaxSize(5);

        c.save(conversationThree);
        c.save(conversationTwo);
        c.save(conversationOne);

        u.save(user);

        g = new GetRelevantConversations(u, c);
    }

    @Test(timeout=50)
    public void testGetRelevantConversations() throws UserNotFound {
        RequestModel r = new RequestModel();

        r.fill(RequestField.USERNAME, "testuser");

        ArrayList<ConversationProfile> cp = g.request(r);

        Assert.assertEquals("a", cp.get(0).topics.get(0));
        Assert.assertEquals("a", cp.get(1).topics.get(0));
        Assert.assertEquals("b", cp.get(2).topics.get(0));
    }
}