package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class EqualSubstring {

    // https://leetcode.cn/problems/get-equal-substrings-within-budget/solutions/2919955/shuang-zhi-zhen-bu-ding-chang-hua-dong-c-9xdl/
    // 由開銷限制，那麼我們只需要先找出每個字符的開銷，組成開銷數組。
    // 題目就變成了 最長的區間內總的開銷要小於最大開銷 計算最大的區間長度
    // 不定長滑動窗口模版套用
    public int equalSubstring(String s, String t, int maxCost) {
        int len = s.length();
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        // 轉換的開銷數組
        int[] costArr = new int[len];
        for (int i = 0; i < len; i++)
            costArr[i] = Math.abs(sArr[i] - tArr[i]);
        int left = 0;
        // 當前開銷和
        int curCostSum = 0;
        // 最大子串長度
        int max = 0;
        for (int right = 0; right < len; right++) {
            // 消費開銷
            curCostSum += costArr[right];
            // 開銷花費超標了
            while (curCostSum > maxCost) {
                // 去除左側開銷直到開銷符合條件
                curCostSum -= costArr[left];
                left++;
            }
            // 記錄每次花費時的長度
            max = Math.max(max, right - left + 1);
        }
        return max;
    }


}
