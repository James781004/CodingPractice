package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class AverageOfSubtree {

    // https://leetcode.cn/problems/count-nodes-equal-to-average-of-subtree/description/
    int res = 0; // 要求的節點個數

    public int averageOfSubtree(TreeNode root) {
        // 後序遍歷+維護子樹值的和與節點總個數
        dfs(root);
        return res;
    }

    // 返回以root為根節點的子樹(包含root本身)值的和與總節點個數
    private int[] dfs(TreeNode root) {
        // base case 葉子結點:值為root.val,個數為1,必定符合題目要求res++
        if (root.left == null && root.right == null) {
            res++;
            return new int[]{root.val, 1};
        }
        // 這裡考慮到root左右子樹為空的情況:若為空值的和為0,總個數也為0
        int[] l = new int[]{0, 0}, r = new int[]{0, 0};
        if (root.left != null) {
            l = dfs(root.left);
        }
        if (root.right != null) {
            r = dfs(root.right);
        }
        // 計算加上root的值
        int sum = l[0] + r[0] + root.val;
        int cnt = l[1] + r[1] + 1;
        // 若root本身也符合要求就+1
        res += sum / cnt == root.val ? 1 : 0;
        // 返回以root為根節點的子樹(包含root本身)值的和與總節點個數
        return new int[]{sum, cnt};
    }
}
