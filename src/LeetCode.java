import java.util.*;

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

    public int countCompleteDayPairs(int[] hours) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        int ans = 0;
        for (int hour : hours) {
            int current = hour % 24;
            map.put(current, map.getOrDefault(current, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int current = entry.getKey();
            int count = entry.getValue();
            if (current == 12) {
                ans += (count * (count - 1)) / 2;
                visited.add(current);
            } else if (current == 0) {
                ans += (count * (count - 1)) / 2;
                visited.add(current);
            } else if (!visited.contains(current)) {
                int pairCount = map.getOrDefault(24 - current, 0);
                if (pairCount > 0) {
                    ans += count * pairCount;
                }
                visited.add(current);
                visited.add(24 - current);
            }
        }
        return ans;
    }

    public int generateKey(int num1, int num2, int num3) {
        StringBuilder ans = new StringBuilder();
        String s1 = String.valueOf(num1);
        String s2 = String.valueOf(num2);
        String s3 = String.valueOf(num3);
        int max = Math.max(s1.length(), Math.max(s2.length(), s3.length()));
        s1 = "0".repeat(max - s1.length()) + s1;
        s2 = "0".repeat(max - s2.length()) + s2;
        s3 = "0".repeat(max - s3.length()) + s3;
        for (int i = 0; i < max; i++) {
            ans.append(Character.getNumericValue(Math.min(s1.charAt(i), Math.min(s2.charAt(i), s3.charAt(i)))));
        }
        return Integer.parseInt(ans.toString());
    }

    public String clearDigits(String s) {
        StringBuilder ans = new StringBuilder(s);
        int alphabeticCount = 0;
        int i = 0;
        while (i < ans.length()) {
            if (Character.isDigit(ans.charAt(i))) {
                clearDigitsHelper(ans, i, alphabeticCount);
                i -= 1;
                alphabeticCount--;
            } else {
                alphabeticCount++;
                i++;
            }
        }
        return ans.toString();
    }

    public void clearDigitsHelper(StringBuilder s, int i, int alphabeticCount) {
        s.deleteCharAt(i);
        if (alphabeticCount <= 0) return;
        for (int j = i - 1; j >= 0; j--) {
            if (Character.isAlphabetic(s.charAt(j))) {
                s.deleteCharAt(j);
                return;
            }
        }
    }

    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        return checkTwoChessboardsHelper(coordinate1) == checkTwoChessboardsHelper(coordinate2);
    }

    private boolean checkTwoChessboardsHelper(String coordinate) {
        return (coordinate.charAt(0) % 2) == (coordinate.charAt(1) % 2);
    }

    public String getEncryptedString(String s, int k) {
        int cycle = k % s.length();
        return s.substring(cycle) + s.substring(0, cycle);
    }

    public boolean isArraySpecial(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] % 2 == nums[i + 1] % 2) return false;
        }
        return true;
    }

    public int numberOfAlternatingGroups(int[] colors) {
        int ans = 0;
        for (int i = 0; i < colors.length; i++) {
            int current = colors[i];
            int next = colors[(i + 1) % colors.length];
            int nextNext = colors[(i + 2) % colors.length];
            if (current == nextNext && current != next) ans++;
        }
        return ans;
    }
}
