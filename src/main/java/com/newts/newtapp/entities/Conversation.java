package com.newts.newtapp.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * A class representing a conversation
 */
@Entity
@Table(name = "conversations")
// A custom type in order to save ArrayLists to the database.
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Conversation {

    /**
     * This Conversation's unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    /**
     * This Conversation's title.
     */
    @Column(name = "title", columnDefinition = "text")
    private String title;

    /**
     * A list of topics that are relevant to this Conversation.
     */
    @Column(name = "topics", columnDefinition = "text[]")
    @Type(type = "list-array")
    private ArrayList<String> topics;

    /**
     * The location where this Conversation's location radius is centered.
     */
    @Column(name = "location", columnDefinition = "text")
    private String location;

    /**
     * The radius within which users should be able to search for this Conversation. NOT IMPLEMENTED.
     */
    @Column(name = "location_radius", columnDefinition = "int")
    private int locationRadius;

    /**
     * The minimum rating a user must have to join this Conversation.
     */
    @Column(name = "min_rating", columnDefinition = "int")
    private int minRating;

    /**
     * The maximum number of users that can be in this Conversation.
     */
    @Column(name = "max_size", columnDefinition = "int")
    private int maxSize;

    /**
     * Boolean value indicating whether this conversation should appear in search and allow new users to join.
     */
    @Column(name = "is_open", columnDefinition = "boolean")
    private boolean isOpen;

    /**
     * A list of messages in this conversation in order of when they were added (new messages are added to the end of
     * the list).
     */
    @Column(name = "messages", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> messages;

    /**
     * A list of users in this conversation in order of when they joined (new users are added to end of list).
     */
    @Column(name = "users", columnDefinition = "int[]")
    @Type(type = "list-array")
    private ArrayList<Integer> users;

    /**
     * The id of the author of the conversation.
     */
    @Column(name = "author_id", columnDefinition = "int")
    private int authorId;

    /**
     * Create a new Conversation object.
     * @param id                Unique id of this conversation
     * @param title             Conversation's title
     * @param topics            List of this conversation's topics
     * @param location          Where this conversation is taking place
     * @param locationRadius    Radius in km where people should be able to see this conversation
     * @param minRating         Minimum rating a user must have in order to join this conversation
     * @param maxSize           Max number of users in this conversation
     * @param creatorId         The id of the creator of this conversation
     */
    public Conversation(int id, String title,
                        ArrayList<String> topics, String location,
                        int locationRadius,
                        int minRating, int maxSize,
                        int creatorId) {
        this.id = id;
        this.title = title;
        this.topics = topics;
        this.location = location;
        this.locationRadius = locationRadius;
        this.minRating = minRating;
        this.maxSize = maxSize;
        isOpen = true;
        messages = new ArrayList<>();
        users = new ArrayList<>();
        users.add(creatorId);
        authorId = creatorId;
    }

    /**
     * Initialize a new empty conversation. Mostly used for testing purposes.
     */
    public Conversation() {
        this.id = 0;
        this.title = "";
        this.topics = new ArrayList<>();
        this.location = "";
        this.locationRadius = 0;
        this.minRating = 0;
        this.maxSize = 0;
        this.isOpen = false;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
        authorId = 0;
    }

    /**
     * Return the id of the conversation.
     * @return a string representing the id
     */
    public int getId(){ return this.id; }

    /**
     * Sets the id of this conversation.
     * @param id    new id
     */
    public void setId(int id){ this.id = id; }

    /**
     * Return the title of the conversation.
     * @return a string representing the title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Return a list of topics for this conversation.
     * @return  List of
     */
    public ArrayList<String> getTopics() { return new ArrayList<>(topics); }

    /**
     * Set list of Conversation's topics.
     * @param topics    List of topics
     */
    public void setTopics(ArrayList<String> topics) { this.topics = topics; }

    /**
     * Return the location of the conversation.
     * @return a string representing the location
     */
    public String getLocation(){
        return this.location;
    }

    /**
     * Return the radius of the location.
     * @return an int representing the location radius
     */
    public int getLocationRadius(){ return this.locationRadius; }

    /**
     * Return the minimum rating of the conversation.
     * @return an int representing the minimum rating of the conversation
     */
    public int getMinRating(){
        return this.minRating;
    }

    /**
     * Return the maximum number of users in the conversation.
     * @return an int representing the max number of users
     */
    public int getMaxSize(){
        return this.maxSize;
    }

    /**
     * Return if the conversation is open.
     * @return true if the conversation is open
     */
    public boolean getIsOpen(){
        return this.isOpen;
    }

    /**
     * Set isOpen status of this Conversation.
     * @param isOpen    true iff conversation is open.
     */
    public void setIsOpen(boolean isOpen) { this.isOpen = isOpen; }

    /**
     * Return the messages in the conversation.
     * @return an ArrayList containing messages
     */
    public ArrayList<Integer> getMessages(){
        return new ArrayList<>(messages);
    }

    /**
     * Set the list of messages in this conversation.
     * @param messages  List of message ids
     */
    public void setMessages(ArrayList<Integer> messages) {
        this.messages = messages;
    }

    /**
     * Return the users in the conversation.
     * @return an ArrayList containing users
     */
    public ArrayList<Integer> getUsers(){
        return new ArrayList<>(users);
    }

    /**
     * Set a title for the conversation.
     * @param title the title to be set
     */
    public void setTitle(String title){ this.title = title; }

    /**
     * Add a topic to this conversation.
     * @param topic the topic to be added
     */
    public void addTopic(String topic) {
        topics.add(topic);
    }

    /**
     * Remove a topic from this conversation, if this topic is in this conversation's topics.
     * @param topic     Topic to remove
     */
    public void removeTopic(String topic) {
        topics.remove(topic);
    }

    /**
     * Set a location for the conversation.
     * @param location the location to be set
     */
    public void setLocation(String location){ this.location = location; }

    /**
     * Set a rating for the conversation.
     * @param minRating the rating to be set
     */
    public void setMinRating(int minRating){ this.minRating = minRating; }

    /**
     * Set the max number of users in the conversation.
     * @param maxSize the max number of users to be set
     */
    public void setMaxSize(int maxSize){ this.maxSize = maxSize; }

    /**
     * Set the status of the conversation.
     */
    public void toggleIsOpen(){ this.isOpen = !this.isOpen; }

    /**
     * Add a message to the conversation.
     * @param messageId the message to be added
     */
    public void addMessage(int messageId){ this.messages.add(messageId); }

    /**
     * Add a user to the conversation.
     * @param userId  a user to be added
     * @return      true iff the user was added to the conversation
     */
    public boolean addUser(int userId){
        if ((users.size() == maxSize) | users.contains(userId)) {
            return false;
        } else {
            return users.add(userId);
        }
    }

    /**
     * Set the list of Users in this conversation.
     * @param users     List of User ids
     */
    public void setUsers(ArrayList<Integer> users) {
        this.users = users;
    }

    /**
     * Returns the number of Users in a Conversation
     * @return Number of users
     */
    public int getNumUsers(){
        return users.size();
    }

    /**
     * Remove a user from a conversation.
     * @param userId  a user to be removed
     * @return      true iff the user was removed
     */
    public boolean removeUser(int userId) {
        int beforeSize = users.size();
        users.remove(Integer.valueOf(userId));
        return beforeSize != users.size();
    }

    /**
     * Delete a message from a conversation.
     * @param messageId   a message to be removed
     */
    public void deleteMessage(int messageId) {
        int beforeSize = messages.size();
        messages.remove(Integer.valueOf(messageId));
    }

    /**
     * Returns the id of the conversation author
     * @return  Author's userid.
     */
    public int getAuthorId(){return authorId;}

    /**
     * Sets author of conversation
     * @param authorID  Author's userid
     */
    public void setAuthorId(int authorID){this.authorId = authorID;}

    /**
     * Sets location radius
     * @param locationRadius Integer for new location radius
     */
    public void setLocationRadius(int locationRadius){
        this.locationRadius = locationRadius;
    }
}
