package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Backtracking;

import EndlessCheng.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class PathSumIII {

    // https://leetcode.cn/problems/path-sum-iii/solutions/2784856/zuo-fa-he-560-ti-shi-yi-yang-de-pythonja-fmzo/
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> cnt = new HashMap<>();
        cnt.put(0L, 1);
        return dfs(root, 0, targetSum, cnt);
    }

    // 參數 s 表示前綴和（從根節點到當前節點的所有節點之和）
    private int dfs(TreeNode node, long s, int targetSum, Map<Long, Integer> cnt) {
        if (node == null) {
            return 0;
        }

        s += node.val;
        // 把 node 當作路徑的終點，統計有多少個起點
        int ans = cnt.getOrDefault(s - targetSum, 0);

        cnt.merge(s, 1, Integer::sum); // cnt[s]++
        ans += dfs(node.left, s, targetSum, cnt);
        ans += dfs(node.right, s, targetSum, cnt);
        cnt.merge(s, -1, Integer::sum); // cnt[s]-- 恢復現場

        return ans;
    }

}
