package design_tasks;

import java.util.TreeMap;

class MyCalendarThree {

    private final TreeMap<Integer, Integer> timeline;

    public MyCalendarThree() {
        timeline = new TreeMap<>();
    }

    public int book(int startTime, int endTime) {
        timeline.put(startTime, timeline.getOrDefault(startTime, 0) + 1);
        timeline.put(endTime, timeline.getOrDefault(endTime, 0) - 1);

        int active = 0, max = 0;
        for (int val : timeline.values()) {
            active += val;
            max = Math.max(max, active);
        }
        return max;
    }
}