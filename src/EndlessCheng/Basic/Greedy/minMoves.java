package EndlessCheng.Basic.Greedy;

import java.util.ArrayList;

public class minMoves {

    // https://leetcode.cn/problems/minimum-adjacent-swaps-for-k-consecutive-ones/solutions/2024387/tu-jie-zhuan-huan-cheng-zhong-wei-shu-ta-iz4v/
    public int minMoves(int[] nums, int k) {
        var p = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; ++i)
            if (nums[i] != 0) p.add(i - p.size());
        int m = p.size();
        int[] s = new int[m + 1]; // p 的前綴和
        for (int i = 0; i < m; i++)
            s[i + 1] = s[i] + p.get(i);
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= m - k; ++i) // p[i] 到 p[i+k-1] 中所有數到 p[i+k/2] 的距離之和，取最小值
            ans = Math.min(ans, s[i] + s[i + k] - s[i + k / 2] * 2 - p.get(i + k / 2) * (k % 2));
        return ans;
    }


}
