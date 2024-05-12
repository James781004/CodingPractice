package WeeklyContest;

import java.util.Arrays;
import java.util.List;

public class Week_397 {
    // https://leetcode.cn/problems/permutation-difference-between-two-strings/solutions/2774884/jian-ji-xie-fa-pythonjavacgo-by-endlessc-tshq/
    public int findPermutationDifference(String s, String t) {
        int[] pos = new int[26];
        for (int i = 0; i < s.length(); i++) {
            pos[s.charAt(i) - 'a'] = i;
        }
        int ans = 0;
        for (int i = 0; i < t.length(); i++) {
            ans += Math.abs(i - pos[t.charAt(i) - 'a']);
        }
        return ans;
    }


    // https://leetcode.cn/problems/taking-maximum-energy-from-the-mystic-dungeon/solutions/2774844/o1-kong-jian-zuo-fa-pythonjavacgo-by-end-8rad/
    public int maximumEnergy(int[] energy, int k) {
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            int s = 0;
            for (int j = i + (energy.length - i - 1) / k * k; j >= 0; j -= k) {
                s += energy[j];
                ans = Math.max(ans, s);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-difference-score-in-a-grid/solutions/2774823/nao-jin-ji-zhuan-wan-dppythonjavacgo-by-swux7/
    public int maxScore(List<List<Integer>> grid) {
        int ans = Integer.MIN_VALUE;
        int n = grid.get(0).size();
        int[] colMin = new int[n];
        Arrays.fill(colMin, Integer.MAX_VALUE);
        for (List<Integer> row : grid) {
            int preMin = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                int x = row.get(j);
                ans = Math.max(ans, x - Math.min(preMin, colMin[j]));
                colMin[j] = Math.min(colMin[j], x);
                preMin = Math.min(preMin, colMin[j]);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-minimum-cost-array-permutation/solutions/2775272/zhuang-ya-dpcong-ji-yi-hua-sou-suo-dao-d-s9t5/
    public int[] findPermutation(int[] a) {
        int n = a.length;
        int[][] memo = new int[1 << n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        int[] ans = new int[n];
        makeAns(1, 0, a, memo, ans, 0);
        return ans;
    }

    private int dfs(int s, int j, int[] a, int[][] memo) {
        if (s == (1 << a.length) - 1) {
            return Math.abs(j - a[0]);
        }
        if (memo[s][j] != -1) { // 之前計算過
            return memo[s][j];
        }
        int res = Integer.MAX_VALUE;
        for (int k = 1; k < a.length; k++) {
            if ((s >> k & 1) == 0) { // k 之前沒填過
                res = Math.min(res, dfs(s | 1 << k, k, a, memo) + Math.abs(j - a[k]));
            }
        }
        memo[s][j] = res; // 記憶化
        return res;
    }

    private void makeAns(int s, int j, int[] a, int[][] memo, int[] ans, int i) {
        ans[i] = j;
        if (s == (1 << a.length) - 1) {
            return;
        }
        int finalRes = dfs(s, j, a, memo);
        for (int k = 1; k < a.length; k++) {
            if ((s >> k & 1) == 0 && dfs(s | 1 << k, k, a, memo) + Math.abs(j - a[k]) == finalRes) {
                makeAns(s | 1 << k, k, a, memo, ans, i + 1);
                break;
            }
        }
    }

}


