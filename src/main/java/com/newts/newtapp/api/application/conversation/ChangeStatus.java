package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.WrongAuthor;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.entities.Conversation;

/**
 * ConversationInteractor that changes the status of the conversation.
 * RequestModel must provide the conversation id
 */
public class ChangeStatus extends ConversationInteractor<Void, Exception> {

    /**
     * Initialize a new ChangeStatus interactor with given ConversationRepository.
     * @param repository ConversationRepository to access conversation data by
     */
    public ChangeStatus(ConversationRepository repository) { super(repository); }

    /**
     * Changes the status of the conversation with the id in the request
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws ConversationNotFound, WrongAuthor {
        int userId = (int) request.get(RequestField.USER_ID);
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);

        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        //Check if the conversation is created by the given userID
        if (conversation.getAuthorId() != userId)  {
            throw new WrongAuthor();
        }

        // Change the status of the conversation
        conversation.toggleIsOpen();
        conversationRepository.save(conversation);
        return null;
    }
}
