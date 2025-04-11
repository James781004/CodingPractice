package EndlessCheng.Basic.Greedy;

import java.util.Arrays;

public class getMaximumConsecutive {

    // https://leetcode.cn/problems/maximum-number-of-consecutive-values-you-can-make/solutions/2091580/mei-xiang-ming-bai-yi-zhang-tu-miao-dong-7xlx/
    public int getMaximumConsecutive(int[] coins) {
        int m = 0; // 一開始只能構造出 0
        Arrays.sort(coins);
        for (int c : coins) {
            if (c > m + 1) // coins 已排序，後面沒有比 c 更小的數了
                break; // 無法構造出 m+1，繼續循環沒有意義
            m += c; // 可以構造出區間 [0,m+c] 中的所有整數
        }
        return m + 1; // [0,m] 中一共有 m+1 個整數
    }


}
