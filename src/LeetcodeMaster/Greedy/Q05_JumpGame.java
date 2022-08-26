package LeetcodeMaster.Greedy;

public class Q05_JumpGame {
//    55. 跳躍遊戲
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0055.%E8%B7%B3%E8%B7%83%E6%B8%B8%E6%88%8F.md
//
//    給定一個非負整數數組，你最初位於數組的第一個位置。
//
//    數組中的每個元素代表你在該位置可以跳躍的最大長度。
//
//    判斷你是否能夠到達最後一個位置。
//
//    示例 1:
//
//    輸入: [2,3,1,1,4]
//    輸出: true
//    解釋: 我們可以先跳 1 步，從位置 0 到達 位置 1, 然後再從位置 1 跳 3 步到達最後一個位置。
//    示例 2:
//
//    輸入: [3,2,1,0,4]
//    輸出: false
//    解釋: 無論怎樣，你總會到達索引為 3 的位置。但該位置的最大跳躍長度是 0 ， 所以你永遠不可能到達最後一個位置。


    public boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        int cover = 0; // 覆蓋範圍, 初始覆蓋範圍應該是0，因為下面的迭代是從下標0開始的
        for (int i = 0; i <= cover; i++) {
            cover = Math.max(cover, i + nums[i]); // 在覆蓋範圍內更新最大的覆蓋範圍
            if (cover >= nums.length - 1) return true;  // 說明可以覆蓋到終點了
        }
        return false;
    }
}
