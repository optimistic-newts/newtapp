package com.newts.newtapp.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {
    User u;

    /**
     * Setting up a test User
     */
    @Before
    public void setUp() {
        ArrayList<String> interests = new ArrayList<>();
        interests.add("Sports");
        interests.add("Music");
        interests.add("Fitness");
        u = new User(1, "testUser", "password123", interests);
    }

    /**
     * Tests the getID method
     */
    @Test
    public void getIdTest() {
        assertEquals(u.getId(), 1);
    }

    /**
     * Tests getUsername method
     */
    @Test
    public void getUsernameTest() {assertEquals(u.getUsername(), "testUser");}

    /**
     * Tests getSetUsername method
     */
    @Test
    public void setUsernameTest() {
        u.setUsername("OptimisticNewt27");
        assertEquals(u.getUsername(), "OptimisticNewt27");
    }

    /**
     * Tests getPassword method
     */
    @Test
    public void getPasswordTest() {assertEquals(u.getPassword(), "password123");}

    @Test
    public void setPasswordTest() {
        u.setPassword("p");
    }

    /**
     * Tests getLocation
     */
    @Test
    public void getLocationTest() {
        u.setLocation("Toronto");
        assertEquals("Toronto", u.getLocation());
    }

    /**
     * Tests getInterests method
     */
    @Test
    public void getInterestsTest() {
        assertEquals(u.getInterests().get(0), "Sports");
        assertEquals(u.getInterests().get(1), "Music");
        assertEquals(u.getInterests().get(2), "Fitness");
    }

    /**
     * Tests addInterests method
     */
    @Test
    public void addInterestsTest() {
        u.addInterests("Stocks");
        assertEquals(u.getInterests().get(3), "Stocks");
    }

    /**
     * Tests removeInterest method
     */
    @Test
    public void removeInterestTest() {
        u.addInterests("Stocks");
        assertEquals(u.getInterests().get(2), "Fitness");
    }
}
