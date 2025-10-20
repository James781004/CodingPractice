package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class SmallestFromLeaf {

    // https://leetcode.cn/problems/smallest-string-starting-from-leaf/solutions/764818/988-cong-xie-jie-dian-kai-shi-de-zui-xia-8gda/
    // 選擇一個大於'z'的字符作為初始值
    String ans = "~";

    public String smallestFromLeaf(TreeNode root) {
        dfs(root, new StringBuffer());
        return ans;
    }

    // 深度優先搜索每一條根到葉的路徑
    public void dfs(TreeNode root, StringBuffer sb) {
        // 遍歷到樹的末端，返回
        if (root == null) return;
        // 將當前節點的字符拼接到路徑字符串中
        sb.append((char) ('a' + root.val));
        // 葉子節點：當前路徑字符串s的字典序若小於ans，則更新到ans中
        if (root.left == null && root.right == null) {
            sb.reverse();
            String s = sb.toString();
            sb.reverse(); // sb進行狀態重置

            if (s.compareTo(ans) < 0) {
                ans = s;
            }
        }
        // 深度優先遍歷
        dfs(root.left, sb);
        dfs(root.right, sb);
        // 狀態重置到上一次
        sb.deleteCharAt(sb.length() - 1);
    }

}
