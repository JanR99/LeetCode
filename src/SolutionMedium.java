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

    public int minOperations(int[] nums, int k) {
        int xor = nums[0];
        for (int i = 1; i < nums.length; i++) {
            xor ^= nums[i];
        }
        if (xor == k) return 0;
        String[] strings = minOperationsHelper(Integer.toBinaryString(xor), Integer.toBinaryString(k));
        String xorBin = strings[0];
        String kBin = strings[1];
        int diff = 0;
        for (int i = 0; i < xorBin.length(); i++) {
            if (xorBin.charAt(i) != kBin.charAt(i)) diff++;
        }
        return diff;
    }


    private String[] minOperationsHelper(String s, String t) {
        if (s.length() < t.length()) {
            int diff = t.length() - s.length();
            StringBuilder sb = new StringBuilder(s);
            for (int i = 0; i < diff; i++) {
                sb.insert(0, "0");
            }
            s = sb.toString();
        } else if (s.length() > t.length()) {
            int diff = s.length() - t.length();
            StringBuilder sb = new StringBuilder(t);
            for (int i = 0; i < diff; i++) {
                sb.insert(0, "0");
            }
            t = sb.toString();
        }
        return new String[]{s, t};
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] ans = new int[rows * cols][2];
        int index = 0;

        int row = rStart, col = cStart;
        int step = 1;
        Direction direction = Direction.EAST;

        ans[index++] = new int[]{row, col};

        while (index < ans.length) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < step; j++) {
                    if (direction == Direction.NORTH) row--;
                    else if (direction == Direction.EAST) col++;
                    else if (direction == Direction.SOUTH) row++;
                    else col--;

                    if (row >= 0 && row < rows && col >= 0 && col < cols) {
                        ans[index++] = new int[]{row, col};
                        if (index == ans.length) return ans;
                    }
                }

                if (direction == Direction.NORTH) direction = Direction.EAST;
                else if (direction == Direction.EAST) direction = Direction.SOUTH;
                else if (direction == Direction.SOUTH) direction = Direction.WEST;
                else direction = Direction.NORTH;
            }
            step++;
        }

        return ans;
    }
}
