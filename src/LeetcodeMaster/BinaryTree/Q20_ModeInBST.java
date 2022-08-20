package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q20_ModeInBST {
//    501.二叉搜索樹中的眾數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0501.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E4%BC%97%E6%95%B0.md
//
//    給定一個有相同值的二叉搜索樹（BST），找出 BST 中的所有眾數（出現頻率最高的元素）。
//
//    假定 BST 有如下定義：
//
//    1. 節點左子樹中所含結點的值小於等於當前節點的值
//    2. 節點右子樹中所含結點的值大於等於當前節點的值
//    3. 左子樹和右子樹都是二叉搜索樹


    List<Integer> resList;
    int maxCount;
    int count;
    TreeNode pre;

    public int[] findMode(TreeNode root) {
        resList = new ArrayList<>();
        maxCount = 0;
        count = 0;
        pre = null;
        process(root);
        int[] res = new int[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

    // BST原則上就是中序遍歷操作
    private void process(TreeNode root) {
        if (root == null) return;
        process(root.left); // 左

        int rootValue = root.value;  // 中

        if (pre == null || rootValue != pre.value) {  // 如果是第一個節點，或者與前一節點數值不同，重置count為1
            count = 1;
        } else {
            count++;
        }

        // 如果count超出maxCount，表示眾數可能性出現變化，
        // 此時必須清空resList，把當前的新眾數重新放入resList，並更新當下眾數頻率
        if (count > maxCount) {
            resList.clear();
            resList.add(rootValue);
            maxCount = count;
        } else if (count == maxCount) { // count達到眾數頻率(maxCount)，把當前的數放入resList
            resList.add(rootValue);
        }

        pre = root; // pre賦值，作為下一循環的前驅節點使用

        // 右
        process(root.right);
    }


    public int[] findModeStack(TreeNode root) {
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();
        int max = 0;
        int count = 0;
        TreeNode cur = root;
        while (cur != null && !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();

                if (pre == null || cur.value != pre.value) {  // 如果是第一個節點，或者與前一節點數值不同，重置count為1
                    count = 1;
                } else {
                    count++;
                }

                if (count > max) { // 如果眾數出現變化，清空resList，把當前的新眾數重新放入resList，並更新當下眾數頻率
                    result.clear();
                    result.add(cur.value);
                    max = count;
                } else if (count == maxCount) { // count達到眾數頻率，把當前的數放入resList
                    result.add(cur.value);
                }

                pre = cur; // pre賦值，作為下一循環的前驅節點使用

                // 右
                cur = cur.right;
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
