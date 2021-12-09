package com.newts.newtapp.api.application.sorters;

import com.newts.newtapp.api.application.ConversationQueue;
import com.newts.newtapp.entities.Conversation;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A ConversationSorter that assigns priority randomly.
 */
public class RandomSorter implements ConversationSorter{
    /**
     * Returns an integer priority from 0 to 20, inclusive.
     * @param conversation  Conversation to assign priority to
     * @param queue         ConversationQueue with priority relevance attributes
     * @return              Integer priority
     */
    @Override
    public int getPriority(Conversation conversation, ConversationQueue queue) {
        return ThreadLocalRandom.current().nextInt(0, 21);
    }
}
