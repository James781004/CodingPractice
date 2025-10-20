package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeDP;

import EndlessCheng.TreeNode;

public class MinimalExecTime {

    // https://leetcode.cn/problems/er-cha-shu-ren-wu-diao-du/solutions/208970/hou-xu-bian-li-di-gui-fan-hui-zuo-you-zi-shu-zui-y/
    public double minimalExecTime(TreeNode root) {
        double[] res = execTime(root, 2);
        return res[0];
    }

   
    private double[] execTime(TreeNode node, int n) {
        if (node == null) {
            // [0]執行完當前節點最小耗時，[1]當前node為根的時間串行之和
            return new double[]{0.0D, 0.0D};
        }
        // 獲取左右子樹的值
        double[] leftTime = execTime(node.left, n);
        double[] rightTime = execTime(node.right, n);
        // 左右子樹節點之和
        double sum = leftTime[1] + rightTime[1];
        // 當前節點執行完的最小消耗時間
        double minTime = Math.max(Math.max(leftTime[0], rightTime[0]), sum / n) + node.val;
        return new double[]{minTime, sum + node.val};
    }


}
