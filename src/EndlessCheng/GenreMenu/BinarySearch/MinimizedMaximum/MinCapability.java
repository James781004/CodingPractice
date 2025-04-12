package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

public class MinCapability {

    // https://leetcode.cn/problems/house-robber-iv/solutions/2093952/er-fen-da-an-dp-by-endlesscheng-m558/
    public int minCapability(int[] nums, int k) {
        int left = 0, right = 0;
        for (int x : nums) {
            right = Math.max(right, x);
        }
        while (left + 1 < right) { // 開區間寫法
            int mid = (left + right) >>> 1;
            if (check(nums, k, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int[] nums, int k, int mx) {
        int f0 = 0, f1 = 0;
        for (int x : nums) {
            if (x > mx) {
                // 此房屋的金額nums[i]大於竊取能力mid，無法竊取，只能順延dp[i-1]保證盡量大
                f0 = f1;
            } else {
                // 此房屋可以竊取, 可選擇竊取（dp[i-2]+1）和不竊取（dp[i-1]）的最優值
                int tmp = f1;
                f1 = Math.max(f1, f0 + 1);
                f0 = tmp;
            }
        }

        // 只要竊取的房屋數量>=k即可成功，保存答案，繼續尋找更小的符合條件的竊取能力
        return f1 >= k;
    }


}
