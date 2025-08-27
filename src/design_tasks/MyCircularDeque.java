package design_tasks;

import java.util.ArrayList;

class MyCircularDeque {

    ArrayList<Integer> list;
    int max;

    public MyCircularDeque(int k) {
        list = new ArrayList<>(k);
        max = k;
    }

    public boolean insertFront(int value) {
        if (isFull()) return false;
        list.add(0, value);
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) return false;
        list.add(value);
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;
        list.remove(0);
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;
        list.remove(list.size() - 1);
        return true;
    }

    public int getFront() {
        return list.isEmpty() ? -1 : list.get(0);
    }

    public int getRear() {
        return list.isEmpty() ? -1 : list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean isFull() {
        return list.size() == max;
    }
}