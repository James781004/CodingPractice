package EndlessCheng.GenreMenu.DP.Probability;

public class SoupServings {

    // https://leetcode.cn/problems/soup-servings/solutions/3745893/gai-lu-dppythonjavacgo-by-endlesscheng-25av/
    public double soupServings(int n) {
        if (n >= 4451) {
            return 1;
        }

        // 由於本題取出的毫升數均為 25 的倍數，
        // 我們可以把 0,25,50,75,100 均除以 25，得到 0,1,2,3,4，
        // 相當於份數每份 25 毫升。
        // （最後剩余的不足 25 的量視作一份，所以是上取整）
        n = (n + 24) / 25;
        double[][] memo = new double[n + 1][n + 1];
        return dfs(n, n, memo);
    }

    // dfs(a,b) 表示兩種湯的剩余量分別為 a 毫升和 b 毫升時，目標事件發生的概率
    private double dfs(int a, int b, double[][] memo) {
        if (a <= 0 && b <= 0) {
            return 0.5; // 如果 a≤0 且 b≤0，目標事件發生的概率為 1。但根據題目要求，實際取一半
        }
        if (a <= 0) { // 此時 b > 0，目標事件發生的概率為 1。
            return 1;
        }
        if (b <= 0) { // 此時 a > 0，目標事件發生的概率為 0。
            return 0;
        }
        if (memo[a][b] == 0) { // 沒有計算過
            memo[a][b] = (dfs(a - 4, b, memo) + dfs(a - 3, b - 1, memo) + dfs(a - 2, b - 2, memo) + dfs(a - 1, b - 3, memo)) / 4;
        }
        return memo[a][b];
    }

}
