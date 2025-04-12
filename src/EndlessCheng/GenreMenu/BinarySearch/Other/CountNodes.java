package EndlessCheng.GenreMenu.BinarySearch.Other;

import EndlessCheng.TreeNode;

public class CountNodes {

    // https://leetcode.cn/problems/count-complete-tree-nodes/solutions/2456908/javapython3cer-fen-cha-zhao-wei-yun-suan-idof/
    public int countNodes(TreeNode root) {
        // 統計二叉樹層數
        TreeNode node = root;
        int level = 0;
        while (node != null) {
            level++;
            node = node.left;
        }
        if (level <= 1) return level; // 層數小於等於1，直接返回節點個數
        // 二分查找節點個數，左閉右開[left, right)
        int left = 1 << (level - 1);    // 左邊界為最少個數
        int right = 1 << level;         // 右邊界為最多個數+1
        int ans = left;     // 最終結果
        int mid;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (check(root, mid, level)) {
                // 第mid個節點存在，說明最終結果一定大於等於mid，暫存mid並更新左邊界以尋找編號更大的節點是否存在
                ans = mid;
                left = mid + 1;
            } else {
                // 第mid個節點不存在，說明結果小於mid，更新右邊界查找編號更小的節點是否存在
                right = mid;
            }
        }
        return ans;
    }

    private boolean check(TreeNode root, int id, int level) {
        int bit = 1 << (level - 2);     // 初始化開始匹配的位
        while (bit > 0) {
            // 將節點編號的二進制看成從根節點到節點的路徑編碼，根據節點編號尋找節點
            if ((bit & id) == 0) {
                root = root.left;
            } else {
                root = root.right;
            }
            bit >>= 1;
        }
        return root != null;    // 節點不為空，說明編號為id的節點存在
    }


}
