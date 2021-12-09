package com.newts.newtapp.api.application.sorters;

import com.newts.newtapp.api.application.ConversationQueue;
import com.newts.newtapp.entities.Conversation;

/**
 * The interface for all Conversation sorting methods to implement.
 */
public interface ConversationSorter {

    /**
     * Returns an integer priority associated with a Conversation based on the attributes of the provided conversation
     * Queue and the implementing ConversationSorter's sorting strategy.
     * @param conversation  Conversation to assign priority to
     * @param queue         ConversationQueue with priority relevance attributes
     * @return              Integer priority
     */
    int getPriority(Conversation conversation, ConversationQueue queue);
}
