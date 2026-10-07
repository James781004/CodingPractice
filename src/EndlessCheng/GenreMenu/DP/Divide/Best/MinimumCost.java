package EndlessCheng.GenreMenu.DP.Divide.Best;

public class MinimumCost {

    // https://leetcode.cn/problems/minimum-cost-to-divide-array-into-subarrays/solutions/3633352/hua-fen-xing-dp-shi-zi-bian-xing-pythonj-cwi9/
    public long minimumCost(int[] nums, int[] cost, int k) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + cost[i]; // cost 的前綴和
        }

        long[] f = new long[n + 1];
        int sumNum = 0;
        for (int i = 1; i <= n; i++) { // 注意這裡 i 從 1 開始，下面不用把 i 加一
            sumNum += nums[i - 1];
            f[i] = Long.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                f[i] = Math.min(f[i], f[j] + (long) sumNum * (s[i] - s[j]) + k * (s[n] - s[j]));
            }
        }
        return f[n];
    }


}
