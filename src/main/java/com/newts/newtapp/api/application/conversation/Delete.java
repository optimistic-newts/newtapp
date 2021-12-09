package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;


public class Delete extends ConversationInteractor<Void,Exception> {

    /**
     * Initialize a new Delete interactor with given UserRepository.
     * @param repository        ConversationRepository to access conversation data by
     * @param userRepository    UserRepository to access user data by
     */
    public Delete(ConversationRepository repository, UserRepository userRepository) { super(repository,
            userRepository); }

    /**
     * Accepts a Delete conversation Request
     * @param request   a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, ConversationNotFound, WrongAuthor {
        int conversationID = (int) request.get(RequestField.CONVERSATION_ID);

        Conversation conversation = conversationRepository.findById(conversationID).orElseThrow(ConversationNotFound::new);

        //Check if the conversation is created by the given userID
        int userID = (int) request.get(RequestField.USER_ID);
        if (conversation.getAuthorId() != userID)  {
            throw new WrongAuthor();
        }

        // Loops through list of users of the conversation and removes conversation from their conversation list
        for (int userId : conversation.getUsers()){
            User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
            user.removeConversation(conversation);
            userRepository.save(user);

        }

        conversationRepository.delete(conversation);
        return null;
    }
}