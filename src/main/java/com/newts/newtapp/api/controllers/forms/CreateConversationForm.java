package com.newts.newtapp.api.controllers.forms;

import java.util.ArrayList;

/**
 * A form defining how a create conversation request's json request body should be formatted for Spring to serialize.
 */
public class CreateConversationForm {
    private final String title;
    private final ArrayList<String> topics;
    private final String location;
    private final int locationRadius;
    private final int minRating;
    private final int maxSize;
    private final int userId;

    public CreateConversationForm(String title, ArrayList<String> topic, int locationRadius, String location, int minRating,
                                  int maxSize, int userId) {
        this.title = title;
        this.topics = topic;
        this.location = location;
        this.locationRadius = locationRadius;
        this.minRating = minRating;
        this.maxSize = maxSize;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public String getLocation() {
        return location;
    }

    public int getLocationRadius() {
        return locationRadius;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMinRating(){
        return minRating;
    }

    public int getUserId(){
        return userId;
    }

}