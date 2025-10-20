package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFrequentTreeSum {

    // https://leetcode.cn/problems/most-frequent-subtree-sum/solutions/1610634/pythonjavatypescriptgo-di-gui-by-himymbe-9tds/
    private Map<Integer, Integer> map;

    public int[] findFrequentTreeSum(TreeNode root) {
        map = new HashMap<>();
        dfs(root);
        int mx = 0;
        final List<Integer> ans = new ArrayList<>();
        for (int k : map.keySet()) {
            int v = map.get(k);
            if (v > mx) {
                ans.clear();
                mx = v;
                ans.add(k);
            } else if (v == mx) {
                ans.add(k);
            }
        }
        int[] res = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    // 遞歸子樹的和，根據子樹和以及自身值，得到自己作為根的和
    // 統計和的次數，返回自己的和給上層節點計算
    private int dfs(TreeNode node) {
        if (node != null) {
            int left = dfs(node.left), right = dfs(node.right);
            int cur = node.val + left + right;
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            return cur;
        }
        return 0;
    }

}
