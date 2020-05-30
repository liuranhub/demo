package com.unisinsight.demo.support.list;

import javax.validation.constraints.NotNull;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;



    public void add(T t) {
        Node<T> node = new Node<>(t);
        if (first == null) {
            first = node;
            last = node;
            return;
        }
        node.previous = last;
        last.next = node;
        last = last.next;
    }

    @Override
    @NotNull public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current;

            @Override
            public boolean hasNext() {
                if (current == null && first == null) {
                    return false;
                }

                if (current != null && current.next == null) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public T next() {
                if (first == null) {
                    throw new RuntimeException("not element");
                } else {
                    if (current == null) {
                        current = first;
                    } else {
                        if (current.next == null) {
                            throw new RuntimeException("not element");
                        }
                        current = current.next;
                    }
                }
                return current.value;
            }

            @Override
            public void remove() {

            }
        };
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        Node(T t){
            this.value = t;
        }
    }
}
