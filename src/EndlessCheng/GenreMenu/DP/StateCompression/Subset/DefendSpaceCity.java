package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.Arrays;

public class DefendSpaceCity {

    // https://leetcode.cn/problems/EJvmW4/solutions/1426981/by-endlesscheng-pk2q/
    public int defendSpaceCity(int[] time, int[] position) {
        var n = Arrays.stream(position).max().getAsInt();
        var m = 1 << Arrays.stream(time).max().getAsInt();
        var rain = new int[n + 1];
        for (var i = 0; i < time.length; i++)
            rain[position[i]] |= 1 << (time[i] - 1);

        var union = new int[m];
        var single = new int[m];
        for (var i = 1; i < m; i++) {
            int lb = i & -i, j = i ^ lb, lb2 = j & -j;
            union[i] = union[j] + (lb == (lb2 >> 1) ? 1 : 3); // lb == (lb2 >> 1) 表示兩個時間點相鄰
            single[i] = single[j] + (lb == (lb2 >> 1) ? 1 : 2); // 遞推
        }

        var f = new int[n + 1][m];
        for (var j = 0; j < m; j++)
            f[0][j] = union[j] + single[((m - 1) ^ j) & rain[0]];
        for (var i = 1; i <= n; ++i) {
            Arrays.fill(f[i], Integer.MAX_VALUE / 2);
            for (var j = 0; j < m; ++j)
                // 枚舉 j 的補集 mask 中的子集 pre
                for (int mask = (m - 1) ^ j, pre = mask; ; pre = (pre - 1) & mask) {
                    var cost = f[i - 1][pre] + union[j] + single[(mask ^ pre) & rain[i]];
                    f[i][j] = Math.min(f[i][j], cost);
                    if (pre == 0) break;
                }
        }
        return f[n][0];
    }


}
