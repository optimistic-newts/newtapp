package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.ConversationData;
import com.newts.newtapp.api.application.datatransfer.MessageData;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.application.user.GetProfileById;
import com.newts.newtapp.api.controllers.assemblers.MessageDataModelAssembler;
import com.newts.newtapp.api.controllers.assemblers.UserProfileModelAssembler;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.Conversation;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;

public class GetConversationData extends ConversationInteractor<ConversationData, Exception> {

    private final UserProfileModelAssembler profileAssembler;
    private final MessageDataModelAssembler messageAssembler;

    /**
     * Creates a GetConversationData Interactor with given User and Conversation Repositories
     * @param userRepository UserRepository containing User data
     * @param conversationRepository ConversationRepository containing Conversation data
     */
    public GetConversationData(ConversationRepository conversationRepository, MessageRepository messageRepository,
                               UserRepository userRepository){
        super(conversationRepository, messageRepository, userRepository);
        profileAssembler = new UserProfileModelAssembler();
        messageAssembler = new MessageDataModelAssembler();
    }

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public ConversationData request(RequestModel request) throws UserNotFound, ConversationNotFound, MessageNotFound, MessageNotFoundInConversation {
        RequestModel requestModel = new RequestModel();
        GetProfileById getProfileById = new GetProfileById(userRepository);
        GetMessageData getMessageData = new GetMessageData(conversationRepository, messageRepository);

        int conversationId = (int) request.get(RequestField.CONVERSATION_ID);
        ArrayList<EntityModel<UserProfile>> userProfiles = new ArrayList<>();
        ArrayList<EntityModel<MessageData>> messageData = new ArrayList<>();
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFound::new);

        // Creating UserProfile objects from list of userIds in Conversation and adding them to an ArrayList of
        // EntityModel of userProfiles and their links
        for(int userId : conversation.getUsers()){
            requestModel.fill(RequestField.USER_ID, userId);
            userProfiles.add(profileAssembler.toModel(getProfileById.request(requestModel)));
        }

        // Creating MessageData objects from list of messageIds and adding them to an ArrayList of
        // EntityModel of messageData and their links
        requestModel.fill(RequestField.CONVERSATION_ID, conversationId);
        for(int m : conversation.getMessages()){
            requestModel.fill(RequestField.MESSAGE_ID, m);
            messageData.add(messageAssembler.toModel(getMessageData.request(requestModel)));
        }
        return new ConversationData(messageData, userProfiles, conversation);
    }
}
