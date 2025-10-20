package EndlessCheng.GenreMenu.Backtracking.NormalTree.Multiply;

import java.util.List;

public class GetMaxFunctionValue {

    // https://leetcode.cn/problems/maximize-value-of-function-in-a-ball-passing-game/solutions/2413298/shu-shang-bei-zeng-by-endlesscheng-xvsv/
    public long getMaxFunctionValue(List<Integer> receiver, long K) {
        int n = receiver.size();
        int m = 64 - Long.numberOfLeadingZeros(K); // K 的二進制長度
        var pa = new int[m][n];
        var sum = new long[m][n];
        for (int i = 0; i < n; i++) {
            pa[0][i] = receiver.get(i);
            sum[0][i] = receiver.get(i);
        }
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[i][x];
                pa[i + 1][x] = pa[i][p];
                sum[i + 1][x] = sum[i][x] + sum[i][p]; // 合並節點值之和
            }
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            long s = i;
            int x = i;
            for (long k = K; k > 0; k &= k - 1) {
                int ctz = Long.numberOfTrailingZeros(k);
                s += sum[ctz][x];
                x = pa[ctz][x];
            }
            ans = Math.max(ans, s);
        }
        return ans;
    }


}
