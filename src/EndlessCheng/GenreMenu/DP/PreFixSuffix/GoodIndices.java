package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodIndices {

    // https://leetcode.cn/problems/find-all-good-indices/solutions/1848029/d-by-endlesscheng-kya3/
    public List<Integer> goodIndices(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        int[] dec = new int[n];
        Arrays.fill(dec, 1);

        // 先倒著遍歷，得到從每個位置向後的最長連續非降序列的長度
        for (int i = n - 2; i >= 1; i--) {
            if (nums[i] <= nums[i + 1])
                dec[i] = dec[i + 1] + 1;
        }

        // 然後正著遍歷，得到每個位置向前的最長連續非增序列的長度，同時統計答案
        int inc = 1;
        for (int i = 1; i < n - 1; i++) {
            if (inc >= k && dec[i + 1] >= k)
                res.add(i);
            if (nums[i - 1] >= nums[i])
                inc++; // 遞推
            else
                inc = 1;
        }
        return res;
    }
}
