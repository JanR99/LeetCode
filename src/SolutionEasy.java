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

    public boolean hasSameDigits(String s) {
        int size = s.length();
        int[] values = new int[size];
        for (int i = 0; i < s.length(); i++) {
            values[i] = Character.getNumericValue(s.charAt(i));
        }

        while (size > 2) {
            for (int i = 0; i < size - 1; i++) {
                values[i] = (values[i] + values[i + 1]) % 10;
            }
            size--;
        }

        return values[0] == values[1];
    }

    public int smallestNumber(int n) {
        int ans = 1;
        while (ans < n) {
            ans = (ans << 1) | ans;
        }
        return ans;
    }

    public int maxAdjacentDistance(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int j = (i + 1) % nums.length;
            max = Math.max(max, Math.abs(nums[j] - nums[i]));
        }
        return max;
    }

    public int maxContainers(int n, int w, int maxWeight) {
        return Math.min(n * n, maxWeight / 2);
    }

    public int[] minBitwiseArray(List<Integer> nums) {
        int[] ans = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            int prime = nums.get(i);
            int val = -1;
            for (int num = 0; num <= prime; num++) {
                if ((num | (num + 1)) == prime) {
                    val = num;
                    break;
                }
            }
            ans[i] = val;
        }
        return ans;
    }

    public int[][] allCellsDistOrder(int rows, int cols, int rCenter, int cCenter) {
        int[][] ans = new int[rows * cols][2];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ans[index++] = new int[]{i, j};
            }
        }

        Arrays.sort(ans, (a, b) -> {
            int distA = Math.abs(a[0] - rCenter) + Math.abs(a[1] - cCenter);
            int distB = Math.abs(b[0] - rCenter) + Math.abs(b[1] - cCenter);
            return Integer.compare(distA, distB);
        });

        return ans;
    }

    public int convertTime(String current, String correct) {
        int currentTime = Integer.parseInt(current.substring(0, 2)) * 60 + Integer.parseInt(current.substring(3));
        int correctTime = Integer.parseInt(correct.substring(0, 2)) * 60 + Integer.parseInt(correct.substring(3));
        int[] arr = {1, 5, 15, 60};
        int i = 3;
        int count = 0;
        while (currentTime != correctTime) {
            if (currentTime < correctTime) {
                count++;
                currentTime += arr[i];
            }
            if (currentTime > correctTime) {
                currentTime -= arr[i];
                i--;
                count--;
            }
        }
        return count;
    }

    public int minOperations2(int[] nums, int k) {
        int count = 0;
        while (true) {
            int max = Integer.MIN_VALUE;
            int h = Integer.MIN_VALUE;
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                if (num < k) return -1;
                if (num == max) {
                    indices.add(i);
                } else if (num > max) {
                    h = max;
                    max = num;
                    indices.clear();
                    indices.add(i);
                } else if (num > h) {
                    h = num;
                }
            }
            boolean allK = true;
            for (int num : nums) {
                if (num != k) {
                    allK = false;
                    break;
                }
            }
            if (allK) break;

            if (h == Integer.MIN_VALUE) {
                h = k;
            }

            for (int index : indices) {
                nums[index] = h;
            }
            count++;
        }
        return count;
    }

    public int countPartitions(int[] nums) {
        int left = nums[0];
        int right = 0;
        for (int i = 1; i < nums.length; i++) {
            right += nums[i];
        }

        int count = 0;
        if ((left % 2) == (right % 2)) {
            count++;
        }

        for (int i = 1; i < nums.length - 1; i++) {
            left += nums[i];
            right -= nums[i];
            if ((left % 2) == (right % 2)) {
                count++;
            }
        }
        return count;
    }

    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];

        for (int i = 0; i <= n - k; ++i) {
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int j = i; j < i + k; ++j) {
                cnt.put(nums[j], cnt.getOrDefault(nums[j], 0) + 1);
            }

            List<int[]> freq = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
                freq.add(new int[] { entry.getValue(), entry.getKey() });
            }

            freq.sort((a, b) -> b[0] != a[0] ? b[0] - a[0] : b[1] - a[1]);
            int xsum = 0;
            for (int j = 0; j < x && j < freq.size(); ++j) {
                xsum += freq.get(j)[0] * freq.get(j)[1];
            }
            ans[i] = xsum;
        }

        return ans;
    }

    public int possibleStringCount(String word) {
        int ans = 1;
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == word.charAt(i + 1)) {
                ans++;
            }
        }
        return ans;
    }

    public int smallestNumber(int n, int t) {
        for (int i = n; i < Integer.MAX_VALUE; i++) {
            String s = Integer.toString(i);
            int prod = 1;
            for (char c : s.toCharArray()) {
                prod *= c - '0';
            }
            if (prod % t == 0) return i;
        }
        return Integer.MAX_VALUE;
    }

    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        for (int i = 0; i + 2 * k <= nums.size(); i++) {
            int prev1 = nums.get(i);
            int prev2 = nums.get(i + k);
            boolean valid = true;
            for (int j = 1; j < k; j++) {
                int cur1 = nums.get(i + j);
                int cur2 = nums.get(i + j + k);
                if (cur1 <= prev1 || cur2 <= prev2) {
                    valid = false;
                    break;
                }
                prev1 = cur1;
                prev2 = cur2;
            }
            if (valid) return true;
        }
        return false;
    }

    public List<Integer> findMissingElements(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num < min) min = num;
            if (num > max) max = num;
            set.add(num);
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = min; i < max; i++) {
            if (!set.contains(i)) ans.add(i);
        }
        return ans;
    }

    public int mirrorDistance(int n) {
        return Math.abs(n - Integer.parseInt(new StringBuilder(String.valueOf(n)).reverse().toString()));
    }

    public int[] sortByReflection(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, Integer.parseInt(new StringBuilder(Integer.toBinaryString(num)).reverse().toString(), 2));
        }

        Integer[] boxed = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(boxed, Comparator.comparingInt((Integer a) -> map.get(a)).thenComparingInt(a -> a));
        return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
    }

    public int countValidSelections(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) continue;
            ans += countValidSelections(nums, i, true) + countValidSelections(nums, i, false);
        }
        return ans;
    }

    public int countValidSelections(int[] nums, int curr, boolean start) {
        nums = nums.clone();
        boolean left = start;
        while (curr >= 0 && curr < nums.length) {
            if (nums[curr] == 0) {
                if (left) curr--;
                else curr++;
                continue;
            }
            nums[curr]--;
            left = !left;
            curr += left ? -1 : 1;
        }
        for (int num : nums) {
            if (num != 0) return 0;
        }
        return 1;
    }

    public boolean canAliceWin(int n) {
        int take = 10;
        while (true) {
            if (n - take < 0) {
                return take % 2 != 0;
            }
            n -= take--;
        }
    }

    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int ans = -1;
        for (int i = 0; i < nums.size(); i++) {
            int current = 0;
            for (int j = i; j < nums.size() && j < i + r; j++) {
                current += nums.get(j);
                int len = j - i + 1;
                if (len >= l && current > 0) {
                    if (ans == -1 || current < ans) {
                        ans = current;
                    }
                }
            }
        }
        return ans;
    }

    public int[] constructTransformedArray(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                ans[i] = 0;
            } else if (nums[i] > 0) {
                ans[i] = nums[(i + nums[i]) % nums.length];
            } else {
                int idx = (i + nums[i]) % nums.length;
                if (idx < 0) idx += nums.length;
                ans[i] = nums[idx];
            }
        }
        return ans;
    }

    public int buttonWithLongestTime(int[][] events) {
        int maxIndex = events[0][0];
        int maxTime = events[0][1];
        int timeBefore = events[0][1];
        for (int i = 1; i < events.length; i++) {
            int newTime = events[i][1] - timeBefore;
            if (newTime > maxTime || (newTime == maxTime && events[i][0] < maxIndex)) {
                maxTime = newTime;
                maxIndex = events[i][0];
            }
            timeBefore = events[i][1];
        }
        return maxIndex;
    }

    public int countSubarrays(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            double sum = nums[i] + nums[i + 2];
            double middle = nums[i + 1] / 2.0;
            if (sum == middle) ans++;
        }
        return ans;
    }

    public int minimumOperations(int[] nums) {
        int ans = 0;
        int i = 0;
        while (i < nums.length) {
            Set<Integer> seen = new HashSet<>();
            boolean unique = true;
            for (int j = i; j < nums.length; j++) {
                if (seen.contains(nums[j])) {
                    unique = false;
                    break;
                }
                seen.add(nums[j]);
            }
            if (unique) return ans;
            i += 3;
            ans++;
        }
        return ans;
    }

    public int minimumOperations(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid[0].length; i++) {
            int before = -1;
            for (int j = 0; j < grid.length; j++) {
                if (before >= grid[j][i]) {
                    int plus = before - grid[j][i] + 1;
                    grid[j][i] += plus;

                    ans += plus;
                }
                before = grid[j][i];
            }
        }
        return ans;
    }

    public boolean hasMatch(String s, String p) {
        int star = p.indexOf('*');
        String prefix = p.substring(0, star);
        String suffix = p.substring(star + 1);

        int start = s.indexOf(prefix);
        if (start == -1) return false;

        int end = s.indexOf(suffix, start + prefix.length());
        return end != -1;
    }

}
