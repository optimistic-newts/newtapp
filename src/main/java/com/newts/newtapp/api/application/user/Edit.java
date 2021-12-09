package com.newts.newtapp.api.application.user;
import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.entities.User;
import com.newts.newtapp.api.gateways.UserRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * UserInteractor that edits user information.
 * RequestModel must provide a User ids, the first is the user who wants to follow the other.
 */
public class Edit extends UserInteractor<Void, Exception> {

    /**
     * Initialize a new Edit interactor with given UserRepository.
     * @param repository    UserRepository to access user data by
     */
    public Edit(UserRepository repository) {
        super(repository);
    }

    /**
     * Accepts a Edit request.
     * @param request   a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, UserAlreadyExists, InvalidUsername {
        String oldUsername = (String) request.get(RequestField.USERNAME);

        // look up the user, if it doesn't exist throw UserNotFound
        User user = userRepository.findByUsername(oldUsername).orElseThrow(UserNotFound::new);

        ArrayList<?> interests = (ArrayList<?>) request.get(RequestField.INTERESTS);
        String username = (String) request.get(RequestField.NEW_USERNAME);
        String location = (String) request.get(RequestField.LOCATION);

        ArrayList<String> interestList = new ArrayList<>();
        for (Object o : interests) {
            if (o instanceof String) {
                interestList.add((String) o);
            }
        }

        if (username.contains(" ")) {
            throw new InvalidUsername();
        }
        if (!Objects.equals(user.getUsername(), username) && userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExists();
        }

        user.setUsername(username);
        user.setLocation(location);
        user.setInterests(interestList);

        userRepository.save(user);
        return null;
    }
}