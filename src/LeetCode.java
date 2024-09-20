import java.util.Arrays;
import java.util.List;

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
}
