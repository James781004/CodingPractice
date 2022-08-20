package LeetcodeMaster.BinaryTree;

public class Q27_ConvertBST {
//    538.把二叉搜索樹轉換為累加樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0538.%E6%8A%8A%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E8%BD%AC%E6%8D%A2%E4%B8%BA%E7%B4%AF%E5%8A%A0%E6%A0%91.md
//
//    給出二叉 搜索 樹的根節點，該樹的節點值各不相同，請你將其轉換為累加樹（Greater Sum Tree），使每個節點 node 的新值等於原樹中大於或等於 node.val 的值之和。
//
//    提醒一下，二叉搜索樹滿足下列約束條件：
//
//    節點的左子樹僅包含鍵 小於 節點鍵的節點。 節點的右子樹僅包含鍵 大於 節點鍵的節點。 左右子樹也必須是二叉搜索樹。
    
    int sum;

    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        process(root);
        return root;
    }

    // 按右中左顺序遍历，累加即可
    public void process(TreeNode root) {
        if (root == null) return;
        process(root.right);
        sum += root.value;
        root.value = sum;
        process(root.left);
    }
}
