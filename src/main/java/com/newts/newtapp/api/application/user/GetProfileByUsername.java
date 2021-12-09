package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.entities.User;

/**
 * Get a UserProfile for a given username.
 */
public class GetProfileByUsername extends UserInteractor<UserProfile, UserNotFound> {

    /**
     * Construct a new GetProfileByUsername interactor with given UserRepository.
     *
     * @param repository UserRepository with user data
     */
    public GetProfileByUsername(UserRepository repository) {
        super(repository);
    }

    /**
     * Handles a GetProfileByUsername request.
     *
     * @param request a request stored as a RequestModel containing username
     * @return a new UserProfile for the given User
     * @throws UserNotFound if user does not exist
     */
    @Override
    public UserProfile request(RequestModel request) throws UserNotFound {
        String username = (String) request.get(RequestField.USERNAME);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
        return new UserProfile(user);
    }
}