package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class LeastOpsExpressTarget {

    // https://leetcode.cn/problems/least-operators-to-express-number/solutions/99170/yong-shi-ji-bai-100-by-gskfid/
    public int leastOpsExpressTarget(int x, int target) {
        int[] nums = new int[32];
        int len = 0;
        for (; target > 0; len++) {
            nums[len] = target % x;
            target = target / x;
        }
        // 表示更高一位為轉換為反向表示時需要增加的運算符數目
        int lastReverse = Integer.MAX_VALUE;
        int ans = -1;
        for (int i = len - 1; i >= 0; i--) {
            // 個位時為加2
            int m = i == 0 ? 2 : i;
            // 正向表示（例如3*5*5*5表示為5*5*5+5*5*5）所需要的數目
            int forward = nums[i] * m;
            // 反向表示（例如3*5*5*5表示為5*5*5*5-5*5*5-5*5*5-5*5*5）,反向表示需要考慮向高位進1
            int reverse = (x - nums[i]) * m + Math.min(lastReverse - i - 1, i + 1);
            ans += Math.min(forward, reverse);
            // 如果當前取得就是反向表示，那麼後面再考慮高一位轉換成反向表示需要增加的運算為0
            lastReverse = forward >= reverse ? 0 : reverse - forward;
        }
        return ans;
    }


}
