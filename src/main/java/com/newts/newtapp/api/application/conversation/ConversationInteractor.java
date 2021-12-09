package com.newts.newtapp.api.application.conversation;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.MessageRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.application.boundary.InputBoundary;

/**
 * An abstract ConversationInteractor object. Generally to be extended as a specific Conversation use case.
 * Requires simply that an implementing class stores a Conversation object and handles requests.
 */
public abstract class ConversationInteractor<ReturnType, ExceptionType extends Exception>
        implements InputBoundary<ReturnType, ExceptionType>{
    // These create an error in code inspection because they are not used in this class, but they are created here and
    // marked as protected so that they can be accessed by subclasses.
    protected final ConversationRepository conversationRepository;
    protected MessageRepository messageRepository;
    protected UserRepository userRepository;

    /**
     * Initialize a new ConversationInteractor with all repositories.
     * @param conversationRepository ConversationRepository containing conversation data.
     * @param messageRepository MessageRepository containing message data.
     * @param userRepository User repository containing user data.
     */
    public ConversationInteractor(ConversationRepository conversationRepository,
                                  MessageRepository messageRepository,
                                  UserRepository userRepository){
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    /**
     * Initialize a new ConversationInteractor with conversation and user repositories.
     * @param conversationRepository ConversationRepository containing conversation data.
     * @param userRepository UserRepository containing user data.
     */
    public ConversationInteractor(ConversationRepository conversationRepository,
                                  UserRepository userRepository){
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Initialize a new ConversationInteractor with conversation and message repositories.
     * @param conversationRepository ConversationRepository containing conversation data.
     * @param messageRepository MessageRepository containing user data.
     */
    public ConversationInteractor(ConversationRepository conversationRepository,
                                  MessageRepository messageRepository){
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Initialize a new ConversationInteractor with conversation repository.
     * @param conversationRepository ConversationRepository containing conversation data.
     */
    public ConversationInteractor(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }
}
