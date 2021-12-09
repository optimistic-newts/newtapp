package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.errors.UserNotFoundInConversation;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;

public class RemoveUser extends ConversationInteractor<Void, Exception> {

    /**
     * Initialize a new RemoveUser interactor with supplied conversation and user repositories
     * @param conversationRepository ConversationRepository which contains conversation data
     * @param userRepository UserRepository which contains user data
     */
    public RemoveUser(ConversationRepository conversationRepository,
                      UserRepository userRepository){
        super(conversationRepository, userRepository);
    }
    /**
     * Accepts a RemoveUser request.
     * Remove a User from a Conversation.
     * @param request a request stored as a RequestModel
     */
    public Void request(RequestModel request) throws UserNotFound, ConversationNotFound, UserNotFoundInConversation {
        int userId = (int)request.get(RequestField.USER_ID);
        int conversationId = (int)request.get(RequestField.CONVERSATION_ID);

        // Fetching the conversation from which we remove user
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        //Fetching the user to remove from conversation
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        // Check that the user is in the conversation
        if (conversation.getUsers().contains(userId)) {
            // Checks to see how many users are in conversation, if 1 delete conversation
            // else just remove user from conversation.
            if(conversation.getNumUsers() == 1){
                user.removeConversation(conversation);
                userRepository.save(user);
                conversationRepository.delete(conversation);
            } else{
                conversation.removeUser(user.getId());
                user.removeConversation(conversation);
                conversationRepository.save(conversation);
                userRepository.save(user);
            }
            return null;
        } else {
            throw new UserNotFoundInConversation();
        }
    }
}
