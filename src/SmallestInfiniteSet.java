import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class SmallestInfiniteSet {
    private int current;
    private final PriorityQueue<Integer> queue;
    private final Set<Integer> visited;

    public SmallestInfiniteSet() {
        current = 1;
        queue = new PriorityQueue<>();
        visited = new HashSet<>();
    }

    public int popSmallest() {
        if (!queue.isEmpty()) {
            int smallest = queue.poll();
            visited.remove(smallest);
            return smallest;
        }
        return current++;
    }

    public void addBack(int num) {
        if (num < current && !visited.contains(num)) {
            queue.offer(num);
            visited.add(num);
        }
    }
}