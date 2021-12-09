package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
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

public class GetRelevantConversationsByFollowTest {
    TestConversationRepository c;
    TestUserRepository u;
    User user;
    User userOne;
    User userTwo;
    Conversation conversationOne;
    Conversation conversationTwo;
    Conversation conversationThree;
    GetRelevantConversationsByFollow g;

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
        user.setUsername("user");
        user.setId(7);
        user.setInterests(interest);
        user.setLocation("Toronto");
        user.setBlockedUsers(blockedUser);
        user.setFollowing(following);

        conversationOne = new Conversation();
        conversationTwo = new Conversation();
        conversationThree = new Conversation();
        conversationOne.setId(1);
        conversationTwo.setId(2);
        conversationThree.setId(3);
        conversationOne.setTitle("a");
        conversationTwo.setTitle("d");
        conversationThree.setTitle("b");
        conversationOne.setTopics(interest);
        conversationTwo.setTopics(notInterest);
        conversationThree.setTopics(notInterest);
        conversationOne.setLocation("a");
        conversationTwo.setLocation("a");
        conversationThree.setLocation("b");
        conversationOne.setMaxSize(5);
        conversationTwo.setMaxSize(5);
        conversationThree.setMaxSize(5);
        conversationOne.setIsOpen(true);
        conversationTwo.setIsOpen(true);
        conversationThree.setIsOpen(true);
        ArrayList<Integer> conversationOneFollowing = new ArrayList<>();
        conversationOneFollowing.add(1);
        conversationOne.setUsers(conversationOneFollowing);
        ArrayList<Integer> conversationTwoFollowing = new ArrayList<>();
        conversationOneFollowing.add(2);
        conversationOne.setUsers(conversationTwoFollowing);
        ArrayList<Integer> conversationThreeFollowing = new ArrayList<>();
        conversationOneFollowing.add(4);
        conversationOne.setUsers(conversationThreeFollowing);

        userOne = new User();
        userOne.setId(1);
        ArrayList<Integer> userOneConversation = new ArrayList<>();
        userOneConversation.add(1);
        userOne.setConversations(userOneConversation);

        userTwo = new User();
        userTwo.setId(2);
        ArrayList<Integer> userTwoConversation = new ArrayList<>();
        userTwoConversation.add(2);
        userTwo.setConversations(userTwoConversation);

        c.save(conversationThree);
        c.save(conversationTwo);
        c.save(conversationOne);

        u.save(user);
        u.save(userOne);
        u.save(userTwo);

        g = new GetRelevantConversationsByFollow(u, c);
    }

    @Test(timeout=50)
    public void testGetRelevantConversationsByFollowers() throws UserNotFound, ConversationNotFound {
        RequestModel r = new RequestModel();

        r.fill(RequestField.USERNAME, "user");

        ArrayList<ConversationProfile> cp = g.request(r);

        Assert.assertEquals(2, cp.size());
        Assert.assertEquals("a", cp.get(0).topics.get(0));
        Assert.assertEquals("b", cp.get(1).topics.get(0));
    }
}