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

    public TreeNode reverseOddLevels(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        int level = 0;
        while (!list.isEmpty()) {
            List<TreeNode> successors = new ArrayList<>();
            for (TreeNode current : list) {
                if (current.left != null) {
                    successors.add(current.left);
                }
                if (current.right != null) {
                    successors.add(current.right);
                }
            }
            if (level % 2 == 0 && !successors.isEmpty()) {
                reverseOddLevelsHelper(successors);
            }
            list = successors;
            level++;
        }
        return root;
    }

    private void reverseOddLevelsHelper(List<TreeNode> successors) {
        int i = 0, j = successors.size() - 1;
        while (i < j) {
            TreeNode front = successors.get(i++);
            TreeNode back = successors.get(j--);
            front.val ^= back.val;
            back.val ^= front.val;
            front.val ^= back.val;
        }
    }
}
