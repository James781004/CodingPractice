package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_451 {

    // https://leetcode.cn/problems/find-minimum-log-transportation-cost/solutions/3685483/o1-shu-xue-gong-shi-yi-xing-dai-ma-pytho-hxo6/
    public long minCuttingCost(int n, int m, int k) {
        return Math.max((long) k * (Math.max(n, m) - k), 0);
    }


    // https://leetcode.cn/problems/resulting-string-after-adjacent-removals/solutions/3685478/zhan-lin-xiang-xiao-chu-fu-ti-dan-python-t1bt/
    public String resultingString(String s) {
        char[] st = new char[s.length()];
        int top = -1; // 棧頂下標

        // 由於每次都是消除最左邊的字符，我們從左到右遍歷 s，同時用棧記錄字符
        for (char b : s.toCharArray()) {
            // 如果棧不為空，且 s[i] 與棧頂是「連續」的，那麼立刻消除，彈出棧頂
            if (top >= 0 && isConsecutive(b, st[top])) {
                top--; // 出棧
            } else {
                st[++top] = b; // 否則把 s[i] 入棧
            }
        }

        // 最後答案就是棧中剩余字符，即棧底到棧頂
        return new String(st, 0, top + 1);
    }


    private boolean isConsecutive(char x, char y) {
        int d = Math.abs(x - y);
        return d == 1 || d == 25; // 連續（包括 'a' 和 'z'）
    }


    // https://leetcode.cn/problems/maximum-profit-from-trading-stocks-with-discounts/solutions/3685504/shu-shang-bei-bao-zhuang-tai-ji-dppython-2q7b/
    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : hierarchy) {
            g[e[0] - 1].add(e[1] - 1);
        }

        int[][] f0 = dfs(0, g, present, future, budget);
        return f0[budget][0];
    }

    private int[][] dfs(int x, List<Integer>[] g, int[] present, int[] future, int budget) {
        // 計算從 x 的所有兒子子樹 y 中，能得到的最大利潤之和
        int[][] subF = new int[budget + 1][2];
        for (int y : g[x]) {
            int[][] fy = dfs(y, g, present, future, budget);
            for (int j = budget; j >= 0; j--) {
                // 枚舉子樹 y 的預算為 jy
                // 當作一個體積為 jy，價值為 fy[jy][k] 的物品
                for (int jy = 0; jy <= j; jy++) {
                    for (int k = 0; k < 2; k++) {
                        subF[j][k] = Math.max(subF[j][k], subF[j - jy][k] + fy[jy][k]);
                    }
                }
            }
        }

        int[][] f = new int[budget + 1][2];
        for (int j = 0; j <= budget; j++) {
            for (int k = 0; k < 2; k++) {
                int cost = present[x] / (k + 1);
                if (j >= cost) {
                    // 不買 x，轉移來源是 subF[j][0]
                    // 買 x，轉移來源為 subF[j-cost][1]，因為對於子樹來說，父節點一定買
                    f[j][k] = Math.max(subF[j][0], subF[j - cost][1] + future[x] - cost);
                } else { // 只能不買 x
                    f[j][k] = subF[j][0];
                }
            }
        }
        return f;
    }


    // https://leetcode.cn/problems/lexicographically-smallest-string-after-adjacent-removals/solutions/3685460/zi-fu-xiao-xiao-le-qu-jian-dp-xian-xing-xmaqk/
    private int[][] memoEmpty;
    private String[] memoDfs;
    private char[] s;

    public String lexicographicallySmallestString(String S) {
        s = S.toCharArray();
        int n = s.length;
        memoEmpty = new int[n][n];
        for (int[] row : memoEmpty) {
            Arrays.fill(row, -1); // -1 表示尚未計算
        }
        memoDfs = new String[n];
        return dfs(0);
    }

//    private boolean isConsecutive(char x, char y) {
//        int d = Math.abs(x - y);
//        return d == 1 || d == 25;
//    }

    private int canBeEmpty(int i, int j) {
        if (i > j) { // 空串
            return 1;
        }
        if (memoEmpty[i][j] != -1) {
            return memoEmpty[i][j];
        }

        // 性質 2
        if (isConsecutive(s[i], s[j]) && canBeEmpty(i + 1, j - 1) > 0) {
            return memoEmpty[i][j] = 1;
        }

        // 性質 3
        for (int k = i + 1; k < j; k += 2) {
            if (canBeEmpty(i, k) > 0 && canBeEmpty(k + 1, j) > 0) {
                return memoEmpty[i][j] = 1;
            }
        }

        return memoEmpty[i][j] = 0;
    }

    private String dfs(int i) {
        if (i == s.length) {
            return "";
        }
        if (memoDfs[i] != null) {
            return memoDfs[i];
        }

        // 包含 s[i]
        String res = s[i] + dfs(i + 1);

        // 不包含 s[i]，注意 s[i] 不能單獨消除，必須和其他字符一起消除
        for (int j = i + 1; j < s.length; j += 2) {
            if (canBeEmpty(i, j) > 0) { // 消除 s[i] 到 s[j]
                String t = dfs(j + 1);
                if (t.compareTo(res) < 0) {
                    res = t;
                }
            }
        }

        return memoDfs[i] = res;
    }


}









