package com.newts.newtapp.api.application;

import com.newts.newtapp.api.application.sorters.InterestSorter;
import com.newts.newtapp.entities.Conversation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ConversationQueueTest {
    ConversationQueue cq;

    /**
     * Set up a test ConversationQueue before each test.
     */
    @Before
    public void setUp() {
        InterestSorter sorter = new InterestSorter();
        ArrayList<String> interests = new ArrayList<>();
        interests.add("Hockey");
        interests.add("Math");
        cq = new ConversationQueue(sorter,"Toronto", interests);
    }

    /**
     * Test location getter.
     */
    @Test
    public void getLocationTest() {
        Assert.assertEquals("Toronto", cq.getLocation());
    }

    /**
     * Test interests array getter.
     */
    @Test
    public void getInterestsTest() {
        Assert.assertArrayEquals(new String[]{"Hockey", "Math"}, cq.getInterests().toArray());
    }

    /**
     * Test toKeyedConversation on a conversation with 0 interest matches and a conversation with 1 interest match.
     */
    @Test
    public void toKeyedConversationTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Physics");
        KeyedConversation keyedConversation0 = cq.toKeyedConversation(conversation0);
        Assert.assertEquals(1, keyedConversation0.getKey());
        KeyedConversation keyedConversation1 = cq.toKeyedConversation(conversation1);
        Assert.assertEquals(0, keyedConversation1.getKey());
    }

    /**
     * Test that size returns the expected size of ConversationQueue.
     */
    @Test
    public void sizeTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Physics");

        Assert.assertEquals(0, cq.size());

        cq.add(conversation0);
        Assert.assertEquals(1, cq.size());

        cq.add(conversation1);
        Assert.assertEquals(2, cq.size());
    }

    /**
     * Test isEmpty on empty and non-empty ConversationQueue.
     */
    @Test
    public void isEmptyTest() {
        Assert.assertTrue(cq.isEmpty());
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Assert.assertFalse(cq.isEmpty());
    }

    /**
     * Test contains with element and non element.
     */
    @Test
    public void containsTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();

        Assert.assertTrue(cq.contains(conversation0));
        Assert.assertFalse(cq.contains(conversation1));
    }

    /**
     * Test iterator returns as expected.
     */
    @Test
    public void iteratorTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation0.addTopic("Toronto");
        cq.add(conversation1);

        Iterator<Conversation> iter = cq.iterator();
        Assert.assertTrue(iter.hasNext());
        Assert.assertEquals(conversation0, iter.next());
        Assert.assertEquals(conversation1, iter.next());
    }

    /**
     * Test toArray returns as expected.
     */
    @Test
    public void toArrayTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation0.addTopic("Toronto");
        cq.add(conversation1);

        Conversation[] expected = new Conversation[]{conversation0, conversation1};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test toArray with input array smaller than required.
     */
    @Test
    public void toArrayTestTooSmall() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation0.addTopic("Toronto");
        cq.add(conversation1);

        Conversation[] input = new Conversation[1];
        Conversation[] expected = new Conversation[]{conversation0, conversation1};

        Assert.assertArrayEquals(expected, cq.toArray(input));
    }

    /**
     * Test toArray with input array that is same size as ConversationQueue.
     */
    @Test
    public void toArrayTestSameSize() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation0.addTopic("Toronto");
        cq.add(conversation1);

        Conversation[] input = new Conversation[2];
        Conversation[] expected = cq.toArray();

        Assert.assertArrayEquals(expected, cq.toArray(input));
    }

    /**
     * Test toArray with input array that is larger than ConversationQueue.
     */
    @Test
    public void toArrayTestLarger() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation0.addTopic("Toronto");
        cq.add(conversation1);

        Conversation[] input = new Conversation[3];
        Conversation[] expected = new Conversation[]{conversation0, conversation1, null};

        Assert.assertArrayEquals(expected, cq.toArray(input));
    }

    /**
     * Test remove on a root node.
     */
    @Test
    public void removeTestFirst() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);

        Assert.assertTrue(cq.remove(conversation0));
        Conversation[] expected = new Conversation[]{conversation1, conversation2};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test remove on last node.
     */
    @Test
    public void removeTestLast() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);

        Assert.assertTrue(cq.remove(conversation2));
        Conversation[] expected = new Conversation[]{conversation0, conversation1};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test remove on middle node - one with parent and children.
     */
    @Test
    public void removeTestMiddle() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);
        Conversation conversation3 = new Conversation();
        conversation3.addTopic("Golf");
        cq.add(conversation3);
        Conversation conversation4 = new Conversation();
        conversation4.addTopic("Hockey");
        cq.add(conversation4);

        Assert.assertTrue(cq.remove(conversation1));
        Conversation[] expected = new Conversation[]{conversation0, conversation4, conversation2, conversation3};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test contains all returns false.
     */
    @Test
    public void containsAllTestFalse() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);
        Conversation conversation3 = new Conversation();
        conversation3.addTopic("Fish");

        Conversation[] compare = new Conversation[]{conversation0, conversation1, conversation2, conversation3};
        Assert.assertFalse(cq.containsAll(List.of(compare)));
    }

    /**
     * Test contains all returns true.
     */
    @Test
    public void containsAllTestTrue() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);

        Conversation[] compare = new Conversation[]{conversation0, conversation1, conversation2};
        Assert.assertTrue(cq.containsAll(List.of(compare)));
    }

    /**
     * Test addAll works as expected.
     */
    @Test
    public void addAllTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        Conversation conversation3 = new Conversation();
        conversation3.addTopic("Fish");
        Conversation[] toAdd = new Conversation[]{conversation1, conversation2, conversation3};

        Assert.assertTrue(cq.addAll(List.of(toAdd)));
        Conversation[] expected = new Conversation[]{conversation0, conversation1, conversation2, conversation3};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test removeAll works as expected where ConversationQueue is left with an item that should not be removed.
     */
    @Test
    public void removeAllTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);
        Conversation conversation3 = new Conversation();
        conversation3.addTopic("Fish");
        cq.add(conversation3);
        Conversation[] toRemove = new Conversation[]{conversation1, conversation2, conversation3};
        Conversation[] expected = new Conversation[]{conversation0};

        Assert.assertTrue(cq.removeAll(List.of(toRemove)));
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test retainAll works as expected in a situation where some elements should be removed
     * and some should be retained.
     */
    @Test
    public void retainAllTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);
        Conversation conversation3 = new Conversation();
        conversation3.addTopic("Fish");
        cq.add(conversation3);
        Conversation[] toKeep = new Conversation[]{conversation1, conversation2};
        Conversation[] expected = new Conversation[]{conversation1, conversation2};

        Assert.assertTrue(cq.retainAll(List.of(toKeep)));
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test clear works as expected on a non-empty ConversationQueue.
     */
    @Test
    public void clearTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Math");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Toronto");
        cq.add(conversation2);
        cq.clear();

        Conversation[] expected = new Conversation[]{};
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test remove returns Conversations in the expected order.
     */
    @Test
    public void removeTest() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Toronto");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Math");
        cq.add(conversation2);

        Assert.assertEquals(conversation0, cq.remove());
        Assert.assertEquals(conversation2, cq.remove());
        Assert.assertEquals(conversation1, cq.remove());
    }

    /**
     * Test poll() returns null if ConversationQueue is empty.
     */
    @Test
    public void pollTestEmpty() {
        Assert.assertNull(cq.poll());
    }

    /**
     * Test element returns null if called on empty ConversationQueue.
     */
    @Test
    public void elementTestEmpty() {
        NoSuchElementException thrown = Assert.assertThrows(NoSuchElementException.class, () -> cq.element());
        Assert.assertTrue(thrown.getMessage().contains("Tried"));
    }

    /**
     * Test whether element returns the head of ConversationQueue and whether the queue is unchanged after this.
     */
    @Test
    public void elementTestNonEmpty() {
        Conversation conversation0 = new Conversation();
        conversation0.addTopic("Hockey");
        cq.add(conversation0);
        Conversation conversation1 = new Conversation();
        conversation1.addTopic("Toronto");
        cq.add(conversation1);
        Conversation conversation2 = new Conversation();
        conversation2.addTopic("Math");
        cq.add(conversation2);

        Conversation[] expected = new Conversation[]{conversation0, conversation2, conversation1};
        Assert.assertEquals(conversation0, cq.element());
        Assert.assertArrayEquals(expected, cq.toArray());
    }

    /**
     * Test whether peek returns null when called on empty ConversationQueue.
     */
    @Test
    public void peekTestEmpty() {
        Assert.assertNull(cq.peek());
    }
}