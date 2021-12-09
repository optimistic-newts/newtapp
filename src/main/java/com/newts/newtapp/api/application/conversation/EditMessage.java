package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.Message;

public class EditMessage extends ConversationInteractor<Void, Exception> {

    /**
     * Initialize a new EditMessage interactor with supplied user, conversation and message repositories
     * @param conversationRepository ConversationRepository which contains conversation data
     * @param messageRepository MessageRepository which contains message data
     */
    public EditMessage(ConversationRepository conversationRepository, MessageRepository messageRepository){
        super(conversationRepository, messageRepository);
    }

    /**
     * Accepts a EditMessage request.
     * Edits a Message.
     * @param request a request stored as a RequestModel
     */
    public Void request(RequestModel request) throws MessageNotFound, WrongAuthor, EmptyMessage, ConversationNotFound,
            UserNotFoundInConversation, MessageNotFoundInConversation {
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        int messageId = (int) request.get(RequestField.MESSAGE_ID);
        int userId = (int) request.get(RequestField.USER_ID);

        // Fetching conversation that the message is being added to
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        // Check if the user and the message is in the conversation.
        if(!(conversation.getUsers().contains(userId))) {
            throw new UserNotFoundInConversation();
        }
        if(!(conversation.getMessages().contains(messageId))) {
            throw new MessageNotFoundInConversation();
        }

        //Fetching the message to edit from conversation
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFound::new);

        //Check if the message is written by the given userID
        if (message.getAuthor() != userId)  {
            throw new WrongAuthor();
        }

        String messageBody = ((String) request.get(RequestField.MESSAGE_BODY));

        ///Checks if message is empty. If not replace the old messageBody with the new messageBody
        if(messageBody.isEmpty()){
            throw new EmptyMessage();
        }
        else{
            message.setBody(messageBody);
            messageRepository.save(message);
            return null;
        }
    }
}

