package com.newts.newtapp.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

/**
 * An object representing a User of the application.
 */
@Entity
@Table(name = "users")
// Custom type for ArrayLists
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class User implements UserDetails {
    /**
     * This User's unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    /**
     * This User's username, used for logging in and is visible to other Users.
     */
    @Column(name = "username", columnDefinition = "text")
    private String username;

    /**
     * This User's hashed password, used for logging in.
     */
    @Column(name = "password", columnDefinition = "text")
    private String password;

    /**
     * This User's location, used for finding local conversations.
     */
    @Column(name = "location", columnDefinition = "text")
    private String location;

    /**
     * A list of this user's interests.
     */
    @Column(name = "interests", columnDefinition = "text[]")
    @Type(type = "list-array")
    private ArrayList<String> interests;

    /**
     * This is the user's total rating- a sum of all ratings given to this user. Used for rating calculation.
     */
    @Column(name = "total_rating", columnDefinition = "int")
    private int totalRating;

    /**
     * Total number of ratings this user has received. This is used for computing the user's rating. Is 1 initially.
     */
    @Column(name = "num_ratings", columnDefinition = "int")
    private int numRatings;

    /**
     * A list of unique user identifiers corresponding to the Users that this User follows.
     */
    @Column(name = "following", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> following;

    /**
     * A list of unique user identifiers corresponding to the Users that follow this User.
     */
    @Column(name = "followers", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> followers;

    /**
     * A list of user identifiers corresponding to the Users that have been blocked by this user.
     */
    @Column(name = "blocked_users", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> blockedUsers;

    /**
     * A list of unique conversation identifiers corresponding to this User's active conversations.
     */
    @Column(name = "conversations", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> conversations;

    @Column(name = "authorities", columnDefinition = "text[]")
    @Type(type = "list-array")
    private ArrayList<String> authorities;

    @Column(name = "rated_users", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> ratedUsers;

    /**
     * Create a new User with given User information.
     * @param username  The user's username.
     * @param password  The user's password.
     * @param interests A List of the user's initial specified interests.
     * @param id        The user's unique id.
     */
    public User(int id,
                String username,
                String password,
                ArrayList<String> interests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.interests = interests;
        location = null;
        totalRating = 5;
        numRatings = 1;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        blockedUsers = new ArrayList<>();
        conversations = new ArrayList<>();
        authorities = new ArrayList<>();
        authorities.add("ROLE_USER");
    }

    /**
     * Empty User constructor- for testing purposes.
     */
    public User() {
        username = null;
        password = null;
        interests = new ArrayList<>();
        id = 0;
        totalRating = 0;
        numRatings = 0;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        blockedUsers = new ArrayList<>();
        conversations = new ArrayList<>();
        authorities = new ArrayList<>();
        authorities.add("ROLE_USER");
    }

    /**
     * Getter method for user's ID.
     * @return Returns user ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Setter method for user's ID.
     * @param id   The new id to assign this user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for user's username.
     * @return Returns user's username.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Setter method for username
     * @param username Username to be set
     */
    public void setUsername(String username) { this.username = username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String auth : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(auth));
        }
        return grantedAuthorities;
    }

    /**
     *  Sets a users authorities.
     * @param authorities   ArrayList of String authorities.
     */
    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Getter method for user's hashed password.
     * @return Returns user's password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Getter method for user's last updated location.
     * NOTE: Wasn't sure what type the location data would be,
     * using type String for now, to be changed later.
     * @return Returns user's last updated location.
     */
    public String getLocation(){
        return location;
    }

    /**
     * Setter method for the user's password
     * @param password Password to be set
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Setter method for user's location.
     * @param location Location of user (Type to be changed)
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Getter method for ArrayList of user interests.
     * @return Returns a copy of the ArrayList of user interests.
     */
    public ArrayList<String> getInterests() {
        return new ArrayList<>(this.interests);
    }

    /**
     * Setter method for Arraylist of user's interests.
     * @param interests Interests of the user
     */
    public void setInterests(ArrayList<String> interests){
        this.interests = interests;
    }

    /**
     * Adds supplied interest to user's interests ArrayList
     * @param interest New interest to be added
     */
    public void addInterests(String interest){
        interests.add(interest);
    }

    /**
     * Removes specified interest from user's interests
     * @param interest Interest to be removed
     */
    public void removeInterest(String interest){
        int index = interests.indexOf(interest);
        if (index != -1)
            interests.remove(index);
    }

    /**
     * Returns this user's rating. totalRatings/numRatings.
     * @return User's average rating
     */
    public double getRating() {
        return (float) totalRating / numRatings;
    }

    /**
     * Adds rating to user's total rating and increases
     * The number of ratings by one.
     * @param rating Rating to be added
     */
    public void addRating(float rating){
        this.totalRating += rating;
        numRatings ++;
    }

    /**
     * @return  Number of ratings this user has received.
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * Set the number of ratings this user has received.
     * @param numRatings    number of ratings this user has received
     */
    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * @return  Sum of all ratings this user has received
     */
    public int getTotalRating() {
        return totalRating;
    }

    /**
     * Set the total rating of this user.
     * @param totalRating   total rating of this user
     */
    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    /**
     * Adds a User to this User's following list.
     * @param other     User to add
     */
    public void addFollowing(User other) {
        following.add(other.getId());
    }

    /**
     * Removes a User from this User's following list, if they are in the list.
     * @param other     User to remove
     */
    public void removeFollowing(User other) {
        following.remove(Integer.valueOf(other.getId()));
    }

    /**
     * @return  List of User ids that this User follows.
     */
    public ArrayList<Integer> getFollowing() {return new ArrayList<>(following);}

    /**
     * Setter method for the Arraylist of user's id this user is following.
     * @param following Ids of users a user is following
     */
    public void setFollowing(ArrayList<Integer> following){
        this.following = following;
    }

    /**
     * Adds a User to this User's follower list.
     * @param other     User to add
     */
    public void addFollower(User other) {
        followers.add(other.getId());
    }

    /**
     * Removes a User from this User's follower list.
     * @param other     User to remove
     */
    public void removeFollower(User other) {
        followers.remove(Integer.valueOf(other.getId()));
    }

    /**
     * @return  List of User ids of those following this User
     */
    public ArrayList<Integer> getFollowers() {return new ArrayList<>(followers);}

    /**
     * Setter method for the Arraylist of ids of followers.
     * @param followers Ids of users that follow a user
     */
    public void setFollowers(ArrayList<Integer> followers){
        this.followers = followers;
    }

    /**
     * Adds specified conversation object to user's list of active conversations
     * @param conversation Conversation to be added
     */
    public void addConversation(Conversation conversation){
        conversations.add(conversation.getId());
    }

    /**
     * Removes specified conversation from user's list
     * of active conversations.
     * @param conversation Conversation to be removed
     */
    public void removeConversation(Conversation conversation){
        int index = conversations.indexOf(conversation.getId());
        if(index != -1)
            conversations.remove(index);
    }

    /**
     * Returns list of user's active conversations.
     * @return ArrayList of user's active conversations.
     */
    public ArrayList<Integer> getConversations(){return new ArrayList<>(conversations);}

    /**
     * Setter method for the Arraylist of conversation's ids a user has joined.
     * @param conversations ids of conversations a user has joined
     */
    public void setConversations(ArrayList<Integer> conversations){
        this.conversations = conversations;
    }

    /**
     * Getter method for User's block list
     * @return Arraylist of Strings containing usernames of all blocked users.
     */
    public ArrayList<Integer> getBlockedUsers(){
        return blockedUsers;
    }

    /**
     * Adds specified user to this User's block list
     * @param userID Identifier of user to be blocked.
     */
    public void addBlockedUser(int userID){
        blockedUsers.add(userID);
    }

    /**
     * Removes specified user from this User's block list
     * @param userID Identifier of user to be unblocked.
     */
    public void removeBlockedUser(int userID){
        blockedUsers.remove(Integer.valueOf(userID));
    }

    /**
     * Sets blockedUsers Arraylist to the ArrayList which has been passed in.
     * @param blockedUsers ArrayList of blocked users.
     */
    public void setBlockedUsers(ArrayList<Integer> blockedUsers){this.blockedUsers = blockedUsers;}

    /**
     * Sets ratedUsers ArrayList to the ArrayList passed in.
     * @param ratedUsers Arraylist of user ids which have been rated by the user
     */
    public void setRatedUsers(ArrayList<Integer> ratedUsers){this.ratedUsers = ratedUsers;}

    /**
     * Returns ratedUsers ArrayList
     * @return ratedUsers Arraylist of user ids which have been rated by the user
     */
    public ArrayList<Integer> getRatedUsers(){return ratedUsers;}

    public void addRatedUser(int userID){
        ratedUsers.add(userID);
    }

}