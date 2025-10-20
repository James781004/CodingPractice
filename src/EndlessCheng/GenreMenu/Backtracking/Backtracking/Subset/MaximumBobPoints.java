package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class MaximumBobPoints {

    // https://leetcode.cn/problems/maximum-points-in-an-archery-competition/solutions/1352593/er-jin-zhi-mei-ju-by-endlesscheng-rjul/
    private int maxScore;
    private int bestMask;

    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int n = aliceArrows.length;

        // 初始化
        maxScore = 0;
        bestMask = 0;

        // 遍歷所有可能
        dfs(0, numArrows, 0, 0, aliceArrows);

        // 構建答案
        int arrowsLeft = numArrows;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                ans[i] = aliceArrows[i] + 1;
                arrowsLeft -= ans[i];
            }
        }
        if (arrowsLeft > 0) {
            ans[0] += arrowsLeft;
        }
        return ans;
    }

    // 子集型回溯，選或不選在第 idx 個區域得分，剩余 arrowsLeft 支箭
    private void dfs(int idx, int arrowsLeft, int score, int mask, int[] aliceArrows) {
        if (idx == aliceArrows.length) {
            if (score > maxScore) {
                maxScore = score;
                bestMask = mask;
            }
            return;
        }

        // 不選擇得分
        dfs(idx + 1, arrowsLeft, score, mask, aliceArrows);

        // 選擇得分，需要 aliceArrows[idx] + 1 支箭
        int arrowsNeeded = aliceArrows[idx] + 1;
        if (arrowsLeft >= arrowsNeeded) {
            dfs(idx + 1, arrowsLeft - arrowsNeeded, score + idx, mask | (1 << idx), aliceArrows);
        }
    }

}
