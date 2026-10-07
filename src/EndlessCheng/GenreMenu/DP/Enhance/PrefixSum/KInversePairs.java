package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class KInversePairs {

    // https://leetcode.cn/problems/k-inverse-pairs-array/solutions/1095444/dong-tai-gui-hua-qian-zhui-he-you-hua-by-28eb/
    public int kInversePairs(int n, int k) {
        int MOD = (int) 1e9 + 7;
        long[] f = new long[k + 1]; // 添加到數字i時，一共形成j個逆序對的方法數
        f[0] = 1;    // 初始化f[0][0]=1
        // oldSum與newSum存儲上一輪與本輪的f[i-1][x]前綴和:sum[i,j]=sum[j+1]-sum[i]
        long[] sum = new long[k + 2];
        // 遍歷每個數字i∈[1,n]
        for (int i = 1; i <= n; i++) {
            // 先計算上一輪的f[i-1][x]前綴和
            for (int j = 0; j <= k; j++) {
                sum[j + 1] = sum[j] + f[j];  // 由於f[i][j]位於[0,1e9+7]因此sum不會溢出
            }
            // 遍歷逆序對數目j∈[0,k]
            for (int j = 0; j <= k; j++) {
                // f[i][j]=∑f[i-1][x] 其中x∈[j-i+1,j]且x>=0
                f[j] = (sum[j + 1] - sum[Math.max(0, j - i + 1)]) % MOD;    // 計算f[i][j]記得取MOD
            }
        }
        return (int) f[k];
    }

}
