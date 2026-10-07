package EndlessCheng.GenreMenu.DP.Print;

import java.util.Arrays;

public class LargestNumber {

    // https://leetcode.cn/problems/form-largest-integer-with-digits-that-add-up-to-target/solutions/3814358/wan-quan-bei-bao-shu-chu-ju-ti-fang-an-p-i29o/
    public String largestNumber(int[] cost, int target) {
        int[] f = new int[target + 1]; // f[j] 表示總成本為 j 時的最長長度
        // 「恰好裝滿」型完全背包的初始值
        Arrays.fill(f, Integer.MIN_VALUE);
        f[0] = 0;

        // 計算最長長度
        for (int c : cost) {
            for (int j = c; j <= target; j++) {
                f[j] = Math.max(f[j], f[j - c] + 1);
            }
        }
        if (f[target] < 0) { // 無解
            return "0";
        }

        StringBuilder ans = new StringBuilder(f[target]); // 預分配空間
        int j = target;
        for (int i = 8; i >= 0; i--) { // 從大到小填 i+1
            int c = cost[i];
            // f[j-c] + 1 == f[j] 說明上面 f[j] 取的 max 來自 f[j-c]，說明可以填 i+1
            while (j >= c && f[j - c] + 1 == f[j]) {
                ans.append((char) ('1' + i));
                j -= c;
            }
        }
        return ans.toString();
    }


}
