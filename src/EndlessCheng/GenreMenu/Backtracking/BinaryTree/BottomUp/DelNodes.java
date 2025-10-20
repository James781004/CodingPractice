package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DelNodes {

    // https://leetcode.cn/problems/delete-nodes-and-return-forest/solutions/2289583/javapython3bian-bian-li-bian-shan-chu-bi-nmnr/
    Set<Integer> set = new HashSet<Integer>();  // 記錄要刪除的節點值的哈希表
    List<TreeNode> ls = new ArrayList<>();      // 記錄森林中每棵樹的根節點的列表

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        // 將要刪除的節點值加入哈希表
        for (int num : to_delete) {
            set.add(num);
        }
        // 如果返回值不為空，即根節點保留，加入列表
        if (dfs(root) != null) {
            ls.add(root);
        }
        // 返回列表
        return ls;
    }

    private TreeNode dfs(TreeNode node) {
        // 空節點返回空值
        if (node == null) {
            return null;
        }
        node.left = dfs(node.left);     // 遞歸處理左子樹，並根據返回結果修改node的左子節點
        node.right = dfs(node.right);   // 遞歸處理右子樹，並根據返回結果修改node的右子節點
        // 當前節點是要刪除的節點
        if (set.contains(node.val)) {
            if (node.left != null) {
                ls.add(node.left);  // 左子節點不為空，加入列表
            }
            if (node.right != null) {
                ls.add(node.right); // 右子節點不為空，加入列表
            }
            return null;    // 返回空節點，使得其父節點的子節點修改為空
        }
        return node;    // 當前節點不是要刪除的節點，返回本身
    }


}
