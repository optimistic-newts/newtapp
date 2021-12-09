package com.newts.newtapp.api.application;

import com.newts.newtapp.api.application.sorters.ConversationSorter;
import com.newts.newtapp.entities.Conversation;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * A Conversation Queue. A Max Priority Queue used to filter conversations by relevance to present to a user.
 */
public class ConversationQueue implements Queue<Conversation> {
    private final ConversationSorter sorter;
    private ArrayList<KeyedConversation> conversations;
    private int size;
    private final String location;
    private final ArrayList<String> interests;

    /**
     * Initialize a new, empty ConversationQueue.
     */
    public ConversationQueue(ConversationSorter sorter, String location,
                             ArrayList<String> interests) {
        this.sorter = sorter;
        conversations = new ArrayList<>();
        conversations.add(null); // item at index 0 will never be used, due to Array representation of binary heap.
        size = 0;
        this.location = location;
        this.interests = interests;
    }

    /**
     * @return  the ConversationSorter associated with this ConversationQueue.
     */
    public ConversationSorter getSorter() {
        return sorter;
    }

    /**
     * @return location where this ConversationQueue is centered.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return array of interests that factor into this ConversationQueue's priority function.
     */
    public ArrayList<String> getInterests() {
        return interests;
    }

    /**
     * Returns a KeyedConversation made up of the given conversation and a priority key generated relative to
     * this ConversationQueue.
     * @param conversation  conversation to include in new KeyedConversation
     * @return  KeyedConversation with Conversation and a generated priority key
     */
    public KeyedConversation toKeyedConversation(Conversation conversation) {
        return new KeyedConversation(conversation, this);
    }

    /**
     * @return  Size of this ConversationQueue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return  true iff this ConversationQueue is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o Object to search for
     * @return  true iff o is in this ConversationQueue
     */
    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Conversation)) {
            return false;
        }
        return conversations.contains(toKeyedConversation((Conversation) o));
    }

    /**
     * Returns an iterator over the elements in this ConversationQueue- in non-increasing order.
     * @return  Iterator over the elements in this ConversationQueue
     */
    @Override
    public Iterator<Conversation> iterator() {
        Conversation[] array = toArray();
        return Arrays.stream(array).iterator();
    }

    /**
     * Returns an array containing the Conversations in this ConversationQueue- in non-increasing order.
     * @return  sorted array
     */
    @Override
    public Conversation[] toArray() {
        ArrayList<KeyedConversation> temp = new ArrayList<>(conversations);
        int tempSize = size;
        Conversation[] toReturn = new Conversation[size];
        int i = 0;
        while (!this.isEmpty()) {
            toReturn[i] = poll();
            i ++;
        }
        conversations = temp;
        size = tempSize;
        return toReturn;
    }

    /**
     * Returns an array containing the Conversations in this ConversationQueue- in non-increasing order. If contents
     * of this ConversationQueue will fit in provided array, then return the provided array with these elements inserted
     * followed by null. If provided array type does not extend Conversation, then return the given array unchanged.
     * @param a     array to fill with ConversationQueue's contents
     * @return      sorted array
     */
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return a;
    }

    /**
     * Returns an array containing the Conversations in this ConversationQueue- in non-increasing order. If contents
     * of this ConversationQueue will fit in provided array, then return the provided array with these elements inserted
     * followed by null.
     * @return  sorted array
     */
    public Conversation[] toArray(Conversation[] a) {
        if (a.length >= size) {
            ArrayList<KeyedConversation> temp = new ArrayList<>(conversations);
            int tempSize = size;
            int i = 0;
            while (!this.isEmpty()) {
                a[i] = poll();
                i ++;
            }
            conversations = temp;
            size = tempSize;
            if (i != a.length) {
                a[i] = null;
            }
            return a;
        } else {
            return toArray();
        }
    }

    /**
     * Removes given object from this ConversationQueue, provided object is in ConversationQueue.
     * @param o object to remove from ConversationQueue
     * @return true iff object was successfully removed
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        int i = conversations.indexOf(toKeyedConversation((Conversation) o));
        if (i == -1) {
            return false;
        }
        conversations.set(i, conversations.get(size));
        size -= 1;
        heapify(i);
        return true;
    }

    /**
     * @param c Collection to check whether this ConversationQueue contains
     * @return  true iff this contains all items in c
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!(object instanceof Conversation)) {
                return false;
            } else if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param c Collection of objects to add to this ConversationQueue
     * @return true iff this ConversationQueue was modified during this call
     */
    @Override
    public boolean addAll(Collection<? extends Conversation> c) {
        boolean toReturn = false;
        for (Conversation object : c) {
            add(object);
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * Removes all elements from this ConversationQueue that are also in an input Collection.
     * @param c Collection to compare this ConversationQueue to
     * @return  true iff at least one item in this ConversationQueue was also in c and was removed as a result
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean toReturn = false;
        for (Object object : c) {
            if (remove(object)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Removes all elements from this ConversationQueue that are not also in an input Collection.
     * @param c Collection to compare this ConversationQueue to
     * @return  true iff at least one item in this ConversationQueue was not also in c and was removed as a result
     */
    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        boolean toReturn = false;
        Iterator<Conversation> old = iterator();
        clear();
        while (old.hasNext()) {
            Conversation item = old.next();
            if (c.contains(item)) {
                add(item);
            } else {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Removes all elements from this ConversationQueue.
     */
    @Override
    public void clear() {
        conversations = new ArrayList<>();
        conversations.add(null); // item at index 0 will never be used, due to Array representation of binary heap.
        size = 0;
    }

    /**
     * Adds a Conversation to this ConversationQueue.
     * @param toAdd item to add
     * @return true iff the item was successfully added
     */
    @Override
    public boolean add(Conversation toAdd) {
        insert(toKeyedConversation(toAdd));
        return true;
    }

    /**
     * Adds a Conversation to this ConversationQueue.
     * @param toAdd item to add
     * @return true if the item was successfully added
     */
    @Override
    public boolean offer(Conversation toAdd) {
        insert(toKeyedConversation(toAdd));
        return true;
    }

    /**
     * Retrieves and removes one highest priority Conversation from this ConversationQueue.
     * @return  highest priority Conversation
     */
    @Override
    public Conversation remove() {
        KeyedConversation max = conversations.get(1);
        conversations.set(1, conversations.get(size));
        size -= 1;
        heapify(1);
        return max.getConversation();
    }

    /**
     * Retrieves and removes one highest priority Conversation from this ConversationQueue.
     * @return  highest priority Conversation or null if ConversationQueue is empty
     */
    @Override
    public Conversation poll() {
        if (this.isEmpty()) {
            return null;
        }
        KeyedConversation max = conversations.get(1);
        conversations.set(1, conversations.get(size));
        size -= 1;
        heapify(1);
        return max.getConversation();
    }

    /**
     * Retrieves but does not remove one highest priority Conversation from this ConversationQueue.
     * @return highest priority Conversation
     */
    @Override
    public Conversation element() throws NoSuchElementException {
        try {
            return conversations.get(1).getConversation();
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Tried to retrieve head of ConversationQueue, but the queue was empty.");
        }
    }

    /**
     * Retrieves, but does not remove, one highest priority Conversation from this ConversationQueue- or null if
     * the queue is empty.
     * @return  highest priority Conversation or null if empty queue
     */
    @Override
    public Conversation peek() {
        if (this.isEmpty()) {
            return null;
        }
        return conversations.get(1).getConversation();
    }

    /**
     * Inserts the given element into a valid location in the ConversationQueue.
     * @param toInsert  Conversation to insert
     */
    private void insert(KeyedConversation toInsert) {
        size += 1;
        int i = size;
        conversations.add(i, toInsert);

        while (i > 1 && conversations.get(parent(i)).getKey() < conversations.get(i).getKey()) {
            KeyedConversation temp = conversations.get(parent(i));
            conversations.set(parent(i), conversations.get(i));
            conversations.set(i, temp);
            i = parent(i);
        }
    }

    /**
     * Modifies the node order of the tree rooted at node with index i such that the tree is a valid max-heap.
     *
     * Precondition: trees rooted at left(i) and right(i) are valid max-heaps.
     *
     * @param i Index of root of tree to heapify in conversations ArrayList.
     */
    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest;

        if (l <= size && conversations.get(l).getKey() > conversations.get(i).getKey()) {
            largest = l;
        } else {
            largest = i;
        }

        if (r <= size && conversations.get(r).getKey() > conversations.get(largest).getKey()) {
            largest = r;
        }

        if (largest != i) {
            KeyedConversation temp = conversations.get(largest);
            conversations.set(largest, conversations.get(i));
            conversations.set(i, temp);
            heapify(largest);
        }

    }

    /**
     * Returns the index of the node at index i's left child.
     * @param i     index of this node
     * @return      index of left child
     */
    private static int left(int i) {
        return 2 * i;
    }

    /**
     * Returns the index of the node at index i's right child.
     * @param i     index of this node
     * @return      index of right child
     */
    private static int right(int i) {
        return (2 * i) + 1;
    }

    /**
     * Returns the index of the node at index i's parent node.
     * @param i     index of this node
     * @return      index of parent
     */
    private static int parent(int i) {
        return i / 2;
    }
}
