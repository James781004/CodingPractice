package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.Arrays;

public class BestTeamScore {

    // https://leetcode.cn/problems/best-team-with-no-conflicts/solutions/2183029/zui-chang-di-zeng-zi-xu-lie-cong-on2-dao-ojqu/
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        var ids = new Integer[n];
        for (int i = 0; i < n; ++i)
            ids[i] = i;
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]);

        for (int i : ids)
            update(ages[i], query(ages[i]) + scores[i]);
        return query(MX);
    }

    private static final int MX = 1000;
    private final int[] t = new int[MX + 1];

    // 返回 max(maxSum[:i+1])
    private int query(int i) {
        int mx = 0;
        for (; i > 0; i &= i - 1)
            mx = Math.max(mx, t[i]);
        return mx;
    }

    // 更新 maxSum[i] 為 mx
    private void update(int i, int mx) {
        for (; i <= MX; i += i & -i)
            t[i] = Math.max(t[i], mx);
    }


}
