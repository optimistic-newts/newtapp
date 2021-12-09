package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.MessageData;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.MessageNotFound;
import com.newts.newtapp.api.errors.MessageNotFoundInConversation;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.Message;

public class GetMessageData extends ConversationInteractor<MessageData, Exception>{

    /**
     * Creates new GetMessageData Interactor with supplied Conversation and Message Repositories
     * @param conversationRepository ConversationRepository containing conversation data
     * @param messageRepository MessageRepository containing message data
     */
    public GetMessageData(ConversationRepository conversationRepository, MessageRepository messageRepository){
        super(conversationRepository, messageRepository);}

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     * @throws MessageNotFound               if the message with given id does not exist
     * @throws MessageNotFoundInConversation if message does not exist in the conversation
     * @throws ConversationNotFound          if the conversation with given id does not exist
     */
    @Override
    public MessageData request(RequestModel request) throws MessageNotFound, ConversationNotFound,
            MessageNotFoundInConversation {
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        // Fetching the conversation from which we delete message
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        int messageId = (int) request.get(RequestField.MESSAGE_ID);

        //Check if the message is in conversation
        if(!(conversation.getMessages().contains(messageId))) {
            throw new MessageNotFoundInConversation();
        }

        Message message =  messageRepository.findById(messageId).orElseThrow(MessageNotFound::new);
        return new MessageData(message);
    }
}
