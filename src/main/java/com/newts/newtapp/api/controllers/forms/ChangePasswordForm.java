package com.newts.newtapp.api.controllers.forms;

/**
 * A form defining how a create user request's json request body should be formatted for Spring to serialize.
 */
public class ChangePasswordForm {
    private final String password;
    private final String newPassword;

    public ChangePasswordForm(String password,  String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}