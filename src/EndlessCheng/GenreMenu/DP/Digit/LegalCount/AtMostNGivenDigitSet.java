package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class AtMostNGivenDigitSet {

    // https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/solutions/1900101/shu-wei-dp-tong-yong-mo-ban-xiang-xi-zhu-e5dg/
    private String[] digits;
    private char s[];
    private int dp[];

    public int atMostNGivenDigitSet(String[] digits, int n) {
        this.digits = digits;
        s = Integer.toString(n).toCharArray();
        dp = new int[s.length];
        Arrays.fill(dp, -1); // dp[i] = -1 表示 i 這個狀態還沒被計算出來
        return f(0, true, false);
    }

    // 定義 f(i,isLimit,isNum) 表示構造從左往右第 i 位及其之後數位的合法方案數，其中：
    // isLimit 表示當前是否受到了 n 的約束。若為真，則第 i 位填入的數字至多為 s[i]，否則至多為 9。
    // 例如 n=234，如果前面填了 23，那麼最後一位至多填 4；如果前面填的不是 23，那麼最後一位至多填 9。
    // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
    // isNum 表示 i 前面的數位是否填了數字。
    // 若為假，則當前位可以跳過（不填數字），或者要填入的數字至少為 1；
    // 若為真，則必須填數字，且要填入的數字從 0 開始。
    // 這樣我們可以控制構造出的是一位數/兩位數/三位數等等。
    // 對於本題而言，要填入的數字可直接從 digits 中選擇。
    private int f(int i, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0; // 如果填了數字，則為 1 種合法方案
        if (!isLimit && isNum && dp[i] >= 0) return dp[i]; // 在不受到任何約束的情況下，返回記錄的結果，避免重復運算
        var res = 0;
        if (!isNum) // 前面不填數字，那麼可以跳過當前數位，也不填數字
            // isLimit 改為 false，因為沒有填數字，位數都比 n 要短，自然不會受到 n 的約束
            // isNum 仍然為 false，因為沒有填任何數字
            res = f(i + 1, false, false);
        var up = isLimit ? s[i] : '9'; // 根據是否受到約束，決定可以填的數字的上限
        // 注意：對於一般的題目而言，如果此時 isNum 為 false，則必須從 1 開始枚舉，由於本題 digits 沒有 0，所以無需處理這種情況
        for (var d : digits) { // 枚舉要填入的數字 d
            if (d.charAt(0) > up) break; // d 超過上限，由於 digits 是有序的，後面的 d 都會超過上限，故退出循環
            // isLimit：如果當前受到 n 的約束，且填的數字等於上限，那麼後面仍然會受到 n 的約束
            // isNum 為 true，因為填了數字
            res += f(i + 1, isLimit && d.charAt(0) == up, true);
        }
        if (!isLimit && isNum) dp[i] = res; // 在不受到任何約束的情況下，記錄結果
        return res;
    }

}
