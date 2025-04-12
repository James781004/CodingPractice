package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class GetSubarrayBeauty {

    // https://leetcode.cn/problems/sliding-subarray-beauty/solutions/2241294/hua-dong-chuang-kou-bao-li-mei-ju-by-end-9mvl/
    public int[] getSubarrayBeauty(int[] nums, int k, int x) {
        final int BIAS = 50;
        var cnt = new int[BIAS * 2 + 1];
        int n = nums.length;
        for (int i = 0; i < k - 1; i++) // 先往窗口內添加 k-1 個數
            ++cnt[nums[i] + BIAS];
        var ans = new int[n - k + 1];
        for (int i = k - 1; i < n; i++) {
            ++cnt[nums[i] + BIAS]; // 進入窗口（保證窗口有恰好 k 個數）
            int left = x;
            for (int j = 0; j < BIAS; j++) { // 暴力枚舉負數范圍 [-50,-1]
                left -= cnt[j];
                if (left <= 0) { // 這結果表示當前子數組已不存在比 j - BIAS 更小的數，所以相當於找到美麗值
                    ans[i - k + 1] = j - BIAS;
                    break;
                }
            }
            --cnt[nums[i - k + 1] + BIAS]; // 離開窗口
        }
        return ans;
    }


}
