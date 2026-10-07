package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;

public class LongestSubsequenceDecreasing {

    // https://leetcode.cn/problems/longest-subsequence-with-decreasing-adjacent-difference/solutions/3038930/zhuang-tai-she-ji-you-hua-pythonjavacgo-qy2bu/
    public int longestSubsequence(int[] nums) {
        int mx = Arrays.stream(nums).max().getAsInt();
        int maxD = mx - Arrays.stream(nums).min().getAsInt();
        int[][] f = new int[nums.length][maxD + 2]; // f[i][j] 表示以 nums[i] 結尾的、最後兩個數之差至少為 j 的子序列的最長長度
        int[] last = new int[mx + 1]; // last[x] 表示 x 上一次出現的下標
        Arrays.fill(last, -1);

        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int j = maxD; j >= 0; j--) {
                // x=nums[i] 單獨形成一個子序列，那麼 f[i][j]=1
                // 子序列最後兩數之差嚴格大於 j，也就是至少為 j+1，那麼 f[i][j]=f[i][j+1]
                f[i][j] = Math.max(f[i][j + 1], 1);

                // 嘗試定義 f[i][j] 表示以 x=nums[i] 結尾的、最後兩個數之差為 j 的子序列的最長長度
                // 這意味著子序列倒數第二個數是 x−j 或者 x+j
                // 子序列最後兩數之差恰好等於 j，
                // 那麼子序列倒數第二個數是 x−j 或者 x+j，並且子序列倒數第二第三的兩數之差至少為 j
                // 那麼 f[i][j]=max(f[last[x−j]][j]+1,f[last[x+j]][j]+1)。
                // 其中 last[x] 表示 x 上一次出現的下標
                if (x - j >= 0 && last[x - j] >= 0) {
                    f[i][j] = Math.max(f[i][j], f[last[x - j]][j] + 1);
                }
                if (x + j <= mx && last[x + j] >= 0) {
                    f[i][j] = Math.max(f[i][j], f[last[x + j]][j] + 1);
                }
                ans = Math.max(ans, f[i][j]);
            }
            last[x] = i;
        }
        return ans;
    }


    // last 可以去掉，改成定義 f[x][j] 表示以元素 x 結尾的、最後兩個數之差至少為 j 的子序列的最長長度
    public int longestSubsequence2(int[] nums) {
        int mx = Arrays.stream(nums).max().getAsInt();
        int maxD = mx - Arrays.stream(nums).min().getAsInt();
        int[][] f = new int[mx + 1][maxD + 1];

        int ans = 0;
        for (int x : nums) {
            int fx = 1;
            for (int j = maxD; j >= 0; j--) {
                if (x - j >= 0) {
                    fx = Math.max(fx, f[x - j][j] + 1);
                }
                if (x + j <= mx) {
                    fx = Math.max(fx, f[x + j][j] + 1);
                }
                f[x][j] = fx;
                ans = Math.max(ans, fx);
            }
        }
        return ans;
    }


}
