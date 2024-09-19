import java.util.HashMap;
import java.util.Map;

public class NeighborSum {

    Map<Integer, Integer> adjacentSums;
    Map<Integer, Integer> diagonalSums;
    int[][] grid;

    public NeighborSum(int[][] grid) {
        adjacentSums = new HashMap<>();
        diagonalSums = new HashMap<>();
        this.grid = grid;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                calculateNeighbors(i, j);
            }
        }
    }

    public int adjacentSum(int value) {
        return adjacentSums.getOrDefault(value, -1);
    }

    public int diagonalSum(int value) {
        return diagonalSums.getOrDefault(value, -1);
    }

    private void calculateNeighbors(int row, int col) {
        int adjacentSum = 0, diagonalSum = 0;

        int[][] adjacentOffsets = { {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
        int[][] diagonalOffsets = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };

        for (int[] offset : adjacentOffsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (isValid(newRow, newCol)) {
                adjacentSum += grid[newRow][newCol];
            }
        }

        for (int[] offset : diagonalOffsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (isValid(newRow, newCol)) {
                diagonalSum += grid[newRow][newCol];
            }
        }

        adjacentSums.put(grid[row][col], adjacentSum);
        diagonalSums.put(grid[row][col], diagonalSum);
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}