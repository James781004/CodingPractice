package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q15_EggDrop {
//    https://www.cnblogs.com/labuladong/p/13937987.html

//    https://leetcode.cn/problems/super-egg-drop/
//    887. 雞蛋掉落
//    給你 k 枚相同的雞蛋，並可以使用一棟從第 1 層到第 n 層共有 n 層樓的建築。
//
//    已知存在樓層 f ，滿足 0 <= f <= n ，任何從 高於 f 的樓層落下的雞蛋都會碎，從 f 樓層或比它低的樓層落下的雞蛋都不會破。
//
//    每次操作，你可以取一枚沒有碎的雞蛋並把它從任一樓層 x 扔下（滿足 1 <= x <= n）。如果雞蛋碎了，你就不能再次使用它。
//    如果某枚雞蛋扔下後沒有摔碎，則可以在之後的操作中 重復使用 這枚雞蛋。
//
//    請你計算並返回要確定 f 確切的值 的 最小操作次數 是多少？

    // https://leetcode.cn/problems/super-egg-drop/solution/887-by-ikaruga/
    public int superEggDrop(int K, int N) {
        // m 最多不會超過 N 次（線性掃描）
        int[][] dp = new int[K + 1][N + 1];
        // base case:
        // dp[0][..] = 0
        // dp[..][0] = 0
        // Java 默認初始化數組都為 0
        int m = 0;
        while (dp[K][m] < N) {
            m++;
            for (int k = 1; k <= K; k++)
                // 1、無論你在哪層樓扔雞蛋，雞蛋只可能摔碎或者沒摔碎，碎了的話就測樓下，沒碎的話就測樓上。
                // 2、無論你上樓還是下樓，總的樓層數 = 樓上的樓層數 + 樓下的樓層數 + 1（當前這層樓）。
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
        }
        return m;
    }


    public int superEggDropMemo(int K, int N) {
        int[][] memo = new int[K][N];
        for (int[] m : memo) {
            Arrays.fill(m, -666);
        }
        return process(K, N, memo);
    }

    private int process(int K, int N, int[][] memo) {
        if (K == 1) return N;
        if (N == 0) return 0;
        if (memo[K][N] != -666) return memo[K][N];

        int res = Integer.MAX_VALUE;

        // 用二分搜索代替線性搜索
        int lo = 1, hi = N;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int broken = process(K - 1, mid - 1, memo); // 碎
            int notBroken = process(K, N - mid, memo); // 沒碎
            if (broken > notBroken) {
                hi = mid - 1;
                res = Math.min(res, broken + 1);
            } else {
                lo = mid + 1;
                res = Math.min(res, notBroken + 1);
            }
        }

        memo[K][N] = res;
        return res;
    }
}
