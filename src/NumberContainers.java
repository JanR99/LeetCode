import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class NumberContainers {

    private final Map<Integer, Integer> indices;
    private final Map<Integer, PriorityQueue<Integer>> numbers;

    public NumberContainers() {
        indices = new HashMap<>();
        numbers = new HashMap<>();
    }

    public void change(int index, int number) {
        if (indices.containsKey(index)) {
            int oldNumber = indices.get(index);
            if (numbers.containsKey(oldNumber)) {
                numbers.get(oldNumber).add(index);
            }
        }
        indices.put(index, number);
        numbers.computeIfAbsent(number, k -> new PriorityQueue<>()).add(index);
    }

    public int find(int number) {
        if (!numbers.containsKey(number)) return -1;
        PriorityQueue<Integer> queue = numbers.get(number);
        while (!queue.isEmpty() && indices.get(queue.peek()) != number) {
            queue.poll();
        }
        return queue.isEmpty() ? -1 : queue.peek();
    }
}
