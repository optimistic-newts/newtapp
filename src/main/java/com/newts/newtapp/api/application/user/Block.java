package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.UserAlreadyBlocked;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;

public class Block extends UserInteractor<Void, Exception>{

    /**
     * Initializes a new Block interactor with a given UserRepository and ConversationRepository
     * @param userRepository UserRepository containing user data
     * @param conversationRepository ConversationRepository containing conversation data
     */
    public Block(UserRepository userRepository,
                 ConversationRepository conversationRepository){
        super(userRepository, conversationRepository);
    }
    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, UserAlreadyBlocked {
        String username = (String) request.get(RequestField.USERNAME);
        String blockedUsername = (String) request.get(RequestField.USERNAME_TWO);

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
        User blockedUser = userRepository.findByUsername(blockedUsername).orElseThrow(UserNotFound::new);
        if(user.getBlockedUsers().contains(blockedUser.getId())){
            throw new UserAlreadyBlocked();
        }

        user.addBlockedUser(blockedUser.getId());

        // Checks to see if user is being followed by blocked user, and makes the blocked user unfollow
        // user. Also, appropriately removes the blocked user from the user's following list.
        if(user.getFollowing().contains(blockedUser.getId())){
            blockedUser.removeFollower(user);
            user.removeFollowing(blockedUser);
        }

        // Checks to see if user is following the blocked user, and makes the user unfollow the blocked user.
        // Also, appropriately removes the user from the blockedUser's following list.
        if(user.getFollowers().contains(blockedUser.getId())){
            blockedUser.removeFollowing(user);
            user.removeFollower(blockedUser);
        }

        return null;
    }
}
