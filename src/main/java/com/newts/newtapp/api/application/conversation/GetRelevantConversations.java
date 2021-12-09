package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.application.user.UserInteractor;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.sorters.InterestSorter;
import com.newts.newtapp.api.application.ConversationQueue;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;

import java.util.ArrayList;

public class GetRelevantConversations extends UserInteractor<ArrayList<ConversationProfile>,UserNotFound> {

    /**
     * Initialize a new Create interactor with given UserRepository.
     * @param repository    UserRepository to access user data by
     */
    public GetRelevantConversations(UserRepository repository, ConversationRepository conversationRepository) {
        super(repository, conversationRepository);
    }

    /**
     *
     * @param request   Accepts a GetRelevantConversations Request
     * @return An array of Conversations
     * @throws UserNotFound if the user can not be found
     */
    @Override
    public ArrayList<ConversationProfile> request(RequestModel request) throws UserNotFound {
        String username = (String) request.get(RequestField.USERNAME);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);

        InterestSorter sorter = new InterestSorter();

        ConversationQueue conversationQueue = new ConversationQueue(sorter, user.getLocation(),
                user.getInterests());

        conversationQueue.addAll(conversationRepository.findAll());

        ArrayList<ConversationProfile> filteredConversations = new ArrayList<>();

        // Removing any conversations authored by users on the user's blocked list
        // and removing any conversations that the user is already in
        for(Conversation c:conversationQueue.toArray()){
            if(!user.getBlockedUsers().contains(c.getAuthorId()) && !user.getConversations().contains(c.getId())){
                ConversationProfile cp = new ConversationProfile(c);
                filteredConversations.add(cp);
            }
        }
        return filteredConversations;
    }
}
