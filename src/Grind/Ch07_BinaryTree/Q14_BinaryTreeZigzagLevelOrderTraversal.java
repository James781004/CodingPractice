package Grind.Ch07_BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q14_BinaryTreeZigzagLevelOrderTraversal {
    // https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/solutions/530584/bfshe-dfsliang-chong-jie-jue-fang-shi-by-184y/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        // 創建隊列，保存節點
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root); // 先把節點加入到隊列中
        boolean leftToRight = true; // 第一步先從左邊開始打印，也可以直接使用 boolean isLeft = ((res.size() & 1) == 0);
        while (!queue.isEmpty()) {
            // 記錄每層節點的值
            List<Integer> level = new ArrayList<>();
            // 統計這一層有多少個節點
            int count = queue.size();
            // 遍歷這一層的所有節點，把他們全部從隊列中移出來，順便
            // 把他們的值加入到集合level中，接著再把他們的子節點（如果有）
            // 加入到隊列中
            for (int i = 0; i < count; i++) {
                // poll移除隊列頭部元素（隊列在頭部移除，尾部添加）
                TreeNode node = queue.poll();
                // 判斷是從左往右打印還是從右往左打印。
                if (leftToRight) {
                    // 如果從左邊打印，直接把訪問的節點值加入到列表level的末尾即可
                    level.add(node.val);
                } else {
                    // 如果是從右邊開始打印，每次要把訪問的節點值
                    // 加入到列表的最前面
                    level.add(0, node.val);
                }
                // 左右子節點如果不為空會被加入到隊列中
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            // 把這一層的節點值加入到集合res中
            res.add(level);
            // 改變下次訪問的方向
            leftToRight = !leftToRight;
        }
        return res;
    }


    public List<List<Integer>> zigzagLevelOrderDFS(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        travel(root, res, 0);
        return res;
    }

    private void travel(TreeNode cur, List<List<Integer>> res, int level) {
        if (cur == null) return;

        // 使用遞歸進行層序遍歷的基本操作
        // 如果res.size() <= level說明下一層的集合還沒創建，所以要先創建下一層的集合
        if (res.size() <= level) {
            List<Integer> newLevel = new LinkedList<>();
            res.add(newLevel);
        }

        // 遍歷到第幾層我們就操作第幾層的數據
        List<Integer> list = res.get(level);

        // 這裡默認根節點是第0層，偶數層相當於從左往右遍歷，
        // 所以要添加到集合的末尾，如果是奇數層相當於從右往左遍歷，
        // 要把數據添加到集合的開頭
        if (level % 2 == 0) list.add(cur.val);
        else list.add(0, cur.val);

        // 分別遍歷左右兩個子節點，到下一層了，所以層數要加1
        travel(cur.left, res, level + 1);
        travel(cur.right, res, level + 1);
    }
}
