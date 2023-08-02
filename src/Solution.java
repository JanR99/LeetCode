import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Solution {

    public static void main(String[] args) {

    }


    Map<Integer, Integer> sumOfMultiplesMem = new HashMap<>();

    public int sumOfMultiples(int n) {
        int sum = 0;
        if(sumOfMultiplesMem.containsKey(n)) return sumOfMultiplesMem.get(n);
        for(int i = 1; i <= n; i++) {
            if(i % 3 == 0 || i % 5 == 0 || i % 7 == 0)
                sum += i;
            sumOfMultiplesMem.put(i, sum);
        }
        return sum;
    }
    public int maxDivScore(int[] nums, int[] divisors) {
        int max = 0;
        int index = -1;
        for(int divisor : divisors) {
            int current = 0;
            for(int num : nums) {
                if(num % divisor == 0)
                    current++;
            }
            if(index == -1) {
                index = divisor;
                max = current;
            } else if(max == current && index > divisor) {
                index = divisor;
            } else if(max < current) {
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
        for(int key : values.keySet()) {
            if(max < values.get(key)) {
                modes.clear();
                max = values.get(key);
                modes.add(key);
            } else if(max == values.get(key)) {
                modes.add(key);
            }
        }
        int[] ans = new int[modes.size()];
        for(int i = 0; i < modes.size(); i++)
            ans[i] = modes.get(i);
        return ans;
    }
    private void findModeHelper(TreeNode node, HashMap<Integer, Integer> values) {
        if(node == null) return;
        values.put(node.val, values.getOrDefault(node.val, 0) + 1);
        findModeHelper(node.left, values);
        findModeHelper(node.right, values);
    }

    public boolean findTarget(TreeNode root, int k) {
        LinkedList<Integer> list = new LinkedList<>();
        findTargetHelper(root, list);
        for(int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            // Two elements found x + y == k
            if(list.contains(k - current)) {
                // If x == y => look if it is the same element
                if(k - current == current) {
                    return list.lastIndexOf(current) != list.indexOf(current);
                }
                return true;
            }
        }
        return false;
    }

    private void findTargetHelper(TreeNode root, LinkedList<Integer> list) {
        if(root == null) return;
        findTargetHelper(root.left, list);
        list.add(root.val);
        findTargetHelper(root.right, list);
    }

    public boolean hasAlternatingBits(int n) {
        String binary = Integer.toBinaryString(n);
        char before = binary.charAt(0);
        for(int i = 1; i < binary.length(); i++) {
            if(before == binary.charAt(i)) return false;
            before = binary.charAt(i);
        }
        return true;
    }

    public boolean isLongPressedName(String name, String typed) {
        int i = 0, n = name.length(), m = typed.length();
        for(int j = 0; j < m; j++)
            if(i < n && name.charAt(i) == typed.charAt(j))
                ++i;
            else if(j == 0 || typed.charAt(j) != typed.charAt(j - 1))
                return false;
        return i == n;
    }

    public int findPoisonedDuration(int[] t, int duration) {
        int total = 0;
        for(int i = 0; i < t.length - 1; i++) {
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
        for(int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if(c == '#') {
                countBackspace++;
                continue;
            }
            if(countBackspace > 0) {
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
        for(char c : s.toCharArray()) {
            if(chars[c - 'a']) {
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
        for(char c : s.toCharArray()) {
            if(currentLength + widths[c - 'a'] > 100) {
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
        while(i <= j) {
            if(chars[i] != chars[j]) {
                if(chars[i] < chars[j]) {
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
        for(String s : details) {
            int age = Integer.parseInt(s.charAt(11) + "" + s.charAt(12), 10);
            if(age > 60) ans++;
        }
        return ans;
    }

    public int diagonalPrime(int[][] nums) {
        int prime = 0;
        int i = 0, j = 0;
        while(i < nums.length && j < nums[i].length) {
            if(nums[i][j] > prime && diagonalPrimeHelper(nums[i][j]))
                prime = nums[i][j];
            i++;
            j++;
        }
        i = 0;
        j = nums[0].length - 1;
        while(i < nums.length && j > 0) {
            if(nums[i][j] > prime && diagonalPrimeHelper(nums[i][j]))
                prime = nums[i][j];
            i++;
            j--;
        }
        return prime;
    }

    private boolean diagonalPrimeHelper(int n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n % 2 == 0 || n % 3 == 0) return false;
        int sqrtN = (int)Math.sqrt(n) + 1;
        for(int i = 6; i <= sqrtN; i += 6) {
            if(n % (i - 1) == 0 || n % (i + 1) == 0) return false;
        }
        return true;
    }

    public int minNumber(int[] nums1, int[] nums2) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int equal = Integer.MAX_VALUE;
        for(int k : nums1) {
            if (k < min1) min1 = k;
            for(int i : nums2) {
                if (min2 > i) min2 = i;
                if (k == i && equal > k) equal = k;
            }
        }
        if(equal != Integer.MAX_VALUE) return equal;
        return Math.min(min1 * 10 + min2, min2 * 10 + min1);
    }

    public String removeTrailingZeros(String num) {
        StringBuilder reversed = new StringBuilder(num).reverse();
        int i = 0;
        while(true) {
            if(reversed.charAt(i) == '0') {
                i++;
            } else {
                break;
            }
        }
        return new StringBuilder(reversed.substring(i)).reverse().toString();
    }

    public int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        if(prices[0] + prices[1] > money) return money;
        return money - (prices[0] + prices[1]);
    }

    public String bestHand(int[] ranks, char[] suits) {
        Map<Character, Integer> suitsMap = new HashMap<>();
        Map<Integer, Integer> rankMap = new HashMap<>();
        for(int i = 0; i < ranks.length; i++) {
            suitsMap.put(suits[i], suitsMap.getOrDefault(suits[i], 0) + 1);
            rankMap.put(ranks[i], rankMap.getOrDefault(ranks[i], 0) + 1);
        }
        for(char c : suitsMap.keySet()) {
            if(suitsMap.get(c) == 5) return "Flush";
        }
        int maxSame = 0;
        for(int c : rankMap.keySet()) {
            if(maxSame < rankMap.get(c))
                maxSame = rankMap.get(c);
        }
        if(maxSame >= 3)
            return "Three of a Kind";
        if(maxSame == 2)
            return "Pair";
        return "High Card";
    }

    public int countValidWords(String sentence) {
        String[] words = sentence.split(" ");
        int ans = 0;
        for(String word : words) {
            if(word.matches("^\\s*$")) continue;
            boolean isOk = true;
            int amountHyphen = 0;
            for(int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if(Character.isDigit(c)) {
                    isOk = false;
                    break;
                }
                if(c == '-') {
                    if(i == 0 || i == word.length() - 1) {
                        isOk = false;
                        break;
                    }
                    if(!Character.isLowerCase(word.charAt(i - 1)) || !Character.isLowerCase(word.charAt(i + 1))) {
                        isOk = false;
                        break;
                    }
                    if(amountHyphen != 0) {
                        isOk = false;
                        break;
                    }
                    amountHyphen++;
                }
                if(c == '!' || c == '.' || c == ',') {
                    if(i != word.length() - 1) {
                        isOk = false;
                        break;
                    }
                }
            }
            if(isOk) ans++;
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
                while(odd[o] == 0) {
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
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                return validPalindromeHelper(s, i + 1, j) || validPalindromeHelper(s, i, j - 1);
            }
            i++;
            j--;
        }
        return true;
    }

    private boolean validPalindromeHelper(String s, int start, int end) {
        while(start < end) {
            if(s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public int maximizeSum(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            if(num > max) max = num;
        }
        int ans = 0;
        for(int i = 0; i < k; i++) {
            ans += max++;
        }
        return ans;
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length];
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new LinkedList<>();
        for(int k : arr2) set.add(k);
        for(int k : arr1) {
            if(set.contains(k)) {
                map.put(k, map.getOrDefault(k, 0) + 1);
            } else {
                list.add(k);
            }
        }
        int index = 0;
        for(int k : arr2) {
            for(int j = 0; j < map.get(k); j++) {
                res[index++] = k;
            }
        }
        Collections.sort(list);
        for(int i : list)
            res[index++] = i;
        return res;
    }

    public int[][] construct2DArray(int[] original, int m, int n) {
        if(m * n < original.length) return new int[][]{};
        int[][] ans = new int[m][n];
        int index = 0;
        try{
            for(int i = 0; i < m; i++) {
                for(int j = 0; j < n; j++) {
                    ans[i][j] = original[index++];
                }
            }

        } catch(ArrayIndexOutOfBoundsException e) {
            return new int[][]{};
        }
        return ans;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        return (p.val == q.val) && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
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
        for (char c = 'a';; c++) {
            if (c != prev && c != next) {
                return c;
            }
        }
    }

    public boolean kLengthApart(int[] nums, int k) {
        int dist = -1;
        for(int num : nums) {
            if(num == 0) {
                if (dist != -1) {
                    ++dist;
                }
            } else {
                if(dist < k && dist != -1)
                    return false;
                dist = 0;
            }
        }
        return true;
    }

    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int n = mat[0].length;
        if(r * c != mat.length * n) {
            return mat;
        }
        int[][] ans = new int[r][c];
        for(int i = 0; i < r * c; i++) {
            ans[i / c][i % c] = mat[i / n][i % n];
        }
        return ans;
    }

    public boolean buddyStrings(String s, String goal) {
        if(s.length() != goal.length()) return false;
        if(s.length() == 1) return false;
        int first = -1, second = -1;
        int[] chars = new int[26];
        for(int i = 0; i < s.length(); i++) {
            chars[s.charAt(i) - 'a']++;
            if(s.charAt(i) != goal.charAt(i)) {
                if(first == -1) {
                    first = i;
                } else if(second == -1) {
                    second = i;
                } else {
                    return false;
                }
            }
        }
        if(first == -1) {
            for(int i : chars) {
                if(i > 1) return true;
            }
            return false;
        }
        if(second == -1) return false;
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(first, s.charAt(second));
        sb.setCharAt(second, s.charAt(first));
        return sb.toString().equals(goal);
    }

    public int countBinarySubstrings(String s) {
        int ans = 0;
        int prevCount = 0;
        int currCount = 1;
        for(int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == s.charAt(i - 1)) {
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
        for(int num : nums) {
            remainder = (remainder * 2 + num) % 5;
            ans.add(remainder == 0);
        }
        return ans;
    }

    public int pivotIndex(int[] nums) {
        int sum = 0;
        for(int num : nums)
            sum += num;
        int leftSum = 0;
        for(int i = 0; i < nums.length; i++) {
            int rightSum = sum - leftSum - nums[i];
            if(leftSum == rightSum)
                return i;
            leftSum += nums[i];
        }
        return -1;
    }

    public boolean repeatedSubstringPattern(String s) {
        if(s == null || s.length() <= 1) return false;
        int n = s.length();
        for(int i = n / 2; i >= 1; i--) {
            if(n % i == 0) {
                int repeats = n / i;
                String substring = s.substring(0, i);
                StringBuilder sb = new StringBuilder();
                sb.append(substring.repeat(repeats));
                if(sb.toString().equals(s)) return true;
            }
        }
        return false;
    }

    public String removeDigit(String number, char digit) {
        List<String> digits = new ArrayList<>();
        for(int i = 0; i < number.length(); i++) {
            if(number.charAt(i) == digit) {
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
            for(int num : nums)
                add(num);
        }

        public int add(int val) {
            if(minHeap.size() < k) {
                minHeap.offer(val);
            } else if(minHeap.peek() != null && val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek() == null ? -1 : minHeap.peek();
        }
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int ans = 0;
        for(int k : arr1) {
            boolean ok = true;
            for(int i : arr2) {
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
        for(int i : nums) {
            if(current == -1) current = i;
            if(i < current) {
                if(wasLower) return false;
                wasLower = true;
            }
            current = i;
        }
        return wasLower ? nums[nums.length - 1] <= nums[0] : nums[nums.length - 1] >= nums[0];
    }

    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int[] i : nums1)
            map.put(i[0], map.getOrDefault(i[0], 0) + i[1]);
        for(int[] i : nums2)
            map.put(i[0], map.getOrDefault(i[0], 0) + i[1]);
        LinkedList<Integer> list = new LinkedList<>(map.keySet());
        Collections.sort(list);
        int[][] ans = new int[list.size()][2];
        for(int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            ans[i][0] = current;
            ans[i][1] = map.get(current);
        }
        return ans;
    }

    public int titleToNumber(String s) {
        int ans = 0;
        int index = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            ans += ((int)Math.pow(26, index++) * (s.charAt(i) - 'A' + 1));
        }
        return ans;
    }

    public boolean equalFrequency(String word) {
        int[] count = new int[26];
        for(char c : word.toCharArray())
            count[c - 'a']++;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int num : count) {
            if (num == 0) continue;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        if(map.size() == 2 && map.firstKey() + 1 == map.lastKey() && map.get(map.lastKey()) == 1)
            return true;
        if(map.size() == 2 && map.firstKey() == 1 && map.get(map.firstKey()) == 1)
            return true;
        return map.size() == 1 && (map.firstKey() == 1 || map.get(map.firstKey()) == 1);
    }

    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : deck)
            map.put(i, map.getOrDefault(i, 0) + 1);
        for(int value1 : map.values()) {
            for(int value2 : map.values()) {
                if(hasGroupsSizeXHelper(value1, value2) == 1) return false;
            }
        }
        return true;
    }

    private int hasGroupsSizeXHelper(int a, int b) {
        if(b == 0) return a;
        return hasGroupsSizeXHelper(b, a % b);
    }

    public int arrangeCoins(int n) {
        int coins = n;
        int stair = 2;
        int ans = 0;
        while(true) {
            if(coins <= 1) {
                if(coins == 1) return ans + 1;
                if(coins - stair == -1) return ans + 1;
                return ans;
            } else {
                coins -= stair++;
            }
            ans++;
        }
    }

    public int countBeautifulPairs(int[] nums) {
        int ans = 0;
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                int a = Character.getNumericValue(String.valueOf(nums[i]).charAt(0));
                String s = String.valueOf(nums[j]);
                int b = Character.getNumericValue(s.charAt(s.length() - 1));
                if(countBeautifulPairsHelper(a, b) == 1) ans++;
            }
        }
        return ans;
    }

    private int countBeautifulPairsHelper(int a, int b) {
        if(b == 0) return a;
        return countBeautifulPairsHelper(b, a % b);
    }

    public int maximumNumberOfStringPairs(String[] words) {
        int ans = 0;
        for(int i = 0; i < words.length; i++) {
            for(int j = i + 1; j < words.length; j++) {
                if(words[i].equals(new StringBuilder(words[j]).reverse().toString()))
                    ans++;
            }
        }
        return ans;
    }

    public int distanceTraveled(int mainTank, int additionalTank) {
        int ans = 0;
        while(mainTank > 0) {
            if(mainTank >= 5 && additionalTank > 0) {
                ans += 50;
                mainTank -= 4;
                additionalTank--;
            } else if(mainTank >= 5) {
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
        if(nums.length < 3) return -1;
        int min = Math.min(nums[0], Math.min(nums[1], nums[2]));
        int max = Math.max(nums[0], Math.max(nums[1], nums[2]));
        for(int num : nums)
            if (num != min && num != max) return num;
        return -1;
    }

    public boolean isFascinating(int n) {
        StringBuilder sb = new StringBuilder(String.valueOf(n));
        sb.append(2 * n);
        sb.append(3 * n);
        boolean[] found = new boolean[9];
        for(int i = 0; i < sb.length(); i++) {
            char current = sb.charAt(i);
            if(current == '0') return false;
            int index = Character.getNumericValue(current) - 1;
            if(!found[index]) {
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
        while((indexAB = current.indexOf("AB")) != -1 || (indexCD = current.indexOf("CD")) != -1) {
            if(indexAB != -1)
                current = current.substring(0, indexAB) + current.substring(indexAB + 2);
            else
                current = current.substring(0, indexCD) + current.substring(indexCD + 2);
        }
        return current.length();
    }

    public int minimizedStringLength(String s) {
        HashSet<Character> set = new HashSet<>();
        for(char c : s.toCharArray())
            set.add(c);
        return set.size();
    }

    public String digitSum(String s, int k) {
        String current = s;
        while(current.length() > k) {
            StringBuilder tmp = new StringBuilder();
            int index = 0;
            while(index < current.length()) {
                if(index + k < current.length())
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
        for(char c : s.toCharArray())
            ans += Character.getNumericValue(c);
        return ans;
    }

    public boolean rotateString(String s, String goal) {
        if(s.length() != goal.length()) return false;
        if(s.equals(goal)) return true;
        String current = s;
        for(int i = 0; i < s.length(); i++) {
            current = current.substring(1) + current.charAt(0);
            if(current.equals(goal)) return true;
        }
        return false;
    }

    public int findLHS(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = 1;
        int len = 0;
        while(right < nums.length) {
            int diff = nums[right] - nums[left];
            if(diff == 1)
                len = Math.max(len, right-left + 1);
            if(diff <= 1)
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
        for(int i = 0; i < nums.length; i++) {
            LinkedList<Integer> tmp = map.getOrDefault(nums[i], new LinkedList<>());
            tmp.addLast(i);
            map.put(nums[i], tmp);
            if(tmp.size() > max) {
                max = tmp.size();
                list.clear();
                list.add(nums[i]);
            } else if(tmp.size() == max) {
                if(!list.contains(nums[i])) {
                    list.add(nums[i]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        if(list.isEmpty()) return 0;
        for(Integer integer : list) {
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
        while(j < s.length) {
            if(i >= g.length) break;
            if(g[i] <= s[j]) {
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
        for(int i = 0; i < list.size() - 1; i++) {
            int first = list.get(i);
            int second = list.get(i + 1);
            min = Math.min(min, second - first);
        }
        return min;
    }

    private void getMinimumDifferenceHelper(TreeNode node, LinkedList<Integer> list) {
        if(node == null) return;
        getMinimumDifferenceHelper(node.left, list);
        list.add(node.val);
        getMinimumDifferenceHelper(node.right, list);
    }

    public boolean lemonadeChange(int[] bills) {
        int amount5 = 0;
        int amount10 = 0;
        for(int bill : bills) {
            if(bill == 5) {
                amount5++;
            } else if(bill == 10) {
                if(amount5 <= 0) return false;
                amount5--;
                amount10++;
            } else {
                if(amount10 > 0 && amount5 > 0) {
                    amount10--;
                    amount5--;
                } else if(amount5 >= 3) {
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
        for(int i = 0; i < binary.length(); i++) {
            if(binary.charAt(i) != '1') {
                continue;
            }
            if(first == -1) {
                first = i;
                continue;
            }
            if(second == -1) {
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
        while(i < arr.length - 1) {
            if(arr[i] != 0) {
                i++;
                continue;
            }
            if(i == arr.length - 1) break;
            for(int j = arr.length - 2; j > i; j--) {
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
        while(current != start || begin) {
            if(begin) {
                begin = false;
                between += distance[current];
            } else if(in) {
                if(current == destination)
                    in = false;
                if(in)
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
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == 'R') {
                    posI = i;
                    posJ = j;
                    break;
                }
            }
        }
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for(int i = 0; i < 4; i++) {
            int x = posI;
            int y = posJ;
            while(x >= 0 && x < 8 && y >= 0 && y < 8) {
                if(board[x][y] == 'p') {
                    ans++;
                    break;
                } else if(board[x][y] == 'B') {
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
        while(i < nums.length) {
            int left = 0;
            List<Integer> found = new ArrayList<>();
            for(int j = 0; j <= i; j++) {
                if(!found.contains(nums[j])) {
                    found.add(nums[j]);
                    left++;
                }
            }
            found.clear();
            int right = 0;
            for(int j = i + 1; j < nums.length; j++) {
                if(!found.contains(nums[j])) {
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
        for(int i = 0; i < nums.length - 1; i++) {
            if(nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }
        int index = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }
        while(index < nums.length) {
            nums[index] = 0;
            index++;
        }
        return nums;
    }

    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int n = nums.length, max = 0, l = 0, r = 0, flag = 0;
        while(r < n){
            if(flag == 0) {
                if(nums[r] % 2 == 0 && nums[r] <= threshold) {
                    l = r;
                    max = Math.max(max, 1);
                    flag = 1;
                }
            } else {
                int x = nums[r - 1], y = nums[r], c = x + y;
                if(c % 2 != 0 && nums[r] <= threshold) {
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
        for(int integer : stones)
            map.put(integer, map.getOrDefault(integer, 0) + 1);
        while(map.size() > 1) {
            int highest = map.lastKey();
            if(map.get(highest) > 1) {
                if(map.get(highest) % 2 == 0) {
                    map.remove(highest);
                } else {
                    map.put(highest, 1);
                }
            } else {
                map.remove(highest);
                int secondHighest = map.lastKey();
                if(map.get(secondHighest) == 1) {
                    map.remove(secondHighest);
                } else {
                    map.put(secondHighest, map.get(secondHighest) - 1);
                }
                int current = highest - secondHighest;
                map.put(current, map.getOrDefault(current, 0) + 1);
            }
        }
        if(map.size() == 1)
            return map.get(map.lastKey()) % 2 == 0 ? 0 : map.lastKey();
        return 0;
    }

    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        int sum = 0;
        while(k > 0) {
            if(numOnes != 0) {
                if(numOnes >= k) {
                    sum += k;
                    k = 0;
                } else {
                    sum += numOnes;
                    k -= numOnes;
                    numOnes = 0;
                }
            } else if(numZeros != 0) {
                if(numZeros >= k) {
                    k = 0;
                } else {
                    k -= numZeros;
                    numZeros = 0;
                }
            } else if(numNegOnes != 0) {
                if(numNegOnes >= k) {
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
        for(int i = 0; i < mat.length; i++) {
            int currentAmount = 0;
            for(int j = 0; j < mat[i].length; j++) {
                if(mat[i][j] == 1) currentAmount++;
            }
            if(currentAmount > ans[1]) {
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
        for(int current : arr)
            map.put(current, map.getOrDefault(current, 0) + 1);
        int max = -1;
        for(int key : map.keySet()) {
            int value = map.get(key);
            if(key == value) {
                max = Math.max(max, value);
            }
        }
        return max;
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int ans = 0;
        Map<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for(int[] dominoe : dominoes) {
            int a = Math.min(dominoe[0], dominoe[1]);
            int b = Math.max(dominoe[0], dominoe[1]);
            if(map.containsKey(a)) {
                HashMap<Integer, Integer> current = map.get(a);
                current.put(b, current.getOrDefault(b, 0) + 1);
            } else {
                HashMap<Integer, Integer> tmp = new HashMap<>();
                tmp.put(b, 1);
                map.put(a, tmp);
            }
        }
        for(int a : map.keySet()) {
            HashMap<Integer, Integer> current = map.get(a);
            for(int b : current.keySet()) {
                int n = current.get(b);
                ans += (n * (n - 1) / 2);
            }
        }
        return ans;
    }

    public int getMinDistance(int[] nums, int target, int start) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == target) {
                min = Math.min(min, Math.abs(i - start));
            }
        }
        return min;
    }

    public String shortestCompletingWord(String licensePlate, String[] words) {
        String ans = "";
        Map<Character, Integer> map = new HashMap<>();
        for(char c : licensePlate.toCharArray()) {
            if(!Character.isLetter(c)) continue;
            c = Character.toLowerCase(c);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for(String s : words) {
            Map<Character, Integer> current = new HashMap<>();
            for(char c : s.toCharArray()) {
                if (!Character.isLetter(c)) continue;
                c = Character.toLowerCase(c);
                current.put(c, current.getOrDefault(c, 0) + 1);
            }
            if(current.size() < map.size()) continue;
            boolean ok = true;
            for(char c : map.keySet()) {
                if(!current.containsKey(c)) {
                    ok = false;
                    break;
                }
                if(current.get(c) < map.get(c)) {
                    ok = false;
                    break;
                }
            }
            if(ok) {
                if(ans.length() == 0) {
                    ans = s;
                } else if(ans.length() > s.length()) {
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
        while(i < chars.length) {
            if(chars[i] == 'O') {
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
        if(children > money) return -1;
        if(money == children * 8) return children;
        if(money > children * 8) return children -1;
        money -= children;
        while(money >= 3) {
            if(money >= 7) {
                money -= 7;
                ans++;
            } else if(money == 3) {
                if(children - ans >= 2) return ans;
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
        while(i <= j) {
            if(i == j) {
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
        if(income >= before) {
            ans += (before * brackets[0][1] / 100.0);
            income -= before;
        } else {
            ans += (income * brackets[0][1] / 100.0);
            return ans;
        }
        for(int i = 1; i < brackets.length; i++) {
            int current = brackets[i][0] - before;
            if(current <= income) {
                ans += (current * brackets[i][1] / 100.0);
                income -= current;
            } else if(income == 0){
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
        for(int i : nums)
            if(i % 2 == 0)
                map.put(i, map.getOrDefault(i, 0) + 1);
        Set<Integer> set = map.keySet();
        int max = -1;
        int num = Integer.MAX_VALUE;
        for(int i : set) {
            int val = map.get(i);
            if(val == max && num > i)
                num = i;
            else if(val > max) {
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
        while(i >= 0 && j >= 0) {
            int current = Character.getNumericValue(sb1.charAt(i--)) + Character.getNumericValue(sb2.charAt(j--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if(ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        while(i >= 0) {
            int current = Character.getNumericValue(sb1.charAt(i--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if(ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        while(j >= 0) {
            int current = Character.getNumericValue(sb2.charAt(j--)) + offset;
            offset = current > 9 ? current / 10 : 0;
            current = current % 10;
            if(ans == null) {
                ans = new ListNode(current);
            } else {
                ListNode node = new ListNode(current);
                node.next = ans;
                ans = node;
            }
        }
        if(offset != 0) {
            ListNode node = new ListNode(offset);
            node.next = ans;
            ans = node;
        }
        return ans;
    }

    private StringBuilder addTwoNumbersHelper(ListNode node) {
        ListNode current = node;
        StringBuilder ans = new StringBuilder();
        while(current != null) {
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
            if(!map.containsKey(key)) return -1;
            used.remove(key);
            used.add(key);
            return map.get(key);
        }

        public void put(int key, int value) {
            if(map.containsKey(key)) {
                map.put(key, value);
                used.remove(key);
                used.add(key);
                return;
            }
            if(map.size() + 1 > max) {
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
        if(daysBetween < 0)
            daysBetween = ChronoUnit.DAYS.between(local2, local1);
        return (int) daysBetween;
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stk = new Stack<>();
        for(int asteroid : asteroids) {
            if(stk.isEmpty() || asteroid > 0) {
                stk.push(asteroid);
            } else {
                while(!stk.isEmpty() && stk.peek() > 0 && stk.peek() < Math.abs(asteroid))
                    stk.pop();
                if(!stk.isEmpty() && stk.peek() == Math.abs(asteroid)) {
                    stk.pop();
                } else {
                    if(stk.isEmpty() || stk.peek() < 0)
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
            case Calendar.SUNDAY    -> "Sunday";
            case Calendar.MONDAY    -> "Monday";
            case Calendar.TUESDAY   -> "Tuesday";
            case Calendar.WEDNESDAY -> "Wednesday";
            case Calendar.THURSDAY  -> "Thursday";
            case Calendar.FRIDAY    -> "Friday";
            case Calendar.SATURDAY  -> "Saturday";
            default -> "Error";
        };
    }

    public boolean checkOnesSegment(String s) {
        boolean endOne = false;
        for(char c : s.toCharArray()) {
            if(endOne && c == '1') return false;
            if(c == '0') endOne = true;
        }
        return true;
    }

    public int minStartValue(int[] nums) {
        int i = 0, j = 0;
        for(int num : nums) {
            j += num;
            i = Math.min(i, j);
        }
        return 1 - i;
    }

    public int[] constructRectangle(int area) {
        int L = area / (int)Math.sqrt(area);
        while(true) {
            if(L == area) return new int[]{area, 1};
            double current = (double)area / (double)L;
            if(current == (int)current) break;
            L++;
        }
        return new int[]{L, area / L};
    }

    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> ans = new LinkedList<>();
        for(String s : words) {
            StringBuilder tmp = new StringBuilder();
            for(char c : s.toCharArray()) {
                if(c == separator) {
                    if(tmp.length() > 0)
                        ans.add(tmp.toString());
                    tmp = new StringBuilder();
                } else {
                    tmp.append(c);
                }
            }
            if(tmp.length() > 0) ans.add(tmp.toString());
        }
        return ans;
    }

    public boolean isGood(int[] nums) {
        int[] ans = new int[nums.length];
        for(int num : nums) {
            if(num >= ans.length || num < 0) return false;
            if(ans[num] == 0) {
                ans[num]++;
            } else if(ans[num] == 1 && num == ans.length - 1) {
                ans[num]++;
            } else {
                return false;
            }
        }
        return true;
    }

    public int sumOfSquares(int[] nums) {
        int ans = 0;
        for(int i = 0; i < nums.length; i++)
            if(nums.length % (i + 1) == 0)
                ans += (int)Math.pow(nums[i], 2);
        return ans;
    }

    public int[] findColumnWidth(int[][] grid) {
        int[] ans = new int[grid[0].length];
        for(int[] row : grid) {
            for(int i = 0; i < row.length; i++) {
                int col = row[i];
                int currentLength = String.valueOf(col).length();
                ans[i] = Math.max(ans[i], currentLength);
            }
        }
        return ans;
    }

    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for(int gift : gifts) queue.add(gift);
        for(int i = 0; i < k; i++) {
            if(queue.isEmpty()) return -1;
            int current = queue.poll();
            current = (int) Math.sqrt(current);
            queue.add(current);
        }
        long ans = 0;
        for(int num : queue) ans += num;
        return ans;
    }

    public boolean validMountainArray(int[] arr) {
        if(arr.length == 1) return false;
        if(arr[0] > arr[1]) return false;
        int before = arr[0];
        boolean wasHigher = true;
        for(int i = 1; i < arr.length; i++) {
            if(wasHigher) {
                if(before < arr[i]) {
                    before = arr[i];
                } else if(before == arr[i]) {
                    return false;
                } else {
                    wasHigher = false;
                    before = arr[i];
                }
            } else {
                if(before <= arr[i]) return false;
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
        for(int i = 1; i <= month; i++) {
            if(i < month) {
                if(i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
                    ans += 31;
                else if(i == 2) {
                    if(dayOfYearHelper(year))
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
        return (int)Arrays.stream(hours).filter(x -> x >= target).count();
    }

    public TreeNode increasingBST(TreeNode root) {
        LinkedList<TreeNode> list = new LinkedList<>();
        increasingBSTHelper(root, list);
        TreeNode newRoot = new TreeNode(list.get(0).val);
        TreeNode current = newRoot;
        for(int i = 1; i < list.size(); i++) {
            current.right = new TreeNode(list.get(i).val);
            current = current.right;
        }
        return newRoot;
    }

    public void increasingBSTHelper(TreeNode node, List<TreeNode> list) {
        if(node == null) return;
        increasingBSTHelper(node.left, list);
        list.add(node);
        increasingBSTHelper(node.right, list);
    }

    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int ans = 0;
        for(int i = 0; i < seats.length; i++) {
            ans += Math.abs(students[i] - seats[i]);
        }
        return ans;
    }

    public String removeOuterParentheses(String s) {
        if(s == null || s.length() == 0) return " ";
        StringBuilder sb = new StringBuilder();
        int begin = 0, count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') count++;
            else if(s.charAt(i) == ')') count--;
            if(count == 0) {
                sb.append(s, begin + 1, i);
                begin = i + 1;
            }
        }
        return sb.toString();
    }
}