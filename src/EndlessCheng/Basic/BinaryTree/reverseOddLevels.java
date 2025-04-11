package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class reverseOddLevels {

    // https://leetcode.cn/problems/reverse-odd-levels-of-binary-tree/solutions/2567086/javapython3cdfsbiao-shi-wei-zhao-dao-mei-5znr/
    public TreeNode reverseOddLevels(TreeNode root) {
        // 從根節點的左右子節點開始遞歸，左右子節點是第一層，奇數次，需要翻轉
        dfs(root.left, root.right, true);
        return root;
    }

    /**
     * 深度優先搜索
     * 根據isSwap，交換node1和node2的值
     */
    private void dfs(TreeNode node1, TreeNode node2, boolean isSwap) {
        if (node1 == null) return;       // 空節點直接返回
        if (isSwap) {
            // 需要交換值，交換
            int temp = node1.val;
            node1.val = node2.val;
            node2.val = temp;
        }
        isSwap = !isSwap;       // 因為只翻轉奇數層，因此標志位交替變化
        dfs(node1.left, node2.right, isSwap);     // node1的左子節點和node2的右子節點是一對
        dfs(node1.right, node2.left, isSwap);     // node1的右子節點和node2的左子節點是一對
    }


    // https://leetcode.cn/problems/reverse-odd-levels-of-binary-tree/solutions/2562073/fan-zhuan-er-cha-shu-de-qi-shu-ceng-by-l-n034/
    public TreeNode reverseOddLevelsBFS(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean isOdd = false;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            List<TreeNode> arr = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode node = queue.poll();
                if (isOdd) { // 奇數層節點用數組 arr 保存起來
                    arr.add(node);
                }
                if (node.left != null) { // BFS 基本流程
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (isOdd) { // 將奇數層中的值進行反轉
                for (int l = 0, r = sz - 1; l < r; l++, r--) {
                    int temp = arr.get(l).val;
                    arr.get(l).val = arr.get(r).val;
                    arr.get(r).val = temp;
                }
            }
            isOdd ^= true; // 奇數層偶數層切換
        }
        return root;
    }

}
