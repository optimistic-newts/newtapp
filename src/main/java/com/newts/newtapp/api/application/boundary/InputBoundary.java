package com.newts.newtapp.api.application.boundary;

import com.newts.newtapp.api.errors.ConversationNotFound;

/**
 * An Input Boundary interface. Defines a standard input method by which to pass RequestModel objects.
 */
public interface InputBoundary<ReturnType, ExceptionType extends Exception> {

    // There is a warning about this method not being used directly by a member of this interface, but this is
    // intentional for our implementation. It does not make sense for a member of this interface to use the method.
    /**
     * Accepts a request.
     * @param request   a request stored as a RequestModel
     */
    ReturnType request(RequestModel request) throws ExceptionType, ConversationNotFound;
}
