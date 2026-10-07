package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

public class ResultArray {

    // https://leetcode.cn/problems/find-x-value-of-array-i/solutions/3656580/zi-shu-zu-dp-shua-biao-fa-pythonjavacgo-8lonk/
    public long[] resultArray(int[] nums, int k) {
        int n = nums.length;
        long[] ans = new long[k];
        int[][] f = new int[n + 1][k]; // 定義 f[i+1][x] 表示右端點為 i 的、元素積模 k 等於 x 的子數組的個數
        for (int i = 0; i < n; i++) {
            int v = nums[i] % k; // 避免下面乘法溢出
            f[i + 1][v] = 1;
            for (int y = 0; y < k; y++) {
                f[i + 1][y * v % k] += f[i][y]; // 刷表法
            }
            for (int x = 0; x < k; x++) {
                ans[x] += f[i + 1][x];
            }
        }
        return ans;
    }


    public long[] resultArray2(int[] nums, int k) {
        long[] ans = new long[k];
        int[] f = new int[k];
        for (int v : nums) {
            v %= k; // 避免下面乘法溢出
            int[] nf = new int[k];
            nf[v] = 1;
            for (int y = 0; y < k; y++) {
                nf[y * v % k] += f[y];
            }
            f = nf;
            for (int x = 0; x < k; x++) {
                ans[x] += f[x];
            }
        }
        return ans;
    }

}
