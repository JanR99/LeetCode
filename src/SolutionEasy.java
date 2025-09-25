import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public int[] recoverOrder(int[] order, int[] friends) {
        Set<Integer> set = Arrays.stream(friends).boxed().collect(Collectors.toSet());
        int[] ans = new int[friends.length];

        int index = 0;
        for (int pos : order) {
            if (index == ans.length) break;
            if (set.contains(pos)) {
                ans[index++] = pos;
            }
        }
        return ans;
    }

    public int gcdOfOddEvenSums(int n) {
        int sumOdd = 0, sumEven = 0;
        int odd = 1, even = 2, i = 0;
        while (i < n) {
            sumOdd += odd;
            sumEven += even;
            odd += 2;
            even += 2;
            i++;
        }
        return gcd(sumOdd, sumEven);
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public int earliestTime(int[][] tasks) {
        int ans = Integer.MAX_VALUE;
        for (int[] task : tasks) {
            int current = task[0] + task[1];
            ans = Math.min(ans, current);
        }
        return ans;
    }

    public String concatHex36(int n) {
        String hex = Integer.toString((int) Math.pow(n, 2), 16);
        String triHex = Integer.toString((int) Math.pow(n, 3), 36);
        return (hex + triHex).toUpperCase();
    }

    public int smallestIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int digitSum = 0;
            String s = String.valueOf(nums[i]);
            for (char c : s.toCharArray()) {
                digitSum += Character.getNumericValue(c);
            }
            if (digitSum == i) {
                return i;
            }
        }
        return -1;
    }

    public int evenNumberBitwiseORs(int[] nums) {
        AtomicInteger or = new AtomicInteger();
        Arrays.stream(nums).filter(n -> n % 2 == 0).forEach(n -> or.updateAndGet(v -> v | n));
        return or.get();
    }
}
