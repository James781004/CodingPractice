package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountSpecialNumbers {

    // https://leetcode.cn/problems/count-special-integers/solutions/1746956/shu-wei-dp-mo-ban-by-endlesscheng-xtgx/
    public int countSpecialNumbers(int n) {
        char[] s = Integer.toString(n).toCharArray();
        int[][] memo = new int[s.length][1 << 10];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        // 遞歸入口：dfs(0,0,true,false)，表示：
        // 從 s[0] 開始枚舉；
        // 一開始集合中沒有數字（空集）；
        // 一開始要受到 n 的約束（否則就可以隨意填了，這肯定不行）；
        // 一開始沒有填數字。
        return dfs(0, 0, true, false, s, memo);
    }

    // dfs(i,mask,isLimit,isNum) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // mask 表示前面選過的數字集合，換句話說，第 i 位要選的數字不能在 mask 中。
    // isLimit 表示當前是否受到了 n 的約束（注意要構造的數字不能超過 n）。
    // 若為真，則第 i 位填入的數字至多為 s[i]，否則可以是 9。
    // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
    // 例如 n=123，如果 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // 如果 i=0 填的是 1，i=1 填的是 2，那麼 i=2 的這一位至多填 3。
    // isNum 表示 i 前面的數位是否填了數字。
    // 若為假，則當前位可以跳過（不填數字），或者要填入的數字至少為 1；
    // 若為真，則要填入的數字可以從 0 開始。
    // 例如 n=123，在 i=0 時跳過的話，相當於後面要構造的是一個 99 以內的數字了，
    // 如果 i=1 不跳過，那麼相當於構造一個 10 到 99 的兩位數，
    // 如果 i=1 跳過，相當於構造的是一個 9 以內的數字。
    // 為什麼要定義 isNum？因為 010 和 10 都是 10，如果認為第一個 0 和第三個 0 都是我們填入的數字，
    // 這就不符合題目要求了，但 10 顯然是符合題目要求的。
    private int dfs(int i, int mask, boolean isLimit, boolean isNum, char[] s, int[][] memo) {
        if (i == s.length) {
            return isNum ? 1 : 0; // isNum 為 true 表示得到了一個合法數字
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask]; // 之前計算過
        }
        int res = 0;
        if (!isNum) { // 可以跳過當前數位
            res = dfs(i + 1, mask, false, false, s, memo);
        }
        // 如果前面填的數字都和 n 的一樣，那麼這一位至多填數字 s[i]（否則就超過 n 啦）
        int up = isLimit ? s[i] - '0' : 9;
        // 枚舉要填入的數字 d
        // 如果前面沒有填數字，則必須從 1 開始（因為不能有前導零）
        for (int d = isNum ? 0 : 1; d <= up; d++) {
            if ((mask >> d & 1) == 0) { // d 不在 mask 中，說明之前沒有填過 d
                res += dfs(i + 1, mask | (1 << d), isLimit && d == up, true, s, memo);
            }
        }
        if (!isLimit && isNum) {
            memo[i][mask] = res; // 記憶化
        }
        return res;
    }


}
