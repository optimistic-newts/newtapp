package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.InvalidConversationSize;
import com.newts.newtapp.api.errors.InvalidMinRating;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.TestConversationRepository;
import com.newts.newtapp.api.gateways.TestUserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class CreateTest {
    TestConversationRepository c;
    TestUserRepository u;
    Create create;

    @Before
    public void setUp() {
        c = new TestConversationRepository();
        u = new TestUserRepository();
        create = new Create(c, u);

        User testUser = new User();
        u.save(testUser);
    }

    @Test(timeout = 50)
    public void testCreate() throws InvalidMinRating, InvalidConversationSize, UserNotFound {
        RequestModel r = new RequestModel();
        r.fill(RequestField.TITLE, "");
        r.fill(RequestField.TOPICS, "");
        r.fill(RequestField.LOCATION, "");
        r.fill(RequestField.LOCATION_RADIUS, 0);
        r.fill(RequestField.MIN_RATING, 0);
        r.fill(RequestField.MAX_SIZE, 1);
        r.fill(RequestField.USER_ID, 1);
        r.fill(RequestField.TOPICS, new ArrayList<String>());
        create.request(r);

        assertTrue(c.findById(1).isPresent());
        Conversation conversation = c.findById(1).get();

        assertTrue(conversation.getIsOpen());
    }


}
