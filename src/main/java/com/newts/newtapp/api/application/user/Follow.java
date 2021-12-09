package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.entities.User;
import com.newts.newtapp.api.gateways.UserRepository;

/**
 * UserInteractor that adds a follow relationship.
 * RequestModel must provide two User ids, the first is the user who wants to follow the other.
 */
public class Follow extends UserInteractor<Void, Exception> {

    /**
     * Initialize a new Follow interactor with given UserRepository.
     * @param repository    UserRepository to access user data by
     */
    public Follow(UserRepository repository) {
        super(repository);
    }

    /**
     * Accepts a Follow request.
     * @param request   a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, SameUser, AlreadyFollowingUser, UserBlocked, BlockedByUser {
        // Duplicated code warning - duplicate is in Unfollow. We are leaving this as duplicated for now
        // as we believe these methods may have different reasons to change and as such should not be combined
        // into one follow/unfollow interactor.
        String usernameFollowing = (String) request.get(RequestField.USERNAME);
        String usernameToFollow = (String) request.get(RequestField.USERNAME_TWO);

        // look up the users, if they don't exist throw UserNotFound
        User user = userRepository.findByUsername(usernameFollowing).orElseThrow(UserNotFound::new);
        User other = userRepository.findByUsername(usernameToFollow).orElseThrow(UserNotFound::new);

        if(user.getBlockedUsers().contains(other.getId())){
            throw new UserBlocked();
        } else if(other.getBlockedUsers().contains(user.getId())){
            throw new BlockedByUser();
        }

        // Check if users are the same
        if (user.getId() == other.getId()) {
            throw new SameUser();
        }

        // Check if user follows other already
        if (user.getFollowing().contains(other.getId())) {
            throw new AlreadyFollowingUser();
        }

        // Add userTwo to following of user
        user.addFollowing(other);
        // Add user to followers of userTwo
        other.addFollower(user);

        // Save both users
        userRepository.save(user);
        userRepository.save(other);
        return null;
    }
}