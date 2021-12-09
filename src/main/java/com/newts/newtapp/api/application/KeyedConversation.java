package com.newts.newtapp.api.application;

import com.newts.newtapp.entities.Conversation;

/**
 * An KeyedConversation. Used to pair a conversation with an integer key representing the relevance to
 * a ConversationQueue's ordering. This class uses number of matching interests to assign priority to a conversation.
 */
public class KeyedConversation {
    private final Conversation conversation;
    private int key;

    /**
     * Create a new KeyedConversation, where key is the number of topic matches.
     * @param conversation  Conversation to pair key to
     * @param queue         ConversationQueue that this conversation will be inserted to
     */
    public KeyedConversation(Conversation conversation, ConversationQueue queue) {
        this.conversation = conversation;
        key = 0;
        key = queue.getSorter().getPriority(conversation, queue);
    }

    /**
     * @return  The conversation associated with this KeyedConversation.
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * @return  The key associated with this KeyedConversation.
     */
    public int getKey() {
        return key;
    }

    /**
     * Primarily intended for testing purposes.
     * @param key   key to set this KeyedConversation's key to.
     */
    public void setKey(int key) {this.key = key;}

    /**
     * @param other an instance of KeyedConversation
     * @return true iff the contained conversations in this and other are equal
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof KeyedConversation)) {
            return false;
        }
        return (this.conversation == ((KeyedConversation) other).getConversation());
    }
}
