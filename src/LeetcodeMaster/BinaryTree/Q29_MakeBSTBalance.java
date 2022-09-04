package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class Q29_MakeBSTBalance {
//    1382.將二叉搜索樹變平衡
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1382.%E5%B0%86%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E5%8F%98%E5%B9%B3%E8%A1%A1.md
//
//    給你一棵二叉搜索樹，請你返回一棵 平衡後 的二叉搜索樹，新生成的樹應該與原來的樹有著相同的節點值。
//
//    如果一棵二叉搜索樹中，每個節點的兩棵子樹高度差不超過 1 ，我們就稱這棵二叉搜索樹是 平衡的 。
//
//    如果有多種構造方法，請你返回任意一種。
//
//    示例：
//
//
//
//    輸入：root = [1,null,2,null,3,null,4,null,null]
//    輸出：[2,1,3,null,null,null,4]
//    解釋：這不是唯一的正確答案，[3,1,4,null,2,null,null] 也是一個可行的構造方案。
//    提示：
//
//    樹節點的數目在 1 到 10^4 之間。
//    樹節點的值互不相同，且在 1 到 10^5 之間。


    // 可以中序遍歷把二叉樹轉變為有序數組，然後在根據有序數組構造平衡二叉搜索樹。
    List<Integer> res = new ArrayList<Integer>();

    public TreeNode balanceBST(TreeNode root) {
        process(root);
        return getTree(res, 0, res.size() - 1);
    }

    private void process(TreeNode root) {
        if (root == null) return;
        process(root.left);
        res.add(root.value);
        process(root.right);
    }

    // 有序數組轉成平衡二叉樹
    private TreeNode getTree(List<Integer> nums, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums.get(mid));
        root.left = getTree(nums, left, mid - 1);
        root.right = getTree(nums, mid + 1, right);
        return root;
    }
}
