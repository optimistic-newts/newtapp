package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.errors.InvalidConversationSize;
import com.newts.newtapp.api.errors.InvalidMinRating;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;

import java.util.ArrayList;

/**
 * ConversationInteractor that creates a new conversation.
 * RequestModel must provide details for a new conversation
 */
public class Create extends ConversationInteractor<Integer, Exception> {

    /**
     * Initialize a new Create interactor with given ConversationRepository.
     * @param repository        ConversationRepository to access Conversation data by
     * @param userRepository    UserRepository to access User data by
     */
    public Create(ConversationRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }

    /**
     * Accepts a Create request and creates a conversation based on the request
     * @param request   a request stored as a RequestModel
     */
    @Override
    public Integer request(RequestModel request) throws InvalidConversationSize, InvalidMinRating, UserNotFound {
        String title = (String) request.get(RequestField.TITLE);
        // Cast Topics to Arraylist of String
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
        if (minRating < 0 | minRating > 5) {
            throw new InvalidMinRating();
        }
        int maxSize = (int) request.get(RequestField.MAX_SIZE);
        if (maxSize < 1) {
            throw new InvalidConversationSize();
        }
        int creatorId = (int) request.get(RequestField.USER_ID);

        Conversation conversation = new Conversation(0, title, topicsList, location,
                locationRadius, minRating, maxSize, creatorId);

        // We need to use the saved conversation to connect it to a user. Else, we don't know what the id is!
        Conversation saved = conversationRepository.save(conversation);

        User user = userRepository.findById(creatorId).orElseThrow(UserNotFound::new);
        user.addConversation(saved);
        userRepository.save(user);
        return saved.getId();
    }
}
