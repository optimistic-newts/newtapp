package com.newts.newtapp.api.controllers.forms;

/**
 * A form defining how a delete-user request's json request body should be formatted for Spring to serialize.
 */
public class UserAuthForm {
    private final String username;
    private final String password;

    public UserAuthForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
