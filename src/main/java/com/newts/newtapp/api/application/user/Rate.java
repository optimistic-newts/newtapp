package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;

public class Rate extends UserInteractor<Void, Exception>{

    /**
     * Creates Rate UserInteractor which allows user to rate another user
     */
    public Rate(UserRepository userRepository){super(userRepository);}

    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, UserAlreadyRated, InvalidRating {
        String username = (String) request.get(RequestField.USERNAME);
        String ratedUsername = (String) request.get(RequestField.USERNAME_TWO);
        int rating = (int) request.get(RequestField.RATING);

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
        User ratedUser = userRepository.findByUsername(ratedUsername).orElseThrow(UserNotFound::new);

        if (user.getRatedUsers().contains(ratedUser.getId())){
            throw new UserAlreadyRated();
        }

        if (0 > rating || rating > 5){
            throw new InvalidRating();
        }

        user.addRatedUser(ratedUser.getId());
        ratedUser.addRating(rating);

        userRepository.save(user);
        userRepository.save(ratedUser);
        return null;
    }
}
