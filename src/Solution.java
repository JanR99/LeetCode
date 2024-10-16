import animals.Animal;
import animals.Dog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

class Solution {

    private record Person(String firstname, String lastname, List<Integer> list, Test test) {

    }

    private static class Test {
        String s;
        Long l;

        public Test(String s, Long l) {
            this.s = s;
            this.l = l;
        }

        @Override
        public String toString() {
            return "[s=" + s + ", l=" + l + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test t = (Test) o;
            return this.s.equals(t.s) && this.l.equals(t.l);
        }
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        printSomething(dog);
    }

    public static void printSomething(Animal animal) {
        System.out.println(animal.getClass().getSimpleName());
    }

    Map<Integer, Integer> sumOfMultiplesMem = new HashMap<>();

    public int sumOfMultiples(int n) {
        int sum = 0;
        if (sumOfMultiplesMem.containsKey(n)) return sumOfMultiplesMem.get(n);
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0)
                sum += i;
            sumOfMultiplesMem.put(i, sum);
        }
        return sum;
    }

    public int maxDivScore(int[] nums, int[] divisors) {
        int max = 0;
        int index = -1;
        for (int divisor : divisors) {
            int current = 0;
            for (int num : nums) {
                if (num % divisor == 0)
                    current++;
            }
            if (index == -1) {
                index = divisor;
                max = current;
            } else if (max == current && index > divisor) {
                index = divisor;
            } else if (max < current) {
                max = current;
                index = divisor;
            }
        }
        return index;
    }

    public int[] findMode(TreeNode root) {
        HashMap<Integer, Integer> values = new HashMap<>();
        findModeHelper(root, values);
        LinkedList<Integer> modes = new LinkedList<>();
        int max = Integer.MIN_VALUE;
        for (int key : values.keySet()) {
            if (max < values.get(key)) {
                modes.clear();
                max = values.get(key);
                modes.add(key);
            } else if (max == values.get(key)) {
                modes.add(key);
            }
        }
        int[] ans = new int[modes.size()];
        for (int i = 0; i < modes.size(); i++)
            ans[i] = modes.get(i);
        return ans;
    }

    private void findModeHelper(TreeNode node, HashMap<Integer, Integer> values) {
        if (node == null) return;
        values.put(node.val, values.getOrDefault(node.val, 0) + 1);
        findModeHelper(node.left, values);
        findModeHelper(node.right, values);
    }

    public boolean findTarget(TreeNode root, int k) {
        LinkedList<Integer> list = new LinkedList<>();
        findTargetHelper(root, list);
        for (int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            // Two elements found x + y == k
            if (list.contains(k - current)) {
                // If x == y => look if it is the same element
                if (k - current == current) {
                    return list.lastIndexOf(current) != list.indexOf(current);
                }
                return true;
            }
        }
        return false;
    }

    private void findTargetHelper(TreeNode root, LinkedList<Integer> list) {
        if (root == null) return;
        findTargetHelper(root.left, list);
        list.add(root.val);
        findTargetHelper(root.right, list);
    }

    public boolean hasAlternatingBits(int n) {
        String binary = Integer.toBinaryString(n);
        char before = binary.charAt(0);
        for (int i = 1; i < binary.length(); i++) {
            if (before == binary.charAt(i)) return false;
            before = binary.charAt(i);
        }
        return true;
    }

    public boolean isLongPressedName(String name, String typed) {
        int i = 0, n = name.length(), m = typed.length();
        for (int j = 0; j < m; j++)
            if (i < n && name.charAt(i) == typed.charAt(j))
                ++i;
            else if (j == 0 || typed.charAt(j) != typed.charAt(j - 1))
                return false;
        return i == n;
    }

    public int findPoisonedDuration(int[] t, int duration) {
        int total = 0;
        for (int i = 0; i < t.length - 1; i++) {
            total += t[i] + duration < t[i + 1] ? duration : (t[i + 1] - t[i]);
        }
        return total + duration;
    }

    public boolean backspaceCompare(String s, String t) {
        StringBuilder sb = backspaceCompareHelper(s);
        StringBuilder tb = backspaceCompareHelper(t);
        return sb.toString().contentEquals(tb);
    }

    private StringBuilder backspaceCompareHelper(String s) {
        StringBuilder sb = new StringBuilder();
        int countBackspace = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '#') {
                countBackspace++;
                continue;
            }
            if (countBackspace > 0) {
                countBackspace--;
                continue;
            }
            sb.append(c);
        }
        return sb;
    }

    public int partitionString(String s) {
        boolean[] chars = new boolean[26];
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (chars[c - 'a']) {
                ans++;
                Arrays.fill(chars, false);
            }
            chars[c - 'a'] = true;
        }
        return ans + 1;
    }

    public int[] numberOfLines(int[] widths, String s) {
        int lines = 1;
        int currentLength = 0;
        for (char c : s.toCharArray()) {
            if (currentLength + widths[c - 'a'] > 100) {
                currentLength = widths[c - 'a'];
                lines++;
            } else {
                currentLength += widths[c - 'a'];
            }
        }
        return new int[]{lines, currentLength};
    }

    public String makeSmallestPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        char[] chars = s.toCharArray();
        while (i <= j) {
            if (chars[i] != chars[j]) {
                if (chars[i] < chars[j]) {
                    chars[j] = chars[i];
                } else {
                    chars[i] = chars[j];
                }
            }
            i++;
            j--;
        }
        return new String(chars);
    }

    public int countSeniors(String[] details) {
        int ans = 0;
        for (String s : details) {
            int age = Integer.parseInt(s.charAt(11) + "" + s.charAt(12), 10);
            if (age > 60) ans++;
        }
        return ans;
    }

    public int diagonalPrime(int[][] nums) {
        int prime = 0;
        int i = 0, j = 0;
        while (i < nums.length && j < nums[i].length) {
            if (nums[i][j] > prime && diagonalPrimeHelper(nums[i][j]))
                prime = nums[i][j];
            i++;
            j++;
        }
        i = 0;
        j = nums[0].length - 1;
        while (i < nums.length && j > 0) {
            if (nums[i][j] > prime && diagonalPrimeHelper(nums[i][j]))
                prime = nums[i][j];
            i++;
            j--;
        }
        return prime;
    }

    private boolean diagonalPrimeHelper(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        int sqrtN = (int) Math.sqrt(n) + 1;
        for (int i = 6; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0) return false;
        }
        return true;
    }

    public int minNumber(int[] nums1, int[] nums2) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int equal = Integer.MAX_VALUE;
        for (int k : nums1) {
            if (k < min1) min1 = k;
            for (int i : nums2) {
                if (min2 > i) min2 = i;
                if (k == i && equal > k) equal = k;
            }
        }
        if (equal != Integer.MAX_VALUE) return equal;
        return Math.min(min1 * 10 + min2, min2 * 10 + min1);
    }

    public String removeTrailingZeros(String num) {
        StringBuilder reversed = new StringBuilder(num).reverse();
        int i = 0;
        while (true) {
            if (reversed.charAt(i) == '0') {
                i++;
            } else {
                break;
            }
        }
        return new StringBuilder(reversed.substring(i)).reverse().toString();
    }

    public int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        if (prices[0] + prices[1] > money) return money;
        return money - (prices[0] + prices[1]);
    }

    public String bestHand(int[] ranks, char[] suits) {
        Map<Character, Integer> suitsMap = new HashMap<>();
        Map<Integer, Integer> rankMap = new HashMap<>();
        for (int i = 0; i < ranks.length; i++) {
            suitsMap.put(suits[i], suitsMap.getOrDefault(suits[i], 0) + 1);
            rankMap.put(ranks[i], rankMap.getOrDefault(ranks[i], 0) + 1);
        }
        for (char c : suitsMap.keySet()) {
            if (suitsMap.get(c) == 5) return "Flush";
        }
        int maxSame = 0;
        for (int c : rankMap.keySet()) {
            if (maxSame < rankMap.get(c))
                maxSame = rankMap.get(c);
        }
        if (maxSame >= 3)
            return "Three of a Kind";
        if (maxSame == 2)
            return "Pair";
        return "High Card";
    }

    public int countValidWords(String sentence) {
        String[] words = sentence.split(" ");
        int ans = 0;
        for (String word : words) {
            if (word.matches("^\\s*$")) continue;
            boolean isOk = true;
            int amountHyphen = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isDigit(c)) {
                    isOk = false;
                    break;
                }
                if (c == '-') {
                    if (i == 0 || i == word.length() - 1) {
                        isOk = false;
                        break;
                    }
                    if (!Character.isLowerCase(word.charAt(i - 1)) || !Character.isLowerCase(word.charAt(i + 1))) {
                        isOk = false;
                        break;
                    }
                    if (amountHyphen != 0) {
                        isOk = false;
                        break;
                    }
                    amountHyphen++;
                }
                if (c == '!' || c == '.' || c == ',') {
                    if (i != word.length() - 1) {
                        isOk = false;
                        break;
                    }
                }
            }
            if (isOk) ans++;
        }
        return ans;
    }

    public int[] sortEvenOdd(int[] nums) {
        int[] even = new int[101];
        int[] odd = new int[101];
        int length = nums.length;
        for (int i = 0; i < length; ++i) {
            if (i % 2 == 0) {
                even[nums[i]]++;
            } else {
                odd[nums[i]]++;
            }
        }
        int e = 0;
        int o = 100;
        for (int i = 0; i < length; ++i) {
            if (i % 2 == 0) {
                // check even
                while (even[e] == 0) {
                    ++e;
                }
                nums[i] = e;
                even[e]--;
            } else {
                while (odd[o] == 0) {
                    --o;
                }
                nums[i] = o;
                odd[o]--;
            }
        }
        return nums;
    }

    public boolean isAlienSorted(String[] words, String order) {
        Map<Character, Integer> alphabet = new HashMap<>();

        // Build the alphabet mapping
        for (int i = 0; i < order.length(); i++) {
            alphabet.put(order.charAt(i), i);
        }

        // Compare adjacent words
        for (int i = 0; i < words.length - 1; i++) {
            if (!isAlienSortedHelper(words[i], words[i + 1], alphabet)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isAlienSortedHelper(String word1, String word2, Map<Character, Integer> alphabet) {
        int minLength = Math.min(word1.length(), word2.length());

        for (int i = 0; i < minLength; i++) {
            char ch1 = word1.charAt(i);
            char ch2 = word2.charAt(i);

            if (ch1 != ch2) {
                return alphabet.get(ch1) <= alphabet.get(ch2);
            }
        }

        return word1.length() <= word2.length();
    }

    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return validPalindromeHelper(s, i + 1, j) || validPalindromeHelper(s, i, j - 1);
            }
            i++;
            j--;
        }
        return true;
    }

    private boolean validPalindromeHelper(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public int maximizeSum(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > max) max = num;
        }
        int ans = 0;
        for (int i = 0; i < k; i++) {
            ans += max++;
        }
        return ans;
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length];
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new LinkedList<>();
        for (int k : arr2) set.add(k);
        for (int k : arr1) {
            if (set.contains(k)) {
                map.put(k, map.getOrDefault(k, 0) + 1);
            } else {
                list.add(k);
            }
        }
        int index = 0;
        for (int k : arr2) {
            for (int j = 0; j < map.get(k); j++) {
                res[index++] = k;
            }
        }
        Collections.sort(list);
        for (int i : list)
            res[index++] = i;
        return res;
    }

    public int[][] construct2DArray(int[] original, int m, int n) {
        if (m * n < original.length) return new int[][]{};
        int[][] ans = new int[m][n];
        int index = 0;
        try {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    ans[i][j] = original[index++];
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return new int[][]{};
        }
        return ans;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return (p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public String modifyString(String s) {
        char[] ans = s.toCharArray();
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == '?') {
                char prev = (i > 0) ? ans[i - 1] : ' ';
                char next = (i < ans.length - 1) ? ans[i + 1] : ' ';
                ans[i] = modifyStringHelper(prev, next);
            }
        }
        return new String(ans);
    }

    private char modifyStringHelper(char prev, char next) {
        for (char c = 'a'; ; c++) {
            if (c != prev && c != next) {
                return c;
            }
        }
    }

    public boolean kLengthApart(int[] nums, int k) {
        int dist = -1;
        for (int num : nums) {
            if (num == 0) {
                if (dist != -1) {
                    ++dist;
                }
            } else {
                if (dist < k && dist != -1)
                    return false;
                dist = 0;
            }
        }
        return true;
    }

    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int n = mat[0].length;
        if (r * c != mat.length * n) {
            return mat;
        }
        int[][] ans = new int[r][c];
        for (int i = 0; i < r * c; i++) {
            ans[i / c][i % c] = mat[i / n][i % n];
        }
        return ans;
    }

    public boolean buddyStrings(String s, String goal) {
        if (s.length() != goal.length()) return false;
        if (s.length() == 1) return false;
        int first = -1, second = -1;
        int[] chars = new int[26];
        for (int i = 0; i < s.length(); i++) {
            chars[s.charAt(i) - 'a']++;
            if (s.charAt(i) != goal.charAt(i)) {
                if (first == -1) {
                    first = i;
                } else if (second == -1) {
                    second = i;
                } else {
                    return false;
                }
            }
        }
        if (first == -1) {
            for (int i : chars) {
                if (i > 1) return true;
            }
            return false;
        }
        if (second == -1) return false;
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(first, s.charAt(second));
        sb.setCharAt(second, s.charAt(first));
        return sb.toString().equals(goal);
    }

    public int countBinarySubstrings(String s) {
        int ans = 0;
        int prevCount = 0;
        int currCount = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                currCount++;
            } else {
                ans += Math.min(prevCount, currCount);
                prevCount = currCount;
                currCount = 1;
            }
        }

        ans += Math.min(prevCount, currCount);
        return ans;
    }

    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> ans = new ArrayList<>(nums.length);
        int remainder = 0;
        for (int num : nums) {
            remainder = (remainder * 2 + num) % 5;
            ans.add(remainder == 0);
        }
        return ans;
    }

    public int pivotIndex(int[] nums) {
        int sum = 0;
        for (int num : nums)
            sum += num;
        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int rightSum = sum - leftSum - nums[i];
            if (leftSum == rightSum)
                return i;
            leftSum += nums[i];
        }
        return -1;
    }

    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() <= 1) return false;
        int n = s.length();
        for (int i = n / 2; i >= 1; i--) {
            if (n % i == 0) {
                int repeats = n / i;
                String substring = s.substring(0, i);
                StringBuilder sb = new StringBuilder();
                sb.append(substring.repeat(repeats));
                if (sb.toString().equals(s)) return true;
            }
        }
        return false;
    }

    public String removeDigit(String number, char digit) {
        List<String> digits = new ArrayList<>();
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == digit) {
                String str = number.substring(0, i) + number.substring(i + 1);
                digits.add(str);
            }
        }
        Collections.sort(digits);
        return digits.get(digits.size() - 1);
    }

    class KthLargest {
        private final PriorityQueue<Integer> minHeap;
        private final int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>(k);
            for (int num : nums)
                add(num);
        }

        public int add(int val) {
            if (minHeap.size() < k) {
                minHeap.offer(val);
            } else if (minHeap.peek() != null && val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek() == null ? -1 : minHeap.peek();
        }
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int ans = 0;
        for (int k : arr1) {
            boolean ok = true;
            for (int i : arr2) {
                if (Math.abs(k - i) <= d) {
                    ok = false;
                    break;
                }
            }
            if (ok) ans++;
        }
        return ans;
    }

    public boolean check(int[] nums) {
        int current = -1;
        boolean wasLower = false;
        for (int i : nums) {
            if (current == -1) current = i;
            if (i < current) {
                if (wasLower) return false;
                wasLower = true;
            }
            current = i;
        }
        return wasLower ? nums[nums.length - 1] <= nums[0] : nums[nums.length - 1] >= nums[0];
    }

    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] i : nums1)
            map.put(i[0], map.getOrDefault(i[0], 0) + i[1]);
        for (int[] i : nums2)
            map.put(i[0], map.getOrDefault(i[0], 0) + i[1]);
        LinkedList<Integer> list = new LinkedList<>(map.keySet());
        Collections.sort(list);
        int[][] ans = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            ans[i][0] = current;
            ans[i][1] = map.get(current);
        }
        return ans;
    }

    public int titleToNumber(String s) {
        int ans = 0;
        int index = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            ans += ((int) Math.pow(26, index++) * (s.charAt(i) - 'A' + 1));
        }
        return ans;
    }

    public boolean equalFrequency(String word) {
        int[] count = new int[26];
        for (char c : word.toCharArray())
            count[c - 'a']++;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : count) {
            if (num == 0) continue;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        if (map.size() == 2 && map.firstKey() + 1 == map.lastKey() && map.get(map.lastKey()) == 1)
            return true;
        if (map.size() == 2 && map.firstKey() == 1 && map.get(map.firstKey()) == 1)
            return true;
        return map.size() == 1 && (map.firstKey() == 1 || map.get(map.firstKey()) == 1);
    }

    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : deck)
            map.put(i, map.getOrDefault(i, 0) + 1);
        for (int value1 : map.values()) {
            for (int value2 : map.values()) {
                if (hasGroupsSizeXHelper(value1, value2) == 1) return false;
            }
        }
        return true;
    }

    private int hasGroupsSizeXHelper(int a, int b) {
        if (b == 0) return a;
        return hasGroupsSizeXHelper(b, a % b);
    }

    public int arrangeCoins(int n) {
        int coins = n;
        int stair = 2;
        int ans = 0;
        while (true) {
            if (coins <= 1) {
                if (coins == 1) return ans + 1;
                if (coins - stair == -1) return ans + 1;
                return ans;
            } else {
                coins -= stair++;
            }
            ans++;
        }
    }

    public int countBeautifulPairs(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int a = Character.getNumericValue(String.valueOf(nums[i]).charAt(0));
                String s = String.valueOf(nums[j]);
                int b = Character.getNumericValue(s.charAt(s.length() - 1));
                if (countBeautifulPairsHelper(a, b) == 1) ans++;
            }
        }
        return ans;
    }

    private int countBeautifulPairsHelper(int a, int b) {
        if (b == 0) return a;
        return countBeautifulPairsHelper(b, a % b);
    }

    public int maximumNumberOfStringPairs(String[] words) {
        int ans = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].contentEquals(new StringBuilder(words[j]).reverse()))
                    ans++;
            }
        }
        return ans;
    }

    public int distanceTraveled(int mainTank, int additionalTank) {
        int ans = 0;
        while (mainTank > 0) {
            if (mainTank >= 5 && additionalTank > 0) {
                ans += 50;
                mainTank -= 4;
                additionalTank--;
            } else if (mainTank >= 5) {
                ans += (mainTank * 10);
                mainTank = 0;
            } else {
                ans += (mainTank * 10);
                mainTank = 0;
            }
        }
        return ans;
    }

    public int findNonMinOrMax(int[] nums) {
        if (nums.length < 3) return -1;
        int min = Math.min(nums[0], Math.min(nums[1], nums[2]));
        int max = Math.max(nums[0], Math.max(nums[1], nums[2]));
        for (int num : nums)
            if (num != min && num != max) return num;
        return -1;
    }

    public boolean isFascinating(int n) {
        StringBuilder sb = new StringBuilder(String.valueOf(n));
        sb.append(2 * n);
        sb.append(3 * n);
        boolean[] found = new boolean[9];
        for (int i = 0; i < sb.length(); i++) {
            char current = sb.charAt(i);
            if (current == '0') return false;
            int index = Character.getNumericValue(current) - 1;
            if (!found[index]) {
                found[index] = true;
            } else {
                return false;
            }
        }
        return true;
    }

    public int minLength(String s) {
        String current = s;
        int indexAB, indexCD = -1;
        while ((indexAB = current.indexOf("AB")) != -1 || (indexCD = current.indexOf("CD")) != -1) {
            if (indexAB != -1)
                current = current.substring(0, indexAB) + current.substring(indexAB + 2);
            else
                current = current.substring(0, indexCD) + current.substring(indexCD + 2);
        }
        return current.length();
    }

    public int minimizedStringLength(String s) {
        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray())
            set.add(c);
        return set.size();
    }

    public String digitSum(String s, int k) {
        String current = s;
        while (current.length() > k) {
            StringBuilder tmp = new StringBuilder();
            int index = 0;
            while (index < current.length()) {
                if (index + k < current.length())
                    tmp.append(digitSumHelper(current.substring(index, index + k)));
                else
                    tmp.append(digitSumHelper(current.substring(index)));
                index += k;
            }
            current = tmp.toString();
        }
        return current;
    }

    private int digitSumHelper(String s) {
        int ans = 0;
        for (char c : s.toCharArray())
            ans += Character.getNumericValue(c);
        return ans;
    }

    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;
        if (s.equals(goal)) return true;
        String current = s;
        for (int i = 0; i < s.length(); i++) {
            current = current.substring(1) + current.charAt(0);
            if (current.equals(goal)) return true;
        }
        return false;
    }

    public int findLHS(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = 1;
        int len = 0;
        while (right < nums.length) {
            int diff = nums[right] - nums[left];
            if (diff == 1)
                len = Math.max(len, right - left + 1);
            if (diff <= 1)
                right++;
            else
                left++;
        }
        return len;
    }

    public int findShortestSubArray(int[] nums) {
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
        int max = -1;
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            LinkedList<Integer> tmp = map.getOrDefault(nums[i], new LinkedList<>());
            tmp.addLast(i);
            map.put(nums[i], tmp);
            if (tmp.size() > max) {
                max = tmp.size();
                list.clear();
                list.add(nums[i]);
            } else if (tmp.size() == max) {
                if (!list.contains(nums[i])) {
                    list.add(nums[i]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        if (list.isEmpty()) return 0;
        for (Integer integer : list) {
            int first = map.get(integer).getFirst();
            int last = map.get(integer).getLast();
            min = Math.min(min, last - first + 1);
        }
        return min;
    }

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int ans = 0;
        int i = 0, j = 0;
        while (j < s.length) {
            if (i >= g.length) break;
            if (g[i] <= s[j]) {
                ans++;
                i++;
            }
            j++;
        }
        return ans;
    }

    public int getMinimumDifference(TreeNode root) {
        LinkedList<Integer> list = new LinkedList<>();
        getMinimumDifferenceHelper(root, list);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list.size() - 1; i++) {
            int first = list.get(i);
            int second = list.get(i + 1);
            min = Math.min(min, second - first);
        }
        return min;
    }

    private void getMinimumDifferenceHelper(TreeNode node, LinkedList<Integer> list) {
        if (node == null) return;
        getMinimumDifferenceHelper(node.left, list);
        list.add(node.val);
        getMinimumDifferenceHelper(node.right, list);
    }

    public boolean lemonadeChange(int[] bills) {
        int amount5 = 0;
        int amount10 = 0;
        for (int bill : bills) {
            if (bill == 5) {
                amount5++;
            } else if (bill == 10) {
                if (amount5 <= 0) return false;
                amount5--;
                amount10++;
            } else {
                if (amount10 > 0 && amount5 > 0) {
                    amount10--;
                    amount5--;
                } else if (amount5 >= 3) {
                    amount5 -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public int binaryGap(int n) {
        String binary = Integer.toBinaryString(n);
        int maxDistance = 0;
        int first = -1, second = -1;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) != '1') {
                continue;
            }
            if (first == -1) {
                first = i;
                continue;
            }
            if (second == -1) {
                second = i;
                maxDistance = second - first;
                continue;
            }
            first = second;
            second = i;
            maxDistance = Math.max(maxDistance, second - first);
        }
        return maxDistance;
    }

    public void duplicateZeros(int[] arr) {
        int i = 0;
        while (i < arr.length - 1) {
            if (arr[i] != 0) {
                i++;
                continue;
            }
            if (i == arr.length - 1) break;
            for (int j = arr.length - 2; j > i; j--) {
                arr[j + 1] = arr[j];
            }
            arr[i + 1] = 0;
            i += 2;
        }
    }

    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        int total = 0;
        int between = 0;
        int current = start;
        boolean begin = true;
        boolean in = true;
        while (current != start || begin) {
            if (begin) {
                begin = false;
                between += distance[current];
            } else if (in) {
                if (current == destination)
                    in = false;
                if (in)
                    between += distance[current];
            }
            total += distance[current];
            current = (current + 1) % distance.length;
        }
        return Math.min(total - between, between);
    }

    public int numRookCaptures(char[][] board) {
        int posI = -1;
        int posJ = -1;
        int ans = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'R') {
                    posI = i;
                    posJ = j;
                    break;
                }
            }
        }
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int x = posI;
            int y = posJ;
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (board[x][y] == 'p') {
                    ans++;
                    break;
                } else if (board[x][y] == 'B') {
                    break;
                }
                x += dx[i];
                y += dy[i];
            }
        }
        return ans;
    }

    public int findDelayedArrivalTime(int arrivalTime, int delayedTime) {
        return (arrivalTime + delayedTime) % 24;
    }

    public int[] distinctDifferenceArray(int[] nums) {
        int[] diff = new int[nums.length];
        int i = 0;
        while (i < nums.length) {
            int left = 0;
            List<Integer> found = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (!found.contains(nums[j])) {
                    found.add(nums[j]);
                    left++;
                }
            }
            found.clear();
            int right = 0;
            for (int j = i + 1; j < nums.length; j++) {
                if (!found.contains(nums[j])) {
                    found.add(nums[j]);
                    right++;
                }
            }
            diff[i] = left - right;
            i++;
        }
        return diff;
    }

    public int[] applyOperations(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }
        while (index < nums.length) {
            nums[index] = 0;
            index++;
        }
        return nums;
    }

    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int n = nums.length, max = 0, l = 0, r = 0, flag = 0;
        while (r < n) {
            if (flag == 0) {
                if (nums[r] % 2 == 0 && nums[r] <= threshold) {
                    l = r;
                    max = Math.max(max, 1);
                    flag = 1;
                }
            } else {
                int x = nums[r - 1], y = nums[r], c = x + y;
                if (c % 2 != 0 && nums[r] <= threshold) {
                    max = Math.max(max, r - l + 1);
                } else {
                    flag = 0;
                    r--;
                }
            }
            r++;
        }
        return max;
    }

    public int lastStoneWeight(int[] stones) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int integer : stones)
            map.put(integer, map.getOrDefault(integer, 0) + 1);
        while (map.size() > 1) {
            int highest = map.lastKey();
            if (map.get(highest) > 1) {
                if (map.get(highest) % 2 == 0) {
                    map.remove(highest);
                } else {
                    map.put(highest, 1);
                }
            } else {
                map.remove(highest);
                int secondHighest = map.lastKey();
                if (map.get(secondHighest) == 1) {
                    map.remove(secondHighest);
                } else {
                    map.put(secondHighest, map.get(secondHighest) - 1);
                }
                int current = highest - secondHighest;
                map.put(current, map.getOrDefault(current, 0) + 1);
            }
        }
        if (map.size() == 1)
            return map.get(map.lastKey()) % 2 == 0 ? 0 : map.lastKey();
        return 0;
    }

    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        int sum = 0;
        while (k > 0) {
            if (numOnes != 0) {
                if (numOnes >= k) {
                    sum += k;
                    k = 0;
                } else {
                    sum += numOnes;
                    k -= numOnes;
                    numOnes = 0;
                }
            } else if (numZeros != 0) {
                if (numZeros >= k) {
                    k = 0;
                } else {
                    k -= numZeros;
                    numZeros = 0;
                }
            } else if (numNegOnes != 0) {
                if (numNegOnes >= k) {
                    sum -= k;
                    k = 0;
                } else {
                    sum -= numNegOnes;
                    k -= numNegOnes;
                    numNegOnes = 0;
                }
            } else {
                break;
            }
        }
        return sum;
    }

    public int[] rowAndMaximumOnes(int[][] mat) {
        int[] ans = new int[2];
        for (int i = 0; i < mat.length; i++) {
            int currentAmount = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 1) currentAmount++;
            }
            if (currentAmount > ans[1]) {
                ans[0] = i;
                ans[1] = currentAmount;
            }
        }
        return ans;
    }

    public int theMaximumAchievableX(int num, int t) {
        return num + 2 * t;
    }

    public int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int current : arr)
            map.put(current, map.getOrDefault(current, 0) + 1);
        int max = -1;
        for (int key : map.keySet()) {
            int value = map.get(key);
            if (key == value) {
                max = Math.max(max, value);
            }
        }
        return max;
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int ans = 0;
        Map<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for (int[] dominoe : dominoes) {
            int a = Math.min(dominoe[0], dominoe[1]);
            int b = Math.max(dominoe[0], dominoe[1]);
            if (map.containsKey(a)) {
                HashMap<Integer, Integer> current = map.get(a);
                current.put(b, current.getOrDefault(b, 0) + 1);
            } else {
                HashMap<Integer, Integer> tmp = new HashMap<>();
                tmp.put(b, 1);
                map.put(a, tmp);
            }
        }
        for (int a : map.keySet()) {
            HashMap<Integer, Integer> current = map.get(a);
            for (int b : current.keySet()) {
                int n = current.get(b);
                ans += (n * (n - 1) / 2);
            }
        }
        return ans;
    }

    public int getMinDistance(int[] nums, int target, int start) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                min = Math.min(min, Math.abs(i - start));
            }
        }
        return min;
    }

    public String shortestCompletingWord(String licensePlate, String[] words) {
        String ans = "";
        Map<Character, Integer> map = new HashMap<>();
        for (char c : licensePlate.toCharArray()) {
            if (!Character.isLetter(c)) continue;
            c = Character.toLowerCase(c);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (String s : words) {
            Map<Character, Integer> current = new HashMap<>();
            for (char c : s.toCharArray()) {
                if (!Character.isLetter(c)) continue;
                c = Character.toLowerCase(c);
                current.put(c, current.getOrDefault(c, 0) + 1);
            }
            if (current.size() < map.size()) continue;
            boolean ok = true;
            for (char c : map.keySet()) {
                if (!current.containsKey(c)) {
                    ok = false;
                    break;
                }
                if (current.get(c) < map.get(c)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                if (ans.isEmpty()) {
                    ans = s;
                } else if (ans.length() > s.length()) {
                    ans = s;
                }
            }
        }
        return ans;
    }

    public int minimumMoves(String s) {
        int ans = 0;
        int i = 0;
        char[] chars = s.toCharArray();
        while (i < chars.length) {
            if (chars[i] == 'O') {
                i++;
                continue;
            }
            ans++;
            i += 3;
        }
        return ans;
    }

    public int distMoney(int money, int children) {
        int ans = 0;
        if (children > money) return -1;
        if (money == children * 8) return children;
        if (money > children * 8) return children - 1;
        money -= children;
        while (money >= 3) {
            if (money >= 7) {
                money -= 7;
                ans++;
            } else if (money == 3) {
                if (children - ans >= 2) return ans;
                return ans == 0 ? 0 : ans - 1;
            } else {
                break;
            }
        }
        return ans;
    }

    public long findTheArrayConcVal(int[] nums) {
        long ans = 0;
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            if (i == j) {
                ans += nums[i];
                break;
            }
            String s = nums[i++] + "" + nums[j--];
            ans += Long.parseLong(s);
        }
        return ans;
    }

    public double calculateTax(int[][] brackets, int income) {
        double ans = 0;
        int before = brackets[0][0];
        if (income >= before) {
            ans += (before * brackets[0][1] / 100.0);
            income -= before;
        } else {
            ans += (income * brackets[0][1] / 100.0);
            return ans;
        }
        for (int i = 1; i < brackets.length; i++) {
            int current = brackets[i][0] - before;
            if (current <= income) {
                ans += (current * brackets[i][1] / 100.0);
                income -= current;
            } else if (income == 0) {
                break;
            } else {
                ans += (income * brackets[i][1] / 100.0);
                break;
            }
            before = brackets[i][0];
        }
        return ans;
    }

    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums)
            if (i % 2 == 0)
                map.put(i, map.getOrDefault(i, 0) + 1);
        Set<Integer> set = map.keySet();
        int max = -1;
        int num = Integer.MAX_VALUE;
        for (int i : set) {
            int val = map.get(i);
            if (val == max && num > i)
                num = i;
            else if (val > max) {
                max = val;
                num = i;
            }
        }
        return max == -1 ? -1 : num;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ans = null;
        StringBuilder sb1 = addTwoNumbersHelper(l1);
        StringBuilder sb2 = addTwoNumbersHelper(l2);
        int i = sb1.length() - 1, j = sb2.length() - 1;
        int offset = 0;
        while (i >= 0 && j >= 0) {
            int current = Character.getNumericValue(sb1.charAt(i--)) + Character.getNumericValue(sb2.charAt(j--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if (ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        while (i >= 0) {
            int current = Character.getNumericValue(sb1.charAt(i--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if (ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        while (j >= 0) {
            int current = Character.getNumericValue(sb2.charAt(j--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if (ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        if (offset != 0) {
            ListNode node = new ListNode(offset);
            node.next = ans;
            ans = node;
        }
        return ans;
    }

    private StringBuilder addTwoNumbersHelper(ListNode node) {
        ListNode current = node;
        StringBuilder ans = new StringBuilder();
        while (current != null) {
            ans.append(current.val);
            current = current.next;
        }
        return ans;
    }

    class LRUCache {
        private final int max;
        private final Map<Integer, Integer> map;
        private final Set<Integer> used;

        public LRUCache(int capacity) {
            this.max = capacity;
            this.map = new HashMap<>(capacity);
            this.used = new LinkedHashSet<>();
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            used.remove(key);
            used.add(key);
            return map.get(key);
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                map.put(key, value);
                used.remove(key);
                used.add(key);
                return;
            }
            if (map.size() + 1 > max) {
                int removeKey = used.iterator().next();
                used.remove(removeKey);
                map.remove(removeKey);
            }
            map.put(key, value);
            used.add(key);
        }
    }

    public int daysBetweenDates(String date1, String date2) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate local1 = LocalDate.parse(date1, dtf);
        LocalDate local2 = LocalDate.parse(date2, dtf);
        long daysBetween = ChronoUnit.DAYS.between(local1, local2);
        if (daysBetween < 0)
            daysBetween = ChronoUnit.DAYS.between(local2, local1);
        return (int) daysBetween;
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stk = new Stack<>();
        for (int asteroid : asteroids) {
            if (stk.isEmpty() || asteroid > 0) {
                stk.push(asteroid);
            } else {
                while (!stk.isEmpty() && stk.peek() > 0 && stk.peek() < Math.abs(asteroid))
                    stk.pop();
                if (!stk.isEmpty() && stk.peek() == Math.abs(asteroid)) {
                    stk.pop();
                } else {
                    if (stk.isEmpty() || stk.peek() < 0)
                        stk.push(asteroid);
                }
            }
        }
        int[] ans = new int[stk.size()];
        int size = stk.size();
        while (!stk.isEmpty()) {
            ans[--size] = stk.pop();
        }
        return ans;
    }

    public String dayOfTheWeek(int day, int month, int year) {
        Calendar c = new GregorianCalendar(year, month - 1, day);
        int dow = c.get(Calendar.DAY_OF_WEEK);
        return switch (dow) {
            case Calendar.SUNDAY -> "Sunday";
            case Calendar.MONDAY -> "Monday";
            case Calendar.TUESDAY -> "Tuesday";
            case Calendar.WEDNESDAY -> "Wednesday";
            case Calendar.THURSDAY -> "Thursday";
            case Calendar.FRIDAY -> "Friday";
            case Calendar.SATURDAY -> "Saturday";
            default -> "Error";
        };
    }

    public boolean checkOnesSegment(String s) {
        boolean endOne = false;
        for (char c : s.toCharArray()) {
            if (endOne && c == '1') return false;
            if (c == '0') endOne = true;
        }
        return true;
    }

    public int minStartValue(int[] nums) {
        int i = 0, j = 0;
        for (int num : nums) {
            j += num;
            i = Math.min(i, j);
        }
        return 1 - i;
    }

    public int[] constructRectangle(int area) {
        int L = area / (int) Math.sqrt(area);
        while (true) {
            if (L == area) return new int[]{area, 1};
            double current = (double) area / (double) L;
            if (current == (int) current) break;
            L++;
        }
        return new int[]{L, area / L};
    }

    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> ans = new LinkedList<>();
        for (String s : words) {
            StringBuilder tmp = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c == separator) {
                    if (tmp.length() > 0)
                        ans.add(tmp.toString());
                    tmp = new StringBuilder();
                } else {
                    tmp.append(c);
                }
            }
            if (tmp.length() > 0) ans.add(tmp.toString());
        }
        return ans;
    }

    public boolean isGood(int[] nums) {
        int[] ans = new int[nums.length];
        for (int num : nums) {
            if (num >= ans.length || num < 0) return false;
            if (ans[num] == 0) {
                ans[num]++;
            } else if (ans[num] == 1 && num == ans.length - 1) {
                ans[num]++;
            } else {
                return false;
            }
        }
        return true;
    }

    public int sumOfSquares(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums.length % (i + 1) == 0)
                ans += (int) Math.pow(nums[i], 2);
        return ans;
    }

    public int[] findColumnWidth(int[][] grid) {
        int[] ans = new int[grid[0].length];
        for (int[] row : grid) {
            for (int i = 0; i < row.length; i++) {
                int col = row[i];
                int currentLength = String.valueOf(col).length();
                ans[i] = Math.max(ans[i], currentLength);
            }
        }
        return ans;
    }

    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for (int gift : gifts) queue.add(gift);
        for (int i = 0; i < k; i++) {
            if (queue.isEmpty()) return -1;
            int current = queue.poll();
            current = (int) Math.sqrt(current);
            queue.add(current);
        }
        long ans = 0;
        for (int num : queue) ans += num;
        return ans;
    }

    public boolean validMountainArray(int[] arr) {
        if (arr.length == 1) return false;
        if (arr[0] > arr[1]) return false;
        int before = arr[0];
        boolean wasHigher = true;
        for (int i = 1; i < arr.length; i++) {
            if (wasHigher) {
                if (before < arr[i]) {
                    before = arr[i];
                } else if (before == arr[i]) {
                    return false;
                } else {
                    wasHigher = false;
                    before = arr[i];
                }
            } else {
                if (before <= arr[i]) return false;
                before = arr[i];
            }
        }
        return !wasHigher;
    }

    public int dayOfYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));
        int ans = 0;
        for (int i = 1; i <= month; i++) {
            if (i < month) {
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
                    ans += 31;
                else if (i == 2) {
                    if (dayOfYearHelper(year))
                        ans += 29;
                    else
                        ans += 28;
                } else ans += 30;
            } else {
                ans += day;
            }
        }
        return ans;
    }

    public boolean dayOfYearHelper(int year) {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }

    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        return (int) Arrays.stream(hours).filter(x -> x >= target).count();
    }

    public TreeNode increasingBST(TreeNode root) {
        LinkedList<TreeNode> list = new LinkedList<>();
        increasingBSTHelper(root, list);
        TreeNode newRoot = new TreeNode(list.get(0).val);
        TreeNode current = newRoot;
        for (int i = 1; i < list.size(); i++) {
            current.right = new TreeNode(list.get(i).val);
            current = current.right;
        }
        return newRoot;
    }

    public void increasingBSTHelper(TreeNode node, List<TreeNode> list) {
        if (node == null) return;
        increasingBSTHelper(node.left, list);
        list.add(node);
        increasingBSTHelper(node.right, list);
    }

    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int ans = 0;
        for (int i = 0; i < seats.length; i++) {
            ans += Math.abs(students[i] - seats[i]);
        }
        return ans;
    }

    public String removeOuterParentheses(String s) {
        if (s == null || s.isEmpty()) return " ";
        StringBuilder sb = new StringBuilder();
        int begin = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') count++;
            else if (s.charAt(i) == ')') count--;
            if (count == 0) {
                sb.append(s, begin + 1, i);
                begin = i + 1;
            }
        }
        return sb.toString();
    }

    public int countGoodRectangles(int[][] rectangles) {
        int maxLen = Integer.MIN_VALUE;
        int amount = 0;
        for (int[] rectangle : rectangles) {
            int current = Math.min(rectangle[0], rectangle[1]);
            if (current < maxLen) continue;
            if (current == maxLen) amount++;
            else {
                maxLen = current;
                amount = 1;
            }
        }
        return amount;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new LinkedList<>();
        List<TreeNode> currentLevel = new LinkedList<>();
        if (root == null) return ans;
        currentLevel.add(root);
        while (!currentLevel.isEmpty()) {
            List<TreeNode> tmp = new LinkedList<>();
            double mean = 0;
            for (TreeNode node : currentLevel) {
                mean += node.val;
                if (node.left != null) tmp.add(node.left);
                if (node.right != null) tmp.add(node.right);
            }
            ans.add(mean / currentLevel.size());
            currentLevel.clear();
            currentLevel = tmp;
        }
        return ans;
    }

    public String finalString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == 'i')
                sb.reverse();
            else
                sb.append(c);
        }
        return sb.toString();
    }

    public int accountBalanceAfterPurchase(int purchaseAmount) {
        int lastDigit = purchaseAmount % 10, tmp;
        if (lastDigit < 5)
            tmp = purchaseAmount - lastDigit;
        else
            tmp = purchaseAmount + (10 - lastDigit);
        return 100 - tmp;
    }

    public boolean findSubarrays(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int front = nums[i] + nums[i + 1];
            for (int j = i + 1; j < nums.length - 1; j++) {
                int back = nums[j] + nums[j + 1];
                if (back == front) return true;
            }
        }
        return false;
    }

    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        int ans = 0;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        while (!list.isEmpty()) {
            TreeNode current = list.pop();
            if (current == null) continue;
            ans++;
            list.add(current.left);
            list.add(current.right);
        }
        return ans;
    }

    public int maxDepth(Node root) {
        if (root == null) return 0;
        LinkedList<Node> currentDepth = new LinkedList<>();
        int ans = 0;
        currentDepth.add(root);
        while (!currentDepth.isEmpty()) {
            LinkedList<Node> tmp = new LinkedList<>();
            for (Node node : currentDepth)
                tmp.addAll(node.children);
            currentDepth.clear();
            currentDepth = tmp;
            ans++;
        }
        return ans;
    }

    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int max1 = nums[n - 1] * nums[n - 2] * nums[n - 3];
        int max2 = nums[0] * nums[1] * nums[n - 1];
        return Math.max(max1, max2);
    }

    public String tree2str(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        tree2strHelper(root, sb);
        return sb.toString();
    }

    private void tree2strHelper(TreeNode root, StringBuilder sb) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            sb.append(root.val);
            return;
        }
        sb.append(root.val);
        sb.append("(");
        tree2strHelper(root.left, sb);
        sb.append(")");
        if (root.right != null) {
            sb.append("(");
            tree2strHelper(root.right, sb);
            sb.append(")");
        }
    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1.left == null && root1.right == null && root2.left == null && root2.right == null)
            return root1.val == root2.val;
        LinkedList<TreeNode> list1 = new LinkedList<>(), list2 = new LinkedList<>();
        leafSimilarHelper(root1, list1);
        leafSimilarHelper(root2, list2);
        if (list1.size() != list2.size()) return false;
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).val != list2.get(i).val) return false;
        }
        return true;
    }

    private void leafSimilarHelper(TreeNode node, LinkedList<TreeNode> list) {
        if (node == null) return;
        if (node.left != null) {
            if (node.left.left == null && node.left.right == null)
                list.add(node.left);
            else
                leafSimilarHelper(node.left, list);
        }
        if (node.right != null) {
            if (node.right.left == null && node.right.right == null)
                list.add(node.right);
            else
                leafSimilarHelper(node.right, list);
        }
    }

    public int[] sortArrayByParityII(int[] nums) {
        int i = 0, j = 1;
        while (i < nums.length && j < nums.length) {
            while (nums[i] % 2 == 0) {
                i += 2;
                if (i >= nums.length) return nums;
            }
            while (nums[j] % 2 != 0) {
                j += 2;
                if (j >= nums.length) return nums;
            }
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i += 2;
            j += 2;
        }
        return nums;
    }

    public int maxNumberOfBalloons(String text) {
        int b = 0, a = 0, l = 0, o = 0, n = 0;
        int ans = 0;
        for (char c : text.toCharArray()) {
            switch (c) {
                case 'b' -> b++;
                case 'a' -> a++;
                case 'l' -> l++;
                case 'o' -> o++;
                case 'n' -> n++;
            }
        }
        while (b >= 1 && a >= 1 && l >= 2 && o >= 2 && n >= 1) {
            ans++;
            b--;
            a--;
            l -= 2;
            o -= 2;
            n--;
        }
        return ans;
    }

    public int countBalls(int lowLimit, int highLimit) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = lowLimit; i <= highLimit; i++) {
            int current = i;
            int sum = 0;
            while (current > 0) {
                sum += (current % 10);
                current /= 10;
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        int max = 0;
        for (int value : map.values())
            max = Math.max(value, max);
        return max;
    }

    public int maxScore(String s) {
        int max = 0;
        for (int i = 1; i <= s.length() - 1; i++) {
            String left = s.substring(0, i);
            String right = s.substring(i);
            int current = maxScoreHelper(left, '0') + maxScoreHelper(right, '1');
            max = Math.max(max, current);
        }
        return max;
    }

    public int maxScoreHelper(String s, char c) {
        int ans = 0;
        for (char ch : s.toCharArray())
            if (ch == c) ans++;
        return ans;
    }

    public double trimMean(int[] arr) {
        Arrays.sort(arr);
        double x = 0.05 * arr.length;
        double sum = 0;
        double n = arr.length - 2 * x;
        for (int i = (int) x; i < arr.length - x; i++) {
            sum += arr[i];
        }
        return sum / n;
    }

    public int maxSum(int[] nums) {
        int ans = -1;
        for (int i = 0; i < nums.length; i++) {
            int first = maxSumHelper(nums[i]);
            for (int j = i + 1; j < nums.length; j++) {
                int second = maxSumHelper(nums[j]);
                if (first == second)
                    ans = Math.max(ans, nums[i] + nums[j]);
            }
        }
        return ans;
    }

    public int maxSumHelper(int x) {
        int max = 0;
        for (char c : String.valueOf(x).toCharArray())
            max = Math.max(Character.getNumericValue(c), max);
        return max;
    }

    public int countTriples(int n) {
        int ans = 0;
        for (int a = 1; a <= n; a++) {
            for (int b = a; b <= n; b++) {
                for (int c = 1; c <= n; c++) {
                    if (a * a + b * b == c * c) ans += 2;
                }
            }
        }
        return ans;
    }

    public boolean isAcronym(List<String> words, String s) {
        if (words.size() != s.length()) return false;
        for (int i = 0; i < words.size(); i++)
            if (words.get(i).charAt(0) != s.charAt(i)) return false;
        return true;
    }

    public int countPairs(List<Integer> nums, int target) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                if (nums.get(i) + nums.get(j) < target) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int timeRequiredToBuy(int[] tickets, int k) {
        int ans = 0;
        int i = 0;
        while (tickets[k] != 0) {
            if (tickets[i] == 0) {
                i = i == tickets.length - 1 ? 0 : i + 1;
                continue;
            }
            ans++;
            tickets[i]--;
            i = i == tickets.length - 1 ? 0 : i + 1;
        }
        return ans;
    }

    public int[] findEvenNumbers(int[] digits) {
        Set<Integer> ans = new HashSet<>();
        for (int i = 0; i < digits.length; i++) {
            int gerade = digits[i];
            if (gerade % 2 != 0) continue;
            for (int j = 0; j < digits.length; j++) {
                for (int k = 0; k < digits.length; k++) {
                    if (i == j || i == k || j == k) continue;
                    int eins = digits[j];
                    int zwei = digits[k];
                    if (eins != 0) {
                        ans.add(eins * 100 + zwei * 10 + gerade);
                    }
                    if (zwei != 0) {
                        ans.add(zwei * 100 + eins * 10 + gerade);
                    }
                }
            }
        }
        int[] ret = new int[ans.size()];
        int index = 0;
        for (int num : ans) ret[index++] = num;
        Arrays.sort(ret);
        return ret;
    }

    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        Set<Integer> ans = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (Math.abs(i - j) <= k && nums[j] == key) {
                    ans.add(i);
                }
            }
        }
        List<Integer> list = new LinkedList<>(ans);
        Collections.sort(list);
        return list;
    }

    public int largestSumAfterKNegations(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : nums) queue.add(num);
        for (int i = 0; i < k; i++) {
            if (queue.isEmpty()) return 0;
            queue.add(-queue.poll());
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            ans += queue.poll();
        }
        return ans;
    }

    public int furthestDistanceFromOrigin(String moves) {
        int left = 0, right = 0;
        int underscore = 0;
        for (char current : moves.toCharArray()) {
            if (current == 'R') {
                right++;
            } else if (current == 'L') {
                left++;
            } else {
                underscore++;
            }
        }
        return Math.abs(left - right) + underscore;
    }

    class MyStack {

        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> tmp = new LinkedList<>();

        public void push(int x) {
            while (!queue.isEmpty()) {
                tmp.add(queue.poll());
            }
            queue.add(x);
            while (!tmp.isEmpty()) {
                queue.add(tmp.poll());
            }
        }

        public int pop() {
            Integer ret = queue.poll();
            return ret == null ? -1 : ret;
        }

        public int top() {
            Integer ret = queue.peek();
            return ret == null ? -1 : ret;
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    public int countElements(int[] nums) {
        Arrays.sort(nums);
        int min = nums[0];
        int max = nums[nums.length - 1];
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == max) break;
            if (nums[i] != min) ans++;
        }
        return ans;
    }

    public int[] distributeCandies(int candies, int numPeople) {
        int[] ans = new int[numPeople];
        int amount = 1;
        int index = 0;
        while (candies != 0) {
            if (candies - amount <= 0) {
                ans[index] += candies;
                break;
            }
            ans[index] += amount;
            candies -= amount++;
            index = index == numPeople - 1 ? 0 : index + 1;
        }
        return ans;
    }

    public String thousandSeparator(int n) {
        StringBuilder s = new StringBuilder(String.valueOf(n)).reverse();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            ans.append(s.charAt(i));
            if ((i + 1) % 3 == 0 && i != s.length() - 1)
                ans.append(".");
        }
        return ans.reverse().toString();
    }

    public int maximumPopulation(int[][] logs) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int[] log : logs) {
            int birth = log[0];
            int death = log[1];
            for (int year = birth; year < death; year++) {
                map.put(year, map.getOrDefault(year, 0) + 1);
            }
        }
        int max = Integer.MIN_VALUE;
        int year = Integer.MAX_VALUE;
        for (int currentYear : map.keySet()) {
            int currentValue = map.get(currentYear);
            if (currentValue > max) {
                year = currentYear;
                max = currentValue;
            } else if (currentValue == max) {
                year = Math.min(currentYear, year);
            }
        }
        return year;
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) return false;
        LinkedList<TreeNode> visited = new LinkedList<>();
        visited.add(root);
        while (!visited.isEmpty()) {
            int levelSize = visited.size();
            boolean foundX = false;
            boolean foundY = false;
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = visited.poll();
                if (node == null) break;
                if (node.val == x) foundX = true;
                if (node.val == y) foundY = true;
                if (node.left != null && node.right != null)
                    if ((node.left.val == x && node.right.val == y) || (node.left.val == y && node.right.val == x))
                        return false;
                if (node.left != null) visited.offer(node.left);
                if (node.right != null) visited.offer(node.right);
            }
            if (foundX && foundY) return true;
            if (foundX || foundY) return false;
        }
        return false;
    }

    public int findTilt(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        int ans = 0;
        while (!list.isEmpty()) {
            TreeNode current = list.pollFirst();
            int left = findTiltHelper(current.left);
            int right = findTiltHelper(current.right);
            current.val = Math.abs(left - right);
            ans += current.val;
            if (current.left != null) {
                list.addLast(current.left);
            }
            if (current.right != null) {
                list.addLast(current.right);
            }
        }
        return ans;
    }

    private int findTiltHelper(TreeNode node) {
        if (node == null) return 0;
        return findTiltHelper(node.left) + findTiltHelper(node.right) + node.val;
    }

    public int numSpecial(int[][] mat) {
        int ans = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                int current = mat[i][j];
                if (current == 0) continue;
                boolean zeros = true;
                for (int row = 0; row < mat.length; row++) {
                    if (row == i) continue;
                    if (mat[row][j] == 1) {
                        zeros = false;
                        break;
                    }
                }
                if (!zeros) continue;
                for (int col = 0; col < mat[0].length; col++) {
                    if (col == j) continue;
                    if (mat[i][col] == 1) {
                        zeros = false;
                        break;
                    }
                }
                if (zeros) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int passThePillow(int n, int time) {
        int current = 1;
        boolean right = true;
        while (time != 0) {
            if (right && current != n) {
                current++;
            } else if (!right && current != 1) {
                current--;
            } else if (right) {
                right = false;
                current--;
            } else {
                right = true;
                current++;
            }
            time--;
        }
        return current;
    }

    public int countSymmetricIntegers(int low, int high) {
        int ans = 0;
        for (int i = low; i <= high; i++) {
            String s = String.valueOf(i);
            if (s.length() % 2 != 0) continue;
            int sumLeft = 0, sumRight = 0;
            int j = 0;
            while (j < s.length()) {
                if (j < s.length() / 2) {
                    sumLeft += Character.getNumericValue(s.charAt(j++));
                } else {
                    sumRight += Character.getNumericValue(s.charAt(j++));
                }
            }
            if (sumLeft == sumRight) ans++;
        }
        return ans;
    }

    public boolean canBeEqual(String s1, String s2) {
        if (s1.equals(s2)) return true;
        String formatted = String.format("%c%c%c%c", s1.charAt(2), s1.charAt(1), s1.charAt(0), s1.charAt(3));
        if (formatted.equals(s2)) return true;
        formatted = String.format("%c%c%c%c", formatted.charAt(0), formatted.charAt(3), formatted.charAt(2), formatted.charAt(1));
        if (formatted.equals(s2)) return true;
        formatted = String.format("%c%c%c%c", s1.charAt(0), s1.charAt(3), s1.charAt(2), s1.charAt(1));
        return formatted.equals(s2);
    }

    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            int current = nums.get(i);
            String bin = Integer.toBinaryString(i);
            int amountOnes = 0;
            for (char c : bin.toCharArray()) {
                if (c == '1') {
                    if (amountOnes >= k) {
                        amountOnes++;
                        break;
                    }
                    amountOnes++;
                }
            }
            if (amountOnes == k) {
                System.out.println(i);
                ans += current;
            }
        }
        return ans;
    }

    public int minimumRightShifts(List<Integer> nums) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < nums.size(); i++) {
            int current = nums.get(i);
            if (current < min) {
                min = current;
                minIndex = i;
            }
        }
        int i = minIndex + 1 < nums.size() ? minIndex + 1 : 0;
        int lower = min;
        while (i != minIndex) {
            int current = nums.get(i);
            if (current < lower) return -1;
            lower = current;
            i = i == nums.size() - 1 ? 0 : i + 1;
        }
        return minIndex == 0 ? 0 : nums.size() - minIndex;
    }

    public int numberOfPoints(List<List<Integer>> nums) {
        int ans = 0;
        List<Integer> visited = new LinkedList<>();
        for (List<Integer> num : nums) {
            int start = num.get(0);
            int end = num.get(1);
            for (int coordinate = start; coordinate <= end; coordinate++) {
                if (visited.contains(coordinate)) continue;
                visited.add(coordinate);
                ans++;
            }
        }
        return ans;
    }

    public int minDeletionSize(String[] strs) {
        int ans = 0;
        int pos = 0;
        while (pos < strs[0].length()) {
            boolean sorted = true;
            char before = Character.MAX_VALUE;
            for (int i = 0; i < strs.length; i++) {
                char current = strs[i].charAt(pos);
                if (i != 0 && before > current) {
                    sorted = false;
                    break;
                }
                before = current;
            }
            if (!sorted) ans++;
            pos++;
        }
        return ans;
    }

    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int ans = 0;
        while (truckSize != 0) {
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;
            for (int i = 0; i < boxTypes.length; i++) {
                if (boxTypes[i][0] == 0) continue;
                int current = boxTypes[i][1];
                if (current > max) {
                    maxIndex = i;
                    max = current;
                }
            }
            if (maxIndex == -1) break;
            int size = boxTypes[maxIndex][0];
            int units = boxTypes[maxIndex][1];
            if (truckSize == size) {
                truckSize -= size;
                ans += (units * size);
            } else if (truckSize > size) {
                truckSize -= size;
                ans += (units * size);
                boxTypes[maxIndex][0] = 0;
            } else {
                ans += (truckSize * units);
                truckSize = 0;
            }
        }
        return ans;
    }

    public String maximumOddBinaryNumber(String s) {
        int amountOnes = 0;
        for (char c : s.toCharArray())
            if (c == '1') amountOnes++;
        if (amountOnes == 0) return s;
        if (amountOnes == 1) return "0".repeat(s.length() - 1) + "1";
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i == s.length() - 1) {
                ans.append("1");
                break;
            }
            if (amountOnes > 1) {
                ans.append("1");
                amountOnes--;
            } else {
                ans.append("0");
            }
        }
        return ans.toString();
    }

    public boolean validPath(int n, int[][] edges, int s, int d) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        return validPathHelper(graph, new boolean[n], s, d);
    }

    private boolean validPathHelper(ArrayList<Integer>[] graph, boolean[] visited, int s, int d) {
        if (s == d) return true;
        visited[s] = true;
        for (int i = 0; i < graph[s].size(); i++) {
            int current = graph[s].get(i);
            if (!visited[current] && validPathHelper(graph, visited, current, d)) return true;
        }
        return false;
    }

    public String[] findRelativeRanks(int[] score) {
        int n = score.length;
        String[] ans = new String[n];
        Map<Integer, Integer> scoreIndexMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            scoreIndexMap.put(score[i], i);
        Arrays.sort(score);
        String[] medals = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        int rank = 1;
        for (int i = n - 1; i >= 0; i--) {
            int index = scoreIndexMap.get(score[i]);
            if (rank <= 3)
                ans[index] = medals[rank - 1];
            else
                ans[index] = String.valueOf(rank);
            rank++;
        }
        return ans;
    }

    public boolean canBeEqual(int[] target, int[] arr) {
        if (target.length != arr.length) return false;
        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int i : target) map1.put(i, map1.getOrDefault(i, 0) + 1);
        for (int i : arr) map2.put(i, map2.getOrDefault(i, 0) + 1);
        for (int key : map1.keySet()) {
            if (!map2.containsKey(key)) return false;
            if (!Objects.equals(map2.get(key), map1.get(key))) return false;
        }
        return true;
    }

    public String makeGood(String s) {
        Stack<Character> stk = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (!stk.isEmpty() && (stk.peek() == ch - 32 || stk.peek() == ch + 32)) stk.pop();
            else stk.push(ch);
        }
        StringBuilder sb = new StringBuilder();
        while (!stk.isEmpty()) {
            char ch = stk.pop();
            sb.append(ch);
        }
        return sb.reverse().toString();
    }

    public int maxDistance(int[] colors) {
        int ans = 0;
        for (int i = 0; i < colors.length; i++) {
            for (int j = i + 1; j < colors.length; j++) {
                if (colors[i] != colors[j]) {
                    ans = Math.max(ans, j - i);
                }
            }
            for (int j = i - 1; j >= 0; j--) {
                if (colors[i] != colors[j]) {
                    ans = Math.max(ans, i - j);
                }
            }
        }
        return ans;
    }

    public String decodeAtIndex(String s, int k) {
        long size = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                size *= c - '0';
            } else {
                size++;
            }
        }
        for (int i = n - 1; i >= 0; --i) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                size /= c - '0';
                k %= (int) size;
            } else {
                if (k == 0 || k == size) {
                    return String.valueOf(c);
                }
                size--;
            }
        }
        return null;
    }

    public List<Integer> minSubsequence(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        Arrays.sort(nums);
        int totalSum = Arrays.stream(nums).sum();
        int currentSum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            currentSum += nums[i];
            ans.add(nums[i]);
            if (currentSum > totalSum - currentSum)
                break;
        }
        return ans;
    }

    public int[] sortArrayByParity(int[] nums) {
        int[] ans = new int[nums.length];
        int frontIndex = 0;
        int endIndex = nums.length - 1;
        for (int num : nums) {
            if (num % 2 == 0) {
                ans[frontIndex++] = num;
            } else {
                ans[endIndex--] = num;
            }
        }
        return ans;
    }

    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;
        int[] minArray = new int[n];
        minArray[0] = nums[0];
        for (int i = 1; i < n; i++)
            minArray[i] = Math.min(minArray[i - 1], nums[i]);
        Stack<Integer> stack = new Stack<>();
        for (int j = n - 1; j >= 0; j--) {
            if (nums[j] > minArray[j]) {
                while (!stack.isEmpty() && stack.peek() <= minArray[j])
                    stack.pop();
                if (!stack.isEmpty() && stack.peek() < nums[j])
                    return true;
                stack.push(nums[j]);
            }
        }
        return false;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int ans = 0;
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            TreeNode current = nodes.poll();
            int leftMax = current.left == null ? 0 : diameterOfBinaryTreeHelper(current.left, 0);
            int rightMax = current.right == null ? 0 : diameterOfBinaryTreeHelper(current.right, 0);
            if (current.left != null) nodes.add(current.left);
            if (current.right != null) nodes.add(current.right);
            ans = Math.max(ans, leftMax + rightMax);
        }
        return ans;
    }

    private int diameterOfBinaryTreeHelper(TreeNode node, int max) {
        if (node == null) return max;
        return Math.max(diameterOfBinaryTreeHelper(node.left, max + 1), diameterOfBinaryTreeHelper(node.right, max + 1));
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            TreeNode current = nodes.poll();
            if (current.val == subRoot.val)
                if (isSubtreeHelper(current, subRoot)) return true;
            if (current.left != null) nodes.add(current.left);
            if (current.right != null) nodes.add(current.right);
        }
        return false;
    }

    private boolean isSubtreeHelper(TreeNode node1, TreeNode node2) {
        LinkedList<TreeNode[]> nodes = new LinkedList<>();
        nodes.add(new TreeNode[]{node1, node2});
        while (!nodes.isEmpty()) {
            TreeNode[] current = nodes.poll();
            if (current[0] == null && current[1] == null) continue;
            if (current[0] == null || current[1] == null) return false;
            if (current[0].val != current[1].val) return false;
            nodes.add(new TreeNode[]{current[0].left, current[1].left});
            nodes.add(new TreeNode[]{current[0].right, current[1].right});
        }
        return true;
    }

    public int dominantIndex(int[] nums) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        int largestIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num > largest) {
                secondLargest = largest;
                largest = num;
                largestIndex = i;
            } else if (num > secondLargest) {
                secondLargest = num;
            }
        }
        return largest >= secondLargest * 2 ? largestIndex : -1;
    }

    public int findLUSlength(String a, String b) {
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }

    public int distributeCandies(int[] candyType) {
        int ans = 0;
        boolean[] found = new boolean[200001];
        for (int candy : candyType) {
            if (ans == candyType.length / 2) break;
            if (!found[candy + 100000]) {
                found[candy + 100000] = true;
                ans++;
            }
        }
        return ans;
    }

    public int minDiffInBST(TreeNode root) {
        LinkedList<Integer> nodes = new LinkedList<>();
        minDiffInBSTHelper(root, nodes);
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            if (i - 1 >= 0) minDiff = Math.min(nodes.get(i) - nodes.get(i - 1), minDiff);
            if (i + 1 < nodes.size()) minDiff = Math.min(nodes.get(i + 1) - nodes.get(i), minDiff);
        }
        return minDiff;
    }

    private void minDiffInBSTHelper(TreeNode node, LinkedList<Integer> nodes) {
        if (node == null) return;
        minDiffInBSTHelper(node.left, nodes);
        nodes.add(node.val);
        minDiffInBSTHelper(node.right, nodes);
    }

    public double findMaxAverage(int[] nums, int k) {
        double biggestSum = 0;
        for (int i = 0; i < k; i++) biggestSum += nums[i];
        double currentSum = biggestSum;
        int minIndex = 0;
        for (int i = k; i < nums.length; i++) {
            currentSum = currentSum - Math.abs(nums[minIndex++]) + nums[i];
            biggestSum = Math.max(currentSum, biggestSum);
        }
        return biggestSum;
    }

    public int findLengthOfLCIS(int[] nums) {
        int max = 1;
        int lastOne = nums[0];
        int currentLength = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > lastOne) {
                currentLength++;
            } else {
                max = Math.max(max, currentLength);
                currentLength = 1;
            }
            lastOne = nums[i];
        }
        return Math.max(currentLength, max);
    }

    public double largestTriangleArea(int[][] points) {
        double maxArea = 0.0;
        for (int i = 0; i < points.length - 2; i++) {
            for (int j = i + 1; j < points.length - 1; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    int x1 = points[i][0], y1 = points[i][1];
                    int x2 = points[j][0], y2 = points[j][1];
                    int x3 = points[k][0], y3 = points[k][1];
                    double current = 0.5 * Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
                    maxArea = Math.max(maxArea, current);
                }
            }
        }
        return maxArea;
    }

    public int minOperations(List<Integer> nums, int k) {
        boolean[] found = new boolean[k];
        int amount = 0;
        int count = 0;
        for (int i = nums.size() - 1; i >= 0; i--) {
            if (amount == k) break;
            count++;
            if (nums.get(i) < 1 || nums.get(i) > k) continue;
            if (found[nums.get(i) - 1]) continue;
            found[nums.get(i) - 1] = true;
            amount++;
        }
        return amount == k ? count : -1;
    }

    public long maximumTripletValue(int[] nums) {
        long max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    max = Math.max((long) (nums[i] - nums[j]) * nums[k], max);
                }
            }
        }
        return max;
    }

    public int alternatingSubarray(int[] nums) {
        int n = nums.length, ans = 0;
        for (int i = 0; i < n; ++i)
            for (int j = i + 1; j < n && nums[j] == nums[i] + (j - i) % 2; ++j)
                ans = Math.max(ans, j - i + 1);
        return ans > 1 ? ans : -1;
    }

    public int numPrimeArrangements(int n) {
        int amount = 0;
        for (int i = 2; i <= n; i++)
            if (numPrimeArrangementsHelper(i))
                amount++;
        long r = 1;
        for (int i = 2; i <= amount; ++i) {
            r = r * i;
            r %= 1000000007;
        }
        for (int i = 2; i <= n - amount; ++i) {
            r = r * i;
            r %= 1000000007;
        }
        return (int) r;
    }

    private boolean numPrimeArrangementsHelper(int num) {
        for (int i = 2; i <= num / 2; i++)
            if (num % i == 0)
                return false;
        return true;
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> ans = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int majority = nums.length / 3;
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for (int key : map.keySet())
            if (map.get(key) > majority) ans.add(key);
        return ans;
    }

    public int maxPower(String s) {
        int max = 0;
        int current = 0;
        char lastChar = ' ';
        for (char c : s.toCharArray()) {
            if (current == 0) {
                current = 1;
                lastChar = c;
            } else if (c == lastChar) {
                current++;
            } else {
                max = Math.max(current, max);
                lastChar = c;
                current = 1;
            }
        }
        return Math.max(current, max);
    }

    public int divisorSubstrings(int num, int k) {
        int ans = 0;
        String numString = String.valueOf(num);
        for (int i = 0; i < numString.length() - k + 1; i++) {
            int current = Integer.parseInt(numString.substring(i, i + k));
            if (current == 0) continue;
            if (num % current == 0) ans++;
        }
        return ans;
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> ans = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int current = arr[i] - arr[i - 1];
            if (current < min) {
                ans.clear();
                ans.add(Arrays.asList(arr[i - 1], arr[i]));
                min = current;
            } else if (current == min) {
                ans.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return ans;
    }

    public int[] searchRange(int[] nums, int target) {
        int start = -1, end = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < target) continue;
            if (nums[i] > target) break;
            if (start == -1) {
                start = i;
            } else {
                end = i;
            }
        }
        return start != -1 && end == -1 ? new int[]{start, start} : new int[]{start, end};
    }

    public double average(int[] salary) {
        double sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int current : salary) {
            min = Math.min(current, min);
            max = Math.max(current, max);
            sum += current;
        }
        return (sum - min - max) / (salary.length - 2);
    }

    public int differenceOfSums(int n, int m) {
        int sum1 = 0, sum2 = 0;
        for (int i = 1; i <= n; i++) {
            if (i % m != 0) {
                sum1 += i;
            } else {
                sum2 += i;
            }
        }
        return sum1 - sum2;
    }

    public int[] circularGameLosers(int n, int k) {
        // Look who had the ball
        boolean[] visited = new boolean[n];
        // Count how many people had the ball
        int found = 0;
        int amount = 1;
        int index = 0;
        // while friend hasn't got the ball the second time
        while (!visited[index]) {
            visited[index] = true;
            found++;
            index = (index + k * amount++) % n;
        }
        int[] ans = new int[n - found];
        int j = 0;
        for (int i = 0; i < n; i++) {
            // people who never got the ball
            if (!visited[i])
                ans[j++] = i + 1;
        }
        return ans;
    }

    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            int newValue = insertGreatestCommonDivisorsHelper(current.val, current.next.val);
            ListNode newNode = new ListNode(newValue, current.next);
            current.next = newNode;
            current = newNode.next;
        }
        return head;
    }

    private int insertGreatestCommonDivisorsHelper(int a, int b) {
        if (b == 0) return a;
        return insertGreatestCommonDivisorsHelper(b, a % b);
    }

    public int[] findArray(int[] pref) {
        int[] ans = new int[pref.length];
        int current = 0;
        for (int i = 0; i < pref.length; i++) {
            ans[i] = current ^ pref[i];
            current ^= ans[i];
        }
        return ans;
    }

    public int removeDuplicates(int[] nums) {
        int found = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length - found; i++) {
            int current = nums[i];
            int get = map.getOrDefault(current, 0);
            if (get >= 2) {
                for (int j = i + 1; j < nums.length; j++) {
                    nums[j - 1] = nums[j];
                }
                i--;
                found++;
            }
            map.put(current, get + 1);
        }
        return nums.length - found;
    }

    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (Math.abs(i - j) < indexDifference) continue;
                if (Math.abs(nums[i] - nums[j]) >= valueDifference) return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

    public void rotate(int[] nums, int k) {
        k %= nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

    public int[] pivotArray(int[] nums, int pivot) {
        LinkedList<Integer> front = new LinkedList<>();
        LinkedList<Integer> back = new LinkedList<>();
        int amountPivot = 0;
        for (int num : nums) {
            if (num == pivot) amountPivot++;
            else if (num < pivot) front.addLast(num);
            else back.addLast(num);
        }
        int[] ans = new int[nums.length];
        int index = 0;
        while (!front.isEmpty()) {
            ans[index++] = front.poll();
        }
        for (int i = 0; i < amountPivot; i++) {
            ans[index++] = pivot;
        }
        while (!back.isEmpty()) {
            ans[index++] = back.poll();
        }
        return ans;
    }

    public int projectionArea(int[][] grid) {
        int a = 0, x = 0;
        for (int i = 0; i < grid.length; i++) {
            int mr = Integer.MIN_VALUE;
            int mc = Integer.MIN_VALUE;
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) x += 1;
                if (grid[i][j] > mr) mr = grid[i][j];
                if (grid[j][i] > mc) mc = grid[j][i];
            }
            a += mr + mc;
        }
        return a + x;
    }

    public List<Integer> lastVisitedIntegers(List<String> words) {
        List<Integer> ans = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        int index = 0;
        for (String s : words) {
            try {
                Integer current = Integer.valueOf(s);
                list.add(current);
                index = list.size() - 1;
            } catch (NumberFormatException e) {
                if (index >= list.size() || index < 0) ans.add(-1);
                else ans.add(list.get(index--));
            }
        }
        return ans;
    }

    public int numberOfCuts(int n) {
        if (n == 1) return 0;
        return n % 2 == 0 ? n / 2 : n;
    }

    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public class NestedIterator implements Iterator<Integer> {

        List<Integer> list;
        Iterator<Integer> iterator;

        public NestedIterator(List<NestedInteger> nestedList) {
            list = new LinkedList<>();
            add(nestedList);
            iterator = list.iterator();
        }

        private void add(List<NestedInteger> nestedList) {
            for (NestedInteger i : nestedList) {
                if (i.isInteger()) {
                    list.add(i.getInteger());
                } else {
                    add(i.getList());
                }
            }
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }

    class BrowserHistory {

        LinkedList<String> history = new LinkedList<>();
        LinkedList<String> forward = new LinkedList<>();

        public BrowserHistory(String homepage) {
            history.push(homepage);
        }

        public void visit(String url) {
            forward.clear();
            history.push(url);
        }

        public String back(int steps) {
            while (history.size() > 1 && steps > 0) {
                String tmp = history.pop();
                forward.push(tmp);
                steps--;
            }
            return history.peek();
        }

        public String forward(int steps) {
            while (!forward.isEmpty() && steps > 0) {
                String tmp = forward.pop();
                history.push(tmp);
                steps--;
            }
            return history.peek();
        }
    }

    class CustomStack {

        LinkedList<Integer> stack = new LinkedList<>();
        int maxSize;

        public CustomStack(int maxSize) {
            this.maxSize = maxSize;
        }

        public void push(int x) {
            if (stack.size() >= maxSize) return;
            stack.push(x);
        }

        public int pop() {
            if (stack.isEmpty()) return -1;
            return stack.pop();
        }

        public void increment(int k, int val) {
            int count = 0;
            for (int i = stack.size() - 1; i >= 0 && count++ < k; i--) {
                int tmp = stack.get(i);
                stack.set(i, tmp + val);
            }
        }
    }

    class FindElements {

        TreeNode root;

        public FindElements(TreeNode root) {
            this.root = root;
            this.root.val = 0;
            recover(this.root);
        }

        private void recover(TreeNode current) {
            if (current == null) return;
            if (current.left != null) {
                current.left.val = current.val * 2 + 1;
                recover(current.left);
            }
            if (current.right != null) {
                current.right.val = current.val * 2 + 2;
                recover(current.right);
            }
        }

        public boolean find(int target) {
            return findHelper(target, root);
        }

        private boolean findHelper(int target, TreeNode current) {
            if (current == null) return false;
            if (current.val == target) return true;
            return findHelper(target, current.left) || findHelper(target, current.right);
        }
    }

    class BSTIterator {

        LinkedList<TreeNode> list;
        Iterator<TreeNode> iter;

        public BSTIterator(TreeNode root) {
            list = new LinkedList<>();
            inorder(root);
            iter = list.iterator();
        }

        private void inorder(TreeNode node) {
            if (node == null) return;
            inorder(node.left);
            list.add(node);
            inorder(node.right);
        }

        public int next() {
            return iter.next().val;
        }

        public boolean hasNext() {
            return iter.hasNext();
        }
    }

    class Twitter {

        private int timestamp;
        private final Map<Integer, List<Integer>> tweets;
        private final Map<Integer, Set<Integer>> followers;

        public Twitter() {
            timestamp = 0;
            tweets = new HashMap<>();
            followers = new HashMap<>();
        }

        public void postTweet(int userId, int tweetId) {
            List<Integer> current = tweets.getOrDefault(userId, new LinkedList<>());
            current.add(0, tweetId);
            tweets.put(userId, current);
            timestamp++;
        }

        public List<Integer> getNewsFeed(int userId) {
            if (!tweets.containsKey(userId)) return new LinkedList<>();
            List<Integer> newsFeed = new LinkedList<>();
            Set<Integer> userFollowers = followers.getOrDefault(userId, new HashSet<>());
            PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> {
                int tweetA = tweets.get(a[0]).get(a[1]);
                int tweetB = tweets.get(b[0]).get(b[1]);
                return Integer.compare(tweetB, tweetA);
            });
            for (Integer followerId : userFollowers) {
                queue.add(new int[]{followerId, 0});
            }
            queue.add(new int[]{userId, 0});
            while (newsFeed.size() < 10 && !queue.isEmpty()) {
                int[] tweetInfo = queue.poll();
                int tweetUserId = tweetInfo[0];
                int currentIndex = tweetInfo[1];
                newsFeed.add(tweets.get(tweetUserId).get(currentIndex));
                currentIndex++;
                if (currentIndex < tweets.get(tweetUserId).size()) {
                    queue.add(new int[]{tweetUserId, currentIndex});
                }
            }
            return newsFeed;
        }

        public void follow(int followerId, int followeeId) {
            if (followerId == followeeId) return;
            Set<Integer> userFollowers = followers.getOrDefault(followerId, new HashSet<>());
            userFollowers.add(followeeId);
            followers.put(followerId, userFollowers);
        }

        public void unfollow(int followerId, int followeeId) {
            if (followerId == followeeId) return;
            Set<Integer> userFollowers = followers.getOrDefault(followerId, new HashSet<>());
            userFollowers.remove(followeeId);
            followers.put(followerId, userFollowers);
        }
    }

    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> nodeMap = new HashMap<>();
        Set<Integer> children = new HashSet<>();
        for (int[] desc : descriptions) {
            int parentValue = desc[0];
            int childValue = desc[1];
            int direction = desc[2];
            TreeNode parent = nodeMap.computeIfAbsent(parentValue, TreeNode::new);
            TreeNode child = nodeMap.computeIfAbsent(childValue, TreeNode::new);
            if (direction == 1) parent.left = child;
            else parent.right = child;
            children.add(childValue);
        }
        TreeNode root = null;
        for (TreeNode node : nodeMap.values()) {
            if (!children.contains(node.val)) {
                root = node;
                break;
            }
        }
        return root;
    }

    class Allocator {

        private final int size;
        List<int[]> freeSpace;
        Map<Integer, List<int[]>> usedSpace;

        public Allocator(int n) {
            this.size = n;
            freeSpace = new LinkedList<>();
            freeSpace.add(new int[]{0, size - 1});
            usedSpace = new HashMap<>();
        }

        public int allocate(int size, int mID) {
            if (freeSpace.isEmpty()) return -1;
            for (int i = 0; i < freeSpace.size(); i++) {
                int[] space = freeSpace.get(i);
                int begin = space[0];
                int end = space[1];
                // Space is too small
                if (end - begin + 1 < size) continue;
                // Get the list of used space from this ID or if absent create new one
                List<int[]> list = usedSpace.getOrDefault(mID, new LinkedList<>());
                int newEnd = begin + size - 1;
                int[] use = new int[]{begin, newEnd};
                // There is left over space
                if (newEnd != end) {
                    int[] tmp = new int[]{newEnd + 1, end};
                    freeSpace.set(i, tmp);
                } else { // There isn't left over space
                    freeSpace.remove(i);
                }
                list.add(use);
                usedSpace.put(mID, list);
                return begin;
            }
            return -1;
        }

        public int free(int mID) {
            if (!usedSpace.containsKey(mID)) return 0;
            List<int[]> current = usedSpace.get(mID);
            int ans = 0;
            for (int[] block : current) {
                ans += block[1] - block[0] + 1;
                freeSpace.add(block);
            }
            freeSpace.sort(Comparator.comparingInt(array -> array[0]));
            mergeFreeBlocks(0);
            usedSpace.remove(mID);
            return ans;
        }

        private void mergeFreeBlocks(int index) {
            if (index >= freeSpace.size() - 1) return;
            int[] currentBlock = freeSpace.get(index);
            int[] nextBlock = freeSpace.get(index + 1);
            if (currentBlock[1] + 1 == nextBlock[0]) {
                freeSpace.set(index, new int[]{currentBlock[0], nextBlock[1]});
                freeSpace.remove(index + 1);
                mergeFreeBlocks(index);
            } else {
                mergeFreeBlocks(index + 1);
            }
        }
    }

    class UndergroundSystem {

        private class Pair<A, B> {
            private final A a;
            private final B b;

            public Pair(A a, B b) {
                this.a = a;
                this.b = b;
            }

            public A getA() {
                return a;
            }

            public B getB() {
                return b;
            }
        }

        Map<Integer, Pair<String, Integer>> map;
        Map<String, Map<String, List<Integer>>> averages;

        public UndergroundSystem() {
            map = new HashMap<>();
            averages = new HashMap<>();
        }

        public void checkIn(int id, String stationName, int t) {
            map.put(id, new Pair<>(stationName, t));
        }

        public void checkOut(int id, String stationName, int t) {
            Pair<String, Integer> current = map.get(id);
            String start = current.getA();
            int startTime = current.getB();
            Map<String, List<Integer>> currentDest = averages.getOrDefault(start, new HashMap<>());
            List<Integer> currentTimes = currentDest.getOrDefault(stationName, new LinkedList<>());
            currentTimes.add(t - startTime);
            currentDest.put(stationName, currentTimes);
            averages.put(start, currentDest);
        }

        public double getAverageTime(String startStation, String endStation) {
            if (!averages.containsKey(startStation)) return 0;
            Map<String, List<Integer>> currentTimes = averages.get(startStation);
            if (!currentTimes.containsKey(endStation)) return 0;
            double ans = 0;
            for (Integer i : currentTimes.get(endStation)) {
                ans += i;
            }
            return ans / currentTimes.get(endStation).size();
        }
    }

    class MyCircularQueue {

        private class Node {

            Node next;
            int val;

            public Node(int val, Node next) {
                this.next = next;
                this.val = val;
            }


        }

        Node head;
        Node tail;
        int currentSize;
        int maxSize;

        public MyCircularQueue(int k) {
            maxSize = k;
        }

        public boolean enQueue(int value) {
            if (currentSize == maxSize) return false;
            // Queue is empty
            if (currentSize == 0) {
                Node newNode = new Node(value, null);
                newNode.next = newNode;
                head = newNode;
                tail = newNode;
            } else if (currentSize == 1) { // Queue only has one element
                head = new Node(value, tail);
                tail.next = head;
            } else { // Queue is bigger than 1
                head = new Node(value, head);
                tail.next = head;
            }
            currentSize++;
            return true;
        }

        public boolean deQueue() {
            if (currentSize == 0) return false;
            Node current = head;
            while (current != null && current.next != tail) {
                current = current.next;
            }
            tail = current;
            tail.next = head;
            currentSize--;
            return true;
        }

        public int Front() {
            return currentSize == 0 ? -1 : tail.val;
        }

        public int Rear() {
            return currentSize == 0 ? -1 : head.val;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public boolean isFull() {
            return currentSize == maxSize;
        }
    }

    class AuthenticationManager {

        private final int timeToLive;
        private Map<String, Integer> entries;

        public AuthenticationManager(int timeToLive) {
            this.timeToLive = timeToLive;
            this.entries = new HashMap<>();
        }

        public void generate(String tokenId, int currentTime) {
            entries.put(tokenId, currentTime + timeToLive);
        }

        public void renew(String tokenId, int currentTime) {
            if (!entries.containsKey(tokenId) || entries.get(tokenId) <= currentTime)
                return;
            entries.put(tokenId, currentTime + timeToLive);
        }

        public int countUnexpiredTokens(int currentTime) {
            int ans = 0;
            for (Map.Entry<String, Integer> entry : entries.entrySet()) {
                if (entry.getValue() > currentTime) ans++;
            }
            return ans;
        }
    }

    class FrontMiddleBackQueue {

        List<Integer> queue;

        public FrontMiddleBackQueue() {
            queue = new ArrayList<>();
        }

        public void pushFront(int val) {
            queue.add(0, val);
        }

        public void pushMiddle(int val) {
            queue.add(queue.size() / 2, val);
        }

        public void pushBack(int val) {
            queue.add(queue.size(), val);
        }

        public int popFront() {
            if (queue.isEmpty()) return -1;
            return queue.remove(0);
        }

        public int popMiddle() {
            if (queue.isEmpty()) return -1;
            return queue.remove((queue.size() - 1) / 2);
        }

        public int popBack() {
            if (queue.isEmpty()) return -1;
            return queue.remove(queue.size() - 1);
        }
    }

    class MyCircularDeque {

        LinkedList<Integer> dequeue;
        int maxSize;

        public MyCircularDeque(int k) {
            dequeue = new LinkedList<>();
            maxSize = k;
        }

        public boolean insertFront(int value) {
            if (dequeue.size() == maxSize) return false;
            dequeue.addFirst(value);
            return true;
        }

        public boolean insertLast(int value) {
            if (dequeue.size() == maxSize) return false;
            dequeue.addLast(value);
            return true;
        }

        public boolean deleteFront() {
            if (dequeue.isEmpty()) return false;
            dequeue.removeFirst();
            return true;
        }

        public boolean deleteLast() {
            if (dequeue.isEmpty()) return false;
            dequeue.removeLast();
            return true;
        }

        public int getFront() {
            if (dequeue.isEmpty()) return -1;
            return dequeue.getFirst();
        }

        public int getRear() {
            if (dequeue.isEmpty()) return -1;
            return dequeue.getLast();
        }

        public boolean isEmpty() {
            return dequeue.isEmpty();
        }

        public boolean isFull() {
            return dequeue.size() == maxSize;
        }
    }

    class ATM {

        long[] money;
        final int AMOUNT_MONEY = 5;
        final long[] banknoteValues = {20, 50, 100, 200, 500};

        public ATM() {
            money = new long[AMOUNT_MONEY];
        }

        public void deposit(int[] banknotesCount) {
            for (int i = 0; i < AMOUNT_MONEY; i++) {
                money[i] += banknotesCount[i];
            }
        }

        public int[] withdraw(int amount) {
            int[] ans = new int[5];
            int remainingAmount = amount;
            for (int i = AMOUNT_MONEY - 1; i >= 0; i--) {
                long banknoteValue = banknoteValues[i];
                long banknotesToUse = Math.min(remainingAmount / banknoteValue, money[i]);
                remainingAmount -= (int) (banknotesToUse * banknoteValue);
                money[i] -= banknotesToUse;
                ans[i] = (int) banknotesToUse;
            }
            if (remainingAmount == 0) {
                return ans;
            } else {
                for (int i = 0; i < AMOUNT_MONEY; i++) {
                    money[i] += ans[i];
                    ans[i] = 0;
                }
                return new int[]{-1};
            }
        }
    }

    public int averageOfSubtree(TreeNode root) {
        if (root == null) return 0;
        if (averageOfSubtreeHelper(root) == root.val)
            return 1 + averageOfSubtree(root.left) + averageOfSubtree(root.right);
        return averageOfSubtree(root.left) + averageOfSubtree(root.right);
    }

    private int averageOfSubtreeHelper(TreeNode node) {
        LinkedList<TreeNode> visited = new LinkedList<>();
        visited.add(node);
        int amountNodes = 0;
        int sum = 0;
        while (!visited.isEmpty()) {
            TreeNode current = visited.pop();
            amountNodes++;
            sum += current.val;
            if (current.left != null) visited.push(current.left);
            if (current.right != null) visited.push(current.right);
        }
        return sum / amountNodes;
    }

    public int sumCounts(List<Integer> nums) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < nums.size(); j++) {
                set.add(nums.get(j));
                ans += (int) Math.pow(set.size(), 2);
            }
        }
        return ans;
    }

    class WordDictionary {

        private class Node {

            private final HashMap<Character, Node> next;
            private boolean end;

            public Node() {
                next = new HashMap<>();
                end = false;
            }
        }

        private Node root;

        public WordDictionary() {
            root = new Node();
        }

        public void addWord(String word) {
            Node current = root;
            for (char c : word.toCharArray()) {
                if (!current.next.containsKey(c)) {
                    current.next.put(c, new Node());
                }
                current = current.next.get(c);
            }
            current.end = true;
        }

        public boolean search(String word) {
            return searchWordHelper(root, word, 0);
        }

        private boolean searchWordHelper(Node node, String word, int index) {
            if (index == word.length()) return node.end;
            char c = word.charAt(index);
            if (c == '.') {
                for (Node child : node.next.values()) {
                    if (searchWordHelper(child, word, index + 1)) {
                        return true;
                    }
                }
            } else {
                if (node.next.containsKey(c)) {
                    return searchWordHelper(node.next.get(c), word, index + 1);
                }
            }
            return false;
        }
    }

    public int findChampion(int[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            int[] current = grid[i];
            int amount1 = Arrays.stream(current).sum();
            if (amount1 == n - 1) return i;
        }
        return -1;
    }

    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        Set<Double> set = new HashSet<>();
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int a = nums[i++];
            int b = nums[j--];
            double average = (a + b) / 2.0;
            set.add(average);
        }
        return set.size();
    }

    class SeatManager {

        boolean[] seats;
        PriorityQueue<Integer> queue;

        public SeatManager(int n) {
            seats = new boolean[n];
            queue = new PriorityQueue<>(n);
            for (int i = 0; i < n; i++) {
                queue.add(i);
            }
        }

        public int reserve() {
            if (queue.isEmpty()) return -1;
            int reserve = queue.remove();
            seats[reserve] = true;
            return reserve + 1;
        }

        public void unreserve(int seatNumber) {
            if (!seats[seatNumber - 1]) return;
            seats[seatNumber - 1] = false;
            queue.add(seatNumber - 1);
        }
    }

    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        if (sx == fx && sy == fy) return t != 1;
        int y = Math.abs(sy - fy);
        int x = Math.abs(sx - fx);
        return Math.max(x, y) <= t;
    }

    public List<List<Integer>> findMatrix(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        for (int num : nums) {
            boolean found = false;
            for (List<Integer> current : ans) {
                if (current.contains(num)) continue;
                current.add(num);
                found = true;
                break;
            }
            if (!found) {
                List<Integer> newList = new LinkedList<>();
                newList.add(num);
                ans.add(newList);
            }
        }
        return ans;
    }

    public int[] processQueries(int[] queries, int m) {
        int[] ans = new int[queries.length];
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < m; i++)
            list.addLast(i + 1);
        for (int i = 0; i < queries.length; i++) {
            int current = queries[i];
            int index = list.indexOf(current);
            list.remove(index);
            ans[i] = index;
            list.addFirst(current);
        }
        return ans;
    }

    public int[] restoreArray(int[][] adjacentPairs) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> pairs = new HashMap<>();
        // Find pairs
        for (int[] pair : adjacentPairs) {
            pairs.computeIfAbsent(pair[0], k -> new LinkedList<>()).add(pair[1]);
            pairs.computeIfAbsent(pair[1], k -> new LinkedList<>()).add(pair[0]);
        }
        int[] ans = new int[adjacentPairs.length + 1];
        int index = 0;
        // Find a start point
        int current = 0;
        for (int key : pairs.keySet()) {
            if (pairs.get(key).size() == 1) {
                current = key;
                break;
            }
        }
        while (index < ans.length) {
            ans[index++] = current;
            visited.add(current);
            for (int neighbor : pairs.get(current)) {
                if (!visited.contains(neighbor)) {
                    current = neighbor;
                    break;
                }
            }
        }
        return ans;
    }

    class Graph {

        int[][] graph;

        public Graph(int n, int[][] edges) {
            graph = new int[n][n];
            for (int[] current : graph) Arrays.fill(current, -1);
            for (int[] edge : edges) {
                addEdge(edge);
            }
        }

        public void addEdge(int[] edge) {
            int from = edge[0];
            int to = edge[1];
            int cost = edge[2];
            graph[from][to] = graph[to][from] = cost;
        }

        public int shortestPath(int node1, int node2) {
            int n = graph.length;
            int[] distance = new int[n];
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[node1] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            pq.add(new int[]{node1, 0});
            while (!pq.isEmpty()) {
                int[] current = pq.poll();
                int u = current[0];
                int dist = current[1];
                if (dist > distance[u]) continue;
                for (int v = 0; v < n; v++) {
                    if (graph[u][v] != -1) {
                        int newDistance = distance[u] + graph[u][v];
                        if (newDistance < distance[v]) {
                            distance[v] = newDistance;
                            pq.add(new int[]{v, newDistance});
                        }
                    }
                }
            }
            return distance[node2] == Integer.MAX_VALUE ? -1 : distance[node2];
        }
    }

    public int maximumStrongPairXor(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (Math.abs(nums[i] - nums[j]) <= Math.min(nums[i], nums[j]))
                    max = Math.max(max, nums[i] ^ nums[j]);
            }
        }
        return max;
    }

    public int countQuadruplets(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    for (int l = k + 1; l < nums.length; l++) {
                        if (nums[i] + nums[j] + nums[k] == nums[l]) ans++;
                    }
                }
            }
        }
        return ans;
    }

    public boolean isPathCrossing(String path) {
        int x = 0, y = 0;
        Map<Integer, List<Integer>> visited = new HashMap<>();
        List<Integer> tmp = new LinkedList<>();
        tmp.add(0);
        visited.put(0, tmp);
        for (char c : path.toCharArray()) {
            switch (c) {
                case 'N' -> y++;
                case 'E' -> x++;
                case 'S' -> y--;
                case 'W' -> x--;
            }
            if (!visited.containsKey(x))
                visited.put(x, new LinkedList<>());
            List<Integer> current = visited.get(x);
            if (current.contains(y)) return true;
            current.add(y);
        }
        return false;
    }

    public int countPalindromicSubsequence(String s) {
        int ans = 0;
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray())
            set.add(c);
        for (char c : set) {
            int start = s.indexOf(c);
            int end = s.lastIndexOf(c);
            if (start < end) {
                Set<Character> charSet = new HashSet<>();
                for (int i = start + 1; i < end; i++)
                    charSet.add(s.charAt(i));
                ans += charSet.size();
            }
        }
        return ans;
    }

    public String reorderSpaces(String text) {
        int amountSpaces = 0;
        List<StringBuilder> strings = new LinkedList<>();
        boolean wasAlph = false;
        StringBuilder current = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                if (wasAlph) {
                    strings.add(current);
                    current = new StringBuilder();
                }
                wasAlph = false;
                amountSpaces++;
            } else {
                wasAlph = true;
                current.append(c);
            }
        }
        if (current.length() != 0) strings.add(current);
        return getStringBuilder(strings, amountSpaces).toString();
    }

    private static StringBuilder getStringBuilder(List<StringBuilder> strings, int amountSpaces) {
        StringBuilder ans = new StringBuilder();
        int spaces = strings.size() > 1 ? amountSpaces / (strings.size() - 1) : 0;
        int extraSpaces = strings.size() > 1 ? amountSpaces % (strings.size() - 1) : amountSpaces;
        for (int i = 0; i < strings.size(); i++) {
            StringBuilder str = strings.get(i);
            ans.append(str);
            if (i != strings.size() - 1) {
                ans.append(" ".repeat(Math.max(0, spaces)));
            }
        }
        ans.append(" ".repeat(Math.max(0, extraSpaces)));
        return ans;
    }

    public int mostFrequent(int[] nums, int key) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != key) continue;
            map.put(nums[i + 1], map.getOrDefault(nums[i + 1], 0) + 1);
        }
        int max = -1;
        int maxValue = -1;
        for (int k : map.keySet()) {
            if (map.get(k) > max) {
                max = map.get(k);
                maxValue = k;
            }
        }
        return maxValue;
    }

    public String findDifferentBinaryString(String[] nums) {
        int n = nums[0].length();
        int max = (int) Math.pow(2, n);
        boolean[] found = new boolean[max];
        for (String s : nums) {
            int index = Integer.parseInt(s, 2);
            found[index] = true;
        }
        for (int i = 0; i < found.length; i++) {
            if (!found[i]) {
                String tmp = Integer.toBinaryString(i);
                return "0".repeat(n - tmp.length()) + tmp;
            }
        }
        return null;
    }

    public int rearrangeCharacters(String s, String target) {
        if (s.equals(target)) return 1;
        if (target.repeat(s.length() / target.length()).equals(s)) return s.length() / target.length();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            max = i;
            for (char c : target.toCharArray()) {
                if (!map.containsKey(c)) {
                    return max;
                }
                int get = map.get(c);
                if (get == 0) return max;
                map.put(c, get - 1);
            }
        }
        return max;
    }

    public int reductionOperations(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        int operations = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1])
                operations++;
            ans += operations;
        }
        return ans;
    }

    public int countNicePairs(int[] nums) {
        long ans = 0;
        int mod = ((int) Math.pow(10, 9) + 7);
        Map<Long, Integer> map = new HashMap<>();
        for (int num : nums) {
            long tmp = countNicePairsHelper(num);
            map.put(tmp, map.getOrDefault(tmp, 0) + 1);
        }
        for (long key : map.keySet()) {
            int val = map.get(key);
            ans = (ans % mod + ((long) val * ((long) val - 1) / 2)) % mod;
        }
        return (int) ans;
    }

    private long countNicePairsHelper(int num) {
        return num - Long.parseLong(new StringBuilder(String.valueOf(num)).reverse().toString());
    }

    public int findMinimumOperations(String s1, String s2, String s3) {
        String first = findMinimumOperationsHelper(s1, s2);
        String second = findMinimumOperationsHelper(first, s3);
        if (second.isEmpty()) return -1;
        return s1.length() - second.length() + s2.length() - second.length() + s3.length() - second.length();
    }

    private String findMinimumOperationsHelper(String s1, String s2) {
        StringBuilder ans = new StringBuilder();
        int i = 0;
        while (i < s1.length() && i < s2.length()) {
            if (s1.charAt(i) != s2.charAt(i)) break;
            ans.append(s1.charAt(i++));
        }
        return ans.toString();
    }

    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        List<int[]> tuples = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> row = nums.get(i);
            for (int j = 0; j < row.size(); j++)
                tuples.add(new int[]{i + j, i, row.get(j)});
        }
        tuples.sort(Comparator.comparingInt((int[] tuple) -> tuple[0]));
        int[] result = new int[tuples.size()];
        for (int i = 0; i < tuples.size(); i++)
            result[i] = tuples.get(i)[2];
        return result;
    }

    public boolean makeEqual(String[] words) {
        Map<Character, Integer> map = new HashMap<>();
        for (String s : words)
            s.chars().forEach(c -> map.merge((char) c, 1, Integer::sum));
        for (int value : map.values())
            if (value % words.length != 0) return false;
        return true;
    }

    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> ans = new LinkedList<>();
        for (int i = 0; i < l.length; i++) {
            int[] current = Arrays.copyOfRange(nums, l[i], r[i] + 1);
            Arrays.sort(current);
            int diff = current[1] - current[0];
            boolean arith = true;
            for (int j = 1; j < current.length - 1; j++) {
                int tmp = current[j + 1] - current[j];
                if (diff != tmp) {
                    arith = false;
                    break;
                }
            }
            ans.add(arith);
        }
        return ans;
    }

    public int getMaximumGenerated(int n) {
        if (n == 0) return 0;
        int max = 1;
        int[] nums = new int[n + 1];
        nums[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                nums[i] = nums[i / 2];
            } else {
                nums[i] = nums[i / 2] + nums[i / 2 + 1];
            }
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> ans = new LinkedList<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].contains(String.valueOf(x)))
                ans.add(i);
        }
        return ans;
    }

    public boolean areSimilar(int[][] mat, int k) {
        int[][] copy = new int[mat.length][];
        for (int i = 0; i < mat.length; i++) {
            copy[i] = Arrays.copyOf(mat[i], mat[i].length);
            areSimilarHelper(copy[i], k, i % 2 != 0);
        }
        for (int i = 0; i < mat.length; i++)
            if (Arrays.compare(mat[i], copy[i]) != 0) return false;
        return true;
    }

    private static void areSimilarHelper(int[] arr, int k, boolean right) {
        int n = arr.length;
        k = (k % n + n) % n;
        for (int i = 0; i < k; i++) {
            if (right) {
                int tmp = arr[n - 1];
                System.arraycopy(arr, 0, arr, 1, n - 1);
                arr[0] = tmp;
            } else {
                int tmp = arr[0];
                System.arraycopy(arr, 1, arr, 0, n - 1);
                arr[n - 1] = tmp;
            }
        }
    }

    public boolean containsPattern(int[] arr, int m, int k) {
        if (m * k > arr.length) return false;
        for (int start = 0; start + m * k - 1 < arr.length; start++) {
            boolean found = true;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < k; j++) {
                    if (arr[start + i] != arr[start + i + j * m]) {
                        found = false;
                        break;
                    }
                }
                if (!found) break;
            }
            if (found) return true;
        }
        return false;
    }

    public int specialArray(int[] nums) {
        int x = nums.length / 2;
        boolean wasHigher = false;
        boolean start = true;
        while (x >= 0 && x <= nums.length) {
            int count = 0;
            for (int num : nums) {
                if (num >= x) {
                    count++;
                }
                if (count > x) break;
            }
            if (count == x) return x;
            if (x == nums.length / 2 && start) {
                start = false;
                wasHigher = count > x;
            }
            if (!wasHigher) x--;
            else x++;
        }
        return -1;
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        int i = 0;
        while (i < arr.length) {
            int current = arr[i];
            int[] tmp = null;
            for (int[] piece : pieces) {
                if (piece[0] != current) continue;
                tmp = piece;
                break;
            }
            if (tmp == null) return false;
            for (int integer : tmp) {
                if (integer != arr[i++]) return false;
            }
        }
        return true;
    }

    public int countCharacters(String[] words, String chars) {
        int[] cnt = new int[26];
        int ans = 0;
        chars.chars().forEach(c -> ++cnt[c - 'a']);
        outer:
        for (String w : words) {
            int[] count = cnt.clone();
            for (char c : w.toCharArray())
                if (--count[c - 'a'] < 0)
                    continue outer;
            ans += w.length();
        }
        return ans;
    }

    public List<Integer> findPeaks(int[] mountain) {
        List<Integer> ans = new LinkedList<>();
        for (int i = 1; i < mountain.length - 1; i++) {
            if (mountain[i - 1] < mountain[i] && mountain[i] > mountain[i + 1])
                ans.add(i);
        }
        return ans;
    }

    public int largestPerimeter1(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i - 2 >= 0; i++) {
            if (nums[i - 2] + nums[i - 1] > nums[i])
                return nums[i] + nums[i - 1] + nums[i - 2];
        }
        return 0;
    }

    public String sortVowels(String s) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (char c : s.toCharArray())
            if (sortVowelsHelper(c))
                queue.add((int) c);
        StringBuilder ans = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (sortVowelsHelper(c)) {
                ans.append((char) (int) queue.poll());
            } else {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    private boolean sortVowelsHelper(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    public String largestGoodInteger(String num) {
        int max = -1;
        for (int i = 0; i < num.length() - 2; i++) {
            char first = num.charAt(i);
            char second = num.charAt(i + 1);
            char third = num.charAt(i + 2);
            if (first == second && first == third) {
                int current = Character.getNumericValue(first);
                if (current > max)
                    max = current;
            }
        }
        if (max == -1)
            return "";
        return "" + max + max + max;
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                matrix[i][j] = matrix[i][j] ^ matrix[j][i];
                matrix[j][i] = matrix[i][j] ^ matrix[j][i];
                matrix[i][j] = matrix[i][j] ^ matrix[j][i];
            }
        }
        for (int[] row : matrix) {
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                row[i] = row[i] ^ row[j];
                row[j] = row[i] ^ row[j];
                row[i] = row[i] ^ row[j];
            }
        }
    }

    public int distributeCandies2(int n, int limit) {
        int ans = 0;
        for (int i = 1; i <= limit; i++) {
            if (i > n) break;
            for (int j = 1; j <= limit; j++) {
                if (i + j > n) break;
                int k = n - i - j;
                if (k <= limit)
                    ans++;
            }
        }
        return ans;
    }

    public int removePalindromeSub(String s) {
        return new StringBuilder(s).reverse().toString().equals(s) ? 1 : 2;
    }

    public int smallestRangeI(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        return Math.max((max - min) - (2 * k), 0);
    }

    public int[] findIntersectionValues(int[] nums1, int[] nums2) {
        int ans1 = 0, ans2 = 0;
        for (int num1 : nums1) {
            boolean found = false;
            for (int num2 : nums2) {
                if (num1 == num2) {
                    found = true;
                    break;
                }
            }
            if (found) ans1++;
        }
        for (int num2 : nums2) {
            boolean found = false;
            for (int num1 : nums1) {
                if (num1 == num2) {
                    found = true;
                    break;
                }
            }
            if (found) ans2++;
        }
        return new int[]{ans1, ans2};
    }

    public int countTestedDevices(int[] batteryPercentages) {
        int ans = 0;
        int count = 0;
        for (int batteryPercentage : batteryPercentages) {
            if (batteryPercentage - count <= 0) continue;
            ans++;
            count++;
        }
        return ans;
    }

    public boolean areAlmostEqual(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            map1.put(c1, map1.getOrDefault(c1, 0) + 1);
            map2.put(c2, map2.getOrDefault(c2, 0) + 1);
            if (c1 != c2) diff++;
            if (diff > 2) break;
        }
        for (char key : map1.keySet()) {
            int val1 = map1.get(key);
            int val2 = map2.getOrDefault(key, -1);
            if (val1 != val2) return false;
        }
        return diff == 0 || diff == 2;
    }

    public int[] rearrangeArray(int[] nums) {
        int indexPos = 0, indexNeg = 0;
        int[] ans = new int[nums.length];
        for (int i = 0; i < ans.length; i++) {
            if (i % 2 == 0)
                indexPos = rearrangeArrayHelper(nums, indexPos, ans, i, true);
            else
                indexNeg = rearrangeArrayHelper(nums, indexNeg, ans, i, false);
        }
        return ans;
    }

    private int rearrangeArrayHelper(int[] nums, int start, int[] ans, int index, boolean pos) {
        int j = start;
        while (j < nums.length) {
            boolean condition = pos == (nums[j] < 0);
            if (condition) {
                j++;
            } else {
                ans[index] = nums[j];
                return j + 1;
            }
        }
        return start;
    }

    public int[][] onesMinusZeros(int[][] grid) {
        int[] onesRow = new int[grid.length], zeroRow = new int[grid.length];
        int[] onesCol = new int[grid[0].length], zeroCol = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    onesRow[i]++;
                    onesCol[j]++;
                } else {
                    zeroRow[i]++;
                    zeroCol[j]++;
                }
            }
        }
        int[][] ans = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int current = onesRow[i] + onesCol[j] - zeroRow[i] - zeroCol[j];
                ans[i][j] = current;
            }
        }
        return ans;
    }

    public List<Integer> partitionLabels(String s) {
        List<Integer> ans = new ArrayList<>();
        int start = 0, end;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            map.put(s.charAt(i), i);
        while (start < s.length()) {
            char current = s.charAt(start);
            end = map.get(current);
            for (int i = start + 1; i < end; i++) {
                char c = s.charAt(i);
                int newEnd = map.get(c);
                end = Math.max(end, newEnd);
            }
            ans.add(end - start + 1);
            start = end + 1;
        }
        return ans;
    }

    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] log : logs) {
            int id = log[0], time = log[1];
            Set<Integer> set = map.getOrDefault(id, new HashSet<>());
            set.add(time);
            map.put(id, set);
        }
        int[] ans = new int[k];
        for (Set<Integer> set : map.values()) {
            int size = set.size();
            if (size > k) continue;
            ans[size - 1]++;
        }
        return ans;
    }

    class FoodRatings {

        private class Food implements Comparable<Food> {

            public int foodRating;
            public String foodName;

            public Food(int foodRating, String foodName) {
                this.foodRating = foodRating;
                this.foodName = foodName;
            }

            @Override
            public int compareTo(Food other) {
                if (foodRating == other.foodRating)
                    return foodName.compareTo(other.foodName);
                return -1 * Integer.compare(foodRating, other.foodRating);
            }
        }

        private Map<String, Integer> foodRatingMap;
        private Map<String, String> foodCuisineMap;
        private Map<String, PriorityQueue<Food>> cuisineFoodMap;

        public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
            foodRatingMap = new HashMap<>();
            foodCuisineMap = new HashMap<>();
            cuisineFoodMap = new HashMap<>();
            for (int i = 0; i < foods.length; ++i) {
                foodRatingMap.put(foods[i], ratings[i]);
                foodCuisineMap.put(foods[i], cuisines[i]);
                cuisineFoodMap.computeIfAbsent(cuisines[i], k -> new PriorityQueue<>()).add(new Food(ratings[i], foods[i]));
            }
        }

        public void changeRating(String food, int newRating) {
            foodRatingMap.put(food, newRating);
            String cuisineName = foodCuisineMap.get(food);
            cuisineFoodMap.get(cuisineName).add(new Food(newRating, food));
        }

        public String highestRated(String cuisine) {
            Food highestRated = cuisineFoodMap.get(cuisine).peek();
            while (true) {
                assert highestRated != null;
                if (foodRatingMap.get(highestRated.foodName) == highestRated.foodRating) break;
                cuisineFoodMap.get(cuisine).poll();
                highestRated = cuisineFoodMap.get(cuisine).peek();
            }
            return highestRated.foodName;
        }
    }

    public int[] findMissingAndRepeatedValues(int[][] grid) {
        boolean[] found = new boolean[grid.length * grid.length];
        int a = -1, b = -1;
        for (int[] row : grid) {
            for (int current : row) {
                if (found[current - 1]) a = current;
                found[current - 1] = true;
            }
        }
        for (int i = 0; i < found.length; i++) {
            if (!found[i]) {
                b = i + 1;
                break;
            }
        }
        return new int[]{a, b};
    }

    public int[][] imageSmoother(int[][] img) {
        int[][] ans = new int[img.length][img[0].length];
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                ans[i][j] = imageSmootherHelper(img, i, j);
            }
        }
        return ans;
    }

    private int imageSmootherHelper(int[][] img, int i, int j) {
        int ans = 0;
        int hit = 9;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                try {
                    ans += img[x][y];
                } catch (IndexOutOfBoundsException e) {
                    hit--;
                }
            }
        }
        return ans / hit;
    }

    public int countDistinctIntegers(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
            int reverse = Integer.parseInt(new StringBuilder(String.valueOf(num)).reverse().toString());
            set.add(reverse);
        }
        return set.size();
    }

    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> ans = new LinkedList<>();
        for (String word : words) {
            char[] map1 = new char[26];
            char[] map2 = new char[26];
            boolean ok = true;
            for (int i = 0; i < word.length(); i++) {
                char c1 = word.charAt(i);
                char c2 = pattern.charAt(i);
                if ((int) map1[c1 - 'a'] == 0 && (int) map2[c2 - 'a'] == 0) {
                    map1[c1 - 'a'] = c2;
                    map2[c2 - 'a'] = c1;
                } else if (map1[c1 - 'a'] != c2 || map2[c2 - 'a'] != c1) {
                    ok = false;
                    break;
                }
            }
            if (ok) ans.add(word);
        }
        return ans;
    }

    public int maxWidthOfVerticalArea(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(point -> point[0]));
        int max = 0;
        for (int i = 1; i < points.length; i++) {
            max = Math.max(max, points[i][0] - points[i - 1][0]);
        }
        return max;
    }

    public int countVowelStrings(int n) {
        return (int) countVowelStringsHelper(n, 1);
    }

    private long countVowelStringsHelper(int n, int vowelIndex) {
        if (n == 0) return 1;
        long count = 0;
        for (int i = vowelIndex; i <= 5; i++) {
            count += countVowelStringsHelper(n - 1, i);
        }
        return count;
    }

    public int[] numberGame(int[] nums) {
        int[] ans = new int[nums.length];
        Arrays.sort(nums);
        int index = 0;
        for (int i = 0; i < nums.length - 1; i += 2) {
            ans[index++] = nums[i + 1];
            ans[index++] = nums[i];
        }
        return ans;
    }

    public int findKOr(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i <= 31; i++) {
            int count = 0;
            for (int num : nums) {
                if (((num >> i) & 1) != 0) count++;
            }
            if (count >= k) ans += (1 << i);
        }
        return ans;
    }

    public int myAtoi(String s) {
        s = s.trim();
        if (s.isEmpty())
            return 0;
        int ans = 0, i = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
            i++;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return s.charAt(0) == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + digit;
            i++;
        }
        return s.charAt(0) == '-' ? -ans : ans;
    }

    public boolean hasTrailingZeros(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (num % 2 == 0) count++;
            if (count >= 2) return true;
        }
        return false;
    }

    public int minOperations(int n) {
        return n % 2 == 0 ? (n / 2) * (n / 2) : ((n - 1) / 2) * ((n + 1) / 2);
    }

    public int minOperations(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for (int val : map.values()) {
            if (val == 1) return -1;
            ans += val / 3;
            if (val % 3 != 0) ans++;
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums) {

        class Pair<V, L> {
            final V val;
            final L length;

            public Pair(V val, L length) {
                this.val = val;
                this.length = length;
            }
        }

        List<Pair<Integer, Integer>> lengths = new LinkedList<>();
        int max = 0;
        for (int num : nums) {
            int currentMax = 1;
            for (Pair<Integer, Integer> current : lengths) {
                if (current.val < num) {
                    currentMax = Math.max(currentMax, current.length + 1);
                }
            }
            max = Math.max(max, currentMax);
            lengths.add(new Pair<>(num, currentMax));
        }
        return max;
    }

    public List<String> subdomainVisits(String[] domains) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : domains) {
            String[] tmp = s.split(" ");
            int amount = Integer.parseInt(tmp[0]);
            String domain = tmp[1];
            while (true) {
                map.put(domain, map.getOrDefault(domain, 0) + amount);
                int index = domain.indexOf(".");
                if (index == -1) break;
                domain = domain.substring(index + 1);
            }
        }
        List<String> ans = new LinkedList<>();
        for (String key : map.keySet())
            ans.add(String.format("%d %s", map.get(key), key));
        return ans;
    }

    public String removeOccurrences(String s, String part) {
        String ans = s;
        while (true) {
            int index = ans.indexOf(part);
            if (index == -1) break;
            int endIndex = index + part.length();
            ans = ans.substring(0, index) + ans.substring(endIndex);
        }
        return ans;
    }

    class CombinationIterator {

        ArrayList<String> combinations;
        Iterator<String> iterator;

        public CombinationIterator(String characters, int combinationLength) {
            combinations = new ArrayList<>();
            generateCombinations(characters, combinationLength, 0, new StringBuilder());
            iterator = combinations.iterator();
        }

        public String next() {
            return iterator.next();
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        private void generateCombinations(String characters, int length, int start, StringBuilder current) {
            if (length == 0) {
                combinations.add(current.toString());
                return;
            }
            for (int i = start; i < characters.length(); i++) {
                current.append(characters.charAt(i));
                generateCombinations(characters, length - 1, i + 1, current);
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) return null;
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);

        if (root.left == null && root.right == null && root.val == target)
            return null;
        return root;
    }

    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<Integer, List<Integer>> bucket = new HashMap<>();
        for (int i = 0; i < groupSizes.length; i++) {
            int size = groupSizes[i];
            List<Integer> current = bucket.getOrDefault(size, new LinkedList<>());
            current.add(i);
            bucket.put(size, current);
        }
        List<List<Integer>> ans = new LinkedList<>();
        for (int size : bucket.keySet()) {
            List<Integer> current = bucket.get(size);
            for (int i = 0; i < current.size(); i += size)
                ans.add(current.subList(i, Math.min(i + size, current.size())));
        }
        return ans;
    }

    public int areaOfMaxDiagonal(int[][] dimensions) {
        int area = 0;
        double maxDiagonal = 0;
        for (int[] dimension : dimensions) {
            int length = dimension[0];
            int width = dimension[1];
            double currentDiagonal = Math.sqrt(length * length + width * width);
            if (currentDiagonal > maxDiagonal ||
                    (currentDiagonal == maxDiagonal && length * width > area)) {
                maxDiagonal = currentDiagonal;
                area = length * width;
            }
        }
        return area;
    }

    public int garbageCollection(String[] garbage, int[] travel) {
        int ans = 0;
        int maxP = 0, maxM = 0, maxG = 0;
        for (int i = 0; i < garbage.length; i++) {
            if (garbage[i].contains("G")) maxG = i;
            if (garbage[i].contains("P")) maxP = i;
            if (garbage[i].contains("M")) maxM = i;
        }
        for (int i = 0; i < garbage.length; i++) {
            boolean found = false;
            if (i <= maxG) {
                found = true;
                ans += garbageCollectionHelper(i, "G", garbage[i], travel);
            }
            if (i <= maxP) {
                found = true;
                ans += garbageCollectionHelper(i, "P", garbage[i], travel);
            }
            if (i <= maxM) {
                found = true;
                ans += garbageCollectionHelper(i, "M", garbage[i], travel);
            }
            if (!found) break;
        }
        return ans;
    }

    private int garbageCollectionHelper(int i, String s, String garbarge, int[] travel) {
        int count = garbarge.length() - garbarge.replace(s, "").length();
        if (i == 0) return count;
        return count == 0 ? travel[i - 1] : travel[i - 1] + count;
    }

    public int triangularSum(int[] nums) {
        int n = nums.length;
        while (n > 1) {
            int[] newNums = new int[n - 1];
            for (int i = 0; i < n - 1; i++)
                newNums[i] = (nums[i] + nums[i + 1]) % 10;
            nums = newNums;
            n--;
        }
        return nums[0];
    }

    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        int[] chars1 = new int[26];
        int[] chars2 = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            chars1[word1.charAt(i) - 'a']++;
            chars2[word2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++)
            if (chars1[i] == 0 && chars2[i] != 0 || chars1[i] != 0 && chars2[i] == 0)
                return false;
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }

    public List<List<Integer>> findWinners(int[][] matches) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] match : matches) {
            int w = match[0], l = match[1];
            map.put(w, map.getOrDefault(w, 0));
            map.put(l, map.getOrDefault(l, 0) + 1);
        }
        List<Integer> noLoose = new LinkedList<>();
        List<Integer> oneLoose = new LinkedList<>();
        for (int current : map.keySet()) {
            int val = map.get(current);
            if (val == 0) noLoose.add(current);
            else if (val == 1) oneLoose.add(current);
        }
        Collections.sort(noLoose);
        Collections.sort(oneLoose);
        return new LinkedList<>(List.of(noLoose, oneLoose));
    }

    public String longestNiceSubstring(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length(); j > i; j--) {
                String current = s.substring(i, j);
                boolean[] lower = new boolean[26];
                boolean[] upper = new boolean[26];
                for (char c : current.toCharArray()) {
                    if (Character.isLowerCase(c)) lower[c - 'a'] = true;
                    else upper[c - 'A'] = true;
                }
                boolean ok = true;
                for (int k = 0; k < 26; k++) {
                    if (lower[k] && !upper[k] || !lower[k] && upper[k]) {
                        ok = false;
                        break;
                    }
                }
                if (ok && current.length() > ans.length()) ans = current;
            }
        }
        return ans;
    }

    public int maxFrequencyElements(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int num : nums) {
            int val = map.getOrDefault(num, 0) + 1;
            max = Math.max(val, max);
            map.put(num, val);
        }
        int ans = 0;
        for (int key : map.keySet())
            if (map.get(key) == max) ans += max;
        return ans;
    }

    public int largestInteger(int num) {
        char[] s = String.valueOf(num).toCharArray();
        List<Integer> even = new LinkedList<>();
        List<Integer> odd = new LinkedList<>();
        for (Character c : s) {
            int digit = Character.getNumericValue(c);
            if (digit % 2 == 0) {
                even.add(digit);
            } else {
                odd.add(digit);
            }
        }
        even.sort(Collections.reverseOrder());
        odd.sort(Collections.reverseOrder());
        StringBuilder ans = new StringBuilder();
        int one = 0, two = 0;
        for (char c : s) {
            int n = Character.getNumericValue(c);
            if (n % 2 == 0) {
                ans.append(even.get(one));
                one++;
            } else {
                ans.append(odd.get(two));
                two++;
            }
        }
        return Integer.parseInt(ans.toString());
    }

    public List<Integer> mostVisited(int n, int[] rounds) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int start = rounds[0];
        for (int i = 1; i < rounds.length; i++) {
            int end = rounds[i];
            int sector = start;
            boolean ok = true;
            while (ok) {
                if (sector == end) ok = false;
                int val = map.getOrDefault(sector, 0) + 1;
                max = Math.max(max, val);
                map.put(sector, val);
                sector = sector % n + 1;
            }
            start = rounds[i] % n + 1;
        }
        List<Integer> ans = new LinkedList<>();
        for (int key : map.keySet())
            if (map.get(key) == max) ans.add(key);
        Collections.sort(ans);
        return ans;
    }

    public int minimumSum(int[] nums) {
        int min = -1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] < nums[j] && nums[k] < nums[j]) {
                        int current = nums[i] + nums[j] + nums[k];
                        if (min > current || min == -1) min = current;
                    }
                }
            }
        }
        return min;
    }

    public int minimumPushes(String word) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : word.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);
        List<Integer> list = new ArrayList<>(map.values());
        list.sort(Comparator.reverseOrder());
        int count = 0;
        int ans = 0;
        for (int current : list) {
            if (count < 8) ans += current;
            else if (count < 16) ans += 2 * current;
            else if (count < 24) ans += 3 * current;
            else ans += 4 * current;
            count++;
        }
        return ans;
    }

    public boolean findRotation(int[][] mat, int[][] target) {
        for (int i = 0; i < 4; i++) {
            if (mat.length != target.length || mat[0].length != target[0].length) continue;
            boolean ok = true;
            for (int j = 0; j < mat.length; j++) {
                if (!Arrays.equals(mat[j], target[j])) {
                    ok = false;
                    break;
                }
            }
            if (ok) return true;
            findRotationHelper(mat);
        }
        return false;
    }

    private void findRotationHelper(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }

    public int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int sum = (n * (n + 1)) / 2;
        int first = -1;
        boolean[] hit = new boolean[n];
        for (int i : nums) {
            if (hit[i - 1]) first = i;
            else {
                hit[i - 1] = true;
                sum -= i;
            }
        }
        return new int[]{first, sum};
    }

    public int minimumDifference(int[] nums, int k) {
        if (k == 1) return 0;
        int min = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length - k; i++) {
            int low = nums[i];
            int high = nums[i + k - 1];
            min = Math.min(min, high - low);
        }
        return min;
    }

    public boolean isCovered(int[][] ranges, int left, int right) {
        List<Integer> points = new LinkedList<>();
        for (int i = left; i <= right; i++) points.add(i);
        for (int[] current : ranges) {
            if (points.isEmpty()) return true;
            for (Integer i = current[0]; i <= current[1]; i++) {
                points.remove(i);
            }
        }
        return points.isEmpty();
    }

    public int minOperations(String s) {
        boolean[] startZero = new boolean[s.length()];
        boolean[] startOne = new boolean[s.length()];
        boolean zero = true;
        for (int i = 0; i < s.length(); i++) {
            if (zero) {
                startZero[i] = false;
                startOne[i] = true;
            } else {
                startZero[i] = true;
                startOne[i] = false;
            }
            zero = !zero;
        }
        int minZero = 0;
        int minOne = 0;
        for (int i = 0; i < s.length(); i++) {
            boolean one = s.charAt(i) == '1';
            if (one != startZero[i]) minZero++;
            if (one != startOne[i]) minOne++;
        }
        return Math.min(minZero, minOne);
    }

    public int maxRepeating(String sequence, String word) {
        int k = 0;
        StringBuilder sb = new StringBuilder(word);
        while (sequence.contains(sb)) {
            k++;
            sb.append(word);
        }
        return k;
    }

    public int pseudoPalindromicPaths(TreeNode root) {
        return pseudoPalindromPathsHelper(root, new int[10]);
    }

    private int pseudoPalindromPathsHelper(TreeNode node, int[] count) {
        if (node == null) return 0;
        count[node.val]++;
        if (node.left == null && node.right == null) {
            int oddCount = 0;
            for (int val : count) {
                if (val % 2 != 0) {
                    oddCount++;
                    if (oddCount > 1) break;
                }
            }
            count[node.val]--;
            return oddCount <= 1 ? 1 : 0;
        }
        int leftCount = pseudoPalindromPathsHelper(node.left, count);
        int rightCount = pseudoPalindromPathsHelper(node.right, count);
        count[node.val]--;
        return leftCount + rightCount;
    }

    public List<String> removeAnagrams(String[] words) {
        List<String> ans = new ArrayList<>();
        int n = words.length;
        for (int i = 0; i < n; ) {
            int j = i + 1;
            while (j < n && removeAnagramsHelper(words[i], words[j])) j++;
            ans.add(words[i]);
            i = j;
        }
        return ans;
    }

    private boolean removeAnagramsHelper(String p, String q) {
        int[] count = new int[26];
        for (int i = 0; i < p.length(); i++) count[p.charAt(i) - 'a']++;
        for (int i = 0; i < q.length(); i++) count[q.charAt(i) - 'a']--;
        for (int curr : count) if (curr != 0) return false;
        return true;
    }

    public int isWinner(int[] player1, int[] player2) {
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < player1.length; i++) {
            sum1 += isWinnerHelper(player1, i);
            sum2 += isWinnerHelper(player2, i);
        }
        return sum1 == sum2 ? 0 : (sum1 > sum2 ? 1 : 2);
    }

    private int isWinnerHelper(int[] player, int i) {
        boolean doub = i - 2 >= 0 && player[i - 2] == 10;
        if (i - 1 >= 0 && player[i - 1] == 10) doub = true;
        return doub ? 2 * player[i] : player[i];
    }

    public int countKeyChanges(String s) {
        int ans = 0;
        char c = Character.toLowerCase(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            char current = Character.toLowerCase(s.charAt(i));
            if (c != current) {
                c = current;
                ans++;
            }
        }
        return ans;
    }

    class MyQueue {

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> tmp = new Stack<>();

        public void push(int x) {
            stack.push(x);
        }

        public int pop() {
            while (!stack.isEmpty()) {
                int i = stack.pop();
                tmp.push(i);
            }
            int ergebnis = tmp.pop();
            while (!tmp.isEmpty()) {
                int i = tmp.pop();
                stack.push(i);
            }
            return ergebnis;
        }

        public int peek() {
            while (!stack.isEmpty()) {
                int i = stack.pop();
                tmp.push(i);
            }
            int ergebnis = tmp.peek();
            while (!tmp.isEmpty()) {
                int i = tmp.pop();
                stack.push(i);
            }
            return ergebnis;
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }

    public int minimumCost1(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                min = Math.min(nums[i] + nums[j], min);
            }
        }
        return min + nums[0];
    }

    public int distinctIntegers(int n) {
        return n == 1 ? 1 : n - 1;
    }

    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
        int sum1 = 0;
        int sum2 = 0;
        for (int curr : aliceSizes)
            sum1 += curr;
        HashSet<Integer> set = new HashSet<>();
        for (int curr : bobSizes) {
            sum2 += curr;
            set.add(curr);
        }
        int half = (sum1 - sum2) / 2;
        for (int a : aliceSizes) {
            int b = a - half;
            if (set.contains(b)) {
                return new int[]{a, b};
            }
        }
        return null;
    }

    public int minimumCost(int[] cost) {
        Arrays.sort(cost);
        int min = 0;
        int i = cost.length - 1;
        while (i >= 0) {
            if (i - 2 >= 0) {
                min += cost[i] + cost[i - 1];
                i -= 3;
            } else if (i - 1 >= 0) {
                min += cost[i] + cost[i - 1];
                break;
            } else {
                min += cost[i];
                break;
            }
        }
        return min;
    }

    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 9; ++i) {
            int num = i;
            int nextDigit = i + 1;
            while (num <= high && nextDigit <= 9) {
                num = num * 10 + nextDigit;
                if (low <= num && num <= high)
                    list.add(num);
                nextDigit++;
            }
        }
        list.sort(null);
        return list;
    }

    public int fillCups(int[] amount) {
        int ans = 0;
        while (amount[0] > 0 || amount[1] > 0 || amount[2] > 0) {
            int a = amount[0], b = amount[1], c = amount[2];
            if (a == 0 && c == 0 || b == 0 && c == 0 || a == 0 && b == 0) {
                ans += a + b + c;
                break;
            }
            int max = Math.max(a, Math.max(b, c));
            int mid;
            if ((a >= b && a <= c) || (a <= b && a >= c)) mid = a;
            else if ((b >= a && b <= c) || (b <= a && b >= c)) mid = b;
            else mid = c;
            int i, j;
            if (max != mid) {
                i = a == max ? 0 : (b == max ? 1 : 2);
                j = a == mid ? 0 : (b == mid ? 1 : 2);
            } else {
                if (a == b) {
                    i = 0;
                    j = 1;
                } else if (a == c) {
                    i = 0;
                    j = 2;
                } else {
                    i = 1;
                    j = 2;
                }
            }
            ans++;
            amount[i]--;
            amount[j]--;
        }
        return ans;
    }

    public String triangleType(int[] nums) {
        if (nums[0] + nums[1] <= nums[2] || nums[0] + nums[2] <= nums[1] || nums[1] + nums[2] <= nums[0])
            return "none";
        if (nums[0] == nums[1] && nums[0] == nums[2]) return "equilateral";
        if (nums[0] == nums[1] || nums[0] == nums[2] || nums[1] == nums[2]) return "isosceles";
        return "scalene";
    }

    public int returnToBoundaryCount(int[] nums) {
        int ans = 0;
        int place = 0;
        for (int num : nums) {
            place += num;
            if (place == 0)
                ans++;
        }
        return ans;
    }

    public class Codec {

        String[] arr;
        int index;

        public String serialize(TreeNode root) {
            if (root == null) return "#";
            return root.val + " " + serialize(root.left) + " " + serialize(root.right);
        }

        public TreeNode deserialize(String data) {
            arr = data.split(" ");
            index = 0;
            return deserializeHelper();
        }

        private TreeNode deserializeHelper() {
            int index = this.index++;
            if (arr[index].equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(arr[index]));
            root.left = deserializeHelper();
            root.right = deserializeHelper();
            return root;
        }
    }

    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        Map<Character, Integer> sorted = frequencySortHelper(map);
        StringBuilder ans = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : sorted.entrySet())
            ans.append(String.valueOf(entry.getKey()).repeat(entry.getValue()));
        return ans.toString();
    }

    public Map<Character, Integer> frequencySortHelper(Map<Character, Integer> map) {
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry : list)
            sortedMap.put(entry.getKey(), entry.getValue());
        return sortedMap;
    }

    public int[][] sortTheStudents(int[][] score, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < score.length; i++) {
            map.put(score[i][k], i);
            scores.add(score[i][k]);
        }
        scores.sort(Comparator.reverseOrder());
        int[][] ans = new int[score.length][];
        for (int i = 0; i < scores.size(); i++)
            ans[i] = score[map.get(scores.get(i))];
        return ans;
    }

    public int[] executeInstructions(int n, int[] startPos, String s) {
        int[] ans = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
            ans[i] = executeInstructionsHelper(s.substring(i), n, startPos);
        return ans;
    }

    private int executeInstructionsHelper(String str, int n, int[] arr) {
        int move = 0;
        int row = arr[0];
        int col = arr[1];
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'R') {
                if (col < n - 1) {
                    move++;
                    col++;
                } else return move;
            } else if (str.charAt(i) == 'D') {
                if (row < n - 1) {
                    move++;
                    row++;
                } else return move;
            } else if (str.charAt(i) == 'L') {
                if (col > 0) {
                    move++;
                    col--;
                } else return move;
            } else if (str.charAt(i) == 'U') {
                if (row > 0) {
                    move++;
                    row--;
                } else return move;
            }
        }
        return move;
    }

    public int countSubstrings(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int left = i, right = j;
                boolean ok = true;
                while (left < right) {
                    if (s.charAt(left++) != s.charAt(right--)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) ans++;
            }
        }
        return ans;
    }

    public int minimumRecolors(String blocks, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < blocks.length() - k + 1; i++) {
            int current = 0;
            for (int j = i; j < i + k; j++) {
                if (blocks.charAt(j) == 'W') current++;
            }
            ans = Math.min(ans, current);
        }
        return ans;
    }

    public int[][] modifiedMatrix(int[][] matrix) {
        int[][] ans = new int[matrix.length][matrix[0].length];
        for (int col = 0; col < matrix[0].length; col++) {
            // Remember all indices with -1
            List<Integer> indices = new LinkedList<>();
            int max = -1;
            for (int row = 0; row < matrix.length; row++) {
                int current = matrix[row][col];
                max = Math.max(current, max);
                if (current == -1) indices.add(row);
                else ans[row][col] = current;
            }
            // Replace all -1's with max
            for (int row : indices)
                ans[row][col] = max;
        }
        return ans;
    }

    public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int ans = 0;
        int i = 0, j = piles.length - 1;
        while (i < j) {
            i++;
            ans += piles[j - 1];
            j -= 2;
        }
        return ans;
    }

    public int[][] matrixBlockSum(int[][] mat, int k) {
        int[][] ans = new int[mat.length][mat[0].length];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[i].length; j++) {
                ans[i][j] = matrixBlockSumHelper(mat, i, j, k);
            }
        }
        return ans;
    }

    public int matrixBlockSumHelper(int[][] mat, int i, int j, int k) {
        int ans = 0;
        int r = Math.max(i - k, 0);
        while (r <= i + k && r < mat.length) {
            int c = Math.max(j - k, 0);
            while (c <= j + k && c < mat[i].length) {
                ans += mat[r][c++];
            }
            r++;
        }
        return ans;
    }

    int bstToGstSum = 0;

    public TreeNode bstToGst(TreeNode root) {
        if (root == null) return null;
        bstToGst(root.right);
        bstToGstSum += root.val;
        root.val = bstToGstSum;
        bstToGst(root.left);
        return root;
    }

    public long largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        long sum = 0;
        long[] sums = new long[n];
        for (int i = 0; i < n; i++) {
            sums[i] = sum;
            sum += nums[i];
        }
        for (int i = n - 1; i >= 2; i--) {
            if (nums[i] < sums[i]) return sums[i] + nums[i];
        }
        return -1;
    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) map.put(i, map.getOrDefault(i, 0) + 1);
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : entries)
            sortedMap.put(entry.getKey(), entry.getValue());
        int ans = entries.size();
        int i = 0;
        entries = new ArrayList<>(sortedMap.entrySet());
        while (k >= 0 && i < entries.size()) {
            int val = entries.get(i++).getValue();
            if (val > k) break;
            ans--;
            k -= val;
        }
        return ans;
    }

    public int wateringPlants(int[] plants, int capacity) {
        int ans = 0;
        int water = capacity;
        for (int i = 0; i < plants.length; i++) {
            int plant = plants[i];
            if (plant <= water) {
                water -= plant;
                ans++;
                continue;
            }
            water = capacity - plant;
            ans += (2 * i + 1);
        }
        return ans;
    }

    public int countBattleships(char[][] board) {
        int ans = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') continue;
                if ((i == 0 || board[i - 1][j] == '.') && (j == 0 || board[i][j - 1] == '.')) ans++;
            }
        }
        return ans;
    }

    public int maxOperations(int[] nums) {
        int ans = 0;
        int sum = -1;
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (sum == -1) {
                ans++;
                sum = nums[i] + nums[i + 1];
            } else if (nums[i] + nums[i + 1] != sum) {
                return ans;
            } else {
                ans++;
            }
        }
        return ans;
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return root;
        }
        TreeNode current = root;
        while (true) {
            if (current.val < val) {
                if (current.right == null) {
                    current.right = new TreeNode(val);
                    return root;
                }
                current = current.right;
            } else {
                if (current.left == null) {
                    current.left = new TreeNode(val);
                    return root;
                }
                current = current.left;
            }
        }
    }

    public int rangeBitwiseAnd(int left, int right) {
        if (left == right) return left;
        if (left == 1073741824 && right == 2147483647) return left;
        long ans = left;
        long i = left + 1;
        while (i <= right) {
            if (ans == 0) return 0;
            ans &= i++;
        }
        return (int) ans;
    }

    public int minNumberOfHours(int iEn, int iEx, int[] energy, int[] experience) {
        int ans = 0;
        int diff;
        for (int i = 0; i < energy.length; i++) {
            if (energy[i] >= iEn) {
                diff = energy[i] - iEn + 1;
                ans = ans + diff;
                iEn = iEn + diff;
            }
            iEn = iEn - energy[i];
            if (experience[i] >= iEx) {
                diff = experience[i] - iEx + 1;
                ans = ans + diff;
                iEx = iEx + diff;
            }
            iEx = iEx + experience[i];
        }
        return ans;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[][] dp = new int[k + 2][n];
        for (int i = 0; i <= k + 1; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        dp[0][src] = 0;
        for (int i = 1; i <= k + 1; i++) {
            dp[i][src] = 0;
            for (int[] flight : flights) {
                int from = flight[0], to = flight[1], cost = flight[2];
                if (dp[i - 1][from] != Integer.MAX_VALUE) {
                    dp[i][to] = Math.min(dp[i][to], dp[i - 1][from] + cost);
                }
            }
        }
        return dp[k + 1][dst] == Integer.MAX_VALUE ? -1 : dp[k + 1][dst];
    }

    public int maximumDifference(int[] nums) {
        int max = -1;
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int diff = nums[i] - min;
            if (diff > 0 && diff > max)
                max = diff;
            if (nums[i] < min)
                min = nums[i];
        }
        return max;
    }

    public boolean isPossibleToSplit(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int val = map.getOrDefault(num, 0) + 1;
            if (val > 2) return false;
            map.put(num, val);
        }
        return true;
    }

    public int findBottomLeftValue(TreeNode root) {
        LinkedList<TreeNode> visited = new LinkedList<>();
        visited.add(root);
        int left = root.val;
        while (!visited.isEmpty()) {
            List<TreeNode> curr = new LinkedList<>();
            while (!visited.isEmpty()) {
                TreeNode node = visited.poll();
                if (node.left != null) curr.add(node.left);
                if (node.right != null) curr.add(node.right);
            }
            if (!curr.isEmpty()) {
                left = curr.get(0).val;
                visited.addAll(curr);
            }
        }
        return left;
    }

    public boolean isEvenOddTree(TreeNode root) {
        LinkedList<TreeNode> visited = new LinkedList<>();
        visited.add(root);
        int level = 0;
        while (!visited.isEmpty()) {
            LinkedList<TreeNode> curr = new LinkedList<>();
            TreeNode prev = null;
            while (!visited.isEmpty()) {
                TreeNode node = visited.poll();
                if (level % 2 == 0) {
                    if (node.val % 2 == 0 || (prev != null && node.val <= prev.val))
                        return false;
                } else {
                    if (node.val % 2 != 0 || (prev != null && node.val >= prev.val))
                        return false;
                }
                prev = node;
                if (node.left != null) curr.add(node.left);
                if (node.right != null) curr.add(node.right);
            }
            visited.addAll(curr);
            level++;
        }
        return true;
    }

    public List<List<String>> displayTable(final List<List<String>> orders) {
        TreeMap<Integer, Map<String, Integer>> tables = new TreeMap<>();
        TreeSet<String> meals = new TreeSet<>();
        for (List<String> order : orders) {
            int tableNum = Integer.parseInt(order.get(1));
            tables.putIfAbsent(tableNum, new HashMap<>());
            Map<String, Integer> table = tables.get(tableNum);
            table.put(order.get(2), table.getOrDefault(order.get(2), 0) + 1);
            meals.add(order.get(2));
        }
        List<List<String>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        ans.get(0).add("Table");
        ans.get(0).addAll(meals);
        for (int tableNum : tables.keySet()) {
            List<String> tableOrders = new ArrayList<>();
            tableOrders.add(String.valueOf(tableNum));
            Map<String, Integer> table = tables.get(tableNum);
            for (int i = 1; i < ans.get(0).size(); ++i) {
                String meal = ans.get(0).get(i);
                tableOrders.add(table.containsKey(meal) ? String.valueOf(table.get(meal)) : "0");
            }
            ans.add(tableOrders);
        }
        return ans;
    }

    public int minCostToMoveChips(int[] position) {
        int even = 0;
        int odd = 0;
        for (int chips : position) {
            if (chips % 2 == 0) even++;
            else odd++;
        }
        return Math.min(even, odd);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tmp = new ListNode(0);
        tmp.next = head;
        ListNode node1 = tmp;
        ListNode node2 = tmp;
        for (int i = 0; i <= n; i++) {
            if (node2 == null) return null;
            node2 = node2.next;
        }
        while (node2 != null) {
            node1 = node1.next;
            node2 = node2.next;
        }
        node1.next = node1.next.next;
        return tmp.next;
    }

    public int minOperations(int[] nums, int k) {
        return (int) Arrays.stream(nums).filter(x -> x < k).count();
    }

    public int[] resultArray(int[] nums) {
        List<Integer> list1 = new LinkedList<>(), list2 = new LinkedList<>();
        list1.add(nums[0]);
        list2.add(nums[1]);
        int ind1 = 1, ind2 = 1;
        for (int i = 2; i < nums.length; i++) {
            if (list1.get(ind1 - 1) > list2.get(ind2 - 1)) {
                list1.add(nums[i]);
                ind1++;
            } else {
                list2.add(nums[i]);
                ind2++;
            }
        }
        int[] ans = new int[nums.length];
        int ind = 0;
        for (int num : list1) ans[ind++] = num;
        for (int num : list2) ans[ind++] = num;
        return ans;
    }

    public int minimumLength(String s) {
        int i = 0, j = s.length() - 1;
        while (i < s.length() && j >= 0 && s.charAt(i) == s.charAt(j) && i < j) {
            char c = s.charAt(i);
            while (i < s.length() && s.charAt(i) == c) i++;
            while (j >= 0 && s.charAt(j) == c) j--;
        }
        return Math.max(0, j - i + 1);
    }

    public int findTheLongestBalancedSubstring(String s) {
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            int amount0 = 0;
            int amount1 = 0;
            boolean ones = false;
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(j) == '0' && !ones) {
                    amount0++;
                } else if (s.charAt(j) == '1') {
                    ones = true;
                    amount1++;
                } else {
                    int min = Math.min(amount0, amount1);
                    max = Math.max(max, min * 2);
                    amount0 = 0;
                    amount1 = 0;
                    ones = false;
                }
            }
            int min = Math.min(amount0, amount1);
            max = Math.max(max, min * 2);
        }
        return max;
    }

    public int goodNodes(TreeNode root) {
        if (root == null) return 0;
        int ans = 0;
        Map<TreeNode, Integer> visited = new HashMap<>();
        visited.put(root, root.val);
        while (!visited.isEmpty()) {
            Map<TreeNode, Integer> next = new HashMap<>();
            for (Map.Entry<TreeNode, Integer> entry : visited.entrySet()) {
                TreeNode node = entry.getKey();
                int max = entry.getValue();
                if (node.val >= max) ans++;
                if (node.left != null) next.put(node.left, Math.max(max, node.val));
                if (node.right != null) next.put(node.right, Math.max(max, node.val));
            }
            visited.clear();
            visited = next;
        }
        return ans;
    }

    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        boolean[] seen = new boolean[n];
        for (List<Integer> edge : edges) seen[edge.get(1)] = true;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (!seen[i]) ans.add(i);
        return ans;
    }

    public int minimumBoxes(int[] apple, int[] capacity) {
        Arrays.sort(capacity);
        int total = Arrays.stream(apple).sum();
        int i = capacity.length - 1;
        int ans = 0;
        while (total > 0) {
            total -= capacity[i--];
            ans++;
        }
        return ans;
    }

    public String customSortString(String order, String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < order.length(); i++)
            map.put(order.charAt(i), i);
        Comparator<Character> comp = (c1, c2) -> {
            int index1 = map.getOrDefault(c1, Integer.MAX_VALUE);
            int index2 = map.getOrDefault(c2, Integer.MAX_VALUE);
            return Integer.compare(index1, index2);
        };
        Character[] chars = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) chars[i] = s.charAt(i);
        Arrays.sort(chars, comp);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

    public ListNode removeZeroSumSublists(ListNode head) {
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode newNode = new ListNode(0);
        newNode.next = head;
        int sum = 0;
        for (ListNode node = newNode; node != null; node = node.next) {
            sum += node.val;
            map.put(sum, node);
        }
        sum = 0;
        for (ListNode p = newNode; p != null; p = p.next) {
            sum += p.val;
            p.next = map.get(sum).next;
        }
        return newNode.next;
    }

    public int semiOrderedPermutation(int[] nums) {
        int x = 0, y = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) x = i;
            else if (nums[i] == n) y = i;
        }
        return x > y ? x + (n - y - 1) - 1 : x + (n - y - 1);
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            if (sum == goal) ans++;
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (sum == goal) ans++;
            }
        }
        return ans;
    }

    public int minMaxDifference(int num) {
        return minMaxDifferenceHelper(num, true) - minMaxDifferenceHelper(num, false);
    }

    private int minMaxDifferenceHelper(int num, boolean max) {
        String current = String.valueOf(num);
        int i = 0;
        while (i < current.length()) {
            if (max && current.charAt(i) == '9') {
                i++;
            } else if (max) {
                return Integer.parseInt(current.replaceAll(String.valueOf(current.charAt(i)), "9"));
            } else if (current.charAt(i) == '0') {
                i++;
            } else {
                return Integer.parseInt(current.replaceAll(String.valueOf(current.charAt(i)), "0"));
            }
        }
        return num;
    }

    public int findMaxLength(int[] nums) {
        int max = 0;
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            count += nums[i] == 1 ? 1 : -1;
            if (map.containsKey(count))
                max = Math.max(max, i - map.get(count));
            else
                map.put(count, i);
        }
        return max;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        int i = 0;
        int newStart = newInterval[0];
        int newEnd = newInterval[1];
        while (i < intervals.length && intervals[i][1] < newStart)
            ans.add(intervals[i++]);
        while (i < intervals.length && intervals[i][0] <= newEnd) {
            newStart = Math.min(newStart, intervals[i][0]);
            newEnd = Math.max(newEnd, intervals[i][1]);
            i++;
        }
        ans.add(new int[]{newStart, newEnd});
        while (i < intervals.length)
            ans.add(intervals[i++]);
        return ans.toArray(new int[ans.size()][2]);
    }

    public int sumOfEncryptedInt(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            String s = String.valueOf(num);
            int max = 0;
            for (char c : s.toCharArray())
                max = Math.max(max, Character.getNumericValue(c));
            ans += Integer.parseInt(String.valueOf(max).repeat(s.length()));
        }
        return ans;
    }

    public boolean isSubstringPresent(String s) {
        StringBuilder reversed = new StringBuilder(s).reverse();
        for (int i = 0; i + 1 < s.length(); i++) {
            if (reversed.indexOf(s.substring(i, i + 2)) != -1) return true;
        }
        return false;
    }

    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        int a = Math.max(rec1[0], rec1[2]);
        int b = Math.max(rec2[0], rec2[2]);
        int c = Math.min(rec1[0], rec1[2]);
        int d = Math.min(rec2[0], rec2[2]);
        int e = Math.max(rec1[1], rec1[3]);
        int f = Math.max(rec2[1], rec2[3]);
        int g = Math.min(rec1[1], rec1[3]);
        int h = Math.min(rec2[1], rec2[3]);

        if (a <= d || b <= c) {
            return false;
        }
        return e > h && f > g;
    }

    public int surfaceArea(int[][] grid) {
        int ans = 0;
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0)
                    ans += 6 * grid[i][j] - 2 * (grid[i][j] - 1);
                if (i > 0)
                    ans -= 2 * Math.min(grid[i - 1][j], grid[i][j]);
                if (j > 0)
                    ans -= 2 * Math.min(grid[i][j - 1], grid[i][j]);
            }
        }
        return ans;
    }

    public int maximumLengthSubstring(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = 0;
            int[] count = new int[26];
            for (int j = i; j < s.length(); j++) {
                int index = s.charAt(j) - 'a';
                count[index]++;
                if (count[index] > 2) break;
                current++;
            }
            ans = Math.max(ans, current);
        }
        return ans;
    }

    public long[] mostFrequentIDs(int[] arr, int[] freq) {
        int n = arr.length;
        PriorityQueue<long[]> queue = new PriorityQueue<>((a, b) -> (int) (b[1] - a[1]));
        HashMap<Long, Long> heap = new HashMap<>();
        long[] ans = new long[n];
        for (int i = 0; i < n; i++) {
            long x = arr[i];
            long c = freq[i];
            heap.put(x, heap.getOrDefault(x, 0L) + c);
            queue.add(new long[]{x, heap.get(x)});
            long[] a = queue.remove();
            while (a[1] != heap.get(a[0])) {
                a = queue.remove();
            }
            ans[i] = a[1];
            queue.add(a);
        }
        return ans;
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                if (product < k) ans++;
                else break;
            }
        }
        return ans;
    }

    public int missingInteger(int[] nums) {
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] + 1 == nums[i]) sum += nums[i];
            else break;
        }
        Arrays.sort(nums);
        for (int num : nums) {
            if (sum == num) sum++;
        }
        return sum;
    }

    public int countHillValley(int[] nums) {
        int ans = 0;
        int start = 1;
        while (start < nums.length && nums[start] == nums[start - 1]) start++;
        int prev = start - 1;
        for (int i = start; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) continue;
            if (nums[i] > nums[prev] && nums[i] > nums[i + 1])
                ans++;
            if (nums[i] < nums[prev] && nums[i] < nums[i + 1])
                ans++;
            prev = i;
        }
        return ans;
    }

    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int ans = 0;
        int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String arrival = arriveAlice.compareTo(arriveBob) >= 0 ? arriveAlice : arriveBob;
        String leave = leaveAlice.compareTo(leaveBob) <= 0 ? leaveAlice : leaveBob;
        while (arrival.compareTo(leave) <= 0) {
            ans++;
            int day = Integer.parseInt(arrival.substring(3));
            int maxDay = days[Integer.parseInt(arrival.substring(0, 2)) - 1];
            if (day < maxDay) {
                day++;
                String tmp = day < 10 ? "0" + day : String.valueOf(day);
                arrival = arrival.substring(0, 3) + tmp;
                continue;
            }
            int month = Integer.parseInt(arrival.substring(0, 2)) + 1;
            String tmp = month < 10 ? "0" + month : String.valueOf(month);
            arrival = tmp + "-01";
        }
        return ans;
    }

    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int sum = 0;
        for (char c : String.valueOf(x).toCharArray()) sum += Character.getNumericValue(c);
        return x % sum == 0 ? sum : -1;
    }

    public int minimumSubarrayLength(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int current = 0;
            int length = 0;
            for (int j = i; j < nums.length; j++) {
                if (i == j) current = nums[j];
                else current |= nums[j];
                length++;
                if (current >= k) {
                    ans = Math.min(ans, length);
                    break;
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public String longestPalindrome(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length(); j > i; j--) {
                if (s.charAt(i) != s.charAt(j - 1)) continue;
                String current = s.substring(i, j);
                if (longestPalindromeHelper(current) && ans.length() < current.length()) {
                    ans = current;
                    break;
                }
            }
        }
        return ans;
    }

    private boolean longestPalindromeHelper(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }

    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        if (n == k) return nums;
        int[] ans = new int[k];
        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);
        int[] tmp = Arrays.copyOfRange(sorted, n - k, n);
        int min = tmp[0];
        int amountMin = 0;
        for (int value : tmp) if (value == min) amountMin++;
        int i = 0, j = 0;
        while (i < nums.length && j < k) {
            if (nums[i] > min) {
                ans[j++] = nums[i];
            } else if (nums[i] == min) {
                if (amountMin == 0) {
                    i++;
                    continue;
                }
                amountMin--;
                ans[j++] = nums[i];
            }
            i++;
        }
        return ans;
    }

    public int captureForts(int[] forts) {
        int ans = 0;
        for (int i = 0; i < forts.length; i++) {
            if (forts[i] != -1) continue;
            for (int j = i - 1; j >= 0; j--) {
                if (forts[j] == 1) {
                    ans = Math.max(ans, i - j - 1);
                    break;
                } else if (forts[j] == -1) break;
            }
            for (int j = i + 1; j < forts.length; j++) {
                if (forts[j] == 1) {
                    ans = Math.max(ans, j - i - 1);
                    break;
                } else if (forts[j] == -1) break;
            }
        }
        return ans;
    }

    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> startZero = new LinkedList<>();
        List<String> startOne = new LinkedList<>();
        boolean zero = true;
        boolean one = true;
        for (int i = 0; i < words.length; i++) {
            if (groups[i] == 0 && zero || groups[i] == 1 && !zero) {
                startZero.add(words[i]);
                zero = !zero;
            }
            if (groups[i] == 1 && one || groups[i] == 0 && !one) {
                startOne.add(words[i]);
                one = !one;
            }
        }
        return startZero.size() > startOne.size() ? startZero : startOne;
    }

    public int incremovableSubarrayCount(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++)
            for (int j = i; j < nums.length; j++)
                if (incremovableSubarrayCountHelper(nums, i, j)) ans++;
        return ans;
    }

    private boolean incremovableSubarrayCountHelper(int[] nums, int start, int end) {
        for (int i = 0; i < start - 1; i++)
            if (nums[i] >= nums[i + 1]) return false;
        if (start > 0 && end < nums.length - 1 && nums[start - 1] >= nums[end + 1]) return false;
        for (int i = end + 1; i < nums.length - 1; i++)
            if (nums[i] >= nums[i + 1]) return false;
        return true;
    }

    public int maxArea(int[] height) {
        int ans = 0;
        int n = height.length;
        int i = 0, j = n - 1;
        while (i < j) {
            int current = Math.min(height[i], height[j]) * (j - i);
            ans = Math.max(current, ans);
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return ans;
    }

    public String minRemoveToMakeValid(String s) {
        StringBuilder ans = new StringBuilder();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count <= 0 && c == ')') continue;
            if (c == '(') count++;
            else if (c == ')') count--;
            ans.append(c);
        }
        String t = ans.toString();
        if (count == 0) return t;
        ans = new StringBuilder();
        int newCount = 0;
        for (int i = t.length() - 1; i >= 0; i--) {
            char c = t.charAt(i);
            if (count != 0 && newCount <= 0 && c == '(') {
                count--;
                continue;
            }
            if (c == ')') newCount++;
            else if (c == '(') newCount--;
            ans.append(c);
        }
        return ans.reverse().toString();
    }

    public int longestMonotonicSubarray(int[] nums) {
        int maxIn = 1, maxDe = 1;
        int before = nums[0];
        int currentIn = 1, currentDe = 1;
        for (int i = 1; i < nums.length; before = nums[i], i++) {
            if (nums[i] > before) {
                currentIn++;
                maxIn = Math.max(maxIn, currentIn);
            } else {
                currentIn = 1;
            }
            if (nums[i] < before) {
                currentDe++;
                maxDe = Math.max(maxDe, currentDe);
            } else {
                currentDe = 1;
            }
        }
        return Math.max(maxIn, maxDe);
    }

    public String getSmallestString(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int dist = getSmallestStringHelper(chars[i]);
            if (dist <= k) {
                chars[i] = 'a';
                k -= dist;
            } else if (k > 0) {
                chars[i] = (char) (chars[i] - k);
                k = 0;
            }
        }
        return new String(chars);
    }

    private int getSmallestStringHelper(char c) {
        int dist = Math.abs(c - 'a');
        return Math.min(dist, 26 - dist);
    }

    public TreeNode balanceBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        balanceBSTInorder(root, list);
        return balanceBSTHelper(list, 0, list.size() - 1);
    }

    private void balanceBSTInorder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        balanceBSTInorder(node.left, list);
        list.add(node.val);
        balanceBSTInorder(node.right, list);
    }

    private TreeNode balanceBSTHelper(List<Integer> list, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(list.get(mid));
        node.left = balanceBSTHelper(list, start, mid - 1);
        node.right = balanceBSTHelper(list, mid + 1, end);
        return node;
    }

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode ans;
        if (a == 0) ans = list2;
        else ans = list1;
        int i = 0;
        ListNode current1 = list1;
        ListNode current2 = list2;
        while (current2.next != null && a == 0)
            current2 = current2.next;
        while (current1 != null) {
            if (i == a - 1) {
                ListNode tmp = current1.next;
                while (i < b) {
                    tmp = tmp.next;
                    i++;
                }
                current1.next = list2;
                while (current2.next != null) {
                    current2 = current2.next;
                }
                current2.next = tmp;
                break;
            } else {
                current1 = current1.next;
                i++;
            }
        }
        return ans;
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorderHelper(preorder, 0, preorder.length - 1);
    }

    private TreeNode bstFromPreorderHelper(int[] preorder, int start, int end) {
        if (start > end) return null;
        TreeNode root = new TreeNode(preorder[start]);
        int max = start + 1;
        while (max <= end && preorder[max] < preorder[start]) max++;
        root.left = bstFromPreorderHelper(preorder, start + 1, max - 1);
        root.right = bstFromPreorderHelper(preorder, max, end);
        return root;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        for (int num : nums) {
            int n = ans.size();
            for (int i = 0; i < n; i++) {
                List<Integer> subset = new ArrayList<>(ans.get(i));
                subset.add(num);
                ans.add(subset);
            }
        }
        return ans;
    }

    public int scoreOfString(String s) {
        return IntStream.range(0, s.length() - 1).map(i -> Math.abs(s.charAt(i) - s.charAt(i + 1))).sum();
    }

    public String findLatestTime(String s) {
        String ans = "";
        if (s.charAt(0) == '?' && s.charAt(1) == '?') {
            ans += "11";
        } else if (s.charAt(0) == '?') {
            if (s.charAt(1) <= '1') ans += "1" + s.charAt(1);
            else ans += "0" + s.charAt(1);
        } else if (s.charAt(1) == '?') {
            if (s.charAt(0) == '1') ans += "11";
            else ans += "09";
        } else {
            ans += s.substring(0, 2);
        }
        ans += ":";
        if (s.charAt(3) == '?' && s.charAt(4) == '?') {
            ans += "59";
        } else if (s.charAt(3) == '?') {
            ans += "5" + s.charAt(4);
        } else if (s.charAt(4) == '?') {
            ans += s.charAt(3) + "9";
        } else {
            ans += s.substring(3);
        }
        return ans;
    }

    public int islandPerimeter(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int cur = grid[i][j];
                if (cur == 0) continue;
                if (i - 1 < 0 || grid[i - 1][j] == 0) ans++;
                if (i + 1 >= grid.length || grid[i + 1][j] == 0) ans++;
                if (j - 1 < 0 || grid[i][j - 1] == 0) ans++;
                if (j + 1 >= grid[0].length || grid[i][j + 1] == 0) ans++;
            }
        }
        return ans;
    }

    public int maxAncestorDiff(TreeNode root) {
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        int ans = 0;
        while (!nodes.isEmpty()) {
            TreeNode current = nodes.pop();
            LinkedList<TreeNode> anc = new LinkedList<>();
            if (current.left != null) {
                anc.add(current.left);
                nodes.add(current.left);
            }
            if (current.right != null) {
                anc.add(current.right);
                nodes.add(current.right);
            }
            while (!anc.isEmpty()) {
                TreeNode tmp = anc.pop();
                ans = Math.max(ans, Math.abs(tmp.val - current.val));
                if (tmp.left != null) anc.add(tmp.left);
                if (tmp.right != null) anc.add(tmp.right);
            }
        }
        return ans;
    }

    public int numberOfSpecialChars(String word) {
        boolean[] lower = new boolean[26];
        boolean[] upper = new boolean[26];
        for (char c : word.toCharArray()) {
            if (Character.isLowerCase(c)) {
                lower[c - 'a'] = true;
            } else {
                upper[c - 'A'] = true;
            }
        }
        return (int) IntStream.range(0, 26).filter(i -> lower[i] && upper[i]).count();
    }

    public String smallestNumber(String pattern) {
        StringBuilder ans = new StringBuilder("123456789".substring(0, pattern.length() + 1));
        int j = 0;
        for (int i = 0; i <= pattern.length(); i++) {
            if (i < pattern.length() && pattern.charAt(i) == 'D') {
                j++;
                continue;
            }
            if (j > 0) {
                StringBuilder s = new StringBuilder(ans.substring(i - j, i + 1));
                ans.replace(i - j, i + 1, s.reverse().toString());
            }
            j = 0;
        }
        return ans.toString();
    }

    public int addedInteger(int[] nums1, int[] nums2) {
        int min1 = nums1[0], min2 = nums2[0];
        for (int i = 1; i < nums1.length; i++) {
            min1 = Math.min(min1, nums1[i]);
            min2 = Math.min(min2, nums2[i]);
        }
        return min2 - min1;
    }

    public boolean canMakeSquare(char[][] grid) {
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (canMakeSquareHelper(grid, i, j)) return true;
            }
        }
        return false;
    }

    private boolean canMakeSquareHelper(char[][] grid, int i, int j) {
        int count1 = 0, count2 = 0;
        for (int row = i; row < i + 2; row++) {
            for (int col = j; col < j + 2; col++) {
                if (grid[row][col] == 'B') {
                    count1++;
                } else {
                    count2++;
                }
            }
        }
        return count1 <= 1 || count2 <= 1;
    }

    public double minimumAverage(int[] nums) {
        double ans = Double.MAX_VALUE;
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;
        while (i < j) {
            double a = nums[i];
            double b = nums[j];
            ans = Math.min((a + b) / 2, ans);
            i++;
            j--;
        }
        return ans;
    }

    public int minimumOperations(int[] nums) {
        return Arrays.stream(nums).filter(x -> x % 3 != 0).toArray().length;
    }

    public int findPermutationDifference(String s, String t) {
        Map<Character, Integer> mapS = new HashMap<>();
        Map<Character, Integer> mapT = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            mapS.put(s.charAt(i), i);
            mapT.put(t.charAt(i), i);
        }
        int ans = 0;
        for (char key : mapS.keySet()) {
            ans += Math.abs(mapS.get(key) - mapT.get(key));
        }
        return ans;
    }

    public int numberOfPairs(int[] nums1, int[] nums2, int k) {
        int ans = 0;
        for (int num1 : nums1) {
            for (int num2 : nums2) {
                if (num1 % (num2 * k) == 0) ans++;
            }
        }
        return ans;
    }

    public int[] getSneakyNumbers(int[] nums) {
        int[] ans = new int[2];
        int index = 0;
        boolean[] found = new boolean[nums.length - 2];
        for (int num : nums) {
            if (found[num]) {
                ans[index++] = num;
            }
            found[num] = true;
        }
        return ans;
    }

    public String convertDateToBinary(String date) {
        String[] strs = date.split("-");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = Integer.toBinaryString(Integer.parseInt(strs[i]));
        }
        return String.format("%s-%s-%s", strs[0], strs[1], strs[2]);
    }

    public List<Integer> stableMountains(int[] height, int threshold) {
        List<Integer> ans = new LinkedList<>();
        for (int i = 1; i < height.length; i++) {
            if (height[i - 1] > threshold) ans.add(i);
        }
        return ans;
    }

    public int[] arrayRankTransform(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        int[] ans = new int[arr.length];
        int rank = 1;
        for (int val : sorted) {
            if (map.containsKey(val)) continue;
            map.put(val, rank++);
        }
        for (int i = 0; i < ans.length; i++) {
            ans[i] = map.get(arr[i]);
        }
        return ans;
    }
}