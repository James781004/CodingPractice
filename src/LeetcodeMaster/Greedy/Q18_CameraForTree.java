package LeetcodeMaster.Greedy;

import LeetcodeMaster.BinaryTree.TreeNode;

public class Q18_CameraForTree {
//    968.監控二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0968.%E7%9B%91%E6%8E%A7%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    給定一個二叉樹，我們在樹的節點上安裝攝像頭。
//
//    節點上的每個攝影頭都可以監視其父對象、自身及其直接子對象。
//
//    計算監控樹的所有節點所需的最小攝像頭數量。
//
//    示例 1：
//
//
//
//    輸入：[0,0,null,0,0]
//    輸出：1
//    解釋：如圖所示，一台攝像頭足以監控所有節點。
//    示例 2：
//
//
//
//    輸入：[0,0,null,0,null,0,null,null,0]
//    輸出：2
//    解釋：需要至少兩個攝像頭來監視樹的所有節點。 上圖顯示了攝像頭放置的有效位置之一。
//    提示：
//
//    給定樹的節點數的範圍是 [1, 1000]。
//    每個節點的值都是 0。

    int res = 0;

    public int minCameraCover(TreeNode root) {
        // 對根節點的狀態做檢驗,防止根節點是無覆蓋狀態 .
        if (minCame(root) == 0) {
            res++;
        }
        return res;
    }

    /**
     * 節點的狀態值：
     * 0 表示無覆蓋
     * 1 表示 有攝像頭
     * 2 表示有覆蓋
     * 後序遍歷，根據左右節點的情況,來判讀 自己的狀態
     */
    private int minCame(TreeNode root) {
        if (root == null) {
            // 空節點默認為 有覆蓋狀態，避免在葉子節點上放攝像頭
            return 2;
        }

        int left = minCame(root.left);
        int right = minCame(root.right);

        // 如果左右節點都覆蓋了的話, 那麽本節點的狀態就應該是無覆蓋,沒有攝像頭
        if (left == 2 && right == 2) {
            //(2,2)
            return 0;
        } else if (left == 0 || right == 0) {
            // 左右節點都是無覆蓋狀態,那 根節點此時應該放一個攝像頭
            // (0,0) (0,1) (0,2) (1,0) (2,0)
            // 狀態值為 1 攝像頭數 ++;
            res++;
            return 1;
        } else {
            // 左右節點的 狀態為 (1,1) (1,2) (2,1) 也就是左右節點至少存在 1個攝像頭，
            // 那麽本節點就是處於被覆蓋狀態
            return 2;
        }
    }

}
