import java.util.*;
import java.util.stream.IntStream;

class Solution {

    public static void main(String[] args) {
        System.out.println(new Solution().countValidWords("!this  1-s b8d!"));
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
}
