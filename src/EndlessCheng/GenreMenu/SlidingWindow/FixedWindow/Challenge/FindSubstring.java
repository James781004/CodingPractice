package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindSubstring {

    // https://leetcode.cn/problems/substring-with-concatenation-of-all-words/solutions/2345526/javapythondan-ci-hua-de-hua-dong-chuang-h93z1/
    public List<Integer> findSubstring(String s, String[] words) {
        int ls = s.length();            // 字符串s的長度
        int m = words.length;           // 總單詞數量
        int n = words[0].length();     // 單個單詞長度
        List<Integer> res = new ArrayList<>();  // 結果數組
        if (ls < m * n) {
            return res;     // 字符串s的長度小於所有單詞拼接起來的長度，直接返回
        }
        // 枚舉每一個切分單詞的起點，共有[0, n-1]個起點
        for (int i = 0; i < n; i++) {
            Map<String, Integer> diff = new HashMap<>();    // 記錄滑動窗口中每個單詞和words中對應單詞的個數差值，diff為空，說明滑動窗口中的單詞與word一致
            String w;   // 子串
            // 從起點i開始，將前m個子串單詞加入哈希表，前m個單詞就是首個滑動窗口裡的單詞; j表示第幾個單詞
            for (int j = 0; j < m && i + (j + 1) * n <= ls; j++) {
                w = s.substring(i + j * n, i + (j + 1) * n);
                diff.put(w, diff.getOrDefault(w, 0) + 1);
            }
            // 遍歷words，進行做差
            for (String word : words) {
                diff.put(word, diff.getOrDefault(word, 0) - 1);
                if (diff.get(word) == 0) {
                    diff.remove(word);      // 單詞數目為0，說明這個單詞在滑動窗口和words的數目匹配，直接移除；
                }
            }
            // 移動這個長度固定為m*n的滑動窗口，左邊界left為每個單詞的起點，滑動窗口范圍[left, left + m * n)
            for (int left = i; left <= ls - m * n; left += n) {
                // 從第二個單詞開始，開始要加入新單詞，移除舊單詞
                if (left > i) {
                    w = s.substring(left - n, left);    // 當前left的前一個單詞是要移除的單詞
                    diff.put(w, diff.getOrDefault(w, 0) - 1);   // 滑動窗口中移除一個單詞，相當於差值-1
                    if (diff.get(w) == 0) {
                        diff.remove(w);
                    }
                    w = s.substring(left + (m - 1) * n, left + m * n);  // 右邊界right = left + (m - 1) * n，為要加入滑動窗口的單詞的起點
                    diff.put(w, diff.getOrDefault(w, 0) + 1);   // 滑動窗口中加入一個單詞，相當於差值+1
                    if (diff.get(w) == 0) {
                        diff.remove(w);
                    }
                }
                // diff為空，說明滑動窗口中的單詞與word一致；left即為子串起點
                if (diff.isEmpty()) {
                    res.add(left);
                }
            }
        }
        return res;
    }


}
