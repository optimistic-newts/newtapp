package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;

import java.util.ArrayList;

public class GetFollowers extends UserInteractor<ArrayList<UserProfile>, Exception> {

    /**
     * Creating GetFollowers UserInteractor which returns an ArrayList of the User's Follower ids
     * @param userRepository UserRepository for User data access
     */
    public GetFollowers(UserRepository userRepository){
        super(userRepository);
    }

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public ArrayList<UserProfile> request(RequestModel request) throws UserNotFound {
        String username = (String) request.get(RequestField.USERNAME);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
        ArrayList<UserProfile> userProfiles = new ArrayList<>();

        // Convert each user in follower list to UserProfile
        for(int userId:user.getFollowers()){
            User follower = userRepository.findById(userId).orElseThrow(UserNotFound::new);
            userProfiles.add(new UserProfile(follower));
        }
        return userProfiles;
    }
}
