package EndlessCheng.GenreMenu.DP.Knapsack.Complete;

import java.util.Arrays;

public class LargestNumber {

    // https://leetcode.cn/problems/form-largest-integer-with-digits-that-add-up-to-target/solutions/2811457/ling-shen-san-bu-tao-lu-ji-yi-hua-di-tui-39n8/
    /*
    完全背包
    capacity = target
    w[i] = cost[i]
    恰好裝滿，求"最大價值"

    相比"完全背包 恰好裝滿 求最大價值", 本題的變化是：
    (1) 重新定義 max: 字符串優先比較長度，長度相同按字典序
    (2) 重新定義 +  : 這里的字符串加法是拼接，但是要處理一下 -inf 加上任何數還應該是 -inf
        而常規的int里，-inf + 數(比如1), 還是認為是 -inf， 不用處理。

    為什麽要重新定義加法：
        dfs()返回時，不合法的會添加一個"#"，合法的會添加""，
        合法的長度會小1，

    */
    // 方法1: 記憶化搜索
    public String largestNumber1(int[] cost, int target) {
        int n = cost.length; // 實際上 n 固定為9: 對應1~9數字的"重量"
        String[][] memo = new String[n][target + 1];
        String res = dfs(n - 1, target, cost, memo);
        return compareMax("0", res);
    }

    private String dfs(int i, int c, int[] cost, String[][] memo) {
        if (i < 0) {
            return c == 0 ? "" : "#"; // "#" 表示無窮小
        }
        if (memo[i][c] != null) {
            return memo[i][c];
        }
        if (c < cost[i]) {
            return memo[i][c] = dfs(i - 1, c, cost, memo);
        }
        return memo[i][c] = compareMax(dfs(i - 1, c, cost, memo), add(i + 1, dfs(i, c - cost[i], cost, memo)));
    }

    // 重新定義計算 價值 的加法
    // ”#"的ASCII碼比'0'還小, 用來表示負無窮, "#"加上任何數都等於"#"
    private String add(int x, String s) {
        if (s.equals("#")) {// 負無窮加上任何數都等於負無窮
            return "#";
        }
        return x + s;  //其余情況就是把當前選擇的數字塞到前面已經計算出來的值的前面
    }

    // 優先按長度，長度相同按字典序，返回較大的
    private String compareMax(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 > len2) {
            return s1;
        }
        if (len1 < len2) {
            return s2;
        }
        return s1.compareTo(s2) < 0 ? s2 : s1;
    }

    // 方法2: 翻譯成遞推
    public String largestNumber2(int[] cost, int target) {
        int n = cost.length; // 實際上 n 固定為9: 對應1~9數字的"重量"
        String[][] f = new String[n + 1][target + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(f[i], "#");
        }
        f[0][0] = "";
        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= target; c++) {
                if (c < cost[i]) {
                    f[i + 1][c] = new String(f[i][c]);
                } else {
                    f[i + 1][c] = compareMax(new String(f[i][c]), add(i + 1, new String(f[i + 1][c - cost[i]])));
                }
            }
        }
        String res = f[n][target];
        return compareMax("0", res);
    }

    // 方法3: 空間優化
    public String largestNumber3(int[] cost, int target) {
        int n = cost.length; // 實際上 n 固定為9: 對應1~9數字的"重量"
        String[] f = new String[target + 1];
        Arrays.fill(f, "#");
        f[0] = "";
        for (int i = 0; i < n; i++) {
            for (int c = cost[i]; c <= target; c++) {
                f[c] = compareMax(f[c], add(i + 1, f[c - cost[i]]));
            }
        }
        String res = f[target];
        return compareMax("0", res);
    }


}
