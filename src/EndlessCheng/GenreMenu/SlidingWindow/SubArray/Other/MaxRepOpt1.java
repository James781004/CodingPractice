package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Other;

public class MaxRepOpt1 {

    // https://leetcode.cn/problems/swap-for-longest-repeated-character-substring/solutions/2294889/javapython3hua-dong-chuang-kou-fen-qing-l7u39/
    public int maxRepOpt1(String text) {
        int[] count = new int[26];          // 統計每個字符的個數的數組
        int n = text.length();              // 字符串長度

        // 統計每個字符的個數
        for (int i = 0; i < n; i++) {
            count[text.charAt(i) - 'a']++;
        }
        
        int i = 0;  // 滑動窗口左邊界，指向當前搜索的重復單字符子串的首個字符
        int j = 0;  // 滑動窗口右邊界，指向當前搜索的重復單字符子串的最後一個字符的下一位
        int k;      // 搜索最近的相同字符重復子串
        int res = 0;    // 記錄結果
        while (i < n) {
            // 搜索當前字符text[i]的重復子串
            while (j < n && text.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (count[text.charAt(i) - 'a'] == j - i) {
                res = Math.max(j - i, res);     // 沒有可交換的字符，直接更新res
            } else {
                res = Math.max(j - i + 1, res); // 交換一個字符，原長度 + 1
                k = j + 1;  // 從j之後搜索間隔一個字符的相同字符重復子串
                while (k < n && text.charAt(k) == text.charAt(i)) {
                    k++;
                }
                // 比較拼接後的重復子串長度和字符個數，取最小值；然後再更新res
                res = Math.max(res, Math.min(k - i, count[text.charAt(i) - 'a']));
            }
            i = j;  // 更新滑動窗口起點，搜索下一個字符的重復子串
        }
        return res;
    }


}
