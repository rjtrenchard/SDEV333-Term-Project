import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeImpl<E> implements Deque<E> {

    int size;
    Node head, tail;

    private class Node {
        E data;
        Node prev, next;

        public Node(E data) {
           this(data, null, null);
        }

        public Node(E data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Checks if the deque is empty.
     *
     * O(1), size access is recorded during node creation/deletion
     *
     * @return true if the deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     *
     * O(1), size access is constant
     *
     * @return number of items in the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Add an item to the left end of the deque.
     *
     * @param item item to be added
     */
    @Override
    public void pushLeft(E item) {
        // case: size is 0, need to assign new head and tail
        if (size == 0) {
            head = tail = new Node(item);
            size++;
            return;
        }

        // case: size is 1, we need to decouple head and tail and make them point to each other
        else if (size == 1) {
            head = new Node(item, null, tail);
            tail.prev = head;
            size++;
            return;
        }

        // case: any other size
        else {
            head = head.next = new Node(item, null, head);
        }

        size++;
    }

    /**
     * Add an item to the right end of the deque.
     * O(1), adding right is instant
     * @param item
     */
    @Override
    public void pushRight(E item) {
        // case: size is 0, need to assign new head and tail
        if (size == 0) {
            head = tail = new Node(item);
            size++;
        }

        // case: size is 1, we need to decouple head and tail and make them point to each other
        else if (size == 1) {
            tail = new Node(item, null, head);
            head.next = tail;
        }

        // case: any other size
        else {

            tail = tail.next = new Node(item, tail, null);
        }
        size++;
    }

    /**
     * Remove an item from the left end of the deque.
     *
     * O(1), left access is instant
     *
     * @return
     */
    @Override
    public E popLeft() {
        if (head == null) throw new NoSuchElementException("No elements left in queue.");

        E data = head.data;

        size--;

        // case: size is 0, the resulting dequeue will be null;
        if (size == 0) {
            head = tail = null;
        }

        // case: size is 1, head will point to tail.
        else if (size == 1) {
            head = tail;

            // erase references to prev/next
            head.next = head.prev = null;
        }

        // case: any other size
        else {
            // move head up one node
            head = head.next;
            //and null its reference to the previous node
            head.prev = null;
        }

        return data;
    }

    /**
     * Remove an item from the right end of the deque.
     *
     *  O(1), contrast to something like a linked list, we can instantly access the right end of a dequeue
     *
     * @return
     */
    @Override
    public E popRight() {
        if (tail == null) throw new NoSuchElementException("No elements left in queue.");

        E data = tail.data;

        size--;

        // case: size is 0, the resulting dequeue will be null;
        if (size == 0) {
            head = tail = null;
        }

        // case: size is 1, tail will point to head.
        else if (size == 1) {
            tail = head;

            // erase references to prev/next
            // dont really need to change anything here. head and tail are the same now
            head.next = head.prev = null;
        }

        // case: any other size
        else {
            // move head back one node
            tail = tail.prev;
            // and null its reference to the next node
            tail.next = null;
        }

        return data;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * O(n), as usual, an iterator will always pass a list once and once only unless break occurs.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node cur = head;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public E next() {
                E data = cur.data;
                cur = cur.next;
                return data;
            }
        };
    }
}