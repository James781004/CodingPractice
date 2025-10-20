package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class GetAllElements {

    // https://leetcode.cn/problems/all-elements-in-two-binary-search-trees/solutions/1461185/by-ac_oier-c8fv/
    int INF = 0x3f3f3f3f;

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> ans = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>(), l2 = new ArrayList<>();
        dfs(root1, l1);
        dfs(root2, l2);
        int n = l1.size(), m = l2.size(), i = 0, j = 0;
        while (i < n || j < m) {
            int a = i < n ? l1.get(i) : INF, b = j < m ? l2.get(j) : INF;
            if (a <= b) {
                ans.add(a);
                i++;
            } else {
                ans.add(b);
                j++;
            }
        }
        return ans;
    }

    void dfs(TreeNode root, List<Integer> list) {
        if (root == null) return;
        dfs(root.left, list);
        list.add(root.val);
        dfs(root.right, list);
    }


}
