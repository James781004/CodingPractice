package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Challenge;

public class MinSwaps {

    // https://leetcode.cn/problems/minimum-swaps-to-group-all-1s-together-ii/solutions/1200528/shi-ma-qing-kuang-hua-dong-chuang-kou-by-r0zx/
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int ans = n, oneCnt = 0;

        // 統計數組中 1 的數量
        for (int num : nums) {
            if (num == 1) oneCnt++;
        }

        // 開一個寬度為 oneCnt 的窗口， 統計窗口裡面 0 的數量 zeroCnt
        // 最少的 zeroCnt 即為此題答案
        for (int i = 0, zeroCnt = 0; i < 2 * n; i++) {
            if (i >= oneCnt) {
                ans = Math.min(ans, zeroCnt);

                // 右移窗口時，被移除的數字是 0 則 zeroCnt--
                if (nums[(i - oneCnt) % n] == 0) {
                    zeroCnt--;
                }
            }

            if (nums[i % n] == 0) zeroCnt++;
        }

        return ans;
    }

}
