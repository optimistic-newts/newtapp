package com.newts.newtapp.entities;

import org.junit.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * A class that the test the Message class
 */
public class MessageTest {
    Message m;

    @Before
    public void setUp() {
        m = new Message(1, "Luke, I am your father", 0, 0);
    }

    @Test(timeout = 50)
    public void getBodyTest() {
        assertEquals("Luke, I am your father", m.getBody());
    }

    @Test(timeout = 50)
    public void getAuthorTest() {
        assertEquals(0, m.getAuthor());
    }

    @Test(timeout = 50)
    public void getIdTest() { assertEquals(1, m.getId());}

    @Test(timeout = 50)
    public void getWrittenAtTest() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String testDate = LocalDateTime.now().format(date);
        assertEquals(testDate, m.getWrittenAt());
    }

    @Test(timeout = 50)
    public void getLastUpdatedAtTest() {
        m.setBody("May the force be with you");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String testDate = LocalDateTime.now().format(date);
        assertEquals(testDate, m.getLastUpdatedAt());
        assertEquals("May the force be with you", m.getBody());
    }
}
