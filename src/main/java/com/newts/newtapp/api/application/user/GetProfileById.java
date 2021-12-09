package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.entities.User;

/**
 * Get a UserProfile for a given User.
 */
public class GetProfileById extends UserInteractor<UserProfile, UserNotFound>{

    /**
     * Construct a new GetProfileById interactor with given UserRepository.
     * @param repository    UserRepository with user data
     */
    public GetProfileById(UserRepository repository) {
        super(repository);
    }

    /**
     * Handles a GetProfileById request.
     * @param request           a request stored as a RequestModel containing user id
     * @return                  a new UserProfile for the given User
     * @throws UserNotFound     if user does not exist
     */
    @Override
    public UserProfile request(RequestModel request) throws UserNotFound {
        int id = (int) request.get(RequestField.USER_ID);
        User user = userRepository.findById(id).orElseThrow(UserNotFound::new);
        return new UserProfile(user);
    }
}
