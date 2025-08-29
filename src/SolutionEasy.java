import java.util.*;

public class SolutionEasy {

    public int minOperations(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        return sum % k;
    }

    public int subarraySum(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int start = Math.max(0, i - nums[i]);
            for (int j = start; j <= i; j++) {
                ans += nums[j];
            }
        }
        return ans;
    }
}
