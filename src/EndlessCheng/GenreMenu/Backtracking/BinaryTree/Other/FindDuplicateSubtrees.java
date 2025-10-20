package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateSubtrees {

    // https://leetcode.cn/problems/find-duplicate-subtrees/solutions/1801809/by-ac_oier-ly58/
    Map<String, Integer> map = new HashMap<>(); // 哈希表記錄每個標識（子樹）出現次數
    List<TreeNode> ans = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        dfs(root);
        return ans;
    }

    // 返回以傳入參數 root 為根節點的子樹所對應的指紋標識
    String dfs(TreeNode root) {
        if (root == null) return " ";
        StringBuilder sb = new StringBuilder();
        sb.append(root.val).append("_");
        sb.append(dfs(root.left)).append(dfs(root.right));
        String key = sb.toString();
        map.put(key, map.getOrDefault(key, 0) + 1);
        if (map.get(key) == 2) ans.add(root); // 當出現次數為 2（首次判定為重復出現）時，將該節點加入答案
        return key;
    }


}
