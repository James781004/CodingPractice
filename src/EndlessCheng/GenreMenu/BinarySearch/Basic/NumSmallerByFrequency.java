package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class NumSmallerByFrequency {

    // https://leetcode.cn/problems/compare-strings-by-frequency-of-the-smallest-character/solutions/2303607/python3javacgotypescript-yi-ti-yi-jie-pa-nu6o/
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int n = words.length;

        // 計算出 f(w)，並將其排序，存放在數組 nums 中
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = f(words[i]);
        }
        Arrays.sort(nums);
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            // 在 nums 中二分查找第一個大於 f(queries[i]) 的位置 l
            int x = f(queries[i]);
            int l = 0, r = n;
            while (l < r) {
                int mid = (l + r) >> 1;
                if (nums[mid] > x) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            // nums 中下標 l 及其後面的元素都滿足 f(q)<f(W)，那麼當前查詢的答案就是 n−l
            ans[i] = n - l;
        }
        return ans;
    }

    // 返回字符串 s 中按字典序比較最小字母的出現頻次
    private int f(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            ++cnt[s.charAt(i) - 'a'];
        }
        for (int x : cnt) {
            if (x > 0) {
                return x;
            }
        }
        return 0;
    }


}
