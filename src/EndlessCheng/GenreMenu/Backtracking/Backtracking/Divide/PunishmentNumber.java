package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

public class PunishmentNumber {

    // https://leetcode.cn/problems/find-the-punishment-number-of-an-integer/solutions/2277792/yu-chu-li-hui-su-by-endlesscheng-ro3s/
    private static final int[] PRE_SUM = new int[1001];

    static {
        for (int i = 1; i <= 1000; i++) {
            char[] s = Integer.toString(i * i).toCharArray();
            PRE_SUM[i] = PRE_SUM[i - 1] + (dfs(s, i, 0, 0) ? i * i : 0);
        }
    }

    private static boolean dfs(char[] s, int i, int p, int sum) {
        if (p == s.length) { // 遞歸終點
            return sum == i; // i 符合要求
        }
        int x = 0;
        for (int j = p; j < s.length; j++) { // 枚舉分割出從 s[p] 到 s[j] 的子串
            x = x * 10 + s[j] - '0'; // 子串對應的整數值
            if (dfs(s, i, j + 1, sum + x)) {
                return true;
            }
        }
        return false;
    }

    public int punishmentNumber(int n) {
        return PRE_SUM[n];
    }


}
