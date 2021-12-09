package com.newts.newtapp.api.application.user;

import com.newts.newtapp.api.application.boundary.RequestField;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.errors.IncorrectPassword;
import com.newts.newtapp.api.errors.InvalidPassword;
import com.newts.newtapp.api.errors.UserNotFound;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class ChangePassword extends UserInteractor<Void, Exception>{


    /**
     * Initialize a new ChangePassword interactor with a given UserInteractor
     * @param userRepository UserRepository containing user data
     */
    public ChangePassword(UserRepository userRepository){super(userRepository);}


    /**
     * Accepts a request.
     *
     * @param request a request stored as a RequestModel
     */
    @Override
    public Void request(RequestModel request) throws UserNotFound, InvalidPassword, IncorrectPassword {
        String username = (String) request.get(RequestField.USERNAME);
        String currentPassword = (String) request.get(RequestField.PASSWORD);
        String newPassword = (String) request.get(RequestField.NEW_PASSWORD);
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFound::new);

        // Checks user password
        if (!BCrypt.checkpw(currentPassword, user.getPassword())){
            throw new IncorrectPassword();
        }

        // Checks that new password is valid
        if(newPassword.length() < 6){throw new InvalidPassword();}
        else{
            // Hashing and setting user password
            String hashed_pw = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(hashed_pw);
            userRepository.save(user);
        }

        return null;
    }
}
