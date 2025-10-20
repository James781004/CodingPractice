package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

public class MinZeroArray {

    // https://leetcode.cn/problems/zero-array-transformation-iv/solutions/3613907/0-1-bei-bao-pythonjavacgo-by-endlesschen-2y0l/
    public int minZeroArray(int[] nums, int[][] queries) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) { // 每個 nums[i] 單獨計算 0-1 背包
            int x = nums[i];
            if (x == 0) {
                continue;
            }
            boolean[] f = new boolean[x + 1];
            f[0] = true;
            for (int k = 0; k < queries.length; k++) {
                int[] q = queries[k];
                if (i < q[0] || i > q[1]) {
                    continue;
                }
                int val = q[2];
                for (int j = x; j >= val; j--) {
                    f[j] = f[j] || f[j - val];
                }
                if (f[x]) { // 滿足要求
                    ans = Math.max(ans, k + 1);
                    break;
                }
            }
            if (!f[x]) { // 所有操作都執行完了也無法滿足
                return -1;
            }
        }
        return ans;
    }


}
