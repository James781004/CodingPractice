package LeetcodeMaster.DP;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q19_WordBreak {
//    139.單詞拆分
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0139.%E5%8D%95%E8%AF%8D%E6%8B%86%E5%88%86.md
//
//    給定一個非空字符串 s 和一個包含非空單詞的列表 wordDict，判定 s 是否可以被空格拆分為一個或多個在字典中出現的單詞。
//
//    說明：
//
//    拆分時可以重覆使用字典中的單詞。
//
//    你可以假設字典中沒有重覆的單詞。
//
//    示例 1： 輸入: s = "leetcode", wordDict = ["leet", "code"] 輸出: true 解釋: 返回 true 因為 "leetcode" 可以被拆分成 "leet code"。
//
//    示例 2： 輸入: s = "applepenapple", wordDict = ["apple", "pen"] 輸出: true 解釋: 返回 true 因為 "applepenapple" 可以被拆分成 "apple pen apple"。   注意你可以重覆使用字典中的單詞。
//
//    示例 3： 輸入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"] 輸出: false


    public boolean wordBreakDP(String s, List<String> wordDict) {
        boolean[] valid = new boolean[s.length() + 1]; // valid[i]表示以i結尾的substr是有效字符
        valid[0] = true; // 字串為空的時候為true

        for (int i = 1; i <= s.length(); i++) { // 遍歷背包
            for (int j = 0; j < i; j++) { // 遍歷物品
                if (wordDict.contains(s.substring(j, i)) && valid[j]) { // substr(起始位置，截取的個數)
                    valid[i] = true;
                }
            }
        }
        return valid[s.length()];
    }


    // 回溯法+記憶化
    private Set<String> set;
    private int[] memo;

    public boolean wordBreak(String s, List<String> wordDict) {
        memo = new int[s.length()];
        set = new HashSet<>(wordDict);
        return backtracking(s, 0);
    }

    public boolean backtracking(String s, int startIndex) {
        if (startIndex == s.length()) return true;
        if (memo[startIndex] == -1) return false;
        for (int i = startIndex; i < s.length(); i++) {
            String sub = s.substring(startIndex, i + 1);
            if (!set.contains(sub)) continue; // 拆分出來的單詞無法匹配
            boolean res = backtracking(s, i + 1);
            if (res) return true;
        }

        // 關鍵: 找遍了startIndex~s.length()也沒能完全匹配，標記從startIndex開始不能找到
        memo[startIndex] = -1;
        return false;
    }
}
