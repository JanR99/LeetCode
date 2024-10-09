import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
        new LeetCode().areSentencesSimilar("A lot", "A lot of words");
    }

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

    public int minChanges(int n, int k) {
        String sN = Integer.toBinaryString(n);
        String sK = Integer.toBinaryString(k);
        int max = Math.max(sN.length(), sK.length());
        sN = "0".repeat(max - sN.length()) + sN;
        sK = "0".repeat(max - sK.length()) + sK;
        int ans = 0;
        for (int i = 0; i < max; i++) {
            if (sN.charAt(i) == '0' && sK.charAt(i) == '1') return -1;
            else if (sN.charAt(i) == '1' && sK.charAt(i) == '0') ans++;
        }
        return ans;
    }

    public int numberOfChild(int n, int k) {
        int i = 0;
        boolean right = true;
        while (k != 0) {
            if (right && i == n - 1) {
                right = false;
                i--;
            } else if (!right && i == 0) {
                right = true;
                i++;
            } else if (!right) {
                i--;
            } else {
                i++;
            }
            k--;
        }
        return i;
    }

    public int winningPlayerCount(int n, int[][] pick) {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int ans = 0;
        for (int[] current : pick) {
            Map<Integer, Integer> currentMap = map.getOrDefault(current[0], new HashMap<>());
            currentMap.put(current[1], currentMap.getOrDefault(current[1], 0) + 1);
            map.put(current[0], currentMap);
            if (!queue.contains(current[0])) queue.add(current[0]);
        }
        while (!queue.isEmpty()) {
            int get = queue.poll();
            Map<Integer, Integer> currentMap = map.get(get);
            for (Map.Entry<Integer, Integer> entry : currentMap.entrySet()) {
                if (entry.getValue() > get) {
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }

    public String getSmallestString(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            int front = Character.getNumericValue(s.charAt(i));
            int back = Character.getNumericValue(s.charAt(i + 1));
            if (front > back && front % 2 == back % 2) {
                return s.substring(0, i) + back + front + s.substring(i + 2);
            }
        }
        return s;
    }

    public String losingPlayer(int x, int y) {
        boolean alice = false;
        while (x > 0 && y > 3) {
            alice = !alice;
            x--;
            y -= 4;
        }
        return alice ? "Alice" : "Bob";
    }

    public boolean satisfiesConditions(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i + 1 != grid.length && grid[i][j] != grid[i + 1][j]) return false;
                if (j + 1 != grid[i].length && grid[i][j] == grid[i][j + 1]) return false;
            }
        }
        return true;
    }

    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(maxHeightOfTriangleHelper(red, blue), maxHeightOfTriangleHelper(blue, red));
    }

    private int maxHeightOfTriangleHelper(int first, int second) {
        int current = 1;
        boolean firstTurn = true;
        while (true) {
            if (firstTurn) {
                if (first < current) break;
                first -= current;
            } else {
                if (second < current) break;
                second -= current;
            }
            current++;
            firstTurn = !firstTurn;
        }
        return current - 1;
    }

    public boolean isValid(String word) {
        if (word.length() < 3) return false;
        boolean vowelFound = false;
        boolean consonantFound = false;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (!Character.isLetterOrDigit(c)) return false;
            if (Character.isLetter(c)) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') vowelFound = true;
                else consonantFound = true;
            }
        }
        return consonantFound && vowelFound;
    }

    public int minElement(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            String s = String.valueOf(num);
            int current = 0;
            for (char c : s.toCharArray()) {
                current += Character.getNumericValue(c);
            }
            min = Math.min(min, current);
        }
        return min;
    }

    public char kthCharacter(int k) {
        StringBuilder s = new StringBuilder("a");
        while (s.length() < k) {
            StringBuilder append = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'z') append.append("a");
                else append.append((char)(s.charAt(i) + 1));
            }
            s.append(append);
        }
        return s.charAt(k - 1);
    }

    public long dividePlayers(int[] skill) {
        Arrays.sort(skill);
        int i = 0, j = skill.length - 1;
        long ans = (long) skill[i] * skill[j];
        int teamSkill = skill[i++] + skill[j--];
        while (i < j) {
            ans += (long) skill[i] * skill[j];
            int currentSkill = skill[i++] + skill[j--];
            if (currentSkill != teamSkill) return -1;
        }
        return ans;
    }

    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        return sentence1.length() < sentence2.length()
                ? areSentencesSimilarHelper(sentence1, sentence2)
                : areSentencesSimilarHelper(sentence2, sentence1);
    }

    public boolean areSentencesSimilarHelper(String sentence1, String sentence2) {
        String[] strings1 = sentence1.split(" ");
        String[] strings2 = sentence2.split(" ");
        int i = 0, j = strings1.length - 1;
        int k = 0, l = strings2.length - 1;
        while (i <= j) {
            if (strings1[i].equals(strings2[k])) {
                i++;
                k++;
            } else if (strings1[j].equals(strings2[l])) {
                j--;
                l--;
            } else {
                return false;
            }
        }
        return true;
    }

    public int minAddToMakeValid(String s) {
        int openCount = 0;
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                openCount++;
            } else {
                if (openCount == 0) ans ++;
                else openCount--;
            }
        }
        return ans + openCount;
    }
}
