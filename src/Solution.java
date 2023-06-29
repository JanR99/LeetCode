import java.util.*;

class Solution {

    public static void main(String[] args) {
        System.out.println(new Solution().binaryGap(22));
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
                for(int i = 0; i < chars.length; i++) {
                    chars[i] = false;
                }
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
        for(int i = 0; i < nums1.length; i++) {
            if(nums1[i] < min1) min1 = nums1[i];
            for(int j = 0; j < nums2.length; j++) {
                if(min2 > nums2[j]) min2 = nums2[j];
                if(nums1[i] == nums2[j] && equal > nums1[i]) equal = nums1[i];
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
        for(int i = 0; i < arr2.length; i++) set.add(arr2[i]);
        for(int i = 0; i < arr1.length; i++) {
            if(set.contains(arr1[i])) {
                map.put(arr1[i], map.getOrDefault(arr1[i], 0) + 1);
            } else {
                list.add(arr1[i]);
            }
        }
        int index = 0;
        for(int i = 0; i < arr2.length; i++) {
            for(int j = 0; j < map.get(arr2[i]); j++) {
                res[index++] = arr2[i];
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
                for(int j = 0; j < repeats; j++) {
                    sb.append(substring);
                }
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
        private PriorityQueue<Integer> minHeap;
        private int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>(k);
            for(int num : nums)
                add(num);
        }

        public int add(int val) {
            if(minHeap.size() < k) {
                minHeap.offer(val);
            } else if(val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek();
        }
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int ans = 0;
        for(int i = 0; i < arr1.length; i++) {
            boolean ok = true;
            for(int j = 0; j < arr2.length; j++) {
                if(Math.abs(arr1[i] - arr2[j]) <= d) {
                    ok = false;
                    break;
                }
            }
            if(ok) ans++;
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
        if(map.size() == 1 && (map.firstKey() == 1 || map.get(map.firstKey()) == 1))
            return true;
        return false;
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
        int indexAB = -1, indexCD = -1;
        while((indexAB = current.indexOf("AB")) != -1 || (indexCD = current.indexOf("CD")) != -1) {
            if(indexAB != -1)
                current = current.substring(0, indexAB) + current.substring(indexAB + 2);
            else if(indexCD != -1)
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
        for(int i = 0; i < list.size(); i++) {
            int first = map.get(list.get(i)).getFirst();
            int last = map.get(list.get(i)).getLast();
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
}