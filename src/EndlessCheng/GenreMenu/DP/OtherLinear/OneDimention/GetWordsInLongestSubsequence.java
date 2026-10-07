package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.ArrayList;
import java.util.List;

public class GetWordsInLongestSubsequence {

    // https://leetcode.cn/problems/longest-unequal-adjacent-groups-subsequence-ii/solutions/2482844/zi-xu-lie-dp-de-si-kao-tao-lu-pythonjava-kmaf/
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        int[] f = new int[n]; // 定義 f[i] 表示在子序列第一個字符串是 words[i] 的前提下，從後綴 [i,n−1] 中能選出的最長子序列的長度
        int[] from = new int[n]; // 記錄最優轉移來源
        int maxI = n - 1; // maxI 是 max(f) 在 f 中的下標，即 f[maxI]=max(f)
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                // 提前比較 f[j] 與 f[i] 的大小，如果 f[j] <= f[i]，就不用執行更耗時的 check 了
                if (f[j] > f[i] && groups[j] != groups[i] && check(words[i], words[j])) {
                    f[i] = f[j];
                    from[i] = j;
                }
            }
            f[i]++; // 加一寫在這裡，表示在 f[j] 子序列的前面加上 words[i]
            if (f[i] > f[maxI]) {
                maxI = i;
            }
        }

        int i = maxI;
        int m = f[i];
        List<String> ans = new ArrayList<>(m); // 預分配空間
        for (int k = 0; k < m; k++) {
            ans.add(words[i]);
            i = from[i];
        }
        return ans;
    }

    private boolean check(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (diff) { // 漢明距離(對應位置字符不同的數目)大於 1
                    return false;
                }
                diff = true;
            }
        }
        return diff;
    }


}
