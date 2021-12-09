package com.newts.newtapp.api.application.datatransfer;

import com.newts.newtapp.entities.Conversation;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;

/**
 * A condensed view of data in a Conversation, for passing Conversation data visible to members of a conversation,
 * without revealing secure information.
 * This is just a data storage object.
 */
public class ConversationData {
    public final ArrayList<EntityModel<MessageData>> messageData;
    public final ArrayList<EntityModel<UserProfile>> userProfiles;

    public final int id;
    public final String title;
    public final ArrayList<String> topics;
    public final String location;
    public final int minRating;
    public final int maxSize;
    public final int currSize;
    public final Boolean isOpen;

    public ConversationData(ArrayList<EntityModel<MessageData>> messageData, ArrayList<EntityModel<UserProfile>> userProfiles, Conversation conversation) {
        this.id = conversation.getId();
        this.title = conversation.getTitle();
        this.topics = conversation.getTopics();
        this.location = conversation.getLocation();
        this.minRating = conversation.getMinRating();
        this.maxSize = conversation.getMaxSize();
        this.currSize = conversation.getNumUsers();
        this.isOpen = conversation.getIsOpen();
        this.messageData = messageData;
        this.userProfiles = userProfiles;
    }
}
