package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class Q28_TreeSumNumbers {
//    129. 求根節點到葉節點數字之和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0129.%E6%B1%82%E6%A0%B9%E5%88%B0%E5%8F%B6%E5%AD%90%E8%8A%82%E7%82%B9%E6%95%B0%E5%AD%97%E4%B9%8B%E5%92%8C.md


    List<Integer> path = new ArrayList<>();
    int res = 0;

    public int sumNumbers(TreeNode root) {
        // 如果節點為0，那麽就返回0
        if (root == null) return 0;
        // 首先將根節點放到集合中
        path.add(root.value);
        // 開始遞歸
        process(root);
        return res;
    }

    private void process(TreeNode root) {
        if (root.left == null && root.right == null) {
            res += listToInt(path);
            return;
        }

        if (root.left != null) {
            path.add(root.left.value);            // 處理節點
            process(root.left);                   // 遞歸
            path.remove(path.size() - 1);  // 回溯，撤銷
        }

        if (root.right != null) {
            path.add(root.right.value);            // 處理節點
            process(root.right);                   // 遞歸
            path.remove(path.size() - 1);   // 回溯，撤銷
        }

        return;
    }

    private int listToInt(List<Integer> path) {
        int sum = 0;
        for (Integer num : path) {
            sum = sum * 10 + num;
        }
        return sum;
    }
}
