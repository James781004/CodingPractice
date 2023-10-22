package Grind.Ch07_BinaryTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Q12_MaximumWidthBinaryTree {
    // https://leetcode.cn/problems/maximum-width-of-binary-tree/solutions/28122/ceng-ci-bian-li-shi-xian-by-aaron_yu/
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        q.offer(root);
        list.add(1); // 根節點下標為 1
        int res = 1;
        while (!q.isEmpty()) {
            int count = q.size();
            for (int i = count; i > 0; i--) {
                TreeNode cur = q.poll();
                Integer curIndex = list.removeFirst();

                // 左子樹結點為 2 * i
                if (cur.left != null) {
                    q.offer(cur.left);
                    list.add(curIndex * 2);
                }

                // 右子樹下標為 2 * i+1
                if (cur.right != null) {
                    q.offer(cur.right);
                    list.add(curIndex * 2 + 1);
                }
            }
            
            // list 中 size 為 1 的情況下，寬度也為 1，沒有必要計算。
            if (list.size() >= 2) {
                res = Math.max(res, list.getLast() - list.getFirst() + 1);
            }
        }

        return res;
    }


    // DFS 解法
    // https://leetcode.cn/problems/maximum-width-of-binary-tree/solutions/1778862/by-ac_oier-33er/
    Map<Integer, Integer> map = new HashMap<>();
    int ans;

    public int widthOfBinaryTreeDFS(TreeNode root) {
        dfs(root, 1, 0);
        return ans;
    }

    void dfs(TreeNode root, int u, int depth) {
        if (root == null) return;

        // 每層的最左節點必然是最先被遍歷到，因此只需要記錄當前層最先被遍歷到點編號（即當前層最小節點編號），
        // 並在 DFS 過程中計算寬度，更新答案即可
        if (!map.containsKey(depth)) map.put(depth, u);
        ans = Math.max(ans, u - map.get(depth) + 1);
        u = u - map.get(depth) + 1;

        // 根據滿二叉樹的節點編號規則：
        // 若根節點編號為 u，則其左子節點編號為 u << 1，其右節點編號為 u << 1 | 1。
        dfs(root.left, u << 1, depth + 1);
        dfs(root.right, u << 1 | 1, depth + 1);
    }
}
