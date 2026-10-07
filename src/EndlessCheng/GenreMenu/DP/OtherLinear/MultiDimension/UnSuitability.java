package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class UnSuitability {

    // https://leetcode.cn/problems/3aqs1c/solutions/1847193/by-endlesscheng-fu9b/
    public int unSuitability(int[] operate) {
        var mx = Arrays.stream(operate).max().orElseThrow() * 2 + 1;
        int[] pre = new int[mx], f = new int[mx];
        Arrays.fill(pre, Integer.MAX_VALUE);
        pre[0] = 0;
        for (var x : operate) {
            Arrays.fill(f, Integer.MAX_VALUE);
            for (var j = 0; j < mx; ++j) {
                var dis = pre[j];
                if (dis == Integer.MAX_VALUE) continue; // 無效的長度（無法組成）
                if (j + x < mx) f[j + x] = Math.min(f[j + x], Math.max(dis, j + x)); // 取正號，折線圖往上走
                if (j >= x) f[j - x] = Math.min(f[j - x], dis); // 取負號，折線圖往下走，且縱坐標沒有小於最下端點的縱坐標
                else f[0] = Math.min(f[0], dis - j + x); // 取負號，折線圖往下走，且縱坐標小於最下端點的縱坐標，那麼產生了一個新的最下端點
            }
            var tmp = pre;
            pre = f;
            f = tmp;
        }
        return Arrays.stream(pre).min().orElseThrow();
    }


}
