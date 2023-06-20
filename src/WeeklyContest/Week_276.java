package WeeklyContest;

import java.util.Arrays;

class Week_276 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2138.Divide%20a%20String%20Into%20Groups%20of%20Size%20k/README.md
    public String[] divideString(String s, int k, char fill) {
        int n = s.length();
        String[] ans = new String[(n + k - 1 / k)];
        if (n % k != 0) {
//            s += String.valueOf(fill).repeat(k - n % k);
            for (int i = 0; i < k - n % k; i++) {
                s += String.valueOf(fill);
            }
        }

        for (int i = 0; i < ans.length; ++i) {
            ans[i] = s.substring(i * k, (i + 1) * k);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2139.Minimum%20Moves%20to%20Reach%20Target%20Score/README.md
    public int minMoves(int target, int maxDoubles) {
        if (target == 1) {
            return 0;
        }
        if (maxDoubles == 0) {
            return target - 1;
        }

        // 貪心：可以double就先double，否則就選+1操作
        if (target % 2 == 0 && maxDoubles > 0) {
            return 1 + minMoves(target >> 1, maxDoubles - 1);
        }
        return 1 + minMoves(target - 1, maxDoubles);
    }


    // https://leetcode.cn/problems/solving-questions-with-brainpower/solution/dao-xu-dp-by-endlesscheng-2qkc/
    // 解法一：倒序 DP（填表法）
    // 填表法適用於大多數 DP：通過當前狀態所依賴的狀態，來計算當前狀態。
    // 設有 n 個問題，定義 f[i] 表示解決區間 [i,n−1] 內的問題可以獲得的最高分數。
    // 倒序遍歷問題列表，對於第 i 個問題，我們有兩種決策：跳過或解決。
    // 若跳過，則有 f[i]=f[i+1]。
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] f = new long[n + 1]; // 解決區間 [i,n−1] 內的問題可以獲得的最高分數。
        for (int i = n - 1; i >= 0; i--) {
            int[] q = questions[i];
            int j = i + q[1] + 1;
            // 兩種決策：跳過或解決
            // 若跳過，則有 f[i]=f[i+1]。
            // 若解決，則需要跳過後續 brainpower[i] 個問題。
            // 下一個可行座標為 i + q[1] + 1，超過尾部n就不能選了
            f[i] = Math.max(f[i + 1], q[0] + (j < n ? f[j] : 0));
        }
        return f[0];
    }


    // 解法二：正序 DP（刷表法）
    // 用當前狀態，去更新當前狀態所影響的狀態
    public long mostPoints2(int[][] questions) {
        int n = questions.length;
        long[] f = new long[n + 1]; // 解決區間 [i,n−1] 內的問題可以獲得的最高分數。
        for (int i = 0; i < n; i++) {
            // 對於問題 i，若跳過，則可以更新 f[i+1]=max(f[i+1],f[i])。
            f[i + 1] = Math.max(f[i + 1], f[i]);

            // 若不跳過，記 j=i+brainpower[i]+1，
            // 則可以更新 f[j]=max(f[j],f[i]+point[i])
            int[] q = questions[i];
            int j = Math.min(i + q[1] + 1, n);
            f[j] = Math.max(f[j], f[i] + q[0]);
        }
        return f[n];
    }


    // https://leetcode.cn/problems/maximum-running-time-of-n-computers/solution/liang-chong-jie-fa-er-fen-da-an-pai-xu-t-grd8/
    public long maxRunTime(int n, int[] batteries) {
        long tot = 0L;
        for (int b : batteries) {
            tot += b; // 理論上總電量至多可以供電tot / n 分鐘
        }
        long l = 0L, r = tot / n;
        while (l < r) {
            long x = (l + r + 1) / 2; // x 可理解為預期讓n台電腦運行的分鐘數
            long sum = 0L;

            // 電量不小於 x 的電池，可以讓其給一台電腦供電 x 分鐘。
            // 由於一個電池不能同時給多台電腦供電，
            // 因此該電池若給一台電腦供電 x 分鐘，那它就不能用於其他電腦了
            for (int b : batteries) {
                sum += Math.min(b, x); // 當前可行總供電時間
            }

            // 如果 n 台電腦運行 x 分鐘的消耗大於當前可行總供電時間，必須縮小 x
            if (n * x <= sum) {
                l = x;
            } else {
                r = x - 1;
            }
        }
        return l;
    }


    public long maxRunTime2(int n, int[] batteries) {
        Arrays.sort(batteries);
        long sum = 0L;
        for (int b : batteries) {
            sum += b; // 理論上總電量至多可以供電sum / n 分鐘
        }

        // 對電池電量排序，然後從電量最大的電池開始遍歷
        // 若該電池電量超過 x (即 sum / n)，則將其供給一台電腦，問題縮減為 n−1 台電腦的子問題。
        // 若該電池電量不超過 x，則其余電池的電量均不超過 x，此時有 n * x = n * (sum / n) ≤ sum。
        // 根據解法一的結論，這些電池可以給 n 台電腦供電 x 分鐘
        // 由於隨著問題規模減小，x 不會增加，因此若遍歷到一個電量不超過 x 的電池時，可以直接返回 x 作為答案。
        // https://leetcode.cn/problems/maximum-running-time-of-n-computers/solution/java-tan-xin-by-feilue-hvjw/
        // 總電量 / n = 每台電腦可以運行的最大時間 = 每台電腦需要的電量
        // 若當前最大電池電量 < 每台電腦需要的電量，說明每台電腦必定可以通過使用不同的電池運行 (總電量 / n) 的時間
        // 若當前最大電池電量 >= 每台電腦可以運行的最大時間，說明這個電池足以支持一台電腦一直運行，
        // 可以將一台電腦和這個電池從總電量中刪去，再計算剩余的電腦和電量
        for (int i = batteries.length - 1; ; --i) {
            if (batteries[i] <= sum / n) {
                return sum / n;
            }
            sum -= batteries[i];
            --n;
        }
    }
}

