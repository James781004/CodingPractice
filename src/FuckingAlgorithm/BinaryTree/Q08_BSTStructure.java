package FuckingAlgorithm.BinaryTree;

import java.util.LinkedList;
import java.util.List;

public class Q08_BSTStructure {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }


    //    https://leetcode.cn/problems/unique-binary-search-trees/
//    96. 不同的二叉搜索樹
//    給你一個整數 n ，求恰由 n 個節點組成且節點值從 1 到 n 互不相同的 二叉搜索樹 有多少種？返回滿足題意的二叉搜索樹的種數。

    // 備忘錄
    int[][] memo;

    public int numTrees(int n) {
        // 備忘錄的值初始化為 0
        memo = new int[n + 1][n + 1];

        // 計算閉區間 [1, n] 組成的 BST 個數
        return count(1, n);
    }


    /* 計算閉區間 [lo, hi] 組成的 BST 個數 */
    private int count(int lo, int hi) {
        if (lo > hi) return 1;

        // 查備忘錄
        if (memo[lo][hi] != 0) return memo[lo][hi];

        int res = 0;
        for (int i = lo; i <= hi; i++) {
            // i 的值作為根節點 root
            int left = count(lo, i - 1);
            int right = count(i + 1, hi);
            // 左右子樹的組合數乘積是 BST 的總數
            res += left * right;
        }

        // 將結果存入備忘錄
        memo[lo][hi] = res;
        return res;
    }


//    https://leetcode.cn/problems/unique-binary-search-trees-ii/
//    95. 不同的二叉搜索樹 II
//    給你一個整數 n ，請你生成並返回所有由 n 個節點組成且節點值從 1 到 n 互不相同的不同 二叉搜索樹 。可以按 任意順序 返回答案。

    // 備忘錄
    List<TreeNode>[][] treeMemo;

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) return new LinkedList<>();
        // 構造閉區間 [1, n] 組成的 BST 
        return build(1, n);

    }


    /* 構造閉區間 [lo, hi] 組成的 BST */
    private List<TreeNode> build(int lo, int hi) {
        List<TreeNode> res = new LinkedList<>();
        // base case
        if (lo > hi) {
            res.add(null);
            return res;
        }

        // 查備忘錄
        if (treeMemo[lo][hi] != null) return treeMemo[lo][hi];

        // 1、窮舉 root 節點的所有可能。
        for (int i = lo; i <= hi; i++) {
            // 2、遞歸構造出左右子樹的所有合法 BST。
            List<TreeNode> leftTree = build(lo, i - 1);
            List<TreeNode> rightTree = build(i + 1, hi);
            // 3、給 root 節點窮舉所有左右子樹的組合。
            for (TreeNode left : leftTree) {
                for (TreeNode right : rightTree) {
                    // i 作為根節點 root 的值
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }

        // 將結果存入備忘錄
        treeMemo[lo][hi] = res;
        return res;

    }
}
