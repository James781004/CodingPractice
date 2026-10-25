package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.HashMap;
import java.util.Map;

public class NumberOfSubsequences {

    // https://leetcode.cn/problems/count-special-subsequences/solutions/3033284/shi-zi-bian-xing-qian-hou-zhui-fen-jie-p-ts6n/
    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        long ans = 0;
        Map<Float, Integer> cnt = new HashMap<>();
        // 枚舉 b 和 c
        for (int i = 4; i < n - 2; i++) {
            // 增量式更新，本輪循環只需枚舉 b=nums[i-2] 這一個數
            // 至於更前面的 b，已經在前面的循環中添加到 cnt 中了，不能重復添加
            float b = nums[i - 2];
            // 枚舉 a
            for (int j = 0; j < i - 3; j++) {
                cnt.merge(nums[j] / b, 1, Integer::sum);
            }

            float c = nums[i];
            // 枚舉 d
            for (int j = i + 2; j < n; j++) {
                ans += cnt.getOrDefault(nums[j] / c, 0);
            }
        }
        return ans;
    }

}
