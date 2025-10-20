package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

public class Intersect {

    // https://leetcode.cn/problems/logical-or-of-two-binary-grids-represented-as-quad-trees/solutions/1670917/sichashu-by-jiang-hui-4-1x8r/
    public Node intersect(Node quadTree1, Node quadTree2) {
        //quadTree1是葉子結點
        if (quadTree1.isLeaf) {
            //值是true
            if (quadTree1.val) {
                return new Node(true, true, null, null, null, null);
            }
            //值不是true
            return quadTree2;
        }
        //quadTree2是葉子結點
        if (quadTree2.isLeaf) {
            //值是true
            if (quadTree2.val) {
                return new Node(true, true, null, null, null, null);
            }
            //值不是true
            return quadTree1;
        }
        //都不是葉子結點
        Node topLeft = intersect(quadTree1.topLeft, quadTree2.topLeft);
        Node topRight = intersect(quadTree1.topRight, quadTree2.topRight);
        Node bottomLeft = intersect(quadTree1.bottomLeft, quadTree2.bottomLeft);
        Node bottomRight = intersect(quadTree1.bottomRight, quadTree2.bottomRight);
        //四個子節點都是葉子結點並且值相同
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val && topLeft.val == bottomLeft.val && topLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true, null, null, null, null);
        }
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }


    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {
        }

        public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }

    ;

}
