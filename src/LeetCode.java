import java.util.Arrays;

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


}
