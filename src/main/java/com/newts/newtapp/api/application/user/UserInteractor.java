package com.newts.newtapp.api.application.user;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.InputBoundary;

/**
 * An abstract UserInteractor object. Generally to be extended as a specific User use case.
 * Requires simply that an implementing class stores a User object and handles requests.
 */
public abstract class UserInteractor<ReturnType, ExceptionType extends Exception>
        implements InputBoundary<ReturnType, ExceptionType> {
    protected final UserRepository userRepository;
    protected ConversationRepository conversationRepository;

    /**
     * Initialize a new UserInteractor with given repository.
     * @param userRepository            UserRepository for User data access
     */
    public UserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Initialize a new UserInteractor with given repositories.
     * @param userRepository            UserRepository for User data access
     * @param conversationRepository    ConversationRepository for Conversation data access
     */
    public UserInteractor(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }
}
