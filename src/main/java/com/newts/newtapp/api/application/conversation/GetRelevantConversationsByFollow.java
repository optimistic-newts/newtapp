package com.newts.newtapp.api.application.conversation;

import com.newts.newtapp.api.application.ConversationQueue;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.application.sorters.InterestSorter;
import com.newts.newtapp.api.application.user.UserInteractor;
import com.newts.newtapp.api.errors.ConversationNotFound;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.Conversation;
import com.newts.newtapp.entities.User;

import java.util.ArrayList;

public class GetRelevantConversationsByFollow extends UserInteractor<ArrayList<ConversationProfile>, UserNotFound> {
    /**
     * Initialize a new Create interactor with given UserRepository.
     * @param repository    UserRepository to access user data by
     */
    public GetRelevantConversationsByFollow(UserRepository repository, ConversationRepository conversationRepository) {
        super(repository, conversationRepository);
    }

    /**
     * Completes a GetRelevantConversations request.
     * Looks for relevant conversations by sorting through conversations of a user's following.
     * @param request   a request stored as a RequestModel
     * @return ArrayList of Conversations containing conversations a user's following, as sorted by InterestSorter.
     * @throws UserNotFound if the user in the request can not be found.
     */
    @Override
    public ArrayList<ConversationProfile> request(RequestModel request) throws UserNotFound, ConversationNotFound {
        String username = (String) request.get(RequestField.USERNAME);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);

        InterestSorter sorter = new InterestSorter();

        ConversationQueue conversationQueue = new ConversationQueue(sorter, user.getLocation(),
                user.getInterests());

        ArrayList<Conversation> conversations = new ArrayList<>();

        //rather than loop through all conversations, we just have to go through our following's conversations.
        for (int i : user.getFollowing()) {
            User them = userRepository.findById(i).orElseThrow(UserNotFound::new);
            for (int j : them.getConversations()) {
                Conversation conversation = conversationRepository.findById(j).orElseThrow(ConversationNotFound::new);
                if (conversation.getIsOpen()) {
                    conversations.add(conversation);
                }
            }
        }

        conversationQueue.addAll(conversations);

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

