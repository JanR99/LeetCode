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

    public int[] minCosts(int[] cost) {
        int[] ans = new int[cost.length];
        ans[0] = cost[0];
        for (int i = 1; i < ans.length; i++) {
            ans[i] = Math.min(ans[i - 1], cost[i]);
        }
        return ans;
    }

    public boolean isBalanced(String num) {
        int evenSum = 0, oddSum = 0;
        for (int i = 0; i < num.length(); i++) {
            int digit = Character.getNumericValue(num.charAt(i));
            if (i % 2 == 0) {
                evenSum += digit;
            } else {
                oddSum += digit;
            }
        }
        return evenSum == oddSum;
    }
}
