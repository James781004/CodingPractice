package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-split-an-array/solutions/2072725/by-endlesscheng-05s0/
    public int minCost(int[] nums, int k) {
        int n = nums.length;
        int[] f = new int[n + 1]; // f[i+1] 表示劃分 nums 的前 i 個數的最小代價
        byte[] state = new byte[n]; // 用一個數組 state 維護每個元素的出現次數
        for (int i = 0; i < n; ++i) {
            Arrays.fill(state, (byte) 0);
            // 用一個變量 unique 維護只出現一次的元素個數
            int unique = 0, mn = Integer.MAX_VALUE;
            for (int j = i; j >= 0; --j) { // 從 i 開始倒序枚舉最後一個子數組的開始位置 j
                int x = nums[j];
                if (state[x] == 0) { // 首次出現
                    state[x] = 1;
                    ++unique;
                } else if (state[x] == 1) { // 不再唯一
                    state[x] = 2;
                    --unique;
                }
                mn = Math.min(mn, f[j] - unique);
            }
            f[i + 1] = k + mn;
        }
        return f[n] + n;
    }


}
