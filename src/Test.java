import static org.junit.Assert.*;

public class Test {

    private static Solution solution = new Solution();

    public static void main(String[] args) {
        sumOfMultiplesTest();
    }

    @org.junit.Test
    public static void sumOfMultiplesTest() {
        assertEquals(21, solution.sumOfMultiples(7));
        assertEquals(40, solution.sumOfMultiples(10));
        assertEquals(30, solution.sumOfMultiples(9));
    }

}
