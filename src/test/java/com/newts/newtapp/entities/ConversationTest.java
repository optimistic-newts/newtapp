package com.newts.newtapp.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A class for testing the Conversation class.
 */
public class ConversationTest {
    Conversation c;

    @Before
    public void setUp() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("");
        c = new Conversation(0, "Sample Conversation", topics, "",
                0, 0, 1, 0);
    }

    @Test(timeout = 50)
    public void TestGetId() { assertEquals( 0, c.getId()); }

    @Test(timeout = 50)
    public void TestGetTitle() {
        assertEquals("Sample Conversation", c.getTitle());
    }

    @Test(timeout = 50)
    public void TestGetTopic() { assertEquals("", c.getTopics().get(0)); }

    @Test(timeout = 50)
    public void TestGetLocation() {
        assertEquals("", c.getLocation());
    }

    @Test(timeout = 50)
    public void TestGetLocationRadius() { assertEquals(0, c.getLocationRadius());}

    @Test(timeout = 50)
    public void TestGetMinRating() {
        assertEquals(0, c.getMinRating());
    }

    @Test(timeout = 50)
    public void TestGetMaxUsers() {
        assertEquals(1, c.getMaxSize());
    }

    @Test(timeout = 50)
    public void TestGetStatus() {
        assertTrue(c.getIsOpen());
    }

    @Test(timeout = 50)
    public void TestGetMessages() {
        assertEquals(new ArrayList<Integer>(), c.getMessages());
    }

    @Test(timeout = 50)
    public void TestGetUsers() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, c.getUsers());
    }

    @Test(timeout = 50)
    public void TestSetTitle() {
        c.setTitle("New Title");
        assertEquals("New Title", c.getTitle());
    }
    @Test(timeout = 50)
    public void TestSetLocation() {
        c.setLocation("New Location");
        assertEquals("New Location", c.getLocation());
    }
    @Test(timeout = 50)
    public void TestSetMinRating() {
        c.setMinRating(5);
        assertEquals(5, c.getMinRating());
    }

    @Test(timeout = 50)
    public void TestSetMaxUsers() {
        c.setMaxSize(2);
        assertEquals(2, c.getMaxSize());
    }

    @Test(timeout = 50)
    public void TestToggleStatus() {
        c.toggleIsOpen();
        assertFalse(c.getIsOpen());
    }

    @Test(timeout = 50)
    public void TestAddMessage() {
        User u = new User(0, "Joe", "pass", new ArrayList<>());
        Message m = new Message(1, "Hello!", 0, 0);
        c.addMessage(m.getId());
        assertSame(m.getId(), c.getMessages().get(c.getMessages().size() - 1));
    }

    @Test(timeout = 50)
    public void TestAddUser() {
        c.setMaxSize(1);
        ArrayList<String> interests = new ArrayList<>(List.of(new String[]{"Golf", "Painting"}));
        User u = new User(0, "testuser", "password", interests);
        c.addUser(u.getId());
        assertSame(u.getId(), c.getUsers().get(c.getUsers().size() - 1));
    }

    @Test(timeout = 50)
    public void TestAddUserAtMax() {
        ArrayList<String> interests = new ArrayList<>(List.of(new String[]{"Golf", "Painting"}));
        User u = new User(0, "testuser", "password", interests);
        assertFalse(c.addUser(u.getId()));
    }

    @Test(timeout = 50)
    public void TestAddUserBelowMax() {
        c.setMaxSize(2);
        ArrayList<String> interests = new ArrayList<>(List.of(new String[]{"Golf", "Painting"}));
        User u = new User(1, "testuser", "password", interests);
        assertTrue(c.addUser(u.getId()));
    }

    @Test(timeout = 50)
    public void TestRemoveUser() {
        User u = new User();
        c.addUser(u.getId());
        assertTrue(c.removeUser(u.getId()));
    }

    @Test(timeout = 50)
    public void TestRemoveUserNotInConversation() {
        User u = new User();
        u.setId(1);
        assertFalse(c.removeUser(u.getId()));
    }
}
