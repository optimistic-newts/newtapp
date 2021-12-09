package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.entities.Conversation;

public class GetConversationProfile extends ConversationInteractor<ConversationProfile, Exception> {

    /**
     * Create a GetConversationProfile interactor with supplied repositories
     * @param conversationRepository ConversationRepository containing Conversation data
     */
    public GetConversationProfile(ConversationRepository conversationRepository){
        super(conversationRepository);
    }


    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public ConversationProfile request(RequestModel request) throws ConversationNotFound {
        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);
        return new ConversationProfile(conversation);
    }
}
