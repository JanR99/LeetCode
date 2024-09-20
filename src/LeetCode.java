import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCode {

    public double minimumAverage(int[] nums) {
        double ans = Double.MAX_VALUE;
        int count = 0;
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;
        while (i < j) {
            double current = (double) (nums[i++] + nums[j--]) / 2.0;
            ans = Math.min(current, ans);
        }
        return ans;
    }

    public boolean canAliceWin(int[] nums) {
        int single = 0, doubles = 0;
        for (int num : nums) {
            if (num < 10) {
                single += num;
            } else {
                doubles += num;
            }
        }
        return doubles > single || single > doubles;
    }

    public int finalPositionOfSnake(int n, List<String> commands) {
        int i = 0, j = 0;
        for (String s : commands) {
            switch (s) {
                case "UP":
                    i -= 1;
                    break;
                case "DOWN":
                    i += 1;
                    break;
                case "LEFT":
                    j -= 1;
                    break;
                case "RIGHT":
                    j += 1;
                    break;
                default:
                    return -1;
            }
        }
        return i * n + j;
    }

    public int duplicateNumbersXOR(int[] nums) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                ans ^= entry.getKey();
            }
        }
        return ans;
    }

    public int[] getFinalState(int[] nums, int k, int multiplier) {
        for (int i = 0; i < k; i++) {
            int minIndex = -1;
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] < min) {
                    min = nums[j];
                    minIndex = j;
                }
            }
            nums[minIndex] *= multiplier;
        }
        return nums;
    }

    public int minimumChairs(String s) {
        int max = 0;
        int current = 0;
        for (char c : s.toCharArray()) {
            switch (c) {
                case 'E':
                    current++;
                    break;
                case 'L':
                    current--;
            }
            max = Math.max(max, current);
        }
        return max;
    }

    public int countKConstraintSubstrings(String s, int k) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int zerosCount = 0;
            int onesCount = 0;
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(j) == '0') zerosCount++;
                else onesCount++;
                if (zerosCount > k && onesCount > k) break;
                ans++;
            }
        }

        return ans;
    }
}
