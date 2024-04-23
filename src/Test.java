import static org.junit.Assert.*;

public class Test {

    private static Solution solution = new Solution();

    public static void main(String[] args) {
        new Test();
    }

    @org.junit.Test
    public void isLongPressedNameTest() {
        assertTrue(solution.isLongPressedName("alex", "aaleex"));
        assertFalse(solution.isLongPressedName("saeed", "ssaaedd"));
        assertTrue(solution.isLongPressedName("leelee", "lleeelee"));
    }

    @org.junit.Test
    public void sumOfMultiplesTest() {
        assertEquals(21, solution.sumOfMultiples(7));
        assertEquals(40, solution.sumOfMultiples(10));
        assertEquals(30, solution.sumOfMultiples(9));
    }

    @org.junit.Test
    public void findModeTest() {
        TreeNode root = new TreeNode(1, null, new TreeNode(2, new TreeNode(2), null));
        assertArrayEquals(new int[]{2}, solution.findMode(root));
    }

    @org.junit.Test
    public void countSeniorsTest() {
        assertEquals(1, solution.countSeniors(new String[]{"qwertzuiopüa70c"}));
    }
}
