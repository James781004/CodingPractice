package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.HashMap;
import java.util.Map;

public class NumberOfSubsequences {

    // https://leetcode.cn/problems/count-special-subsequences/solutions/3033284/shi-zi-bian-xing-qian-hou-zhui-fen-jie-p-ts6n/
    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> suf = new HashMap<>();
        // 枚舉 c
        for (int i = 4; i < n - 2; i++) {
            int c = nums[i];
            // 枚舉 d
            for (int j = i + 2; j < n; j++) {
                int d = nums[j];
                int g = gcd(c, d);
                // 把分子和分母（兩個 short）壓縮成一個 int
                suf.merge((d / g) << 16 | (c / g), 1, Integer::sum);
            }
        }

        long ans = 0;
        // 枚舉 b
        for (int i = 2; i < n - 4; i++) {
            int b = nums[i];
            // 枚舉 a
            for (int j = 0; j < i - 1; j++) {
                int a = nums[j];
                int g = gcd(a, b);
                ans += suf.getOrDefault((a / g) << 16 | (b / g), 0);
            }
            // 撤銷之前統計的 d'/c'
            int c = nums[i + 2];
            for (int j = i + 4; j < n; j++) {
                int d = nums[j];
                int g = gcd(c, d);
                suf.merge((d / g) << 16 | (c / g), -1, Integer::sum);
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

}
