package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class bestTeamScore {

    // https://leetcode.cn/problems/best-team-with-no-conflicts/solutions/2183029/zui-chang-di-zeng-zi-xu-lie-cong-on2-dao-ojqu/
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length, ans = 0;
        var ids = new Integer[n];
        for (int i = 0; i < n; i++)
            ids[i] = i;

        // 如果 scores[j]=scores[i]，由於分數相同的，按照年齡從小到大排序，所以 ages[j]≤ages[i]；
        // 如果 scores[j]<scores[i]，根據題目要求，必須滿足 ages[j]≤ages[i]。
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]);

        var f = new int[n + 1]; // 定義 f[i] 表示以 ages[i] 結尾的遞增子序列的最大得分
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++)
                if (ages[ids[j]] <= ages[ids[i]])
                    f[i] = Math.max(f[i], f[j]);
            f[i] += scores[ids[i]];
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }

    // 基於值域計算
    public int bestTeamScore2(int[] scores, int[] ages) {
        int n = scores.length, u = 0, ans = 0;
        var ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
            u = Math.max(u, ages[i]);
        }
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]);

        var maxSum = new int[u + 1];
        for (int i : ids) {
            int age = ages[i];
            for (int j = 1; j <= age; j++)
                maxSum[age] = Math.max(maxSum[age], maxSum[j]);
            maxSum[age] += scores[i];
            ans = Math.max(ans, maxSum[age]);
        }
        return ans;
    }

    // 樹狀數組優化
    public int bestTeamScore3(int[] scores, int[] ages) {
        int n = scores.length;
        var ids = new Integer[n];
        for (int i = 0; i < n; i++)
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
