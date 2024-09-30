class CustomStack {

    int[] stack;
    int size;
    int pointer;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        size = 0;
        pointer = -1;
    }

    public void push(int x) {
        if (pointer == stack.length - 1) {
            return;
        }
        pointer++;
        stack[pointer] = x;
        size++;
    }

    public int pop() {
        if (isEmpty()) return -1;
        int ans = stack[pointer];
        stack[pointer] = -1;
        pointer--;
        size--;
        return ans;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void increment(int k, int val) {
        for (int i = 0; i < k && i < size; i++) {
            stack[i] += val;
        }
    }
}