package com.newts.newtapp.api.application.sorters;

import com.newts.newtapp.api.application.ConversationQueue;
import com.newts.newtapp.entities.Conversation;

/**
 * A ConversationSorter that assigns priority by the number of matching interests.
 */
public class InterestSorter implements ConversationSorter {

    /**
     * Returns an integer priority associated with a Conversation based on the interests attribute of the provided
     * ConversationQueue. Specifically, the priority of a conversation is equal to the number of interests shared
     * by the Queue and the Conversation.
     * @param conversation  Conversation to assign priority to
     * @param queue         ConversationQueue with priority relevance attributes
     * @return              Integer priority
     */
    @Override
    public int getPriority(Conversation conversation, ConversationQueue queue) {
        int key = 0;
        for (String interest : queue.getInterests()) {
            for (String topic : conversation.getTopics()) {
                if (topic.equals(interest)) {
                    key++;
                }
            }
        }
        return key;
    }
}
