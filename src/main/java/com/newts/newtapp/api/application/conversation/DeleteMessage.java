package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.Message;

public class DeleteMessage extends ConversationInteractor<Void, Exception> {

    /**
     * Initialize a new DeleteMessage interactor with supplied conversation and message repositories
     * @param conversationRepository ConversationRepository which contains conversation data
     * @param messageRepository MessageRepository which contains message data
     */
    public DeleteMessage(ConversationRepository conversationRepository,
                         MessageRepository messageRepository){
        super(conversationRepository, messageRepository);
    }

    /**
     * Accepts a DeleteMessage request.
     * Remove a Message from a Conversation.
     * @param request a request stored as a RequestModel
     */
    public Void request(RequestModel request) throws ConversationNotFound, MessageNotFound, WrongAuthor,
            MessageNotFoundInConversation, UserNotFoundInConversation {
        int conversationId = (int)request.get(RequestField.CONVERSATION_ID);
        int messageId = (int) request.get(RequestField.MESSAGE_ID);
        int userId = (int) request.get(RequestField.USER_ID);

        // Fetching the conversation from which we delete message
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        // Check if the user and the message is in the conversation.
        if(!(conversation.getUsers().contains(userId))) {
            throw new UserNotFoundInConversation();
        }
        if(!(conversation.getMessages().contains(messageId))) {
            throw new MessageNotFoundInConversation();
        }

        //Fetching the message to remove from conversation
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFound::new);

        //Check if the message is written by the given userID
        if (message.getAuthor() != userId)  {
            throw new WrongAuthor();
        }

        // Check that the message is in the conversation and delete it if so.
        if (conversation.getMessages().contains(messageId)) {
            conversation.deleteMessage(message.getId());
            messageRepository.delete(message);
            conversationRepository.save(conversation);
            return null;
        } else {
            throw new MessageNotFoundInConversation();
        }
    }
}
