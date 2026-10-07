package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.HashMap;
import java.util.Map;

public class MaximumLength {

    // https://leetcode.cn/problems/find-the-maximum-length-of-a-good-subsequence-ii/solutions/2805263/dong-tai-gui-hua-you-hua-pythonjavacgo-b-jqn2/
    public int maximumLength(int[] nums, int k) {
        Map<Integer, int[]> fs = new HashMap<>();
        int[][] records = new int[k + 1][3];
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, i -> new int[k + 1]);
            for (int j = k; j >= 0; j--) {
                f[j]++;
                if (j > 0) {
                    int mx = records[j - 1][0], mx2 = records[j - 1][1], num = records[j - 1][2];
                    f[j] = Math.max(f[j], (x != num ? mx : mx2) + 1);
                }

                // records[j] 維護 fs[.][j] 的 mx, mx2, num
                int v = f[j];
                int[] p = records[j];
                if (v > p[0]) {
                    if (x != p[2]) {
                        p[2] = x;
                        p[1] = p[0];
                    }
                    p[0] = v;
                } else if (x != p[2] && v > p[1]) {
                    p[1] = v;
                }
            }
        }
        return records[k][0];
    }


    public int maximumLength2(int[] nums, int k) {
        Map<Integer, int[]> fs = new HashMap<>();
        int[] mx = new int[k + 2];
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, i -> new int[k + 1]);
            for (int j = k; j >= 0; j--) {
                f[j] = Math.max(f[j], mx[j]) + 1;
                mx[j + 1] = Math.max(mx[j + 1], f[j]);
            }
        }
        return mx[k + 1];
    }


}
