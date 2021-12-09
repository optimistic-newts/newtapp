package com.newts.newtapp.api.controllers.forms;

import java.util.ArrayList;

/**
 * A form defining how a create user request's json request body should be formatted for Spring to serialize.
 */
public class CreateUserForm {
    private final String username;
    private final String password;
    private final String location;
    private final ArrayList<String> interests;

    public CreateUserForm(String username, String password,  String location, ArrayList<String> interests) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }
}
