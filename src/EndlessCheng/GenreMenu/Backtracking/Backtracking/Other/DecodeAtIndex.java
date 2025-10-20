package EndlessCheng.GenreMenu.Backtracking.Backtracking.Other;

public class DecodeAtIndex {

    // https://leetcode.cn/problems/decoded-string-at-index/solutions/342943/java-di-gui-shuang-bai-by-seaway-john/
    public String decodeAtIndex(String S, int K) {
        long n = 0; // 用 long 類型來存放解碼後字串的長度，因為長度可能非常大

        // 第一步：計算解碼後的總長度
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (Character.isDigit(c)) {
                // 如果是數字，總長度會乘以這個數字
                n *= (c - '0');
            } else {
                // 如果是字母，總長度加 1
                n++;
            }
        }

        // 第二步：從後往前找第 K 個字元
        for (int i = S.length() - 1; i >= 0; i--) {
            char c = S.charAt(i);

            // K = K % n 的用意是，如果 K 超過當前解碼字串的長度，
            // 只需要找到 K 在這個重複區塊中的相對位置
            K %= n;

            // 檢查 K 是否已經為 0。當 K=0 時，表示我們找到的字元是區塊的最後一個字元。
            // 因為題目給的是1-based index (從1開始)，而我們的 K 在循環中會變成 0-based index，
            // 所以 K=0 的情況就相當於找到了目標。
            if (K == 0 && Character.isLetter(c)) {
                return String.valueOf(c);
            }

            // 這是關鍵邏輯：反向追溯
            if (Character.isDigit(c)) {
                // 如果當前字元是數字，那它就是一個重複區塊的結尾。
                // 需要反向計算出這個數字影響之前的字串的長度
                n /= (c - '0');
            } else {
                // 如果當前字元是字母，那它就是一個獨立的字元，長度減 1
                n--;
            }
        }

        return ""; // 如果沒有找到（雖然題目保證一定會找到）
    }


}
