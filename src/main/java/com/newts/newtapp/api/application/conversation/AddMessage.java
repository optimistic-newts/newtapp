package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.errors.UserNotFoundInConversation;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.EmptyMessage;
import com.newts.newtapp.entities.*;


public class AddMessage extends ConversationInteractor<Void, Exception> {

    /**
     * Initialize a new AddMessage interactor with supplied message, conversation and user repositories
     * @param messageRepository MessageRepository containing message data
     * @param conversationRepository ConversationRepository containing conversation data
     */
    public AddMessage(ConversationRepository conversationRepository,
                      MessageRepository messageRepository){
        super(conversationRepository, messageRepository);
    }

    /**
     * Accepts an AddMessage request.
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws ConversationNotFound, EmptyMessage, UserNotFoundInConversation {
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        int userId = (int) request.get(RequestField.USER_ID);

        // Fetching conversation that the message is being added to
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        // Check if the user is in the conversation.
        if(!(conversation.getUsers().contains(userId))) {
            throw new UserNotFoundInConversation();
        }

        String messageBody = ((String) request.get(RequestField.MESSAGE_BODY));

        // Checks if message is empty. If not add to conversation
        if(messageBody.isEmpty()){
            throw new EmptyMessage();
        }
        else{
            // Write time and Update time are handled within message constructor
            Message message = new Message(0, messageBody, userId, conversationId);
            Message newMessage = messageRepository.save(message);
            conversation.addMessage(newMessage.getId());
            conversationRepository.save(conversation);
        }
        return null;
    }
}