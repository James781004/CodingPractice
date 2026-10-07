package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

import java.util.Arrays;

public class BestTeamScore {

    // https://leetcode.cn/problems/best-team-with-no-conflicts/solutions/2183029/zui-chang-di-zeng-zi-xu-lie-cong-on2-dao-ojqu/
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length, ans = 0;
        var ids = new Integer[n];
        for (int i = 0; i < n; ++i)
            ids[i] = i;
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]);

        var f = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j)
                if (ages[ids[j]] <= ages[ids[i]])
                    f[i] = Math.max(f[i], f[j]);
            f[i] += scores[ids[i]];
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }

}
