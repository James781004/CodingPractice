package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.Arrays;

public class KSum {

    // https://leetcode.cn/problems/find-the-k-sum-of-an-array/solutions/1764389/zhuan-huan-dui-by-endlesscheng-8yiq/
    public long kSum(int[] nums, int k) {
        long sum = 0, right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum += nums[i];
            } else {
                nums[i] = -nums[i];
            }
            right += nums[i];
        }
        Arrays.sort(nums);

        long left = -1;
        while (left + 1 < right) { // 開區間二分，原理見【前置知識】
            long mid = (left + right) / 2;
            cnt = k - 1; // 空子序列算一個
            dfs(0, mid, nums);
            if (cnt == 0) { // 找到 k 個元素和不超過 mid 的子序列
                right = mid;
            } else {
                left = mid;
            }
        }
        return sum - right;
    }

    private int cnt;

    // 反向遞歸，增加改成減少，這樣可以少傳一些參數
    private void dfs(int i, long s, int[] nums) {
        if (cnt == 0 || i == nums.length || s < nums[i]) {
            return;
        }
        cnt--;
        dfs(i + 1, s - nums[i], nums); // 選
        dfs(i + 1, s, nums); // 不選
    }


}
