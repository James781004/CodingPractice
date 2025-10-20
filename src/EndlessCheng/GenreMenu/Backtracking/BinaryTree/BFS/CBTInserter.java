package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class CBTInserter {

    // https://leetcode.cn/problems/complete-binary-tree-inserter/solutions/1696367/by-ac_oier-t9dh/
    List<TreeNode> list = new ArrayList<>();
    int idx = 0;

    public CBTInserter(TreeNode root) {
        list.add(root);
        int cur = 0; // 使用下標指針 cur 來模擬出隊位置
        while (cur < list.size()) { // 使用數組對構造函數傳入的 root 進行 BFS 層序遍歷
            TreeNode node = list.get(cur);
            if (node.left != null) list.add(node.left);
            if (node.right != null) list.add(node.right);
            cur++;
        }
    }

    public int insert(int val) {
        TreeNode node = new TreeNode(val);
        while (list.get(idx).left != null && list.get(idx).right != null) idx++; // 使用全局變量 idx 不斷往後搜索
        TreeNode fa = list.get(idx); // 在數組（層序遍歷順序）中找到首個「存在左右空子節點」的父節點 fa
        if (fa.left == null) fa.left = node; // 將新節點 node 添加到當前樹
        else if (fa.right == null) fa.right = node;
        list.add(node); // 將 node 也添加到數組尾部
        return fa.val;
    }

    public TreeNode get_root() {
        return list.get(0);
    }

}
