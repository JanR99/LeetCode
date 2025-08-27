package design_tasks;

import java.util.Iterator;
import java.util.LinkedList;

class PeekingIterator implements Iterator<Integer> {

    private final LinkedList<Integer> stack;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.stack = new LinkedList<>();
        iterator.forEachRemaining(stack::add);
    }

    public Integer peek() {
        return stack.peek();
    }

    @Override
    public Integer next() {
        return stack.pop();
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}