package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.SameSidePointers;

import java.util.Arrays;

public class RecoverArray {

    // https://leetcode.cn/problems/recover-the-original-array/solutions/1177346/mei-ju-higher0-shuang-zhi-zhen-by-endles-ic64/
    public int[] recoverArray(int[] nums) {
        Arrays.sort(nums);
        int m = nums.length;
        int[] ans = new int[m / 2];
        for (int i = 1; i < m; i++) {
            // nums[0] 必定是第一個值，用nums中的其他值枚舉
            int diff = nums[i] - nums[0];
            // diff不為奇數和0
            if (diff % 2 == 1 || diff == 0) continue;
            int k = diff / 2;
            ans[0] = nums[0] + nums[i] >> 1;
            // 數組下標
            int idx = 1;
            // l從1開始
            int l = 1;
            // r從i+1開始 如果nums[i]為目標值的higher，那個下一個值+k 一定比nums[i]大
            int r = i + 1;
            boolean[] visited = new boolean[m];
            visited[i] = true;
            while (r < m) {
                // i+1後是沒有被遍歷過的，r遍歷過的不能遍歷，l遍歷過的在l和r前面
                while (l < m && visited[l]) l++;
                // 需要大於2*k才可能
                // r不可能等於l
                while (r < m && nums[r] - nums[l] < 2 * k) r++;
                // 邊界 或者 大於2*k 則無效
                if (r == m || nums[r] - nums[l] > 2 * k) break;
                visited[r] = true;
                ans[idx] = nums[r] + nums[l] >> 1;
                idx++;
                l++;
                r++;
            }
            // 第一次滿足，直接返回，lower[0]對應的higher[0]肯定是在比較前面的
            if (idx == m / 2) return ans;
        }
        return ans;
    }

}
