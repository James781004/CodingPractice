package EndlessCheng.GenreMenu.DP.Divide.Fixed;

public class MaxSum {

    // https://leetcode.cn/problems/sum-of-k-subarrays-with-length-at-least-m/solutions/3591733/hua-fen-xing-dp-qian-zhui-he-shi-zi-bian-3k0w/
    public int maxSum(int[] nums, int k, int m) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i]; // nums 的前綴和
        }

        // f[i][j] 表示從長為 j 的前綴 nums[0] 到 nums[j−1] 取出 i 個連續子數組所得到的最大和
        int[][] f = new int[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            f[i][i * m - 1] = Integer.MIN_VALUE;
            int mx = Integer.MIN_VALUE;
            // 左右兩邊留出足夠空間給其他子數組
            for (int j = i * m; j <= n - (k - i) * m; j++) {
                // mx 表示最大的 f[i-1][L]-s[L]，其中 L 在區間 [(i-1)*m, j-m] 中
                mx = Math.max(mx, f[i - 1][j - m] - s[j - m]);
                f[i][j] = Math.max(f[i][j - 1], mx + s[j]); // 不選 vs 選
            }
        }
        return f[k][n];
    }

    public int maxSum2(int[] nums, int k, int m) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }

        int[] f = new int[n + 1];
        int[] g = new int[n + 1];
        for (int i = 1; i <= k; i++) {
            g[i * m - 1] = Integer.MIN_VALUE;
            int mx = Integer.MIN_VALUE;
            for (int j = i * m; j <= n - (k - i) * m; j++) {
                mx = Math.max(mx, f[j - m] - s[j - m]);
                g[j] = Math.max(g[j - 1], mx + s[j]);
            }
            int[] tmp = f;
            f = g;
            g = tmp;
        }
        return f[n];
    }


}
