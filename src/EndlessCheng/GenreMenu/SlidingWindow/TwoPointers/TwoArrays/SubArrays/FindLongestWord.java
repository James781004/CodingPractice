package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

import java.util.List;

public class FindLongestWord {

    // https://leetcode.cn/problems/longest-word-in-dictionary-through-deleting/solutions/996112/suan-fa-xiao-ai-shuang-zhi-zhen-xiang-ji-6rey/
    public String findLongestWord(String s, List<String> dictionary) {
        var arr = s.toCharArray();

        // 第一優先級長度，越長的越靠前；第二優先級字典序，越小的越靠前
        dictionary.sort((a, b) -> {
            if (a.length() != b.length()) {
                return Integer.compare(b.length(), a.length());
            }
            return a.compareTo(b);
        });

        for (var str : dictionary) {
            if (isSubsequence(arr, str.toCharArray())) {
                return str;
            }
        }

        // 未找到，返回空字符串
        return "";
    }

    private boolean isSubsequence(char[] s, char[] t) {
        // 判斷 t 是否為 s 的子序列
        int n = s.length, m = t.length;

        // 如果 t 長度大於 s，一定不是子序列
        if (m > n) return false;

        // 記錄當前 s 中匹配到了哪個位置
        int i = 0;

        for (char ch : t) {
            while (i < n && s[i] != ch) i++;
            if (i >= n) return false;

            // 此時 s[i] = ch，下次要從 s[i + 1] 開始匹配
            i++;
        }

        return true;
    }


}
