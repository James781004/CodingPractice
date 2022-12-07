package FuckingAlgorithm.Extra;

import java.util.ArrayList;

public class Q11_SubsequenceMatching {
    //    https://leetcode.cn/problems/is-subsequence/
    public boolean isSubsequence(String s, String t) {
        int m = s.length(), n = t.length();
        // 對 t 進行預處理
        ArrayList<Integer>[] index = new ArrayList[256];
        for (int i = 0; i < n; i++) {
            char c = t.charAt(i);
            if (index[c] == null)
                index[c] = new ArrayList<>();
            index[c].add(i);
        }

        // 串 t 上的指針
        int j = 0;
        // 借助 index 查找 s[i]
        for (int i = 0; i < m; i++) {
            char c = s.charAt(i);
            // 整個 t 壓根兒沒有字符 c
            if (index[c] == null) return false;
            int pos = left_bound(index[c], j);
            // 二分搜索區間中沒有找到字符 c
            if (pos == -1) return false;
            // 向前移動指針 j
            j = index[c].get(pos) + 1;
        }
        return true;
    }

    // 查找左側邊界的二分查找
    int left_bound(ArrayList<Integer> arr, int target) {
        int left = 0, right = arr.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (target > arr.get(mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (left == arr.size()) {
            return -1;
        }
        return left;
    }


//    https://leetcode.cn/problems/number-of-matching-subsequences/

    public int numMatchingSubseq(String s, String[] words) {
        // 對 s 進行預處理
        // char -> 該 char 的索引列表
        ArrayList<Integer>[] index = new ArrayList[256];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (index[c] == null) {
                index[c] = new ArrayList<>();
            }
            index[c].add(i);
        }

        int res = 0;
        for (String word : words) {
            // 字符串 word 上的指針
            int i = 0;
            // 串 s 上的指針
            int j = 0;
            // 借助 index 查找 word 中每個字符的索引
            for (; i < word.length(); i++) {
                char c = word.charAt(i);
                // 整個 s 壓根兒沒有字符 c
                if (index[c] == null) {
                    break;
                }
                int pos = left_bound(index[c], j);
                // 二分搜索區間中沒有找到字符 c
                if (pos == -1) {
                    break;
                }
                // 向前移動指針 j
                j = index[c].get(pos) + 1;
            }
            // 如果 word 完成匹配，則是子序列
            if (i == word.length()) {
                res++;
            }
        }

        return res;
    }
}
