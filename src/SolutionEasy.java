import java.util.*;

public class SolutionEasy {

    public int minOperations(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        return sum % k;
    }

}
