import java.util.HashMap;

public class QuadTree {

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    public Node construct(int[][] grid) {
        return constructHelper(grid, 0, 0, grid.length);
    }

    private Node constructHelper(int[][] grid, int x, int y, int size) {
        if (size == 1)
            return new Node(grid[x][y] == 1, true);
        int newSize = size / 2;
        Node topLeft = constructHelper(grid, x, y, newSize);
        Node topRight = constructHelper(grid, x, y + newSize, newSize);
        Node bottomLeft = constructHelper(grid, x + newSize, y, newSize);
        Node bottomRight = constructHelper(grid, x + newSize, y + newSize, newSize);
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true);
        } else {
            return new Node(true, false, topLeft, topRight, bottomLeft, bottomRight);
        }
    }
}
