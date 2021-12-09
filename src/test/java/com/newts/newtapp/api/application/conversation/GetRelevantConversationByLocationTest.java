package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
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

public class GetRelevantConversationByLocationTest {
    TestConversationRepository c;
    TestUserRepository u;
    User user;
    Conversation conversationOne;
    Conversation conversationTwo;
    Conversation conversationThree;
    GetRelevantConversationsByLocation g;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();

        user = new User();
        ArrayList<Integer> following = new ArrayList<>();
        following.add(1);
        following.add(2);
        ArrayList<Integer> blockedUser = new ArrayList<>();
        ArrayList<String> interest = new ArrayList<>();
        interest.add("a");
        ArrayList<String> notInterest = new ArrayList<>();
        notInterest.add("b");
        user.setId(7);
        user.setInterests(interest);
        user.setLocation("a");
        user.setBlockedUsers(blockedUser);
        user.setFollowing(following);

        conversationOne = new Conversation();
        conversationTwo = new Conversation();
        conversationThree = new Conversation();
        conversationOne.setId(1);
        conversationTwo.setId(2);
        conversationThree.setId(3);
        conversationOne.setTitle("a");
        conversationTwo.setTitle("b");
        conversationThree.setTitle("aa");
        conversationOne.setIsOpen(true);
        conversationTwo.setIsOpen(true);
        conversationThree.setIsOpen(true);
        conversationOne.setTopics(interest);
        conversationTwo.setTopics(notInterest);
        conversationThree.setTopics(notInterest);
        conversationOne.setLocation("a");
        conversationTwo.setLocation("b");
        conversationThree.setLocation("a");
        conversationOne.setMaxSize(5);
        conversationTwo.setMaxSize(5);
        conversationThree.setMaxSize(5);

        c.save(conversationThree);
        c.save(conversationTwo);
        c.save(conversationOne);

        u.save(user);

        g = new GetRelevantConversationsByLocation(u, c);
    }

    @Test(timeout=50)
    public void testGetRelevantConversationsByLocation() throws UserNotFound {
        RequestModel r = new RequestModel();

        r.fill(RequestField.USER_ID, 7);

        ArrayList<ConversationProfile> cp = g.request(r);

        Assert.assertEquals(2, cp.size());
        Assert.assertEquals("a", cp.get(0).topics.get(0));
        Assert.assertEquals("b", cp.get(1).topics.get(0));
    }
}