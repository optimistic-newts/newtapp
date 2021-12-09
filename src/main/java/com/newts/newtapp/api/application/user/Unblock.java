package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.UserNotBlocked;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;

public class Unblock extends UserInteractor<Void, Exception>{
    /**
     * Initialize a new Unblock UserInteractor with given repository.
     *
     * @param userRepository UserRepository for User data access
     */
    public Unblock(UserRepository userRepository) {
        super(userRepository);
    }

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, UserNotBlocked {
        String userId = (String) request.get(RequestField.USERNAME);
        String otherId = (String) request.get(RequestField.USERNAME_TWO);

        User user = userRepository.findByUsername(userId).orElseThrow(UserNotFound::new);
        User other = userRepository.findByUsername(otherId).orElseThrow(UserNotFound::new);

        // Checking to see whether the user is actually blocked
        if(!user.getBlockedUsers().contains(other.getId())){
            throw new UserNotBlocked();
        }

        user.removeBlockedUser(other.getId());
        return null;
    }
}
