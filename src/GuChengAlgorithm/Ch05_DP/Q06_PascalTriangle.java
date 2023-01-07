package GuChengAlgorithm.Ch05_DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Q06_PascalTriangle {
    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_9
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        if (numRows > 0) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            res.add(list);
        }

        for (int i = 1; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            for (int j = 0; j < i; j++) {
                list.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
            }
            list.add(1);
            res.add(list);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_19
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        if (rowIndex >= 0) res.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            List<Integer> pre = new ArrayList<>(res);
            for (int j = 1; j < i; j++) {
                res.set(j, pre.get(j) + pre.get(j - 1));
            }
            res.add(1);
        }
        return res;
    }

    public List<Integer> getRow2(int n) {
        List<Integer> row = new ArrayList<>();
        Integer[][] memo = new Integer[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            row.add(nCk(n, i, memo));
        }
        return row;
    }

    private Integer nCk(int n, int k, Integer[][] memo) {
        if (k == 0 || n == k) return 1;
        if (memo[n][k] != n) return memo[n][k];
        int res = nCk(n - 1, k - 1, memo) + nCk(n - 1, k, memo);
        return memo[n][k] = res;
    }


    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_66
    int MOD = (int) 1e9 + 7;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        Long[][] memo = new Long[n][n];
        List<Integer> l = new ArrayList<>();
        for (int i : nums) l.add(i);
        long res = (MOD + totalWays(l, memo) - 1) % MOD;
        return (int) res;
    }

    private long totalWays(List<Integer> t, Long[][] memo) {
        if (t.size() == 0) return 1;
        List<Integer> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        int head = t.get(0);
        for (int i = 1; i < t.size(); i++) {
            if (t.get(i) > head) r.add(t.get(i));
            else l.add(t.get(i));
        }
        long cnt = (totalWays(l, memo) * totalWays(r, memo)) % MOD;
        long comb = nCk(t.size() - 1, l.size(), memo);
        return (comb * cnt) % MOD;
    }

    private long nCk(int n, int k, Long[][] memo) {
        if (k == 0 || n == k) return 1;
        if (memo[n][k] != null) return memo[n][k];
        return memo[n][k] = (nCk(n - 1, k - 1, memo) + nCk(n - 1, k, memo)) % MOD;
    }


    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_73
    double[] dp = new double[100];

    public double getProbability(int[] balls) {
        dp[0] = 1;
        int sum = Arrays.stream(balls).sum();
        for (int i = 1; i < 100; i++) {
            dp[i] = dp[i - 1] * i;  // nCk模版
        }
        double validNumber = dfs(0, 0, 0, 0, balls, 0);
        double totalNumber = myNCK(sum, sum / 2);
        return validNumber / totalNumber;
    }

    private double dfs(int count1, int count2, int sum1, int sum2, int[] balls, int i) {
        if (i == balls.length) return (sum1 == sum2 && count1 == count2) ? 1 : 0;
        double res = dfs(count1 + 1, count2, sum1 + balls[i], sum2, balls, i + 1);  // 全box1
        res += dfs(count1, count2 + 1, sum1, sum2 + balls[i], balls, i + 1);  // 全box2
        for (int j = 0; j < balls[i]; j++) {  // 同種顏色所有球分開，box1 j個，box2 (n-j)個
            res += myNCK(balls[i], j) *
                    dfs(count1 + 1, count2 + 1, sum1 + j, sum2 + balls[i] - j, balls, i + 1);
        }
        return res;
    }

    private double myNCK(int a, int b) {
        return dp[a] / dp[b] / dp[a - b];
    }


    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_58
    int[][] pathDP = new int[32][32];

    public String kthSmallestPath(int[] d, int k) {
        makeTriangle(pathDP);
        return getPath(d[0], d[1], k);
    }

    public String getPath(int r, int c, int k) {
        if (r == 0 && c == 0) return "";
        if (r == 0) return "H" + getPath(r, c - 1, k);
        else if (c == 0) return "V" + getPath(r - 1, c, k);
        else if (k <= pathDP[r + c - 1][c - 1]) return "H" + getPath(r, c - 1, k);
        else return "V" + getPath(r - 1, c, k - pathDP[r + c - 1][c - 1]);
    }

    public void makeTriangle(int[][] dp) {
        dp[0][0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
    }


    // https://docs.google.com/presentation/d/1OUJVix1iZlXr-H8gyp3l_E5MGwgvGSlLl4-By_5Ryy4/edit#slide=id.ga10bd2d508_0_87
    int M = (int) 1e9 + 7;
    int[][] memo;

    public int numPermDISequence(String S) {
        int n = S.length();
        memo = new int[n + 1][n + 1];
        return (int) helper(S, new HashMap<>());
    }

    private long helper(String s, HashMap<String, Long> map) {
        if (s.equals("")) return 1;
        if (map.containsKey(s)) return map.get(s);
        long res = 0;
        int n = s.length();
        if (s.charAt(0) == 'D') res = (res + helper(s.substring(1), map)) % M;
        if (s.charAt(n - 1) == 'I') res = (res + helper(s.substring(0, n - 1), map)) % M;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i - 1) == 'I' && s.charAt(i) == 'D') {
                long left = helper(s.substring(0, i - 1), map);
                long right = helper(s.substring(i + 1), map);
                res += (((left * right) % M) * getNCK(n, i)) % M;
                res %= M;
            }
        }
        map.put(s, res);
        return res;
    }

    private int getNCK(int n, int k) {
        if (k == 0 || n == k) return 1;
        if (memo[n][k] != 0) return memo[n][k];
        return memo[n][k] = (getNCK(n - 1, k) + getNCK(n - 1, k - 1)) % M;
    }


}
