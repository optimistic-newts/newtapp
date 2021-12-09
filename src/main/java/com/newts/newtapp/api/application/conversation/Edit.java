package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.InvalidConversationSize;
import com.newts.newtapp.api.errors.InvalidMinRating;
import com.newts.newtapp.api.errors.WrongAuthor;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.entities.Conversation;

import java.util.ArrayList;

public class Edit extends ConversationInteractor<Void,Exception>{

    /**
     * Initialize a new Edit interactor with given ConversationRepository.
     * @param conversationRepository ConversationRepository to access Conversation data by
     */
    public Edit(ConversationRepository conversationRepository){super(conversationRepository);}

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws ConversationNotFound, WrongAuthor, InvalidMinRating, InvalidConversationSize {
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        int userId = (int) request.get(RequestField.USER_ID);

        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        //Check if the conversation is created by the given userId
        if (conversation.getAuthorId() != userId)  {
            throw new WrongAuthor();
        }

        String title = (String) request.get(RequestField.TITLE);
        // Cast TOPICS to ArrayList of String
        ArrayList<?> topics = (ArrayList<?>) request.get(RequestField.TOPICS);
        ArrayList<String> topicsList = new ArrayList<>();
        for (Object o : topics) {
            if (o instanceof String) {
                topicsList.add((String) o);
            }
        }
        String location = (String) request.get(RequestField.LOCATION);
        int locationRadius = (int) request.get(RequestField.LOCATION_RADIUS);
        int minRating = (int) request.get(RequestField.MIN_RATING);

        // Applied the same conditions of minRating and maxSize in Create conversation.
        if (minRating < 0 | minRating > 5) {
            throw new InvalidMinRating();
        }
        int maxSize = (int) request.get(RequestField.MAX_SIZE);
        if (maxSize < 1) {
            throw new InvalidConversationSize();
        }

        // Sets updated conversation information
        conversation.setTitle(title);
        conversation.setLocation(location);
        conversation.setLocationRadius(locationRadius);
        conversation.setMinRating(minRating);
        conversation.setTopics(topicsList);
        conversation.setMaxSize(maxSize);

        conversationRepository.save(conversation);
        return null;
    }
}
