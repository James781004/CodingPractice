package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.HashMap;
import java.util.Map;

public class MaximumLengthGoodSubsequenceII {

    // https://leetcode.cn/problems/find-the-maximum-length-of-a-good-subsequence-ii/solutions/2805263/dong-tai-gui-hua-you-hua-pythonjavacgo-b-jqn2/
    public int maximumLength(int[] nums, int k) {
        Map<Integer, int[]> fs = new HashMap<>();

        int[][] records = new int[k + 1][3];

        for (int x : nums) {
            // 取出以x結尾的長度不超過j的最大子序列長度是多少
            int[] f = fs.computeIfAbsent(x, i -> new int[k + 1]);

            for (int j = k; j >= 0; j--) {
                f[j]++; //選當前x，並且當前x與前一個相同 或者作為子序列的第一個數;

                if (j > 0) {
                    // 考慮不同
                    int mx = records[j - 1][0], mx2 = records[j - 1][1], num = records[j - 1][2];

                    f[j] = Math.max(f[j], (x != num ? mx : mx2) + 1);
                }

                // 更新
                int v = f[j]; // 以x結尾相鄰不同對數小於等於j的最大序列長度
                int[] record = records[j];

                if (v > record[0]) {
                    if (x != record[2]) {
                        // 之前相鄰對數不超過j的最大長度變次小長度。
                        record[1] = record[0];
                        record[2] = x;
                    }
                    record[0] = v;
                } else if (x != record[2] && v > record[1]) {
                    // 更新次小
                    record[1] = v;
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
