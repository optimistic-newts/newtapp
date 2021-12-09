package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.SameUser;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.entities.User;

public class Unfollow extends UserInteractor<Void, Exception>{

    /**
     * UserInteractor which removes follower/following relationship between users
     * @param userRepository UserRepository which stores user data
     */
    public Unfollow(UserRepository userRepository){super(userRepository);}

    /**
     * Accepts a request.
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, SameUser {
        String usernameFollowing = (String) request.get(RequestField.USERNAME);
        String usernameToFollow = (String) request.get(RequestField.USERNAME_TWO);

        // look up the users, if they don't exist throw UserNotFound
        User user = userRepository.findByUsername(usernameFollowing).orElseThrow(UserNotFound::new);
        User other = userRepository.findByUsername(usernameToFollow).orElseThrow(UserNotFound::new);

        // Check if users are the same
        if (user.getId() == other.getId()) {
            throw new SameUser();
        }

        // Check if user is following other
        if (!user.getFollowing().contains(other.getId())){
            throw new UserNotFound();
        }

        // Check if other has user as a follower
        if(!other.getFollowers().contains(user.getId())){
            throw new UserNotFound();
        }

        user.removeFollowing(other);
        other.removeFollower(user);

        userRepository.save(user);
        userRepository.save(other);
        return null;
    }
}
