import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countTestedDevices(new int[]{1,1,2,1,3}));
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
        return sb.toString().equals(tb.toString());
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
                if (words[i].equals(new StringBuilder(words[j]).reverse().toString()))
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
                if (ans.length() == 0) {
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
        if (s == null || s.length() == 0) return " ";
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
            if (map2.get(key) != map1.get(key)) return false;
        }
        return true;
    }

    public String makeGood(String s) {
        Stack<Character> stk = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (stk.size() > 0 && (stk.peek() == ch - 32 || stk.peek() == ch + 32)) stk.pop();
            else stk.push(ch);
        }
        StringBuilder sb = new StringBuilder();
        while (stk.size() > 0) {
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
                k %= size;
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
            int minus = Math.abs(nums[minIndex]);
            int plus = nums[i];
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
                if (index >= list.size() || index < 0 || list.isEmpty()) ans.add(-1);
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
                remainingAmount -= banknotesToUse * banknoteValue;
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
                ans += Math.pow(set.size(), 2);
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
        return ans.toString();
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
        int ans = 0;
        String first = findMinimumOperationsHelper(s1, s2);
        String second = findMinimumOperationsHelper(first, s3);
        if (second.length() < 1) return -1;
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

    public int largestPerimeter(int[] nums) {
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
}