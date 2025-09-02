import java.util.*;

public class SolutionMedium {

    public List<String> validStrings(int n) {
        List<StringBuilder> strings = new LinkedList<>();
        strings.add(new StringBuilder(n).append('0'));
        strings.add(new StringBuilder(n).append('1'));
        for (int i = 1; i < n; i++) {
            List<StringBuilder> nextString = new LinkedList<>();
            for (StringBuilder sb : strings) {
                if (sb.charAt(sb.length() - 1) == '0') {
                    nextString.add(sb.append('1'));
                } else {
                    nextString.add(new StringBuilder(sb).append('0'));
                    nextString.add(sb.append('1'));
                }
            }
            strings = nextString;
        }
        return strings.stream().map(StringBuilder::toString).toList();
    }

    public int countMaxOrSubsets(int[] nums) {
        int max = 0;
        for (int n : nums) max |= n;
        return countMaxOrSubsetsHelper(nums, 0, 0, max);
    }

    private int countMaxOrSubsetsHelper(int[] nums, int index, int current, int max) {
        if (index == nums.length) {
            return current == max ? 1 : 0;
        }
        int include = countMaxOrSubsetsHelper(nums, index + 1, current | nums[index], max);
        int exclude = countMaxOrSubsetsHelper(nums, index + 1, current, max);
        return include + exclude;
    }

    public int[] findThePrefixCommonArray(int[] a, int[] b) {
        if (a.length != b.length) return null;
        int[] common = new int[a.length];
        Set<Integer> foundA = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            foundA.add(a[i]);
            int current = 0;
            for (int j = 0; j <= i; j++) {
                if (foundA.contains(b[j])) {
                    current++;
                }
            }
            common[i] = current;
        }
        return common;
    }

}
