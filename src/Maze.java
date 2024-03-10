public class Maze {

    public boolean isDeadEnd(boolean[][][] maze, boolean[][] deadEnd, int y, int x) {
        int amountEnds = 0;
        if (y + 1 >= deadEnd.length || maze[y][x][0] || deadEnd[y + 1][x])
            amountEnds++;
        if (y - 1 < 0 || maze[y][x][2] || deadEnd[y - 1][x])
            amountEnds++;
        if (x + 1 >= deadEnd[0].length || maze[y][x][1] || deadEnd[y][x + 1])
            amountEnds++;
        if (x - 1 < 0 || maze[y][x][3] || deadEnd[y][x - 1])
            amountEnds++;
        return amountEnds >= 3;
    }

    public int[] seekDeadEnd(boolean[][][] maze, boolean[][] deadEnd) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (isDeadEnd(maze, deadEnd, i, j)) return new int[]{i, j};
            }
        }
        return null;
    }

    public void solveMaze(boolean[][][] maze, boolean[][] deadEnd) {
        while (true) {
            int[] points = seekDeadEnd(maze, deadEnd);
            if (points == null) return;
            deadEnd[points[0]][points[1]] = true;
        }
    }

    public int getSteps(boolean[][][] maze, boolean[][] deadEnd) {
        int ans = 0;
        for (boolean[] x : deadEnd) {
            for (boolean y : x) {
                if (!y) ans++;
            }
        }
        return ans;
    }
}
