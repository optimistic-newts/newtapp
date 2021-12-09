package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.application.user.UserInteractor;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;

import java.util.ArrayList;

public class GetConversationsByUsername extends UserInteractor<ArrayList<ConversationProfile>, Exception> {
    /**
     * Create a GetConversationProfile interactor with supplied repositories
     * @param conversationRepository ConversationRepository containing Conversation data
     * @param userRepository         UserRepository containing User data
     */
    public GetConversationsByUsername(ConversationRepository conversationRepository, UserRepository userRepository){
        super(userRepository, conversationRepository);
    }


    /**
     * Accepts a request.
     * @param request               a request stored as a RequestModel
     * @throws ConversationNotFound conversation  not found with given id
     * @throws UserNotFound         user  not found with given id
     */
    @Override
    public ArrayList<ConversationProfile> request(RequestModel request) throws ConversationNotFound, UserNotFound {
        // Fetch the user, so we can access the user's conversation list
        String username = (String) request.get(RequestField.USERNAME);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);

        // Initialize the ArrayList to hold the conversation profiles
        ArrayList<ConversationProfile> conversationProfiles = new ArrayList<>();

        for (int cid : user.getConversations()) {
            Conversation conversation = conversationRepository.findById(cid).orElseThrow(ConversationNotFound::new);
            conversationProfiles.add(new ConversationProfile(conversation));
        }

        return conversationProfiles;
    }
}

