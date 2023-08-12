package WeeklyContest;

class Week_242 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1869.Longer%20Contiguous%20Segments%20of%20Ones%20than%20Zeros/README.md
    public boolean checkZeroOnes(String s) {
        int n0 = 0, n1 = 0;
        int t0 = 0, t1 = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '0') {
                ++t0;
                t1 = 0;
            } else {
                ++t1;
                t0 = 0;
            }
            n0 = Math.max(n0, t0);
            n1 = Math.max(n1, t1);
        }
        return n1 > n0;
    }


    // https://leetcode.cn/problems/minimum-speed-to-arrive-on-time/solutions/791152/java-er-fen-fa-zhu-xing-zhu-shi-101ms528-zab8/
    // 二分枚舉速度值，找到滿足條件的最小速度
    public int minSpeedOnTime(int[] dist, double hour) {
        if (dist.length > Math.ceil(hour)) return -1;
        // 搜索邊界
        int left = 1, right = Integer.MAX_VALUE;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 如果以 mid 速度可達，那麼就嘗試減小速度
            if (check(dist, hour, mid)) right = mid;
                // 否則就需要加了
            else left = mid + 1;
        }
        return left;
    }

    private boolean check(int[] dist, double hour, int speed) {
        double cnt = 0.0;
        // 對除了最後一個站點以外的時間進行向上取整累加
        for (int i = 0; i < dist.length - 1; ++i) {
            // 除法的向上取整
            cnt += (dist[i] + speed - 1) / speed;
        }
        // 加上最後一個站點所需的時間
        cnt += (double) dist[dist.length - 1] / speed;
        return cnt <= hour;
    }


    // https://leetcode.cn/problems/jump-game-vii/solutions/791552/javatu-jie-dong-tai-gui-hua-qian-zhui-he-upyr/
    // “動態規劃 + 前綴和”實現
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        // 在dp數組中，認為dp[i] = 1代表能到達i位置，dp[i] = 0認為不能到達i位置
        int[] dp = new int[n];
        int[] pre = new int[n];

        // “下標從0開始的二進制字符串s”一定可以到達位置0
        dp[0] = 1;

        /**
         * 為什麼有這一步預處理呢？因為dp[0] = 1
         * 在0 < i < minJump之前均有dp[i] = 0
         * 遵循pre[i] = dp[i] + pre[i - 1]可以進行該步預處理
         */
        for (int i = 0; i < minJump; i++) {
            pre[i] = 1;
        }

        for (int i = minJump; i < n; i++) {
            if (s.charAt(i) == '0') {
                // 搜索前一步：距離最近的 (right = i - minJump) 到跳的距離最遠的 (left = i - maxJump)
                // 之間是否有存在的能到達的點
                int left = i - maxJump, right = i - minJump;

                // 如果沒有能到達的點，left到right的和就是0，如果有能到達的點，left到right的和就會大於0
                int total = pre[right] - (left <= 0 ? 0 : pre[left - 1]);
                dp[i] = (total > 0) ? 1 : 0;
            }

            // 一邊求dp一邊累加前綴和
            pre[i] = dp[i] + pre[i - 1];
        }
        return dp[n - 1] == 1;
    }

    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1872.Stone%20Game%20VIII/README.md
    // https://leetcode.cn/problems/stone-game-viii/solutions/791601/bao-li-on-2-on-dpyou-hua-xue-dao-liao-by-6peh/
    // “前綴和 + 動態規劃”實現
    // 每次取走最左邊的 x 個石子，把它們的和放回最左邊，前綴和 preSum[x] 不變。
    // 假設 dp[i] 表示當 Alice 選擇 [i, n) 范圍內的某個下標時，Alice 與 Bob 分數的最大差值。
    // 1. 若 Alice 選擇 i，她獲得的分數是 preSum[i]，此時 Bob 會在 [i+1, n] 范圍內選擇，
    //    並且 Bob 也會采取最優策略，此時最大差值為 dp[i+1]。狀態轉移方程：dp[i] = preSum[i] - dp[i+1]。
    // 2. 若 Alice 沒選擇 i，那麼她需要在 [i+1, n) 范圍內找，狀態轉移方程為 dp[i] = dp[i+1]。
    // 最優策略下，dp[i] = max(dp[i+1], preSum[i] - dp[i+1])。
    // 這裡的空間復雜度可以優化為一個變量 f。
    // x 必須大於 1，所以題目即是求 dp[1]。
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        int[] preSum = new int[n];
        preSum[0] = stones[0];
        for (int i = 1; i < n; ++i) {
            preSum[i] = preSum[i - 1] + stones[i];
        }
        int f = preSum[n - 1];
        for (int i = n - 2; i > 0; --i) {
            f = Math.max(f, preSum[i] - f);
        }
        return f;
    }
}



