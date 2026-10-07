package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountSteppingNumbers {

    // https://leetcode.cn/problems/count-stepping-numbers-in-range/solutions/2364624/shu-wei-dp-tong-yong-mo-ban-by-endlessch-h8fj/
    private static final int MOD = (int) 1e9 + 7;

    public int countSteppingNumbers(String low, String high) {
        return (calc(high) - calc(low) + MOD + (valid(low) ? 1 : 0)) % MOD; // +MOD 防止算出負數
    }

    private char s[];
    private int memo[][];

    // 定義 calc(n) 表示不超過 n 的步進數字數目
    private int calc(String s) {
        this.s = s.toCharArray();
        int m = s.length();
        memo = new int[m][10];
        for (int i = 0; i < m; i++)
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        return f(0, 0, true, false);
    }

    // 定義 f(i,pre,isLimit,isNum) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // pre 表示上一個數位的值。如果 isNum 為 false，可以忽略 pre。
    // isLimit 表示當前是否受到了 n 的約束（注意要構造的數字不能超過 n）。
    // 若為真，則第 i 位填入的數字至多為 s[i]，否則可以是 9。
    // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
    // 例如 n=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // isNum 表示 i 前面的數位是否填了數字。
    // 若為假，則當前位可以跳過（不填數字），或者要填入的數字至少為 1；
    // 若為真，則要填入的數字可以從 0 開始。
    // 例如 n=123，在 i=0 時跳過的話，相當於後面要構造的是一個 99 以內的數字了，
    // 如果 i=1 不跳過，那麼相當於構造一個 10 到 99 的兩位數，
    // 如果 i=1 也跳過，相當於構造的是一個 9 以內的數字。
    private int f(int i, int pre, boolean isLimit, boolean isNum) {
        if (i == s.length)
            return isNum ? 1 : 0; // isNum 為 true 表示得到了一個合法數字
        if (!isLimit && isNum && memo[i][pre] != -1)
            return memo[i][pre];
        int res = 0;
        if (!isNum) // 可以跳過當前數位
            res = f(i + 1, pre, false, false);
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的數字都和 s 的一樣，那麼這一位至多填數字 s[i]（否則就超過 s 啦）
        for (int d = isNum ? 0 : 1; d <= up; d++) // 枚舉要填入的數字 d
            if (!isNum || Math.abs(d - pre) == 1) // 第一位數字隨便填，其余必須相差 1
                res = (res + f(i + 1, d, isLimit && d == up, true)) % MOD;
        if (!isLimit && isNum)
            memo[i][pre] = res;
        return res;
    }

    private boolean valid(String s) {
        for (int i = 1; i < s.length(); i++)
            if (Math.abs((int) s.charAt(i) - (int) s.charAt(i - 1)) != 1)
                return false;
        return true;
    }


}
