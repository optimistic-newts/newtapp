package com.newts.newtapp.api.application.datatransfer;

import com.newts.newtapp.entities.User;

import java.util.ArrayList;

/**
 * A condensed view of a User, for passing User information to a client browser without revealing secure information.
 * This is just a data storage object.
 */
@SuppressWarnings("unused")
public class UserProfile {
    public final int id;
    public final String username;
    public final String location;
    public final ArrayList<String> interests;
    public final ArrayList<Integer> following;
    public final ArrayList<Integer> followers;
    public final ArrayList<Integer> conversations;

    public UserProfile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.location = user.getLocation();
        this.interests = user.getInterests();
        this.following = user.getFollowing();
        this.followers = user.getFollowers();
        this.conversations = user.getConversations();
    }
}
