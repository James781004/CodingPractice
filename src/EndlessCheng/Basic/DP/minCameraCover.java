package EndlessCheng.Basic.DP;

import EndlessCheng.TreeNode;

public class minCameraCover {

    // https://leetcode.cn/problems/binary-tree-cameras/solution/shi-pin-ru-he-si-kao-shu-xing-dpgai-chen-uqsf/
    int res = 0;

    public int minCameraCover(TreeNode root) {
        if (dfs(root) == 0) {
            res++;
        }
        return res;


    }

    /*
        dfs返回節點的狀態
        0:未被攝像頭照到（覆蓋）
        1：被攝像頭照到（覆蓋）
        2：放置了攝像頭
    */
    public int dfs(TreeNode root) {
        /*為了保證攝像頭數目最小，葉子節點不能放置攝像頭。
        所以root是null時，設置其狀態是已覆蓋。*/
        if (root == null) {
            return 1;
        }

        int left = dfs(root.left);
        int right = dfs(root.right);

        // 左右孩子一共有 00,01,02,11,12,22 這些狀態
        // 包含了 00 01 02 狀態，左右孩子只要有一個未被覆蓋，root就需要放置攝像頭
        if (left == 0 || right == 0) {
            res++;
            return 2;
        }

        // 11狀態  root需要被父節點的攝像頭覆蓋，設置root的狀態是0
        if (left == 1 && right == 1) {
            return 0;
        }

        // 12 22狀態，root被兒子覆蓋 設置root的狀態是1
        if (left + right >= 3) {
            return 1;
        }

        // 所有的狀態都已經被包含，這裡隨便返回一個值
        return 0;

    }


}
