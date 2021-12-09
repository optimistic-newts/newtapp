package com.newts.newtapp.api.application;
import com.newts.newtapp.api.application.boundary.RequestModel;
import com.newts.newtapp.api.application.conversation.GetRelevantConversations;
import com.newts.newtapp.api.application.datatransfer.ConversationProfile;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.gateways.ConversationRepository;
import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.api.errors.*;
import com.newts.newtapp.api.application.user.*;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * A facade for user interactors.
 */
@Configuration
public class UserManager {
    /**
     * The application's repositories to provide database access:
     */
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    /**
     * Initialize a new UserManager with given repositories.
     * @param userRepository    UserRepository to access User data
     * @param conversationRepository ConversationRepository to access Conversation data
     */
    public UserManager(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    /**
     * Returns a UserProfile given a User id in a RequestModel.
     * @param request           RequestModel containing User's id
     * @return                  UserProfile of corresponding User
     * @throws UserNotFound     if User id does not exist
     */
    public UserProfile getProfileById(RequestModel request) throws UserNotFound {
        GetProfileById getProfileById = new GetProfileById(userRepository);
        return getProfileById.request(request);
    }

    /**
     * Returns a UserProfile given a username in a RequestModel.
     * @param request           RequestModel containing username
     * @return                  UserProfile of corresponding User
     * @throws UserNotFound     if no user exists with username
     */
    public UserProfile getProfileByUsername(RequestModel request) throws UserNotFound {
        GetProfileByUsername getProfileByUsername = new GetProfileByUsername(userRepository);
        return getProfileByUsername.request(request);
    }

    /**
     * Creates a new user according to the given RequestModel and sets this UserManager's user accordingly.
     * @param request   RequestModel containing new User information.
     */
    public void create(RequestModel request) throws InvalidPassword, UserAlreadyExists, InvalidUsername {
        Create create = new Create(userRepository);
        create.request(request);
    }

    /**
     * Deletes a user according to the given RequestModel and sets this UserManager's user to null.
     * @param request   RequestModel containing delete User information.
     */
    public void delete(RequestModel request) throws UserNotFound, IncorrectPassword, ConversationNotFound {
        Delete delete = new Delete(userRepository);
        delete.request(request);
    }

    /**
     * Deletes a user according to the given RequestModel and sets this UserManager's user to null.
     * @param request   RequestModel containing delete User information.
     */
    public void edit(RequestModel request) throws UserNotFound, UserAlreadyExists, InvalidUsername {
        Edit edit = new Edit(userRepository);
        edit.request(request);
    }

    /**
     * Changes a user's password.
     * @throws UserNotFound         Given user is not in repository
     * @throws InvalidPassword      Password not valid
     * @throws IncorrectPassword    Old password is wrong
     */
    public void changePassword(RequestModel request) throws UserNotFound, InvalidPassword, IncorrectPassword {
        ChangePassword changePassword = new ChangePassword(userRepository);
        changePassword.request(request);
    }

    /**
     * Adjust two given users such that the first follows the second.
     * @param request   RequestModel containing addFollow User information.
     */
    public void follow(RequestModel request) throws UserNotFound, SameUser, AlreadyFollowingUser,
            UserBlocked, BlockedByUser {
        Follow follow = new Follow(userRepository);
        follow.request(request);
    }

    /**
     * Removes second user from first user's following list, and appropriately removes the first user
     * from the second user's follower list.
     * @param request RequestModel containing unfollow User information.
     */
    public void unfollow(RequestModel request) throws UserNotFound, SameUser{
        Unfollow unfollow = new Unfollow(userRepository);
        unfollow.request(request);
    }

    /**
     * Block a given user
     * @param request   RequestModel containing the user and the user to block.
     */
    public void block(RequestModel request) throws UserNotFound, UserAlreadyBlocked {
        Block block = new Block(userRepository, conversationRepository);
        block.request(request);
    }

    /**
     * Unlock a given user
     * @param request   RequestModel containing the user and the user to unblock.
     */
    public void unblock(RequestModel request) throws UserNotFound, UserNotBlocked {
        Unblock unblock = new Unblock(userRepository);
        unblock.request(request);
    }

    /**
     * Returns ArrayList of UserProfiles of the User's following list
     * @param request RequestModel containing the user id
     * @return Arraylist of UserProfiles
     */
    public ArrayList<UserProfile> getFollowing(RequestModel request) throws UserNotFound {
        GetFollowing getFollowing = new GetFollowing(userRepository);
        return getFollowing.request(request);
    }

    /**
     * Returns ArrayList of UserProfiles of the User's follower list
     * @param request RequestModel containing the user id
     * @return Arraylist of UserProfiles
     */
    public ArrayList<UserProfile> getFollowers(RequestModel request) throws UserNotFound {
        GetFollowers getFollowers = new GetFollowers(userRepository);
        return getFollowers.request(request);
    }

    public ArrayList<ConversationProfile> getRelevantConversations(RequestModel request) throws UserNotFound {
        GetRelevantConversations getRelevantConversations = new GetRelevantConversations(userRepository,
                conversationRepository);
        return getRelevantConversations.request(request);
    }

    /**
     * Rate given user
     * @param request RequestModel containing the user to be rated and the rating
     */
    public void rate(RequestModel request) {
        Rate rate = new Rate((userRepository));
    }




}
