package Grind.Ch14_Trie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q02_WordBreak {
    // https://leetcode.cn/problems/word-break/solutions/302729/dan-ci-chai-fen-ju-jue-zhuang-xcong-jian-dan-de-xi/
    // 動態規劃算法，dp[i]表示s前i個字符能否拆分
    // 轉移方程：dp[j] = dp[i] && check(s[i+1, j]);
    // check(s[i+1, j])就是判斷i+1到j這一段字符是否能夠拆分
    // 其實，調整遍歷順序，這等價於s[i+1, j]是否是wordDict中的元素
    // 這個舉個例子就很容易理解。
    // 假如wordDict=["apple", "pen", "code"],s = "applepencode";
    // dp[8] = dp[5] + check("pen")
    // 翻譯一下：前八位能否拆分取決於前五位能否拆分，加上五到八位是否屬於字典
    // （注意：i的順序是從j-1 -> 0
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        for (String str : wordDict) set.add(str);
        boolean[] dp = new boolean[s.length() + 1]; //前n個字符能否拆分 s = "leetcode", wordDict = ["leet", "code"]
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i; j >= 0; j--) {
                dp[i] = dp[j] && set.contains(s.substring(j, i));
                if (dp[i]) break;
            }
        }
        return dp[s.length()];
    }


    // 回溯法+記憶化
    // https://leetcode.cn/problems/word-break/solutions/850456/dai-ma-sui-xiang-lu-139-dan-ci-chai-fen-50a1a/
    private Set<String> set;
    private int[] memo;

    public boolean wordBreakDFS(String s, List<String> wordDict) {
        memo = new int[s.length()];
        set = new HashSet<>(wordDict);
        return backtracking(s, 0);
    }

    public boolean backtracking(String s, int startIndex) {
        if (startIndex == s.length()) {
            return true;
        }
        if (memo[startIndex] == -1) {
            return false;
        }

        for (int i = startIndex; i < s.length(); i++) {
            String sub = s.substring(startIndex, i + 1);
            // 拆分出來的單詞無法匹配
            if (!set.contains(sub)) {
                continue;
            }
            boolean res = backtracking(s, i + 1);
            if (res) return true;
        }
        // 這裡是關鍵，找遍了startIndex~s.length()也沒能完全匹配，標記從startIndex開始不能找到
        memo[startIndex] = -1;
        return false;
    }
}
